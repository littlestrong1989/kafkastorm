package com.wyd.kafkastorm;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;

public class DbUtil {

	public static Connection getConn() {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://192.168.0.171:3306/gunsoul_gm_tool_v2";
		String username = "root";
		String password = "root";
		Connection conn = null;
		try {
			Class.forName(driver); // classLoader,加载对应驱动
			conn = (Connection) DriverManager.getConnection(url, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static int insert(String target, Date time, String districtServer, String channel,
			Integer counts) {
		Connection conn = getConn();
		int i = 0;
		String sql = "insert into t_daily_statistics (target,time,districtServer,channel,counts) values(?,?,?,?,?)";
		PreparedStatement pstm = null;
		try {
			pstm = (PreparedStatement) conn.prepareStatement(sql);
			pstm.setString(1, target);
			pstm.setTimestamp(2, new Timestamp((time).getTime()));
			pstm.setString(3, districtServer);
			pstm.setString(4, channel);
			pstm.setInt(5, counts);
			i = pstm.executeUpdate();
			pstm.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null)
					pstm.close();
			} catch (SQLException se2) {

			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException se) {
				se.printStackTrace();
			}
		}
		return i;
	}
}
