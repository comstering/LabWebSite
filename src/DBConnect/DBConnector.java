package DBConnect;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import Security.AES;

public class DBConnector {
	//  DB 접속 변수
	private String dbURL = "jdbc:mysql://localhost:3307/LabWebSite?serverTimezone=UTC";
	private String dbID = "comstering";
	private String dbPassword = "";
	
	//  DB 패스워드 변수
	private Properties password;
	private FileInputStream fis_password;
	private Properties key;
	private FileInputStream fis_key;
	
	public DBConnector() {
		try {
			//  암호화된 DB password read
			password = new Properties();
			fis_password = new FileInputStream("/volume1/Tomcat/LabWebSite/WEB-INF/classes/Security/password.properties");
			password.load(new BufferedInputStream(fis_password));

			//  외부에 저장된 비밀키read
			key = new Properties();
			fis_key = new FileInputStream("/volume1/Security/key.properties");
			key.load(new BufferedInputStream(fis_key));
			
			dbPassword = AES.aesDecryption(password.getProperty("password"), key.getProperty("key"));
		} catch (FileNotFoundException e) { //예외처리, 대응부재 제거
			System.err.println("DBConnector FileNotFoundException error");
		} catch (IOException e) {
			System.err.println("DBConnector IOException error");
		} catch (InvalidKeyException e) {
			System.err.println("DBConnector InvalidKeyException error");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("DBConnector NoSuchAlgorithmException error");
		} catch (NoSuchPaddingException e) {
			System.err.println("DBConnector NoSuchPaddingException error");
		} catch (InvalidAlgorithmParameterException e) {
			System.err.println("DBConnector InvalidAlgorithmParameterException error");
		} catch (IllegalBlockSizeException e) {
			System.err.println("DBConnector IllegalBlockSizeException error");
		} catch (BadPaddingException e) {
			System.err.println("DBConnector BadPaddingException error");
		} finally {    //  자원 해제
			try {
				if(fis_password != null) {
					fis_password.close();
				}
				if(fis_key != null) {
					fis_key.close();
				}
			} catch (IOException e) {
				System.err.println("DBConnector close IOException error");
			}
		}
	}
	
	public Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(dbURL, dbID, dbPassword);
		} catch (ClassNotFoundException e) {
			System.err.println("DBConnector getConnection ClassNotFoundException error");
		} catch (SQLException e) {
			System.err.println("DBConnector getConnection SQLException error");
		}
		return null;
	}
}
