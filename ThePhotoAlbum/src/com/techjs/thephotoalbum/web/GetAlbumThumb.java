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

@WebServlet("/App/GetAlbumThumb")
public class GetAlbumThumb extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			Long userId = UserSessionUtil.getUserIdFromSession(request);
			Long albumId  = Long.parseLong(request.getParameter("albumId"));
			AlbumDao albumDao = (AlbumDao) getServletContext().getAttribute(Constants.ALBUM_DAO	);
			try {
				byte[] data = albumDao.getAlbumThumb(userId, albumId);
				if (data != null) {
					response.getOutputStream().write(data);
				}
				else {
					byte[] logo = getServletContext().getResourceAsStream("/WEB-INF/logo.png").readAllBytes();
					response.getOutputStream().write(logo);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
	}

}
