package com.techjs.thephotoalbum.web.ajax;

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

@WebServlet("/App/UpdatePhoto")
public class UpdatePhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Long photoId = Long.parseLong(request.getParameter("photoId"));
		Long albumId = Long.parseLong(request.getParameter("albumId"));
		Long userId = UserSessionUtil.getUserIdFromSession(request);
		Photo photo = new Photo();
		photo.setTitle(title);
		photo.setDescription(description);
		photo.setId(photoId);
		photo.setAlbumId(albumId);
		photo.setUserId(userId);
		
		PhotoDao photoDao = (PhotoDao) getServletContext().getAttribute(Constants.PHOTO_DAO);
		
		try {
			photoDao.updatePhoto(photo);
			response.getWriter().println("Changes Are Saved!");
		} catch (SQLException e) {
			e.printStackTrace();
			response.getWriter().println("Failed to Save Changes");
		}
	
	}

}
