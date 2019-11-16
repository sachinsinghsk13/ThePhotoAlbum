package com.techjs.thephotoalbum.web.ajax;

import java.io.IOException;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.techjs.thephotoalbum.auth.UserSession;
import com.techjs.thephotoalbum.dao.AlbumDao;
import com.techjs.thephotoalbum.models.Album;
import com.techjs.thephotoalbum.utils.Constants;

@WebServlet("/App/AlbumList")
public class AlbumList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute(Constants.USER_SESSION);
		Long userId = userSession.getCurrentUser().getId();
		AlbumDao albumDao = (AlbumDao) getServletContext().getAttribute(Constants.ALBUM_DAO);
		try {
			List<Album> albums = albumDao.getAllAlbumOfUser(userId);
			Type type = new TypeToken<List<Album>>() {}.getType();
			Gson gson = new Gson();
			String json = gson.toJson(albums, type);
			response.getWriter().print(json);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
