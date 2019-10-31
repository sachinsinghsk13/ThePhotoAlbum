package com.techjs.thephotoalbum.web.ajax;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techjs.thephotoalbum.dao.UserDao;
import com.techjs.thephotoalbum.utils.Constants;

@WebServlet("/EmailAvailability")
public class EmailAvailability extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		UserDao userDao = (UserDao) request.getServletContext().getAttribute(Constants.USER_DAO);
		try {
			boolean result = userDao.isEmailAvailble(email);
			if (result) {
				response.getWriter().println("{\"isEmailAvailable\" : true}");
			}
			else {
				response.getWriter().println("{\"isEmailAvailable\" : false}");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
