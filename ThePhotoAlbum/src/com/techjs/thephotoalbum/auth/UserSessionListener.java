package com.techjs.thephotoalbum.auth;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.techjs.thephotoalbum.utils.Constants;

@WebListener
public class UserSessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent se)  { 
         UserSession userSession = new UserSession();
         se.getSession().setAttribute(Constants.USER_SESSION, userSession);
         System.out.println("Created.....");
    }

    public void sessionDestroyed(HttpSessionEvent se)  { 
        
    }
	
}
