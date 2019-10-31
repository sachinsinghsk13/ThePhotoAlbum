package com.techjs.thephotoalbum.auth;

import com.techjs.thephotoalbum.models.User;

public class NewUserRegistration {
	private User user;
	String otp;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	
}
