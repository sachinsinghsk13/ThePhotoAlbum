package com.techjs.thephotoalbum.dao;

import java.sql.SQLException;
import java.util.List;

import com.techjs.thephotoalbum.models.Album;

public interface AlbumDao {
	List<Album> getAllAlbumOfUser(Long id) throws SQLException;
	List<Album> getAllAlbumOfUserInLimit(Long id, int offset, int limit) throws SQLException;
	int getTotalNoOfAlbumOfUser(Long id) throws SQLException;
	Album getAlbumOfUser(Long userId, Long albumId) throws SQLException;
	void updateAlbum(Album album) throws SQLException;
	void insertAlbum(Album album) throws SQLException;
	void deleteAlbum(Long userId, Long albumId) throws SQLException;
}
