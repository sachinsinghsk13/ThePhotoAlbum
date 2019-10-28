package com.techjs.thephotoalbum.auth;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailClient implements OTPService {
	private Session session;
	public EmailClient(Properties emailProperties, Authenticator authenticator) {
		this.session = Session.getDefaultInstance(emailProperties, authenticator);
	}
	
	@Override
	public void sendOTP(String recipient, String otp) throws AddressException, MessagingException {
		MimeMessage message = new MimeMessage(session);
		message.addRecipient(Message.RecipientType.TO,new InternetAddress(recipient));
		message.setSubject("OTP for The Photo Album Registration");
		message.setText("<p style=\"font-size:20pt\">Your OTP is  <b>" + otp + " </b></p>");
		Transport.send(message);
	}
	
	
	
}
