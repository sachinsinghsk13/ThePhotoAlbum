"use strict";
/**
 * MyPhotoAlbum specific Photo Entity Class
 * This is used to upload the photo to the server
 * @author Sachin Singh
 * @version 1.0
 */
class Photo {
    /**
     * @param file File Object of HTML File Input Element
     * @param onready Function that gets imediately invoked after the initialization
     */
    constructor(file, onready) {
        this.title = "";
        this.filename = "";
        this.size = 0;
        this.description = "";
        this.height = 0;
        this.width = 0;
        if (!file) {
            throw new Error("No Blob or Corrupted Blob");
        }
        else if (!(file instanceof File)) {
            throw new Error("Must Require A File Type to Construct Photo");
        }
        this.onready = onready;
        this.file = file;
        this.initialize();
    }
    /**
     * Starts Initializing This Photo Object. onready function is fired when initialization completes
     */
    initialize() {
        this.title = this.file.name;
        this.filename = this.file.name;
        this.size = this.file.size / 1024;
        this.description = "";
        let fileReader = new FileReader();
        fileReader.onload = (progress) => {
            let image = new Image();
            image.onload = (event) => {
                this.width = image.naturalWidth;
                this.height = image.naturalHeight;
                this.htmlTag = image;
                this.quality = this.width > 1000 && this.height > 1000 ? "HD" : "SD";
                if (this.width == this.height)
                    this.orientation = "SQUARE";
                else
                    this.orientation =
                        this.width > this.height ? "LANDSCAPE" : "PORTRAIT";
                if (this.onready)
                    this.onready(this);
            };
            image.src = fileReader.result; // convert data to url string
        };
        fileReader.readAsDataURL(this.file);
    }
    getQualityTag() {
        return this.width > 1000 && this.height > 1000 ? "HD" : "SD";
    }
    getPhotoResolutionHTMLTag() {
        var tag = this.getQualityTag();
        var badge = tag == "HD" ? "danger" : "info";
        return `${this.width}x${this.height} <span class="badge badge-${badge}">${tag}</span>`;
    }
    getSizeHTMLTag() {
        let badgeText = this.size > 1024 ? "MB" : "KB";
        let badgeSize = this.size > 1024 ? (this.size / 1024).toFixed(2) : this.size.toFixed(2);
        return `${badgeSize} <span class="badge badge-primary">${badgeText}</span>`;
    }
    getImageOrientation() {
        return this.orientation;
    }
}
class PhotoPreviewManager {
    constructor(fileInputId, previewContainerid, maxFileUpload) {
        this.totalPhotos = 0;
        this.previewContainer = $(`#${previewContainerid}`);
        this.fileInput = $(`#${fileInputId}`);
        this.photos = new Array();
        this.maxFileUpload = maxFileUpload;
        this.uploadButton = $(`#upload-button`);
        this.uploadButton.on('click', this.startUpload.bind(this));
        this.fileInput.on("change", event => {
            this.showAnimation();
            let files = event.target.files;
            if (files == null)
                return;
            else if (files.length > this.maxFileUpload) {
                alert(`Only ${this.maxFileUpload} is allowed for upload at once.`);
                return;
            }
            else {
                this.totalPhotos = files.length;
                this.photos = new Array();
                for (let i = 0; i < this.totalPhotos; i++) {
                    let file = files.item(i);
                    let photo = new Photo(file, _photo => {
                        this.photos.push(_photo);
                        if (this.photos.length == this.totalPhotos)
                            this.showPreview();
                    });
                }
            }
        });
    }
    startUpload() {
        if (this.totalPhotos < 1) {
            alert('Please Select Atleast One Photo To Start Upload.');
            return;
        }
        // disable image preview section
        this.previewContainer.css({
            "pointer-events": "none",
            "opacity": "0.4"
        });
        // Prepare the payload
        let formData = new FormData();
        this.photos.forEach((photo) => {
            formData.append(photo.filename, photo.file);
        });
        formData.append('details', JSON.stringify(this.photos));
        formData.forEach((value, key) => {
            console.log(key, ' : ', value);
        });
        // make the ajax call
        $.ajax({
            beforeSend: () => {
                $('#progressbar-div').removeClass("d-none");
            },
            xhr: () => {
                let xhr = new XMLHttpRequest();
                xhr.onprogress = (evt) => {
                    if (evt.lengthComputable) {
                        let percent = ((evt.loaded / evt.total) * 100).toFixed(0);
                        $('#progressbar-div .progressbar').css('width', percent);
                    }
                };
                return xhr;
            },
            method: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            url: '/ThePhotoAlbum/App/PhotoUpload',
        }).then(() => {
            $('#progressbar-div').addClass('d-none');
            window.location.reload();
        });
    }
    showPreview() {
        this.previewContainer.empty();
        this.previewContainer.append(this.paginationGenerator());
    }
    paginationGenerator() {
        let ul = $("<ul>")
            .addClass("pagination")
            .addClass("justify-content-center");
        for (let i = 1; i <= this.totalPhotos; i++) {
            let btn = $("<button>")
                .attr("data-pagination-id", i)
                .addClass("page-link")
                .text(i)
                .on("click", () => {
                this.previewContainer
                    .find("ul > li")
                    .removeClass("active")
                    .eq(i - 1)
                    .addClass("active");
                this.imagePreviewGenerator(i - 1);
            });
            let li = $("<li>")
                .addClass("page-item")
                .append(btn);
            if (i == 1)
                li.addClass("active");
            ul.append(li);
        }
        $.get("/ThePhotoAlbum/static/html/img-preview.html", page => {
            this.previewContainer.prepend($("<div>")
                .attr("id", "inner-img-container")
                .append(page));
            this.imagePreviewGenerator(0);
        });
        return $("<div>")
            .addClass("row")
            .addClass("m-3")
            .append($("<div>")
            .addClass("col")
            .append(ul));
    }
    imagePreviewGenerator(index) {
        let innerContainer = this.previewContainer.find("#inner-img-container");
        let photo = this.photos[index];
        innerContainer
            .find("#preview-img")
            .empty()
            .append($(photo.htmlTag).addClass("img-fluid"));
        innerContainer.find("#serial_no").text(`#${index + 1}`);
        innerContainer
            .find("#title_input")
            .val(photo.title)
            .off()
            .on("blur", evt => {
            photo.title = $(evt.target).val();
            console.log(photo.title);
        });
        innerContainer
            .find("#description_input")
            .val(photo.description)
            .off()
            .on("blur", evt => {
            photo.description = $(evt.target).val();
            console.log(photo.description);
        });
        innerContainer.find("#media_type").text(photo.file.type);
        innerContainer.find("#file_size").html(photo.getSizeHTMLTag());
        innerContainer.find("#resolution").html(photo.getPhotoResolutionHTMLTag());
        innerContainer
            .find("#orientation")
            .text(photo.getImageOrientation());
    }
    showAnimation() {
        this.previewContainer.empty();
        let animation = $("<div>")
            .addClass("text-center")
            .append($("<div>")
            .addClass("spinner-grow")
            .addClass("text-primary"))
            .append($("<div>")
            .addClass("spinner-grow")
            .addClass("text-success"))
            .append($("<div>")
            .addClass("spinner-grow")
            .addClass("text-danger"));
        this.previewContainer.append(animation);
    }
}
$(() => {
    let preview = new PhotoPreviewManager("photo_upload_input", "image-preview-container", 5);
});
