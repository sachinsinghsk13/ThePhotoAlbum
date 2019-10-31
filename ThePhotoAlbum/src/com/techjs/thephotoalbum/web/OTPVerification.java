package com.techjs.thephotoalbum.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.techjs.thephotoalbum.auth.NewUserRegistration;
import com.techjs.thephotoalbum.dao.UserDao;
import com.techjs.thephotoalbum.models.User;
import com.techjs.thephotoalbum.utils.Constants;

@WebServlet("/OTPVerification")
public class OTPVerification extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String otp = request.getParameter("otp");
		HttpSession session = request.getSession();
		NewUserRegistration newUser = (NewUserRegistration) session.getAttribute("newUser");
		if (otp != null && otp.equals(newUser.getOtp())) {
			User user = newUser.getUser();
			UserDao userDao = (UserDao) request.getServletContext().getAttribute(Constants.USER_DAO);
			try {
				userDao.insertUser(user);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		else {
			response.getWriter().println("Registration Fail");
		}
	}

}
