package User;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import Security.AES;

public class UserDAO {
	
	//  DB 접속 변수
	private String dbURL = "jdbc:mysql://localhost:3307/LabWebSite?serverTimezone=UTC";
	private String dbID = "comstering";
	private String dbPassword = "";
	
	//  DB 패스워드 변수
	private Properties password;
	private FileInputStream fis_password;
	private Properties key;
	private FileInputStream fis_key;
	
	//  DB 연결 변수
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
	}
	
	public int login(String userID, String userPassword) {
		try {
			//  암호화된 DB password read
			password = new Properties();
			fis_password = new FileInputStream("/volume1/Tomcat/RandomPassword/WEB-INF/classes/Security/password.properties");
			password.load(new BufferedInputStream(fis_password));

			//외부에 저장된 비밀키 read
			key = new Properties();
			fis_key = new FileInputStream("/volume1/Security/key.properties");
			key.load(new BufferedInputStream(fis_key));
			
			dbPassword = AES.aesDecryption(password.getProperty("password"), key.getProperty("key"));
		} catch (FileNotFoundException e) { //예외처리 ,대응부재 제거
			System.err.println("UserDAO FileNotFoundException error");	
		} catch (IOException e) {
			System.err.println("UserDAO IOException error");
		} catch (InvalidKeyException e) {
			System.err.println("UserDAO InvalidKeyException error");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("UserDAO NoSuchAlgorithmException error");
		} catch (NoSuchPaddingException e) {
			System.err.println("UserDAO NoSuchPaddingException error");
		} catch (InvalidAlgorithmParameterException e) {
			System.err.println("UserDAO InvalidAlgorithmParameterException error");
		} catch (IllegalBlockSizeException e) {
			System.err.println("UserDAO IllegalBlockSizeException error");
		} catch (BadPaddingException e) {
			System.err.println("UserDAO BadPaddingException error");
		} finally {    //  자원 해제
			try {
				if(fis_password != null) {
					fis_password.close();
				}
				if(fis_key != null) {
					fis_key.close();
				}
			} catch (IOException e) {
				System.err.println("UserDAO close IOException error");
			}
		}

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (ClassNotFoundException e) {
			System.err.println("UserDAO ClassNotFoundException error");
		} catch (SQLException e) {
			System.err.println("UserDAO Constructor SQLException error");
		}
		String SQL = "SELECT Password FROM User WHERE ID = ?";
			try {
				pstmt = conn.prepareStatement(SQL);
				pstmt.setString(1, userID);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					if(rs.getString(1).equals(userPassword)) {
						return 1;    //  로그인 성공
					} else {
						return 0;    //  비밀번호 오류
					}
				}
				return -1;    //  아이디 없음
			} catch (SQLException e) {
				System.err.println("UserDAO SQLException error");
			}
			return -2;    //  데이터베이스 오류
	}
}
