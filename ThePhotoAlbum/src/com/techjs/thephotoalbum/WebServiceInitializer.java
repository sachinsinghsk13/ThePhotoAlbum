package com.techjs.thephotoalbum;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import org.glassfish.jersey.servlet.ServletContainer;

@WebListener
public class WebServiceInitializer implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent sce)  { 
    }


    public void contextInitialized(ServletContextEvent sce)  { 
       ServletContext sc = sce.getServletContext();
       ServletRegistration.Dynamic jersey = sc.addServlet("jersey", ServletContainer.class);
       jersey.setInitParameter("jersey.config.server.provider.packages", "com.techjs.thephotoalbum.web.rest.resources");
       jersey.setLoadOnStartup(1);
       jersey.addMapping("/webapi/*");
    }
	
}
