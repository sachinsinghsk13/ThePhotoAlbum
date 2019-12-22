"use strict";
class EditPhotoModal {
    constructor(params) {
        this.openBtn = $(`#${params.openBtnId}`);
        this.titleField = $(`#${params.titleFieldId}`);
        this.descriptionField = $(`#${params.descriptionFieldId}`);
        this.saveBtn = $(`#${params.saveBtnId}`);
        this.modalContainer = $(`#${params.modalContainerId}`);
        this.postURL = params.postURL;
        this.initialize();
    }
    initialize() {
        this.openBtn.click(() => {
            this.modalContainer.modal("show");
        });
        this.titleField.on("change blur", () => {
            this.hideFeedback(this.titleField);
        });
        this.descriptionField.on("change blur", () => {
            this.hideFeedback(this.descriptionField);
        });
        this.saveBtn.click(() => {
            let title = this.titleField.val();
            let description = this.descriptionField.val();
            if (!this.validate(title, description))
                return;
            this.saveChanges(title, description);
        });
    }
    validate(title, description) {
        if (!title) {
            this.showFeedback(this.titleField, "Please Enter Photo Title.");
            return false;
        }
        else if (title.length > 30) {
            this.showFeedback(this.titleField, "Photo Title Can Only Be 30 Characters Long");
            return false;
        }
        else
            return true;
    }
    showFeedback(field, msg) {
        this.hideFeedback(field);
        field.addClass("is-invalid");
        let alert = $("<div>")
            .addClass("invalid-feedback")
            .text(msg);
        field.parent().append(alert);
    }
    hideFeedback(field) {
        field.removeClass("is-invalid");
        field
            .parent()
            .children()
            .remove(".invalid-feedback");
    }
    saveChanges(title, description) {
        this.saveBtn
            .addClass("disabled")
            .html(`<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Creating`);
        let albumId = this.modalContainer.attr('data-album-id');
        let photoId = this.modalContainer.attr('data-photo-id');
        $.post(this.postURL, {
            title: title,
            description: description,
            albumId: albumId,
            photoId: photoId
        })
            .done((res) => {
            this.saveBtn.html(`Saved`);
            let notification = $(` <div class="alert alert-light">
            <i class="fas fa-check-circle text-success"></i> ${res}
        </div>`);
            this.modalContainer.find('.modal-body').append(notification);
            setTimeout(() => {
                this.modalContainer.modal('hide');
                window.location.reload();
            }, 700);
        })
            .fail((res) => {
            this.saveBtn.removeClass("disabled").html(`Try Again`);
            let nofication = $(` <div class="alert alert-light">
        <i class="fas fa-times-circle text-danger"></i> ${res}
        </div>`);
            this.modalContainer
                .find(".modal-body")
                .remove(".alert")
                .append(nofication);
        });
    }
}
class FavouritePhoto {
    constructor(changeBtnId, postURL) {
        this.favouriteBtn = $(`#${changeBtnId}`);
        this.postURL = postURL;
        this.initialize();
    }
    initialize() {
        this.favouriteBtn.click(() => {
            let photoId = $('.modal').attr('data-photo-id');
            let favourite = this.favouriteBtn.attr('data-favourite');
            favourite = (favourite == "YES") ? "NO" : "YES";
            let param = {
                photoId: photoId,
                favourite: favourite
            };
            $.post(this.postURL, param).done(() => {
                window.location.reload();
            });
        });
    }
}
$(() => {
    let param = {
        saveBtnId: "saveBtn",
        modalContainerId: "editPhotoModal",
        titleFieldId: "photoTitle",
        descriptionFieldId: "photoDescription",
        openBtnId: "openModalBtn",
        postURL: "/ThePhotoAlbum/App/UpdatePhoto"
    };
    var editModal = new EditPhotoModal(param);
    var favourite = new FavouritePhoto('changeFavourite', '/ThePhotoAlbum/App/MarkFavourite');
});
