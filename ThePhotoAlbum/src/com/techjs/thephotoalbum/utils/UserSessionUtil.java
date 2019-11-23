package com.techjs.thephotoalbum.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.techjs.thephotoalbum.auth.UserSession;

public class UserSessionUtil {
	public static Long getUserIdFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession();
		UserSession userSession = (UserSession) session.getAttribute(Constants.USER_SESSION);
		if (userSession.isUserLoggedin()) {
			return userSession.getCurrentUser().getId();
		}
		return -1L;
	}
}
