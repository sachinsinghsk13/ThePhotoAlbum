package com.techjs.thephotoalbum.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.techjs.thephotoalbum.auth.UserSession;
import com.techjs.thephotoalbum.dao.PhotoDao;
import com.techjs.thephotoalbum.models.Photo;
import com.techjs.thephotoalbum.utils.Constants;

@WebServlet("/App/GetPhoto")
public class GetPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long photoId = Long.parseLong(request.getParameter("photoId"));
		Long albumId = Long.parseLong(request.getParameter("albumId"));
		
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute(Constants.USER_SESSION); 
		Long userId = userSession.getCurrentUser().getId();
		PhotoDao photoDao = (PhotoDao) getServletContext().getAttribute(Constants.PHOTO_DAO);
		try {
			Photo photo = photoDao.getPhoto(photoId, albumId, userId);
			response.setContentType("image/jpeg");
			response.getOutputStream().write(photo.getBinaryData());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
