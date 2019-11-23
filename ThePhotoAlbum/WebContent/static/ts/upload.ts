/**
 * MyPhotoAlbum specific Photo Entity Class
 * This is used to upload the photo to the server
 * @author Sachin Singh
 * @version 1.0
 */
class Photo {
  file: File;
  onready: ((photo: Photo) => void | undefined) | undefined | null;
  title: string = "";
  filename: string = "";
  size: number = 0;
  description: string = "";
  height: number = 0;
  width: number = 0;
  quality: "HD" | "SD" | undefined;
  orientation: "LANDSCAPE" | "PORTRAIT" | "SQUARE" | undefined;
  htmlTag!: HTMLImageElement;
  /**
   * @param file File Object of HTML File Input Element
   * @param onready Function that gets imediately invoked after the initialization
   */
  constructor(file: File | null, onready: ((photo: Photo) => void) | null) {
    if (!file) {
      throw new Error("No Blob or Corrupted Blob");
    } else if (!(file instanceof File)) {
      throw new Error("Must Require A File Type to Construct Photo");
    }
    this.onready = onready;
    this.file = file;
    this.initialize();
  }
  /**
   * Starts Initializing This Photo Object. onready function is fired when initialization completes
   */
  private initialize(): void {
    this.title = this.file.name.substr(0, 30);
    this.filename = this.file.name;
    this.size = this.file.size / 1024;
    this.description = "";
    let fileReader = new FileReader();
    fileReader.onload = (progress: ProgressEvent<FileReader>) => {
      let image = new Image();
      image.onload = (event: Event) => {
        this.width = image.naturalWidth;
        this.height = image.naturalHeight;
        this.htmlTag = image;
        this.quality = this.width > 1000 && this.height > 1000 ? "HD" : "SD";
        if (this.width == this.height) this.orientation = "SQUARE";
        else
          this.orientation =
            this.width > this.height ? "LANDSCAPE" : "PORTRAIT";

        if (this.onready) this.onready(this);
      };
      image.src = <string>fileReader.result; // convert data to url string
    };
    fileReader.readAsDataURL(this.file);
  }
  getQualityTag(): undefined | string {
    return this.width > 1000 && this.height > 1000 ? "HD" : "SD";
  }

  getPhotoResolutionHTMLTag() {
    var tag = this.getQualityTag();
    var badge = tag == "HD" ? "danger" : "info";
    return `${this.width}x${this.height} <span class="badge badge-${badge}">${tag}</span>`;
  }

  getSizeHTMLTag() {
    let badgeText = this.size > 1024 ? "MB" : "KB";
    let badgeSize =
      this.size > 1024 ? (this.size / 1024).toFixed(2) : this.size.toFixed(2);
    return `${badgeSize} <span class="badge badge-primary">${badgeText}</span>`;
  }

  getImageOrientation() {
    return this.orientation;
  }
}

class PhotoModel {
  filename: string;
  title: string;
  description: string;
  size: number;
  width: number;
  height: number;
  orientation: string | undefined;
  quality: string | undefined;

  constructor(
    filename: string,
    title: string,
    description: string,
    size: number,
    width: number,
    height: number,
    orientation: "LANDSCAPE" | "PORTRAIT" | "SQUARE" | undefined,
    quality: "HD" | "SD" | undefined
  ) {
    this.filename = filename;
    this.title = title;
    this.description = description;
    this.size = size;
    this.width = width;
    this.height = height;
    this.quality = quality;
    this.orientation = orientation;
  }
}

class PhotoPreviewManager {
  private photos: Photo[];
  private previewContainer: JQuery<HTMLElement>;
  private fileInput: JQuery<HTMLInputElement>;
  private uploadButton: JQuery<HTMLElement>;
  private targetAlbumField: JQuery<HTMLElement>;
  private maxFileUpload: number;
  private totalPhotos: number = 0;
  constructor(
    fileInputId: string,
    previewContainerid: string,
    maxFileUpload: number
  ) {
    this.previewContainer = $(`#${previewContainerid}`);
    this.fileInput = $(`#${fileInputId}`);
    this.photos = new Array();
    this.maxFileUpload = maxFileUpload;
    this.uploadButton = $(`#upload-button`);
    this.targetAlbumField = $("#target_album");
    this.uploadButton.on("click", this.startUpload.bind(this));
    this.fileInput.on("change", event => {
      this.showAnimation();
      let files = event.target.files;
      if (files == null) return;
      else if (files.length > this.maxFileUpload) {
        alert(`Only ${this.maxFileUpload} is allowed for upload at once.`);
        return;
      } else {
        this.totalPhotos = files.length;
        this.photos = new Array();
        for (let i = 0; i < this.totalPhotos; i++) {
          let file = files.item(i);
          let photo = new Photo(file, _photo => {
            this.photos.push(_photo);
            if (this.photos.length == this.totalPhotos) this.showPreview();
          });
        }
      }
    });
  }
  startUpload() {
    if (this.totalPhotos < 1) {
      alert("Please Select Atleast One Photo To Start Upload.");
      return;
    }
    // disable image preview section

    this.previewContainer.css({
      "pointer-events": "none",
      opacity: "0.4"
    });

    // Prepare the payload
    let formData = new FormData();

    // Insert Binary Large Objects
    this.photos.forEach(photo => {
      formData.append(photo.filename, photo.file);
    });

    // Insert target AlbumId
    let albumId = <string>this.targetAlbumField.val();
    formData.append("albumId", albumId);

    // Create PhotoModel from Photos
    let photoModelPayload: PhotoModel[] = [];
    this.photos.forEach(photo => {
      let pm = new PhotoModel(
        photo.filename,
        photo.title,
        photo.description,
        photo.size,
        photo.width,
        photo.height,
        photo.orientation,
        photo.quality
      );
      photoModelPayload.push(pm);
    });

    // Insert Payload Details
    formData.append("payload_info", JSON.stringify(photoModelPayload));

    // make the ajax call

    $.ajax({
      beforeSend: () => {
        $("#progressbar-div").removeClass("d-none");
      },
      xhr: () => {
        let xhr = new XMLHttpRequest();
        if (xhr.upload) {
          xhr.upload.addEventListener("progress", evt => {
            if (evt.lengthComputable) {
              let percent = ((evt.loaded / evt.total) * 100).toFixed(0);
              $("#progressbar-div .progress-bar").css("width", percent+"%");
            }
          });
        }
        return xhr;
      },
      method: "POST",
      data: formData,
      contentType: false,
      processData: false,
      url: "/ThePhotoAlbum/App/PhotoUpload"
    }).then(() => {
      $("#progressbar-div").addClass("d-none");
      this.previewContainer.css({"opacity":1,"pointer-events":"auto"});
      this.previewContainer.empty().append($("<p>").addClass("text-muted").addClass("text-center").addClass("h6").text("Select Images to See Preview"));
    });
  }
  private showPreview() {
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
      if (i == 1) li.addClass("active");
      ul.append(li);
    }

    $.get("/ThePhotoAlbum/static/html/img-preview.html", page => {
      this.previewContainer.prepend(
        $("<div>")
          .attr("id", "inner-img-container")
          .append(page)
      );
      this.imagePreviewGenerator(0);
    });

    return $("<div>")
      .addClass("row")
      .addClass("m-3")
      .append(
        $("<div>")
          .addClass("col")
          .append(ul)
      );
  }
  imagePreviewGenerator(index: number) {
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
        photo.title = <string>$(evt.target).val();
        console.log(photo.title);
      });
    innerContainer
      .find("#description_input")
      .val(photo.description)
      .off()
      .on("blur", evt => {
        photo.description = <string>$(evt.target).val();
        console.log(photo.description);
      });
    innerContainer.find("#media_type").text(photo.file.type);
    innerContainer.find("#file_size").html(photo.getSizeHTMLTag());
    innerContainer.find("#resolution").html(photo.getPhotoResolutionHTMLTag());
    innerContainer
      .find("#orientation")
      .text(<string>photo.getImageOrientation());
  }

  showAnimation() {
    this.previewContainer.empty();
    let animation = $("<div>")
      .addClass("text-center")
      .append(
        $("<div>")
          .addClass("spinner-grow")
          .addClass("text-primary")
      )
      .append(
        $("<div>")
          .addClass("spinner-grow")
          .addClass("text-success")
      )
      .append(
        $("<div>")
          .addClass("spinner-grow")
          .addClass("text-danger")
      );

    this.previewContainer.append(animation);
  }
}
class AlbumManager {
  private albumTitleField: JQuery<HTMLElement>;
  private albumDescriptionField: JQuery<HTMLElement>;
  private albumCreateButton: JQuery<HTMLElement>;
  private targetAlbumSelectField: JQuery<HTMLElement>;
  constructor() {
    this.albumTitleField = $(`#album_title`);
    this.albumDescriptionField = $(`#album_description`);
    this.albumCreateButton = $(`#album_create_btn`);
    this.targetAlbumSelectField = $(`#target_album`);
    // load the user albums
    this.loadUserAlbums();
    this.albumCreateButton.on("click", evt => {
      let title = <string>this.albumTitleField.val();
      let description = <string>this.albumDescriptionField.val();

      if (!title) {
        alert("Album Title Can't be Empty");
        return;
      }

      if (!description) description = "";

      $.post("/ThePhotoAlbum/App/CreateAlbum", {
        title: title,
        description: description
      }).done(() => {
        this.loadUserAlbums();
        $("#album_alerts").append(
          $("<div>")
            .addClass("alert")
            .addClass("alert-info")
            .addClass("alert-dismissible")
            .html("Album Created  <strong> " + title + "</strong>")
            .fadeOut(4000)
        );
        this.albumTitleField.val("");
        this.albumDescriptionField.val("");
      });
    });
  }

  loadUserAlbums() {
    $.get("/ThePhotoAlbum/App/AlbumList", data => {
      let list = JSON.parse(data);
      this.targetAlbumSelectField.empty();
      list.forEach((item: any) => {
        this.targetAlbumSelectField.append(
          $("<option>", { value: item.id, text: item.title })
        );
      });
    });
  }
}

$(() => {
  let preview = new PhotoPreviewManager(
    "photo_upload_input",
    "image-preview-container",
    5
  );
  let albumManager = new AlbumManager();
});
