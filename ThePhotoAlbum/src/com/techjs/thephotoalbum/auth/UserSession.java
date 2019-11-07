package com.techjs.thephotoalbum.auth;

import java.sql.SQLException;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import com.techjs.thephotoalbum.dao.UserDao;
import com.techjs.thephotoalbum.models.User;

@WebListener
public class UserSession implements HttpSessionBindingListener {
	private boolean userLoggedin;
	private User currentUser;
	private UserDao userDao;
	
    public UserSession(UserDao userDao) {
        this.userDao = userDao;
        this.userLoggedin = false;
    }

    
    public UserSession() {
		super();
	}

    public boolean isUserLoggedin() {
		return userLoggedin;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}


	public User getCurrentUser() {
		return currentUser;
	}


	public void login(User user) {
		this.currentUser = user;
		this.userLoggedin = true;
	}
	
	
	public void logout() {
		this.currentUser = null;
		this.userLoggedin = false;
	}
	
	
	public void valueBound(HttpSessionBindingEvent event)  { 
       
    }


    public void valueUnbound(HttpSessionBindingEvent event)  { 
        update();
    }
	
    public void update() {
    	if (userLoggedin) {
        	try {
				userDao.updateUser(this.currentUser);
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
    }
}
