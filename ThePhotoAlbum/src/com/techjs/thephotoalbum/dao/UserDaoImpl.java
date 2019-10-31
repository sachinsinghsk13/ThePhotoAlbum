package com.techjs.thephotoalbum.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.techjs.thephotoalbum.beans.DataSourceAndQueries;
import com.techjs.thephotoalbum.beans.Login;
import com.techjs.thephotoalbum.models.User;
import com.techjs.thephotoalbum.utils.Gender;
import com.techjs.thephotoalbum.utils.SQLQueries;
import com.techjs.thephotoalbum.utils.SQLQueriesConstants;

public class UserDaoImpl implements UserDao {
	
	private DataSource datasource;
	private SQLQueries queries;
	
	public UserDaoImpl(DataSourceAndQueries dataSourceAndQueries) {
		this.datasource = dataSourceAndQueries.getDataSource();
		this.queries = dataSourceAndQueries.getSqlQueries();
	}
	public UserDaoImpl(DataSource dataSource, SQLQueries queries) {
		this.datasource = dataSource;
		this.queries = queries;
	}
	@Override
	public List<User> getAllUsers() throws SQLException {
		Connection connection = datasource.getConnection();
		String sql = queries.getQuery(SQLQueriesConstants.GET_ALL_USERS);
		System.out.println(sql);
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.GET_ALL_USERS));
		ResultSet resultSet = statement.executeQuery();
		List<User> users = new ArrayList<User>();
		while(resultSet.next()) {
			User user = new User();
			user.setId(resultSet.getLong(1));
			user.setName(resultSet.getString(2));
			user.setEmail(resultSet.getString(3));
			user.setDob(resultSet.getDate(4));
			user.setGender(Gender.valueOf(Gender.class, resultSet.getString(5)));
			users.add(user);
		}
		connection.close();
		return users;
	}

	@Override
	public User getUserById(Long id) throws SQLException {
		Connection connection = datasource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.GET_USER_BY_ID));
		statement.setLong(1, id);
		ResultSet rs = statement.executeQuery();
		User user = null;
		if (rs.next()) {
			user = new User();
			user.setId(rs.getLong(1));
			user.setName(rs.getString(2));
			user.setEmail(rs.getString(3));
			user.setDob(rs.getDate(4));
			user.setGender(Gender.valueOf(rs.getString(5)));
		}
		connection.close();
		return user;
	}

	@Override
	public void updateUser(User user) throws SQLException {
		Connection connection = datasource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.UPDATE_USER));
		statement.setString(1, user.getName());
		statement.setString(2, user.getPassword());
		statement.setLong(3, user.getId());
		statement.executeUpdate();
		connection.close();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void insertUser(User user) throws SQLException {
		Connection connection = datasource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.INSERT_NEW_USER),Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, user.getName());
		statement.setString(2, user.getEmail());
		statement.setDate(3, new Date(user.getDob().getYear(),user.getDob().getMonth(), user.getDob().getDate()));
		statement.setString(4, user.getGender().toString());
		statement.setString(5, user.getPassword());
		statement.execute();
		ResultSet rs = statement.getGeneratedKeys();
		if (rs.next()) {
			user.setId(rs.getLong(1));
		}
		connection.close();
		
	}

	@Override
	public void deleteUser(Long id) throws SQLException {
		Connection connection = datasource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.DELETE_USER));
		statement.setLong(1, id);
		statement.executeUpdate();
		connection.close();
	}

	@Override
	public boolean isUserAuthenticated(Login login) throws SQLException {
		Connection connection = datasource.getConnection();
		PreparedStatement statement = connection.prepareStatement(queries.getQuery(SQLQueriesConstants.AUTHENTICATE_USER));
		statement.setString(1, login.getEmail());
		statement.setString(2, login.getPassword());
		ResultSet rs = statement.executeQuery();
		int i = 0;
		if (rs.next()) {
			i = rs.getInt(1);		
		}
		return (i == 1) ? true : false;
	}
	@Override
	public boolean isEmailAvailble(String email) throws SQLException {
		Connection connection = datasource.getConnection();
		ResultSet rs = connection.createStatement().executeQuery("SELECT COUNT(*) FROM USERS WHERE EMAIL = \'" + email + "\';");
		boolean result = true;
		if (rs.next()) {
			if (rs.getInt(1) == 1)
				result = false;
		}
		return result;
	}

}
