package com.techjs.thephotoalbum.web;

import java.io.IOException;
import java.util.Date;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.techjs.thephotoalbum.auth.EmailClient;
import com.techjs.thephotoalbum.auth.NewUserRegistration;
import com.techjs.thephotoalbum.auth.OTPGenerator;
import com.techjs.thephotoalbum.models.User;
import com.techjs.thephotoalbum.utils.Constants;
import com.techjs.thephotoalbum.utils.Gender;

@WebServlet("/UserRegistration")
public class UserRegistration extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fullname = request.getParameter("fullname");
		String email = request.getParameter("email");
		String birthday = request.getParameter("birthday");
		String gender = request.getParameter("gender");
		String password = request.getParameter("password");
		
		User user = new User();
		user.setName(fullname);
		user.setEmail(email);
		user.setGender(Gender.valueOf(gender));
		user.setDob(birthday);
		user.setPassword(password);
		
		String otp = OTPGenerator.generateOTP();
		
		NewUserRegistration newUserRegistration = new NewUserRegistration();
		newUserRegistration.setUser(user);
		newUserRegistration.setOtp(otp);
		
		HttpSession session = request.getSession();
		session.setAttribute("newUser", newUserRegistration);
		
		EmailClient emailClient = (EmailClient) request.getServletContext().getAttribute(Constants.EMAIL_CLIENT);
		try {
			
			emailClient.sendOTP(user.getEmail(), otp);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		request.setAttribute("newUser", newUserRegistration);
		request.getRequestDispatcher("/otp-verify.jsp").forward(request, response);
	}

}
