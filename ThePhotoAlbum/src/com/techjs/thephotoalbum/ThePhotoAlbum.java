package com.techjs.thephotoalbum;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techjs.thephotoalbum.auth.EmailClient;
import com.techjs.thephotoalbum.auth.GmailAuthenticator;
import com.techjs.thephotoalbum.beans.DataSourceAndQueries;
import com.techjs.thephotoalbum.beans.Login;
import com.techjs.thephotoalbum.dao.AlbumDao;
import com.techjs.thephotoalbum.dao.AlbumDaoImpl;
import com.techjs.thephotoalbum.dao.PhotoDao;
import com.techjs.thephotoalbum.dao.PhotoDaoImpl;
import com.techjs.thephotoalbum.dao.UserDao;
import com.techjs.thephotoalbum.dao.UserDaoImpl;
import com.techjs.thephotoalbum.models.Photo;
import com.techjs.thephotoalbum.models.User;
import com.techjs.thephotoalbum.utils.Constants;
import com.techjs.thephotoalbum.utils.Gender;
import com.techjs.thephotoalbum.utils.SQLQueries;
import com.techjs.thephotoalbum.utils.SQLQueriesConstants;

/**
 * Bootstraping class for the Application
 *
 */
@WebListener
public class ThePhotoAlbum implements ServletContextListener {
	private static Logger logger = LoggerFactory.getLogger(ThePhotoAlbum.class);
	private ServletContext servletContext;

	public ThePhotoAlbum() throws IOException {
		logger.info("Starting ThePhotoAlbum");
	}

	public void contextDestroyed(ServletContextEvent sce) {

	}

	public void contextInitialized(ServletContextEvent sce) {
		this.servletContext = sce.getServletContext();
		try {
			configureDataSource();
			loadSQLQueries();
			generateSchema();
			setupDao();
			setupGmailClient();
			
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
	}

	private void setupDao() {
		DataSource dataSource = (DataSource) servletContext.getAttribute(Constants.DATASOURCE);
		SQLQueries queries = (SQLQueries) servletContext.getAttribute(Constants.SQLQueries);
		DataSourceAndQueries dsq = new DataSourceAndQueries();
		dsq.setDataSource(dataSource);
		dsq.setSqlQueries(queries);
		UserDao userDao = new UserDaoImpl(dsq);
		servletContext.setAttribute(Constants.USER_DAO, userDao);
		AlbumDao albumDao = new AlbumDaoImpl(dsq);
		servletContext.setAttribute(Constants.ALBUM_DAO, albumDao);
		PhotoDao photoDao = new PhotoDaoImpl(dsq);
		servletContext.setAttribute(Constants.PHOTO_DAO, photoDao);
	}

	private void configureDataSource() throws IOException {
		logger.info("Configuring Database Connection Pool");
		
		Properties databaseConfigs = new Properties();
		InputStream is = ThePhotoAlbum.class.getResourceAsStream("/configs/datasource.properties");
		databaseConfigs.load(is);
		logger.debug("Loaded Config Properities : " + databaseConfigs.toString());
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(databaseConfigs.getProperty("jdbc.driverClassName"));
		dataSource.setUrl(databaseConfigs.getProperty("jdbc.url"));
		dataSource.setUsername(databaseConfigs.getProperty("jdbc.username"));
		dataSource.setPassword(databaseConfigs.getProperty("jdbc.password"));
		servletContext.setAttribute(Constants.DATASOURCE, dataSource);
	}
	
	private void loadSQLQueries() throws IOException {
		InputStream schema_ddl = ThePhotoAlbum.class.getResourceAsStream("/sql/schema-ddl.properties");
		InputStream queries = ThePhotoAlbum.class.getResourceAsStream("/sql/queries.properties");
		Properties properties = new Properties();
		properties.load(schema_ddl);
		properties.load(queries);
		logger.debug("Loaded SQL Properities : " + properties.toString());
		SQLQueries sqlQueries = new SQLQueries();
		sqlQueries.addQueries(properties);
		servletContext.setAttribute(Constants.SQLQueries, sqlQueries);
	}
	
	private void generateSchema() throws SQLException {
		logger.info("GENERATING DATABASE SCHEMA");
		SQLQueries sqlQueries = (SQLQueries) servletContext.getAttribute(Constants.SQLQueries);
		DataSource dataSource = (DataSource) servletContext.getAttribute(Constants.DATASOURCE);
		Connection connection = dataSource.getConnection();
		connection.createStatement().execute(sqlQueries.getQuery(SQLQueriesConstants.CREATE_USER_TABLE));
		logger.debug("User table created");
		
		connection.createStatement().execute(sqlQueries.getQuery(SQLQueriesConstants.CREATE_ALBUM_TABLE));
		logger.debug("Album table created");
		
		connection.createStatement().execute(sqlQueries.getQuery(SQLQueriesConstants.CREATE_PHOTO_TABLE));
		logger.debug("Photo table created");
		// mobile upload album trigger
		connection.createStatement().execute(sqlQueries.getQuery(SQLQueriesConstants.DROP_MOBILE_UPLOAD_ALBUM_TRIGGER));
		connection.createStatement().execute(sqlQueries.getQuery(SQLQueriesConstants.CREATE_MOBILE_UPLOAD_ALBUM_TRIGGER));
		connection.close();
	}
	
	private void setupGmailClient() {
		InputStream mailConfig = ThePhotoAlbum.class.getResourceAsStream("/configs/javaMailConfig.properties");
		InputStream authConfig = ThePhotoAlbum.class.getResourceAsStream("/configs/gmailAuthentication.properties");
		Properties mailConfigProp = new Properties();
		Properties authConfigProp = new Properties();
		try {
			mailConfigProp.load(mailConfig);
			authConfigProp.load(authConfig);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		GmailAuthenticator authenticator = new GmailAuthenticator(authConfigProp.getProperty("email"), authConfigProp.getProperty("password"));
		EmailClient emailClient = new EmailClient(mailConfigProp, authenticator);
		servletContext.setAttribute(Constants.EMAIL_CLIENT, emailClient);
	}
	
	
}
