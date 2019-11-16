package com.techjs.thephotoalbum.web.ajax;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.techjs.thephotoalbum.auth.UserSession;
import com.techjs.thephotoalbum.dao.PhotoDao;
import com.techjs.thephotoalbum.dao.UserDao;
import com.techjs.thephotoalbum.models.JsPhotoModel;
import com.techjs.thephotoalbum.models.Photo;
import com.techjs.thephotoalbum.utils.Constants;
import com.techjs.thephotoalbum.utils.ImageQuality;
import com.techjs.thephotoalbum.utils.Orientation;

@WebServlet("/App/PhotoUpload")
@MultipartConfig(location = "/home/sachinsingh/Workspace", fileSizeThreshold = 2 * 1024 * 1024)
public class PhotoUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Part payload_info_part = request.getPart("payload_info");
		String json = new String(payload_info_part.getInputStream().readAllBytes());
		Type type = new TypeToken<List<JsPhotoModel>>() {
		}.getType();
		List<JsPhotoModel> payload_info = new Gson().fromJson(json, type);
		Part albumIdPart = request.getPart("albumId");
		Long albumId = Long.valueOf(new String(albumIdPart.getInputStream().readAllBytes()));
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute(Constants.USER_SESSION);
		Long userId = userSession.getCurrentUser().getId();
		System.out.println(userId + " : " + albumId);

		PhotoDao photoDao = (PhotoDao) getServletContext().getAttribute(Constants.PHOTO_DAO);
		try {

			for (JsPhotoModel model : payload_info) {
				Part part = request.getPart(model.getFilename());

				Photo photo = new Photo();
				photo.setUserId(userId);
				photo.setAlbumId(albumId);
				photo.setTitle(model.getTitle());
				photo.setDescription(model.getDescription());
				photo.setFileSize(model.getSize());
				photo.setWidth(model.getWidth());
				photo.setHeight(model.getHeight());
				photo.setQuality(ImageQuality.valueOf(model.getQuality()));
				photo.setOrientation(Orientation.valueOf(model.getOrientation()));
				photo.setBinaryData(part.getInputStream().readAllBytes());
				photo.setThumbBinaryData(photo.getBinaryData());
				photoDao.insertPhoto(photo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
