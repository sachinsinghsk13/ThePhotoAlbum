package com.techjs.thephotoalbum.auth;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.techjs.thephotoalbum.beans.LoginCredential;
import com.techjs.thephotoalbum.dao.UserDao;
import com.techjs.thephotoalbum.models.User;
import com.techjs.thephotoalbum.presentation.AlertMessage;
import com.techjs.thephotoalbum.utils.Constants;

@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get the email and password
		
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		boolean savePassword = (request.getParameter("rememeberme") != null);
		LoginCredential login = new LoginCredential(email, password);
		
		// authenticate the email and password
		
		UserDao userDao = (UserDao) request.getServletContext().getAttribute(Constants.USER_DAO);
		try {
			if (userDao.isUserAuthenticated(login)) {
				HttpSession session = request.getSession();
				UserSession userSession = new UserSession();
				User user = userDao.getUserByEmail(email);
				userSession.setUserDao(userDao);
				userSession.login(user);
				session.setAttribute(Constants.USER_SESSION, userSession);
				if (savePassword) {
					setCredentialsCookies(response, login);
				}
				
				response.sendRedirect(request.getContextPath() + "/App/Home");
			}
			else {
				AlertMessage message = new AlertMessage();
				message.setMessage("Invalid Email or Password!");
				message.setType("danger"); // bootstrap warning type
				request.setAttribute("alerts", Arrays.asList(message));
				request.getRequestDispatcher("/index.jsp").forward(request, response);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private void setCredentialsCookies(HttpServletResponse response, LoginCredential login) {
		Cookie emailCookie = new Cookie("email", login.getEmail());
		Cookie passwordCookie = new Cookie("password", login.getPassword());
		
		emailCookie.setMaxAge(30 * 24 * 60 * 60); // cookie is valid for 30 days
		passwordCookie.setMaxAge(30 * 24 * 60 * 60); // cookie is valid for 30 days
		response.addCookie(emailCookie);
		response.addCookie(passwordCookie);
		
	}

}
