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
import com.techjs.thephotoalbum.utils.Favourite;
import com.techjs.thephotoalbum.utils.UserSessionUtil;

@WebServlet("/App/MarkFavourite")
public class MarkFavourite extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long userId = UserSessionUtil.getUserIdFromSession(request);
		Long photoId = Long.parseLong(request.getParameter("photoId"));
		String favourite = request.getParameter("favourite");
		Photo photo = new Photo();
		photo.setId(photoId);
		photo.setUserId(userId);
		photo.setFavourite(Favourite.valueOf(favourite));
		
		PhotoDao photoDao = (PhotoDao) getServletContext().getAttribute(Constants.PHOTO_DAO);
		try {
			photoDao.markFavourite(photo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
