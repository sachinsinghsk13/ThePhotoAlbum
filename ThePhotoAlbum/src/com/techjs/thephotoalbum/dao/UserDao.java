package com.techjs.thephotoalbum.dao;

import java.sql.SQLException;
import java.util.List;

import com.techjs.thephotoalbum.beans.Login;
import com.techjs.thephotoalbum.models.User;

public interface UserDao {
	List<User> getAllUsers() throws SQLException;
	User getUserById(Long id) throws SQLException;
	void updateUser(User user) throws SQLException;
	void insertUser(User user) throws SQLException;
	void deleteUser(Long id) throws SQLException;
	boolean isUserAuthenticated(Login login) throws SQLException;
	boolean isEmailAvailble(String email) throws SQLException;

}
