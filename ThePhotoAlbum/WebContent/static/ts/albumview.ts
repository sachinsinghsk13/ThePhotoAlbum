interface EditModalParams {
  openBtnId: string;
  modalContainerId: string;
  titleFieldId: string;
  descriptionFieldId: string;
  saveBtnId: string;
  postURL: string;
}

class EditAlbumModal {
  openBtn: JQuery<HTMLElement>;
  modalContainer: JQuery<HTMLElement>;
  titleField: JQuery<HTMLElement>;
  descriptionField: JQuery<HTMLElement>;
  saveBtn: JQuery<HTMLElement>;
  postURL: string;
  constructor(params: EditModalParams) {
    this.openBtn = $(`#${params.openBtnId}`);
    this.titleField = $(`#${params.titleFieldId}`);
    this.descriptionField = $(`#${params.descriptionFieldId}`);
    this.saveBtn = $(`#${params.saveBtnId}`);
    this.modalContainer = $(`#${params.modalContainerId}`);
    this.postURL = params.postURL;
    this.initialize();
  }

  private initialize(): void {
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
      let title: string = <string>this.titleField.val();
      let description: string = <string>this.descriptionField.val();
      if (!this.validate(title, description)) return;
      this.saveChanges(title, description);
    });
  }
  private validate(title: string, description: string): boolean {
    if (!title) {
      this.showFeedback(this.titleField, "Please Enter Album Title.");
      return false;
    } else if (title.length > 30) {
      this.showFeedback(
        this.titleField,
        "Album Title Can Only Be 30 Characters Long"
      );
      return false;
    } else return true;
  }
  showFeedback(field: JQuery<HTMLElement>, msg: string): void {
    this.hideFeedback(field);
    field.addClass("is-invalid");
    let alert = $("<div>")
      .addClass("invalid-feedback")
      .text(msg);
    field.parent().append(alert);
  }

  hideFeedback(field: JQuery<HTMLElement>): void {
    field.removeClass("is-invalid");
    field
      .parent()
      .children()
      .remove(".invalid-feedback");
  }

  saveChanges(title: string, description: string): void {
    this.saveBtn
      .addClass("disabled")
      .html(
        `<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span> Creating`
      );
    let albumid = this.modalContainer.attr('data-album-id');
    $.post(this.postURL, {
      title: title,
      description: description,
      albumId: albumid
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

$(() => {
  let param: EditModalParams = {
    saveBtnId: "saveBtn",
    modalContainerId: "editAlbumModal",
    titleFieldId: "albumTitle",
    descriptionFieldId: "albumDescription",
    openBtnId: "openEditModal",
    postURL: "/ThePhotoAlbum/App/UpdateAlbum"
  };
  var editModal = new EditAlbumModal(param);
});