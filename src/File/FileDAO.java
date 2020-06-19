package File;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import DBConnect.DBConnector;

public class FileDAO {
	//  DB 연결변수
	private DBConnector dbConnector;
	private Connection conn;
	
	//  SQL 질의 변수
	private String sql;
	private PreparedStatement pstmt;
	
	//  파일 저장경로 Properties를 위한 변수
	private Properties path;
	private FileInputStream fis_path;
	
	//  SQL 질의 결과 저장 변수
	private ResultSet rs;
	
	
	public FileDAO() {
		dbConnector = DBConnector.getInstance();
	}
	
	public String getPath() {    //  파일 저장경로 불러오기
		try {
			path = new Properties();
			fis_path = new FileInputStream("/volume1/Security/LabWebSite/path.properties");
			path.load(new BufferedInputStream(fis_path));
			
			return path.getProperty("path");
		} catch (FileNotFoundException e) {    //  예외처리, 대응부재 제거
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
		return "error";    //  경로 오류
	}
	
	public int upload(String category, int BoardID, String fileName, String fileRealName) {    //  파일 업로드
		sql = "insert into PostFile(Category, BoardID, FileName, FileRealName) value (?, ?, ?, ?)";
		conn = dbConnector.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setInt(2, BoardID);
			pstmt.setString(3, fileName);
			pstmt.setString(4, fileRealName);
			return pstmt.executeUpdate();
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("FileDAO upload SQLException error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch(SQLException e) {
				System.err.println("FiletDAO upload close SQLException error");
			}
		}
		
		return -1;
	}
	
	public ArrayList<String> getFile(String category, int BoardID) {    //  파일 불러오기
		sql = "select FileName, FileRealName from PostFile where Category=? and BoardID = ? and Available = 1";
		conn = dbConnector.getConnection();
		ArrayList<String> file = new ArrayList<String>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setInt(2, BoardID);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				file.add(rs.getString(1) + "," + rs.getString(2));
			}
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("FileDAO getFile SQLException error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("FiletDAO upload close SQLException error");
			}
		}
		
		return file;
	}
	
	public int delete(String category, int BoardID, String FileName) {    //  파일 삭제
		sql = "update PostFile set Available = 0 where Category=? and BoardID = ? and FileName = ?";
		conn = dbConnector.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setInt(2, BoardID);
			pstmt.setString(3, FileName);
			return pstmt.executeUpdate();
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("FileDAO delete SQLException error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch(SQLException e) {
				System.err.println("FiletDAO delete close SQLException error");
			}
		}
		return -1;    //  DB 오류
	}
}