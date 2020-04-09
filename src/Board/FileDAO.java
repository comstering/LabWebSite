package Board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnect.DBConnector;

public class FileDAO {
	private DBConnector dbConnector;
	private Connection conn;
	
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public FileDAO() {
		dbConnector = new DBConnector();
	}
	
	public int upload(String category, int BoardID, String fileName) {
		sql = "insert into ?File(BoardID, FileName) value (?, ?)";
		conn = dbConnector.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setInt(2, BoardID);
			pstmt.setString(3, fileName);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return -1;
	}
}
