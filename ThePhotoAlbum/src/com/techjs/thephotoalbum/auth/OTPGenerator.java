package com.techjs.thephotoalbum.auth;

/**
 * Generates One Time Passwords for Email Verifications
 * 
 * @author Sachin Singh
 * */

public class OTPGenerator {
	
	/**
	 * Returns a five digit random number
	 * @return five digit OTP
	 * */
	public static String generateOTP() {
		Integer otp = 0;
		do {
			double random = Math.random();
			otp = (int) Math.round(random * 100000);
		} while(otp < 10000);
		return otp.toString();
	}
	
}