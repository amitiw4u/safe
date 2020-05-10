package com.sf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.sf.config.db.AppDataSource;
import com.sf.exception.SafeException;

/**
 * @author Amitabh
 *
 */
@Service
public class BaseDao {

	@Autowired
	@Qualifier("commonDataSource")
	private DataSource ds;

	public Boolean insertDetails(Object obj) {
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ds.getConnection();
		} catch (Exception e) {
			throw new SafeException("Unable to fetch data", e);
		} finally {
			AppDataSource.closeResources(null, pstmt, con);
		}
		return false;
	}

	public List<Map<String, Object>> fetchResult(String query, List<?> params) {
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(query);
			updateParams(pstmt, params);
			rs = pstmt.executeQuery();
			return fetchResult(rs);
		} catch (Exception e) {
			throw new SafeException("Unable to fetch data", e);
		} finally {
			AppDataSource.closeResources(rs, pstmt, con);
		}
	}

	public List<Map<String, Object>> fetchResult(ResultSet rs) throws SQLException {
		List<Map<String, Object>> result = new ArrayList<>();
		ResultSetMetaData rsMetaData = rs.getMetaData();
		int numberOfColumns = rsMetaData.getColumnCount();
		while (rs.next()) {
			Map<String, Object> rowResult = new HashMap<>();
			for (int i = 1; i < numberOfColumns + 1; i++) {
				String columnName = rsMetaData.getColumnLabel(i);
				rowResult.put(columnName, rs.getObject(columnName));
			}
			result.add(rowResult);
		}
		return result;
	}

	protected void updateParams(PreparedStatement pstmt, List<?> params) {
		try {

			for (int i = 1; i <= params.size(); i++) {
				Object param = params.get(i - 1);
				if (param instanceof Integer) {
					pstmt.setInt(i, (Integer) param);
				} else if (param instanceof Double) {
					pstmt.setDouble(i, (Double) param);
				} else if (param instanceof Byte) {
					pstmt.setByte(i, (Byte) param);
				} else if (param instanceof Boolean) {
					pstmt.setBoolean(i, (Boolean) param);
				} else {
					pstmt.setString(i, param.toString());
				}
			}
		} catch (Exception e) {
			throw new SafeException("Not able to add params.", e);
		}

	}
}
