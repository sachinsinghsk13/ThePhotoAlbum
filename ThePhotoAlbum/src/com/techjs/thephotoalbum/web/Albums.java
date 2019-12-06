package com.techjs.thephotoalbum.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.techjs.thephotoalbum.dao.AlbumDao;
import com.techjs.thephotoalbum.dao.PhotoDao;
import com.techjs.thephotoalbum.models.Album;
import com.techjs.thephotoalbum.models.Photo;
import com.techjs.thephotoalbum.presentation.PaginationContext;
import com.techjs.thephotoalbum.utils.Constants;
import com.techjs.thephotoalbum.utils.UserSessionUtil;
/**
 * Servlet class for showing all albums of the user.
 * 
 * @author Sachin Singh
 * */

@WebServlet(urlPatterns = {"/App/Albums"}, initParams = {
		@WebInitParam(name = "albumPerPage", value = "12")
})
public class Albums extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// Get the current user's id.
		Long userId = UserSessionUtil.getUserIdFromSession(request);
		
		// Get the Dao's for database activities
		AlbumDao albumDao = (AlbumDao) getServletContext().getAttribute(Constants.ALBUM_DAO);
		PhotoDao photoDao = (PhotoDao) getServletContext().getAttribute(Constants.PHOTO_DAO);
		
		try {
			/* Handling Pagination */
			
			PaginationContext paginationContext  = new PaginationContext();
			Integer totalAlbums = albumDao.getTotalNoOfAlbumOfUser(userId);
			HttpSession session  = request.getSession();
			Integer albumPerPage = (Integer) session.getAttribute("albumPerPage");
			
			// if current session has no detail about this. set it from init params.
			if (albumPerPage == null) {
				albumPerPage = Integer.parseInt(getInitParameter("albumPerPage"));
			}
			
			System.out.println(totalAlbums + " ; " + albumPerPage);
			Integer totalPages = (int) Math.ceil((double) totalAlbums/albumPerPage);
			
			Integer page = 1;
			if (request.getParameter("page") != null) {
				page = Integer.parseInt(request.getParameter("page"));
			}
			
			paginationContext.setTotalItems(totalAlbums);
			paginationContext.setItemPerPage(albumPerPage);
			paginationContext.setTotalPages(totalPages);
			paginationContext.setCurrentPage(page);
			
			request.setAttribute("paginationContext", paginationContext);
						
			Integer offset = ((page - 1) * albumPerPage);			
			List<Album> albums = albumDao.getAllAlbumOfUserInLimit(userId, offset, albumPerPage);
			for (Album album : albums) {
				Integer total = photoDao.getTotalNoOfPhotosInAlbum(userId, album.getId());
				album.setTotalPhotos(total);
			}
			
			request.setAttribute("albums", albums);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		request.getRequestDispatcher("/App/Albums.jsp").forward(request, response);
	}
}
