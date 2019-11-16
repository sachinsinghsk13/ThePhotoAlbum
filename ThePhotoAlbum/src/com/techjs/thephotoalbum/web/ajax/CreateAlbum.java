package com.techjs.thephotoalbum.web.ajax;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.techjs.thephotoalbum.auth.UserSession;
import com.techjs.thephotoalbum.dao.AlbumDao;
import com.techjs.thephotoalbum.models.Album;
import com.techjs.thephotoalbum.utils.Constants;

@WebServlet("/App/CreateAlbum")
public class CreateAlbum extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = request.getParameter("title");
		String description = request.getParameter("description");
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute(Constants.USER_SESSION);
		AlbumDao albumDao = (AlbumDao) getServletContext().getAttribute(Constants.ALBUM_DAO);
		Album album = new Album();
		album.setUserId(userSession.getCurrentUser().getId());
		album.setTitle(title);
		album.setDescription(description);
		try {
			albumDao.insertAlbum(album);
			System.out.println("Album Created");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
