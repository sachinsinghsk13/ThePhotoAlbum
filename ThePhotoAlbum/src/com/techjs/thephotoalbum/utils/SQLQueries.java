package com.techjs.thephotoalbum.utils;

import java.util.Properties;

public class SQLQueries {
	private Properties queries;
	
	public SQLQueries() {
		this.queries = new Properties();
	}
	
	public void addQueries(Properties properties) {
		this.queries.putAll(properties);
	}
	
	public String getQuery(SQLQueriesConstants queryConst) {
		
		return queries.getProperty(queryConst.toString());
	}
}
