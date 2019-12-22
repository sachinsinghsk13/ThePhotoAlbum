package com.techjs.thephotoalbum.web.ajax;

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

@WebServlet("/App/UpdateAlbum")
public class UpdateAlbum extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		Long albumId = Long.parseLong(request.getParameter("albumId"));
		Long userId = UserSessionUtil.getUserIdFromSession(request);
		Album album = new Album();
		album.setTitle(title);
		album.setDescription(description);
		album.setId(albumId);
		album.setUserId(userId);
		AlbumDao albumDao = (AlbumDao) getServletContext().getAttribute(Constants.ALBUM_DAO);
		
		try {
			albumDao.updateAlbum(album);
			response.getWriter().println("Changes Are Saved!");
		} catch (SQLException e) {
			e.printStackTrace();
			response.getWriter().println("Failed to Save Changes");
		}
	}

}
