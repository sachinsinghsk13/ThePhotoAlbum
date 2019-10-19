package com.techjs.thephotoalbum;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techjs.thephotoalbum.utils.Constants;

/**
 * Bootstraping class for the Application
 *
 */
@WebListener
public class ThePhotoAlbum implements ServletContextListener {
	private static Logger logger = LoggerFactory.getLogger(ThePhotoAlbum.class);
	private Properties databaseConfigs;
    public ThePhotoAlbum() throws IOException {
    	logger.info("Starting ThePhotoAlbum");
    	databaseConfigs = new Properties();
    	InputStream is = ThePhotoAlbum.class.getResourceAsStream("/datasource.properties");
    	databaseConfigs.load(is);
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
        
    }


    public void contextInitialized(ServletContextEvent sce)  { 
         ServletContext servletContext = sce.getServletContext();
         
         logger.info("Configuring Database Connection Pool");
         
         
         BasicDataSource dataSource = new BasicDataSource();
         dataSource.setDriverClassName(databaseConfigs.getProperty("jdbc.driverClassName"));
         dataSource.setUrl(databaseConfigs.getProperty("jdbc.url"));
         dataSource.setUsername(databaseConfigs.getProperty("jdbc.username"));
         dataSource.setPassword(databaseConfigs.getProperty("jdbc.password"));
         
         servletContext.setAttribute(Constants.DATASOURCE, dataSource);
    }
	
}
