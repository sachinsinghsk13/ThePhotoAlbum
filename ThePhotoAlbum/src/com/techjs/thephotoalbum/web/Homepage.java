package com.techjs.thephotoalbum.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.techjs.thephotoalbum.dao.AlbumDao;
import com.techjs.thephotoalbum.dao.PhotoDao;
import com.techjs.thephotoalbum.dao.UserDao;
import com.techjs.thephotoalbum.models.Album;
import com.techjs.thephotoalbum.models.Photo;
import com.techjs.thephotoalbum.utils.Constants;
import com.techjs.thephotoalbum.utils.UserSessionUtil;

@WebServlet("/App/Home")
public class Homepage extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long userId = UserSessionUtil.getUserIdFromSession(request);
		AlbumDao albumDao = (AlbumDao) getServletContext().getAttribute(Constants.ALBUM_DAO	);
		PhotoDao photoDao = (PhotoDao) getServletContext().getAttribute(Constants.PHOTO_DAO);
		try {
			Integer t = albumDao.getTotalNoOfAlbumOfUser(userId);
			request.setAttribute("totalAlbums", t);
			List<Album> albums = albumDao.getAllAlbumOfUserInLimit(userId, 0, 6);
			for (Album album : albums) {
				Integer total = photoDao.getTotalNoOfPhotosInAlbum(userId, album.getId());
				album.setTotalPhotos(total);
			}
			request.setAttribute("albums", albums);
			
			List<Photo> recents = photoDao.getRecentPhotos(userId);
			request.setAttribute("recentUploads", recents);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/App/Home.jsp").forward(request, response);
	}
}
