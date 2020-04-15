package File;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import DBConnect.DBConnector;

public class FileDAO {
	private DBConnector dbConnector;
	private Connection conn;
	
	private String sql;
	private PreparedStatement pstmt;
	
	private Properties path;
	private FileInputStream fis_path;
	private ResultSet rs;
	
	
	public FileDAO() {
		dbConnector = new DBConnector();
	}
	
	public String getPath() {
		try {
			//  파일 저장경로 불러오기
			path = new Properties();
			fis_path = new FileInputStream("/volume1/Secutiry/LabWebSite/path.properties");
			path.load(new BufferedInputStream(fis_path));
			
			return path.getProperty("path");
		} catch (FileNotFoundException e) { //예외처리 ,대응부재 제거
			System.err.println("FileDAO getPath FileNotFoundException error");
		} catch (IOException e) {
			System.err.println("FileDAO getPath IOException error");
		} finally {    //  자원 해제
			try {
				if(fis_path != null) {
					fis_path.close();
				}
			} catch (IOException e) {
				System.err.println("FileDAO getPath close IOException error");
			}
		}
		return "";
	}
	
	public int upload(String category, int BoardID, String fileName, String fileRealName) {
		sql = "insert into "+ category +"File(BoardID, FileName, FileRealName) value (?, ?, ?)";
		conn = dbConnector.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, BoardID);
			pstmt.setString(2, fileName);
			pstmt.setString(3, fileRealName);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("FileDAO upload SQLException error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch(SQLException e) {
				System.err.println("FiletDAO upload close SQLException error");
			}
		}
		
		return -1;
	}
	
	public String getFile(String category, int BoardID) {
		sql = "select FileName, FileRealName from " + category + "File where BoardID = ?";
		conn = dbConnector.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, BoardID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return "have," + rs.getString(1) + "," + rs.getString(2);
			} else {
				return "nohave";
			}
		} catch (SQLException e) {
			System.err.println("FileDAO getFile SQLException error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("FiletDAO upload close SQLException error");
			}
		}
		
		return "error";
	}
}