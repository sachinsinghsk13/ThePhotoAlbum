package com.techjs.thephotoalbum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.sun.mail.util.QDecoderStream;
import com.techjs.thephotoalbum.beans.DataSourceAndQueries;
import com.techjs.thephotoalbum.models.Album;
import com.techjs.thephotoalbum.models.Photo;
import com.techjs.thephotoalbum.utils.ImageQuality;
import com.techjs.thephotoalbum.utils.Orientation;
import com.techjs.thephotoalbum.utils.SQLQueries;
import com.techjs.thephotoalbum.utils.SQLQueriesConstants;

public class PhotoDaoImpl implements PhotoDao {
	private DataSource dataSource;
	private SQLQueries queries;
	
	public PhotoDaoImpl(DataSourceAndQueries dataSourceAndQueries) {
		this.dataSource = dataSourceAndQueries.getDataSource();
		this.queries = dataSourceAndQueries.getSqlQueries();
	}
	
	public PhotoDaoImpl(DataSource dataSource, SQLQueries sqlQueries) {
		this.dataSource = dataSource;
		this.queries = sqlQueries;
	}
	
	@Override
	public List<Photo> getAllPhotosOfAlbum(Long userId, Long albumId) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.GET_ALL_PHOTOS_OF_ALBUM));
		statement.setLong(1, userId);
		statement.setLong(2, albumId);
		ResultSet rs = statement.executeQuery();
		List<Photo> photos = new ArrayList<Photo>();
		while (rs.next()) {
			Photo photo = new Photo();
			photo.setId(rs.getLong(1));
			photo.setTitle(rs.getString(2));
			photo.setDescription(rs.getString(3));
			photo.setUploadDate(rs.getDate(4));
			photo.setBinaryData(rs.getBytes(5));
			photo.setThumbBinaryData(rs.getBytes(6));
			photo.setWidth(rs.getInt(7));
			photo.setHeight(rs.getInt(8));
			photo.setOrientation(Orientation.valueOf(rs.getString(9)));
			photo.setQuality(ImageQuality.valueOf(rs.getString(10)));
			photo.setUserId(userId);
			photo.setAlbumId(albumId);
			photos.add(photo);
		}
		connection.close();
		return photos;
	}

	@Override
	public List<Photo> getAllPhotosOfAlbumInLimit(Long userId, Long albumId, int offset, int limit)
			throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.GET_ALL_PHOTOS_OF_ALBUM));
		statement.setLong(1, userId);
		statement.setLong(2, albumId);
		statement.setInt(3, offset);
		statement.setInt(4, limit);
		ResultSet rs = statement.executeQuery();
		List<Photo> photos = new ArrayList<Photo>();
		while (rs.next()) {
			Photo photo = new Photo();
			photo.setId(rs.getLong(1));
			photo.setTitle(rs.getString(2));
			photo.setDescription(rs.getString(3));
			photo.setUploadDate(rs.getDate(4));
			photo.setBinaryData(rs.getBytes(5));
			photo.setThumbBinaryData(rs.getBytes(6));
			photo.setWidth(rs.getInt(7));
			photo.setHeight(rs.getInt(8));
			photo.setOrientation(Orientation.valueOf(rs.getString(9)));
			photo.setQuality(ImageQuality.valueOf(rs.getString(10)));
			photo.setUserId(userId);
			photo.setAlbumId(albumId);
			photos.add(photo);
		}
		connection.close();
		return photos;
	
	}

	@Override
	public int getTotalNoOfPhotosInAlbum(Long userId, Long albumId) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Photo getPhoto(Long photoId, Album album) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePhoto(Photo photo, Album album) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void insertPhoto(Photo photo, Album album) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void deletePhoto(Long photoId, Album album) throws SQLException {
		// TODO Auto-generated method stub

	}

}
