package com.techjs.thephotoalbum.web;

import java.io.IOException;
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

@WebServlet(urlPatterns = {"/App/AlbumView"}, initParams = {
		@WebInitParam(name = "photoPerPage", value = "12")
})
public class AlbumView extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Long userId = UserSessionUtil.getUserIdFromSession(request);
		Long albumId = Long.parseLong(request.getParameter("albumId"));
		AlbumDao albumDao = (AlbumDao) getServletContext().getAttribute(Constants.ALBUM_DAO);
		PhotoDao photoDao = (PhotoDao) getServletContext().getAttribute(Constants.PHOTO_DAO);
		try {
			PaginationContext paginationContext = new PaginationContext();
			Integer totalPhotos = photoDao.getTotalNoOfPhotosInAlbum(userId, albumId);
			HttpSession session = request.getSession();
			Integer photoPerPage = (Integer) session.getAttribute("photoPerPage");

			// if current session has no detail about this. set it from init params.
			if (photoPerPage == null) {
				photoPerPage = Integer.parseInt(getInitParameter("photoPerPage"));
			}
			Integer totalPages = (int) Math.ceil((double) totalPhotos / photoPerPage);

			Integer page = 1;
			if (request.getParameter("page") != null) {
				page = Integer.parseInt(request.getParameter("page"));
			}

			paginationContext.setTotalItems(totalPhotos);
			paginationContext.setItemPerPage(photoPerPage);
			paginationContext.setTotalPages(totalPages);
			paginationContext.setCurrentPage(page);

			request.setAttribute("paginationContext", paginationContext);
			
			Album album = albumDao.getAlbumOfUser(userId, albumId);
			album.setTotalPhotos(totalPhotos);
			request.setAttribute("album", album);
			
			Integer offset = ((page - 1) * photoPerPage);			
			List<Photo> photos = photoDao.getAllPhotosOfAlbumInLimit(userId, albumId, offset, photoPerPage);
			request.setAttribute("photos", photos);
			request.getRequestDispatcher("/App/AlbumView.jsp").forward(request, response);
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
