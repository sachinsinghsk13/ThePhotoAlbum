package com.techjs.thephotoalbum.web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techjs.thephotoalbum.dao.AlbumDao;
import com.techjs.thephotoalbum.utils.Constants;
import com.techjs.thephotoalbum.utils.UserSessionUtil;

@WebServlet("/App/DeleteAlbum")
public class DeleteAlbum extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long userId = UserSessionUtil.getUserIdFromSession(request);
		Long albumId = Long.parseLong(request.getParameter("albumId"));
		
		AlbumDao albumDao = (AlbumDao) getServletContext().getAttribute(Constants.ALBUM_DAO);
		try {
			albumDao.deleteAlbum(userId, albumId);
			response.sendRedirect("/ThePhotoAlbum/App/Albums");			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
