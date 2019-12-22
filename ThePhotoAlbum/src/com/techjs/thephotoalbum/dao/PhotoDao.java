package com.techjs.thephotoalbum.dao;

import java.sql.SQLException;
import java.util.List;

import com.techjs.thephotoalbum.models.Album;
import com.techjs.thephotoalbum.models.Photo;

public interface PhotoDao {
	List<Photo> getAllPhotosOfAlbum(Long userId, Long albumId) throws SQLException;
	List<Photo> getAllPhotosOfAlbumInLimit(Long userId, Long albumId, int offset, int limit) throws SQLException;
	List<Photo> getRecentPhotos(Long userId, Integer offset, Integer limit) throws SQLException;
	List<Photo> getFavouritePhotos(Long userId, Integer offset, Integer limit) throws SQLException;
	Integer getTotalNoOfPhotosInAlbum(Long userId, Long albumId) throws SQLException;
	Integer getTotalNoOfPhotosOfUser(Long userId) throws SQLException;
	Integer getTotalNoOfFavouritePhotos(Long userId) throws SQLException;
	Photo getPhoto(Long photoId, Album album) throws SQLException;
	Photo getPhoto(Long photoId, Long albumId, Long userId) throws SQLException;
	void updatePhoto(Photo photo) throws SQLException;
	void insertPhoto(Photo photo) throws SQLException;
	void deletePhoto(Photo photo) throws SQLException;
	void markFavourite(Photo photo) throws SQLException;
}
