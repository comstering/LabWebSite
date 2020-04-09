package Board;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import DBConnect.DBConnector;
import Security.AES;

public class FileDAO {
	private DBConnector dbConnector;
	private Connection conn;
	
	private String sql;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	private Properties path;
	private FileInputStream fis_path;
	
	
	public FileDAO() {
		dbConnector = new DBConnector();
	}
	
	public String getPath(String category) {
		try {
			//  ���� ������ �ҷ�����
			path = new Properties();
			fis_path = new FileInputStream("/volume1/Tomcat/LabWebSite/WEB-INF/classes/Security/password.properties");
			path.load(new BufferedInputStream(fis_path));
			
			return path.getProperty("path") + category;
		} catch (FileNotFoundException e) { //����ó�� ,�������� ����
			System.err.println("FileDAO getPath FileNotFoundException error");
		} catch (IOException e) {
			System.err.println("FileDAO getPath IOException error");
		} finally {    //  �ڿ� ����
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
}
