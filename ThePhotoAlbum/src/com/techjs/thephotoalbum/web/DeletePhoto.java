package com.techjs.thephotoalbum.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techjs.thephotoalbum.dao.PhotoDao;
import com.techjs.thephotoalbum.models.Photo;
import com.techjs.thephotoalbum.utils.Constants;
import com.techjs.thephotoalbum.utils.UserSessionUtil;

@WebServlet("/App/DeletePhoto")
public class DeletePhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long userId = UserSessionUtil.getUserIdFromSession(request);
		Long albumId = Long.parseLong(request.getParameter("albumId"));
		Long photoId = Long.parseLong(request.getParameter("photoId"));
		
		PhotoDao photoDao = (PhotoDao) getServletContext().getAttribute(Constants.PHOTO_DAO);
		Photo photo = new Photo();
		photo.setId(photoId);
		photo.setUserId(userId);
		try {
			photoDao.deletePhoto(photo);
			response.sendRedirect("/ThePhotoAlbum/App/AlbumView?albumId=" + albumId);			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
