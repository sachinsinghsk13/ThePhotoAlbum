package com.techjs.thephotoalbum.auth;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

public interface OTPService {
	void sendOTP(String recipient, String otp) throws AddressException, MessagingException;
}
