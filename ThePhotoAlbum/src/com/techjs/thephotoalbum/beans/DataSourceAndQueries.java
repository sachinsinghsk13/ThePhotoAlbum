package com.techjs.thephotoalbum.beans;

import javax.sql.DataSource;

import com.techjs.thephotoalbum.utils.SQLQueries;

public class DataSourceAndQueries {
	private DataSource dataSource;
	private SQLQueries sqlQueries;
	
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	public SQLQueries getSqlQueries() {
		return sqlQueries;
	}
	public void setSqlQueries(SQLQueries sqlQueries) {
		this.sqlQueries = sqlQueries;
	}
	
}
