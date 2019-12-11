package com.techjs.thephotoalbum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.sun.mail.util.QDecoderStream;
import com.techjs.thephotoalbum.beans.DataSourceAndQueries;
import com.techjs.thephotoalbum.models.Album;
import com.techjs.thephotoalbum.models.Photo;
import com.techjs.thephotoalbum.utils.Favourite;
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
			photo.setFavourite(Favourite.valueOf(rs.getString(11)));
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
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.GET_ALL_PHOTOS_OF_ALBUM_IN_LIMIT));
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
			photo.setFileSize(rs.getDouble(5));
			photo.setBinaryData(rs.getBytes(6));
			photo.setThumbBinaryData(rs.getBytes(7));
			photo.setWidth(rs.getInt(8));
			photo.setHeight(rs.getInt(9));
			photo.setOrientation(Orientation.valueOf(rs.getString(10)));
			photo.setQuality(ImageQuality.valueOf(rs.getString(11)));
			photo.setFavourite(Favourite.valueOf(rs.getString(12)));
			photo.setUserId(userId);
			photo.setAlbumId(albumId);
			photos.add(photo);
		}
		connection.close();
		return photos;
	
	}

	@Override
	public int getTotalNoOfPhotosInAlbum(Long userId, Long albumId) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.GET_TOTAL_NO_OF_PHOTOS_IN_ALBUM));
		statement.setLong(1, userId);
		statement.setLong(2, albumId);
		ResultSet rs = statement.executeQuery();
		int count = 0;
		if (rs.next()) {
			count = rs.getInt(1);
		}
		connection.close();
		return count;
	}

	@Override
	public Photo getPhoto(Long photoId, Album album) throws SQLException {
		return getPhoto(photoId, album.getId(), album.getUserId());
	}

	@Override
	public void updatePhoto(Photo photo, Album album) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.UPDATE_PHOTO));
		statement.setString(1, photo.getTitle());
		statement.setString(2, photo.getDescription());
		statement.setLong(3, album.getUserId());
		statement.setLong(4, album.getId());
		statement.setLong(5, photo.getId());
		statement.executeUpdate();
		connection.close();
	}

	@Override
	public void insertPhoto(Photo photo) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.INSERT_PHOTO), Statement.RETURN_GENERATED_KEYS);
		statement.setLong(1, photo.getUserId());
		statement.setLong(2, photo.getAlbumId());
		statement.setString(3, photo.getTitle());
		statement.setString(4, photo.getDescription());
		statement.setDouble(5, photo.getFileSize());
		statement.setBytes(6, photo.getBinaryData());
		statement.setBytes(7, photo.getThumbBinaryData());
		statement.setInt(8, photo.getWidth());
		statement.setInt(9, photo.getHeight());
		statement.setString(10, photo.getOrientation().toString());
		statement.setString(11, photo.getQuality().toString());
		statement.executeUpdate();
		ResultSet rs = statement.getGeneratedKeys();
		long key = 0;
		if (rs.next()) {
			key = rs.getLong(1);
			photo.setId(key);
		}
		connection.close();
	}

	@Override
	public void deletePhoto(Long photoId, Album album) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.DELETE_PHOTO));
		statement.setLong(3, album.getUserId());
		statement.setLong(4, album.getId());
		statement.setLong(5, photoId);
		statement.executeUpdate();
		connection.close();
	}

	@Override
	public Photo getPhoto(Long photoId, Long albumId, Long userId) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.GET_PHOTO));
		statement.setLong(1, userId);
		statement.setLong(2, albumId);
		statement.setLong(3, photoId);
		ResultSet rs = statement.executeQuery();
		Photo photo = new Photo();
		if (rs.next()) {
			photo.setId(rs.getLong(1));
			photo.setTitle(rs.getString(2));
			photo.setDescription(rs.getString(3));
			photo.setUploadDate(rs.getDate(4));
			photo.setFileSize(rs.getDouble(5));
			photo.setBinaryData(rs.getBytes(6));
			photo.setThumbBinaryData(rs.getBytes(7));
			photo.setWidth(rs.getInt(8));
			photo.setHeight(rs.getInt(9));
			photo.setOrientation(Orientation.valueOf(rs.getString(10)));
			photo.setQuality(ImageQuality.valueOf(rs.getString(11)));
			photo.setFavourite(Favourite.valueOf(rs.getString(12)));
			photo.setUserId(userId);
			photo.setAlbumId(albumId);
		}
		connection.close();
		return photo;
	}

	@Override
	public List<Photo> getRecentPhotos(Long userId) throws SQLException {
		List<Photo> photos = new ArrayList<Photo>();
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.GET_RECENT_PHOTOS));
		statement.setLong(1, userId);
		ResultSet rs = statement.executeQuery();
		while (rs.next()) {
			Photo photo = new Photo();
			photo.setId(rs.getLong(1));
			photo.setTitle(rs.getString(2));
			photo.setDescription(rs.getString(3));
			photo.setAlbumId(rs.getLong(4));
			photos.add(photo);
		}
		connection.close();
		return photos;
	}
}
