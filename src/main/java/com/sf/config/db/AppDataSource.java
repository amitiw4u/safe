package com.sf.config.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class AppDataSource {

//	private static Logger log = LoggerFactory.getLogger(AppDataSource.class);
	
	@Primary
	@Bean(name = "dataSourceProperties")
	public DataSourceProperties dataSourceProperties() {
		return new DataSourceProperties();
	}
	
	@Primary
	@Bean(name = "commonDataSource")
	public DataSource dataSource() {
		DataSourceProperties dataSourceProperties = dataSourceProperties();
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setUrl(dataSourceProperties.getUrl());
		dataSource.setUsername(dataSourceProperties.getUsername());
		dataSource.setPassword(dataSourceProperties.getPassword());
		return dataSource;
	}

	/**
	 * To close resources.
	 * 
	 * @param resultSet
	 *            the result set
	 * @param statement
	 *            the statement
	 * @param connection
	 *            the connection
	 */
	public static void closeResources(ResultSet resultSet, PreparedStatement statement, Connection connection) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				if (log.isErrorEnabled()) {
					log.error("SQLException occured while closing connection:", e);
				}

			}
			resultSet = null;
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				if (log.isErrorEnabled()) {
					log.error("SQLException occured while closing statement:", e);
				}

			}
			statement = null;
		}
		if (connection != null) {
			setAutoCommitPropetry(true, connection);
			try {
				connection.close();
			} catch (SQLException e) {
				if (log.isErrorEnabled()) {
					log.error("SQLException occured while closing resultset:", e);
				}

			}
			connection = null;
		}
	}

	private static void setAutoCommitPropetry(boolean autoCommit, Connection con) {
		try {
			if (con == null || con.isClosed()) {
				return;
			}
			if (autoCommit == con.getAutoCommit()) {
				return;
			}
			con.setAutoCommit(autoCommit);
		} catch (Exception e) {
			log.info(con.toString());
			log.error("Exception occured while resetting property for autoCommit:", e);
		}
	}

	/**
	 * Rollbcak a given database connection
	 * 
	 * @param con
	 */
	public static void rollbackConnection(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
				log.error("Failed to rollback connection");
			}
		}
	}

	public Connection getConnection() throws SQLException {
		return dataSource().getConnection();
	}
	
}
