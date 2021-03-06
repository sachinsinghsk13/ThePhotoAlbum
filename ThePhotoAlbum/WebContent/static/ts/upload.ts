/**
 * MyPhotoAlbum specific Photo Entity Class
 * This is used to upload the photo to the server
 * @author Sachin Singh
 * @version 1.0
 */
class Photo {
    file: File;
    onready: ( ( photo: Photo ) => void | undefined ) | undefined | null;
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
    constructor( file: File | null, onready: ( ( photo: Photo ) => void ) | null ) {
        if ( !file ) {
            throw new Error( "No Blob or Corrupted Blob" );
        } else if ( !( file instanceof File ) ) {
            throw new Error( "Must Require A File Type to Construct Photo" );
        }
        this.onready = onready;
        this.file = file;
        this.initialize();
    }
    /**
     * Starts Initializing This Photo Object. onready function is fired when initialization completes
     */
    private initialize(): void {
        let str = this.file.name.substr( 0, 30 );
        this.title = str.substr( 0, str.lastIndexOf( "." ) );
        this.filename = this.file.name;
        this.size = this.file.size / 1024;
        this.description = "";
        let fileReader = new FileReader();
        fileReader.onload = ( progress: ProgressEvent<FileReader> ) => {
            let image = new Image();
            image.onload = ( event: Event ) => {
                this.width = image.naturalWidth;
                this.height = image.naturalHeight;
                this.htmlTag = image;
                this.quality = this.width > 1000 && this.height > 1000 ? "HD" : "SD";
                if ( this.width == this.height ) this.orientation = "SQUARE";
                else
                    this.orientation =
                        this.width > this.height ? "LANDSCAPE" : "PORTRAIT";

                if ( this.onready ) this.onready( this );
            };
            image.src = <string>fileReader.result; // convert data to url string
        };
        fileReader.readAsDataURL( this.file );
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
            this.size > 1024 ? ( this.size / 1024 ).toFixed( 2 ) : this.size.toFixed( 2 );
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
        this.previewContainer = $( `#${previewContainerid}` );
        this.fileInput = $( `#${fileInputId}` );
        this.photos = new Array();
        this.maxFileUpload = maxFileUpload;
        this.uploadButton = $( `#upload-button` );
        this.targetAlbumField = $( "#target_album" );
        this.uploadButton.on( "click", this.startUpload.bind( this ) );
        this.fileInput.on( "change", event => {
            this.showAnimation();
            let files = event.target.files;
            if ( files == null || files.length == 0)  {
                this.showSelectPhotos();
                return;
            }
            else if ( files.length > this.maxFileUpload ) {
                alert( `Only ${this.maxFileUpload} is allowed for upload at once.` );
                return;
            } else {
                this.totalPhotos = files.length;
                this.photos = new Array();
                for ( let i = 0; i < this.totalPhotos; i++ ) {
                    let file = files.item( i );
                    let photo = new Photo( file, _photo => {
                        this.photos.push( _photo );
                        if ( this.photos.length == this.totalPhotos ) this.showPreview();
                    } );
                }
            }
        } );
    }
    startUpload() {
        if ( this.totalPhotos < 1 ) {
            alert( "Please Select Atleast One Photo To Start Upload." );
            return;
        }
        // disable image preview section

        this.previewContainer.css( {
            "pointer-events": "none",
            opacity: "0.4"
        } );

        // Prepare the payload
        let formData = new FormData();

        // Insert Binary Large Objects
        this.photos.forEach( photo => {
            formData.append( photo.filename, photo.file );
        } );

        // Insert target AlbumId
        let albumId = <string>this.targetAlbumField.val();
        formData.append( "albumId", albumId );

        // Create PhotoModel from Photos
        let photoModelPayload: PhotoModel[] = [];
        this.photos.forEach( photo => {
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
            photoModelPayload.push( pm );
        } );

        // Insert Payload Details
        formData.append( "payload_info", JSON.stringify( photoModelPayload ) );

        // make the ajax call

        $.ajax( {
            beforeSend: () => {
                $( "#progressbar-div" ).removeClass( "d-none" );
            },
            xhr: () => {
                let xhr = new XMLHttpRequest();
                if ( xhr.upload ) {
                    xhr.upload.addEventListener( "progress", evt => {
                        if ( evt.lengthComputable ) {
                            let percent = ( ( evt.loaded / evt.total ) * 100 ).toFixed( 0 );
                            $( "#progressbar-div .progress-bar" ).css( "width", percent + "%" );
                        }
                    } );
                }
                return xhr;
            },
            method: "POST",
            data: formData,
            contentType: false,
            processData: false,
            url: "/ThePhotoAlbum/App/PhotoUpload"
        } ).then(() => {
            $( "#progressbar-div" ).addClass( "d-none" );
            this.previewContainer.css( { opacity: 1, "pointer-events": "auto" } );
            window.location.href = `/ThePhotoAlbum/App/AlbumView?albumId=${albumId}`;
        } );
    }
    private showPreview() {
        this.previewContainer.empty();
        this.previewContainer.append( this.paginationGenerator() );
    }

    paginationGenerator() {
        let ul = $( "<ul>" )
            .addClass( "pagination" )
            .addClass( "justify-content-center" );

        for ( let i = 1; i <= this.totalPhotos; i++ ) {
            let btn = $( "<button>" )
                .attr( "data-pagination-id", i )
                .addClass( "page-link" )
                .text( i )
                .on( "click", () => {
                    this.previewContainer
                        .find( "ul > li" )
                        .removeClass( "active" )
                        .eq( i - 1 )
                        .addClass( "active" );
                    this.imagePreviewGenerator( i - 1 );
                } );
            let li = $( "<li>" )
                .addClass( "page-item" )
                .append( btn );
            if ( i == 1 ) li.addClass( "active" );
            ul.append( li );
        }

        $.get( "/ThePhotoAlbum/static/html/img-preview.html", page => {
            this.previewContainer.prepend(
                $( "<div>" )
                    .attr( "id", "inner-img-container" )
                    .append( page )
            );
            this.imagePreviewGenerator( 0 );
        } );

        return $( "<div>" )
            .addClass( "row" )
            .addClass( "m-3" )
            .append(
            $( "<div>" )
                .addClass( "col" )
                .append( ul )
            );
    }
    imagePreviewGenerator( index: number ) {
        let innerContainer = this.previewContainer.find( "#inner-img-container" );
        let photo = this.photos[index];
        innerContainer
            .find( "#preview-img" )
            .empty()
            .append( $( photo.htmlTag ).addClass( "img-fluid" ) );
        innerContainer.find( "#serial_no" ).text( `#${index + 1}` );
        innerContainer
            .find( "#title_input" )
            .val( photo.title )
            .off()
            .on( "blur", evt => {
                photo.title = <string>$( evt.target ).val();
                console.log( photo.title );
            } );
        innerContainer
            .find( "#description_input" )
            .val( photo.description )
            .off()
            .on( "blur", evt => {
                photo.description = <string>$( evt.target ).val();
                console.log( photo.description );
            } );
        innerContainer.find( "#media_type" ).text( photo.file.type );
        innerContainer.find( "#file_size" ).html( photo.getSizeHTMLTag() );
        innerContainer.find( "#resolution" ).html( photo.getPhotoResolutionHTMLTag() );
        innerContainer
            .find( "#orientation" )
            .text( <string>photo.getImageOrientation() );
    }

    showAnimation() {
        this.previewContainer.empty();
        let animation = $( "<div>" )
            .addClass( "text-center" )
            .append(
            $( "<div>" )
                .addClass( "spinner-grow" )
                .addClass( "text-primary" )
            )
            .append(
            $( "<div>" )
                .addClass( "spinner-grow" )
                .addClass( "text-success" )
            )
            .append(
            $( "<div>" )
                .addClass( "spinner-grow" )
                .addClass( "text-danger" )
            );

        this.previewContainer.append( animation );
    }

    showSelectPhotos() {
        this.previewContainer.empty().append(
            $( "<p>" )
                .addClass( "text-muted" )
                .addClass( "text-center" )
                .addClass( "h6" )
                .text( "Select Images to See Preview" )
        );
    }
}

interface ModalParam {
    openBtnId: string;
    modalContainerId: string;
    albumTitleFieldId: string;
    albumDescriptionFieldId: string;
    createAlbumBtnId: string;
    targetAlbumSelectFieldId: string,
    postURL: string;
}

class AlbumManager {
    openBtn: JQuery<HTMLElement>;
    modalContainer: JQuery<HTMLElement>;
    albumTitleField: JQuery<HTMLElement>;
    albumDescriptionField: JQuery<HTMLElement>;
    createAlbumBtn: JQuery<HTMLElement>;
    targetAlbumSelectField: JQuery<HTMLElement>;
    postURL: string;
    constructor( params: ModalParam ) {
        this.openBtn = $( `#${params.openBtnId}` );
        this.modalContainer = $( `#${params.modalContainerId}` );
        this.albumTitleField = $( `#${params.albumTitleFieldId}` );
        this.albumDescriptionField = $( `#${params.albumDescriptionFieldId}` );
        this.createAlbumBtn = $( `#${params.createAlbumBtnId}` );
        this.targetAlbumSelectField = $( `#${params.targetAlbumSelectFieldId}` );
        this.postURL = params.postURL;
        this.loadUserAlbums();
        this.initialize();
    }

    private initialize(): void {
        this.openBtn.click(() => {
            this.modalContainer.modal( "show" );
        } );
        this.albumTitleField.on( "change blur", () => {
            this.hideFeedback( this.albumTitleField );
        } );

        this.albumDescriptionField.on( "change blur", () => {
            this.hideFeedback( this.albumDescriptionField );
        } );

        this.createAlbumBtn.click(() => {
            let title: string = <string>this.albumTitleField.val();
            let description: string = <string>this.albumDescriptionField.val();
            if ( !this.validate( title, description ) ) return;
            this.createAlbum( title, description );
        } );
    }

    private validate( title: string, description: string ): boolean {
        if ( !title ) {
            this.showFeedback( this.albumTitleField, "Please Enter Album Title." );
            return false;
        } else if ( title.length > 30 ) {
            this.showFeedback(
                this.albumTitleField,
                "Album Title Can Only Be 30 Characters Long"
            );
            return false;
        } else return true;
    }

    private showFeedback( field: JQuery<HTMLElement>, msg: string ): void {
        this.hideFeedback( field );
        field.addClass( "is-invalid" );
        let alert = $( "<div>" )
            .addClass( "invalid-feedback" )
            .text( msg );
        field.parent().append( alert );
    }

    private hideFeedback( field: JQuery<HTMLElement> ): void {
        field.removeClass( "is-invalid" );
        field
            .parent()
            .children()
            .remove( ".invalid-feedback" );
    }

    private createAlbum( title: string, description: string ): void {
        this.createAlbumBtn
            .addClass( "disabled" )
            .html(
            `<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Creating`
            );
        $.post( this.postURL, {
            title: title,
            description: description
        } )
            .done(() => {
                this.loadUserAlbums();
                this.createAlbumBtn.removeClass( "disabled" ).html( `Create` );
                this.resetFields();
                let nofication = $( ` <div class="alert alert-light">
              <i class="fas fa-check-circle text-success"></i> Album <strong>${title}</strong> Created Successfully
          </div>`);
                this.modalContainer
                .find('.modal-body')
                .append( nofication );
                setTimeout(() => {
                    this.modalContainer.modal('hide');
                    this.modalContainer.find('.alert').remove();
                },700);
            } )
            .fail(() => {
                this.createAlbumBtn.removeClass( "disabled" ).html( `Try Again` );
                let nofication = $( ` <div class="alert alert-light">
          <i class="fas fa-times-circle  text-danger"></i> Failed To Create Album
          </div>`);
                this.modalContainer
                    .find( ".alert" )
                    .remove()
                    .append( nofication );
            } );
    }

    private resetFields() {
        this.albumTitleField.val( '' );
        this.albumDescriptionField.val( '' );
    }
    private loadUserAlbums() {
        $.get( "/ThePhotoAlbum/App/AlbumList", data => {
            let list = JSON.parse( data );
            this.targetAlbumSelectField.empty();
            list.forEach(( item: any ) => {
                this.targetAlbumSelectField.append(
                    $( "<option>", { value: item.id, text: item.title } )
                );
            } );
        } );
    }
}

$(() => {
    let preview = new PhotoPreviewManager(
        "photo_upload_input",
        "image-preview-container",
        10
    );
    let albumManager = new AlbumManager( {
        openBtnId: "album-create-btn",
        modalContainerId: "createAlbumModal",
        albumTitleFieldId: "albumTitle",
        albumDescriptionFieldId: "albumDescription",
        createAlbumBtnId: "albumCreateBtn",
        postURL: "/ThePhotoAlbum/App/CreateAlbum",
        targetAlbumSelectFieldId: "target_album"
    } );
} );