package com.techjs.thephotoalbum;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.techjs.thephotoalbum.utils.Constants;
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
		} catch (IOException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
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
		Properties properties = new Properties();
		properties.load(schema_ddl);
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
}
