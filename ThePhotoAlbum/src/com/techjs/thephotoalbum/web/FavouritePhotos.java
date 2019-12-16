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

import com.techjs.thephotoalbum.dao.PhotoDao;
import com.techjs.thephotoalbum.models.Photo;
import com.techjs.thephotoalbum.presentation.PaginationContext;
import com.techjs.thephotoalbum.utils.Constants;
import com.techjs.thephotoalbum.utils.UserSessionUtil;

@WebServlet(urlPatterns = { "/App/Favourites" }, initParams = { @WebInitParam(name = "photoPerPage", value = "12") })
public class FavouritePhotos extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long userId = UserSessionUtil.getUserIdFromSession(request);
		PhotoDao photoDao = (PhotoDao) getServletContext().getAttribute(Constants.PHOTO_DAO);
		try {
			PaginationContext paginationContext = new PaginationContext();
			Integer totalPhotos = photoDao.getTotalNoOfFavouritePhotos(userId);
			paginationContext.setTotalItems(totalPhotos);
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
			Integer offset = ((page - 1) * photoPerPage);			
			List<Photo> photos = photoDao.getFavouritePhotos(userId, offset, photoPerPage);
			request.setAttribute("photos", photos);
			request.getRequestDispatcher("/App/Favourites.jsp").forward(request, response);
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}

}
