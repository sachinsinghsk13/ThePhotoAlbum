package com.techjs.thephotoalbum.dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.techjs.thephotoalbum.beans.DataSourceAndQueries;
import com.techjs.thephotoalbum.models.Album;
import com.techjs.thephotoalbum.utils.SQLQueries;
import com.techjs.thephotoalbum.utils.SQLQueriesConstants;

public class AlbumDaoImpl implements AlbumDao {
	private DataSource dataSource;
	private SQLQueries queries;
	
	public AlbumDaoImpl(DataSourceAndQueries dataSourceAndQueries) {
		this.dataSource = dataSourceAndQueries.getDataSource();
		this.queries = dataSourceAndQueries.getSqlQueries();
	}
	
	public AlbumDaoImpl(DataSource dataSource, SQLQueries sqlQueries) {
		this.dataSource = dataSource;
		this.queries = sqlQueries;
	}
	
	@Override
	public List<Album> getAllAlbumOfUser(Long id) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.GET_ALL_ALBUM_OF_USER));
		statement.setLong(1, id);
		ResultSet rs = statement.executeQuery();
		List<Album> albums = new ArrayList<Album>();
		while (rs.next()) {
			Album album = new Album();
			album.setId(rs.getLong(1));
			album.setTitle(rs.getString(2));
			album.setDescription(rs.getString(3));
			album.setCreateTime(rs.getDate(4));
			album.setLastPhotoUploadTime(rs.getDate(5));
			album.setUserId(id);
			albums.add(album);
		}
		connection.close();
		return albums;
	}

	@Override
	public List<Album> getAllAlbumOfUserInLimit(Long id, int offset, int limit) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.GET_ALL_ALBUM_OF_USER));
		statement.setLong(1, id);
		statement.setInt(2, offset);
		statement.setInt(3, limit);
		ResultSet rs = statement.executeQuery();
		List<Album> albums = new ArrayList<Album>();
		while (rs.next()) {
			Album album = new Album();
			album.setId(rs.getLong(1));
			album.setTitle(rs.getString(2));
			album.setDescription(rs.getString(3));
			album.setCreateTime(rs.getDate(4));
			album.setLastPhotoUploadTime(rs.getDate(5));
			album.setUserId(id);
			albums.add(album);
		}
		connection.close();
		return albums;
	}

	@Override
	public int getTotalNoOfAlbumOfUser(Long id) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.GET_TOTAL_NO_OF_ALBUMS_OF_USER));
		statement.setLong(1, id);
		ResultSet rs = statement.executeQuery();
		int count = 0;
		if (rs.next()) {
			count = rs.getInt(1);
		}
		connection.close();
		return count;
	}

	@Override
	public Album getAlbumOfUser(Long userId, Long albumId) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.GET_ALBUM_OF_USER));
		statement.setLong(1, userId);
		statement.setLong(2, albumId);
		ResultSet rs = statement.executeQuery();
		Album album = null;
		if (rs.next()) {
			album = new Album();
			album.setTitle(rs.getString(1));
			album.setDescription(rs.getString(2));
			album.setCreateTime(rs.getDate(3));
			album.setLastPhotoUploadTime(rs.getDate(4));
			album.setId(albumId);
			album.setUserId(userId);
		}
		connection.close();
		return album;
	}

	@Override
	public void updateAlbum(Album album) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.UPDATE_ALBUM));
		statement.setString(1, album.getTitle());
		statement.setString(2, album.getDescription());
		statement.setBinaryStream(3, new ByteArrayInputStream(album.getAlbumCover()));
		statement.setLong(4, album.getUserId());
		statement.setLong(5, album.getId());
		statement.executeUpdate();
		connection.close();
	}

	@Override
	public void insertAlbum(Album album) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.INSERT_ALBUM), Statement.RETURN_GENERATED_KEYS);
		statement.setLong(1, album.getUserId());
		statement.setString(2, album.getTitle());
		statement.setString(3, album.getDescription());
		statement.setBinaryStream(4, new ByteArrayInputStream(album.getAlbumCover()));
		statement.executeUpdate();
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()) {
			album.setId(rs.getLong(1));
		}
		connection.close();
	}

	@Override
	public void deleteAlbum(Long userId, Long albumId) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.DELETE_ALBUM));
		statement.setLong(1, userId);
		statement.setLong(2, albumId);
		statement.executeUpdate();
		connection.close();
	}

	@Override
	public byte[] getAlbumCover(Long userId, Long albumId) throws SQLException {
		Connection connection = dataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.GET_ALBUM_COVER));
		statement.setLong(1, userId);
		statement.setLong(2, albumId);
		ResultSet rs = statement.executeQuery();
		byte[] data = null;
		if (rs.next()) {
			data = rs.getBytes(1);
		}
		connection.close();
		return data;
	}
	
}
