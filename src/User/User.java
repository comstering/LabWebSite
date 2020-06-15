package User;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import Security.AES;

public class User {
	private String userID;    //  아이디
	private String userPassword;    //  패스워드
	private String userName;    //  이름
	private String userPhoneNumber;    //  전화번호
	private String userEmail;    //  이메일
	private String userGender;    //  성별
	private String userAuthority;    //  권한
	
	private Properties key;
	private FileInputStream fis_key;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserPhoneNumber() {
		return userPhoneNumber;
	}
	public void setUserPhoneNumber(String userPhoneNumber) {
		this.userPhoneNumber = userPhoneNumber;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public String getUserAuthority() {
		String authority = null;
		try {
			//  key 가져오기
			key = new Properties();
			fis_key = new FileInputStream("/volume1/Security/key.properties");
			key.load(new BufferedInputStream(fis_key));

			//  권한 암호화
			authority = AES.aesEncryption(this.userAuthority, key.getProperty("key"));
		} catch (FileNotFoundException e) {    //  예외처리, 대응부재 제거
			System.err.println("User FileNotFoundException error");
		} catch (IOException e) {
			System.err.println("User IOException error");
		} catch (InvalidKeyException e) {
			System.err.println("User InvalidKeyException error");
		} catch (NoSuchAlgorithmException e) {
			System.err.println("User NoSuchAlgorithmException error");
		} catch (NoSuchPaddingException e) {
			System.err.println("User NoSuchPaddingException error");
		} catch (InvalidAlgorithmParameterException e) {
			System.err.println("User InvalidAlgorithmParameterException error");
		} catch (IllegalBlockSizeException e) {
			System.err.println("User IllegalBlockSizeException error");
		} catch (BadPaddingException e) {
			System.err.println("User BadPaddingException error");
		} finally {    //  자원해제
			try {
				if(fis_key != null) {fis_key.close();}
			} catch (IOException e) {
				System.err.println("User close IOException error");
			}
		}
		return authority;
	}
	public void setUserAuthority(String userAuthority) {
		this.userAuthority = userAuthority;
	}
	public User() {
		
	}
	public User(String userID, String userPassword, String userName, String userPhoneNumber, String userEmail,
			String userGender, String userAuthority) {
		super();
		this.userID = userID;
		this.userPassword = userPassword;
		this.userName = userName;
		this.userPhoneNumber = userPhoneNumber;
		this.userEmail = userEmail;
		this.userGender = userGender;
		this.userAuthority = userAuthority;
	}
	
}