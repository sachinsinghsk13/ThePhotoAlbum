package com.techjs.thephotoalbum.auth;

public class OTPGenerator {
	public static String generateOTP() {
		Integer otp = 0;
		do {
			double random = Math.random();
			otp = (int) Math.round(random * 100000);
		} while(otp < 10000);
		return otp.toString();
	}
	
}