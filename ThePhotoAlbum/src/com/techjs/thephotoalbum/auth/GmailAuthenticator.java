package com.techjs.thephotoalbum.auth;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class GmailAuthenticator extends Authenticator {
	private String email;
	private String password;
	
	public GmailAuthenticator(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.email, this.password);
	}
	
}
