"use strict";
class LoginForm {
    constructor(formId, loginEmailFieldId, passwordFieldId) {
        this.form = $(`#${formId}`);
        this.loginEmailField = $(`#${loginEmailFieldId}`);
        this.passwordField = $(`#${passwordFieldId}`);
        this.form.on("submit", event => {
            const EmailRegex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            this.loginEmailField
                .parent()
                .find(".invalid-feedback")
                .remove();
            let email = this.loginEmailField.val();
            if (!email || !EmailRegex.test(email)) {
                let msg = !email
                    ? "Please Fill The Email Address!"
                    : "You Entered An Invalid Email Address!";
                this.loginEmailField.addClass("is-invalid");
                this.loginEmailField.parent().append($("<div>")
                    .addClass("invalid-feedback")
                    .text(msg));
                event.preventDefault();
                return;
            }
            let password = this.passwordField.val();
            this.passwordField
                .parent()
                .find(".invalid-feedback")
                .remove();
            if (!password) {
                let msg = !password
                    ? "Please Fil The Password!"
                    : "Password is Invalid";
                this.passwordField.addClass("is-invalid");
                this.passwordField.parent().append($("<div>")
                    .addClass("invalid-feedback")
                    .text(msg));
                event.preventDefault();
                return;
            }
            this.loginEmailField.removeClass("is-invalid");
            this.loginEmailField
                .parent()
                .find(".invalid-feedback")
                .remove();
            this.passwordField.removeClass("is-invalid");
            this.passwordField
                .parent()
                .find(".invalid-feedback")
                .remove();
        });
    }
}
class RegistrationForm {
    constructor() {
        this.isEmailAvailable = true;
        this.form = $("#registration-form");
        this.fullnameField = $("#reg-fullname");
        this.emailField = $("#reg-email");
        this.birthdayField = $("#reg-birthday");
        this.genderField = $("#reg-gender");
        this.passwordField = $("#reg-password");
        this.confirmPasswordField = $("#reg-confirm-password");
        this.initialize();
    }
    initialize() {
        this.form.on("submit", event => {
            this.validate(event);
        });
        this.emailField.on("blur change", event => {
        	const EmailRegex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        	let email = this.emailField.val();
            if (!email || !EmailRegex.test(email)) {
                let msg = !email ? "Please Enter Your Email" : "This Email Is Not Valid";
                this.showErrors(this.emailField, msg);
                event.preventDefault();
                return;
            }
            else
                this.hideErrors(this.emailField);
            
            fetch("/ThePhotoAlbum/EmailAvailability?email="+email)
                .then(res => {
                res
                    .json()
                    .then(data => {
                    let isAvailable = data.isEmailAvailable;
                    if (isAvailable) {
                        this.isEmailAvailable = true;
                        this.hideErrors(this.emailField);
                    }
                    else {
                        this.isEmailAvailable = false;
                        this.showErrors(this.emailField, "Email Already Taken");
                    }
                })
                    .catch(() => { });
            })
                .catch(() => { });
        });
    }
    validate(evt) {
        const nameRegex = /^[A-Za-z ]{3,15}$/;
        const EmailRegex = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        const passwordRegex = /^\w{4,8}$/;
        let fullname = this.fullnameField.val();
        if (!fullname || !nameRegex.test(fullname)) {
            let msg = !fullname
                ? "Please Enter Your Name"
                : "This Name is Not Valid";
            this.showErrors(this.fullnameField, msg);
            evt.preventDefault();
            return;
        }
        else
            this.hideErrors(this.fullnameField);
        
        let email = this.emailField.val();
        if (!email || !EmailRegex.test(email)) {
            let msg = !email ? "Please Enter Your Email" : "This Email Is Not Valid";
            this.showErrors(this.emailField, msg);
            evt.preventDefault();
            return;
        }
        else
            this.hideErrors(this.emailField);
        
        
        let birthday = new Date(this.birthdayField.val() + "T00:00");
        let today = new Date();
        let tenYearBack = new Date();
        tenYearBack.setFullYear(today.getFullYear() - 10);
        if (birthday.getTime() > tenYearBack.getTime()) {
            this.showErrors(this.birthdayField, `You're too young to be here.`);
            evt.preventDefault();
            return;
        }
        else
            this.hideErrors(this.birthdayField);
        let password = this.passwordField.val();
        let confirmPassword = this.confirmPasswordField.val();
        if (!password || !passwordRegex.test(password)) {
            let msg = !password
                ? "Please Enter Password!"
                : "Please Choose a Strong Password";
            this.showErrors(this.passwordField, msg);
            evt.preventDefault();
            return;
        }
        else
            this.hideErrors(this.passwordField);
        if (!confirmPassword || confirmPassword !== password) {
            let msg = !confirmPassword
                ? "Please Re-Enter Your Password Here"
                : "Password Does't Match";
            this.showErrors(this.confirmPasswordField, msg);
            evt.preventDefault();
            return;
        }
        else
            this.hideErrors(this.confirmPasswordField);
        if (!this.isEmailAvailable) {
            evt.preventDefault();
            this.showErrors(this.emailField, "We've already an account with this email");
            return;
        }
    }
    showErrors(field, message) {
        this.hideErrors(field);
        field.addClass("is-invalid");
        field.parent().append($("<div>")
            .addClass("invalid-feedback")
            .text(message));
    }
    hideErrors(field) {
        field.removeClass("is-invalid");
        field
            .parent()
            .find(".invalid-feedback")
            .remove();
    }
}

function getHeroSectionHeight() {
    return ($(window).height() - ($(window).height() * 20 )/100) + "px";
}

$(() => {
	$('#hero-section').css('height',getHeroSectionHeight());
	   $(window).on('resize', ()=> {
	    $('#hero-section').css('height',getHeroSectionHeight());
	   })
	new LoginForm('login-form','login-email','login-password');
    new RegistrationForm();
});
