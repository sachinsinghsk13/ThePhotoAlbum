package com.techjs.thephotoalbum.auth;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.techjs.thephotoalbum.utils.Constants;

@WebFilter(description = "This Fileter Provides Security to The Application", urlPatterns = { "/App/*" })
public class AuthenticationFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession(false); // returns only new session
		// this is a old session
		if (session != null) {
			// check if any user is logged in
			UserSession userSession = (UserSession) session.getAttribute(Constants.USER_SESSION);
			if (userSession == null) {
				res.sendRedirect(req.getContextPath());
			}
			else if (!userSession.isUserLoggedin()) {
				res.sendRedirect(req.getContextPath());
			}
			else {
				chain.doFilter(request, response);
			}
		}
		
		// This is a new session and user have to login to view the private resource
		else {
				res.sendRedirect(req.getContextPath());
		}
	}


}
