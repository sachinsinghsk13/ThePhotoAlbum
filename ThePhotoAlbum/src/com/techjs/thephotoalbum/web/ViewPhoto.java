package com.techjs.thephotoalbum.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techjs.thephotoalbum.dao.AlbumDao;
import com.techjs.thephotoalbum.dao.PhotoDao;
import com.techjs.thephotoalbum.models.Album;
import com.techjs.thephotoalbum.models.Photo;
import com.techjs.thephotoalbum.utils.Constants;
import com.techjs.thephotoalbum.utils.UserSessionUtil;

@WebServlet("/App/ViewPhoto")
public class ViewPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long userId = UserSessionUtil.getUserIdFromSession(request);
		Long albumId = Long.parseLong(request.getParameter("albumId"));
		Long photoId = Long.parseLong(request.getParameter("photoId"));
		
		AlbumDao albumDao = (AlbumDao) getServletContext().getAttribute(Constants.ALBUM_DAO);
		PhotoDao photoDao = (PhotoDao) getServletContext().getAttribute(Constants.PHOTO_DAO);
		
		try {
			Photo photo = photoDao.getPhoto(photoId, albumId, userId);
			request.setAttribute("photo", photo);
			Album album = albumDao.getAlbumOfUser(userId, albumId);
			request.setAttribute("album", album);
			request.getRequestDispatcher("/App/ViewPhoto.jsp").forward(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

}

