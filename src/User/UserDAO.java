package User;

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
import Security.XSS;
import Security.org.mindrot.jbcrypt.BCrypt;

public class UserDAO {
	//  DB 연결 변수
	private DBConnector dbConnector;
	private Connection conn;
	
	//  SQL 질의 변수
	private String sql = "";
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//  관리자 확인 변수
	private Properties authority;
	private FileInputStream fis_authority;
	
	public UserDAO() {
		dbConnector = new DBConnector();
	}
	
	private String hashPassword(String plainPassword) {    //  비밀번호 해쉬 암호화
		return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
	}
	
	private boolean checkPass(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);
	}
	
	private String getDate() {    //  회원가입 시간
		sql = "select now()";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);    //  DB 시간 반환
			}
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("PostDAO getDate SQLExceptoin error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("PostDAO getDate close SQLException error");
			}
		}
		return "";    //  DB 오류
	}
	public int join(User user) {    // 회원가입
		sql = "insert into User (ID, Password, Name, PhoneNumber, Email, Gender, Authority, Date) values(?,?,?,?,?,?,?,?)";
		conn = dbConnector.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, XSS.prevention(user.getUserID()));
			pstmt.setString(2, hashPassword(XSS.prevention(user.getUserPassword())));
			pstmt.setString(3, XSS.prevention(user.getUserName()));
			pstmt.setString(4, XSS.prevention(user.getUserPhoneNumber()));
			pstmt.setString(5, XSS.prevention(user.getUserEmail()));
			pstmt.setString(6, XSS.prevention(user.getUserGender()));
			pstmt.setString(7, XSS.prevention(user.getUserAuthority()));
			pstmt.setString(8, getDate());
			return pstmt.executeUpdate();    //  회원가입 성공
		} catch(SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("UserDAO join SQLException error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch(SQLException e) {
				System.err.println("UserDAO join close SQLException error");
			}
		}
		return 0;    //  회원가입 실패(아이디 중복, DB 오류)
	}
	
	public String login(String userID, String userPassword) {    //  로그인
		sql = "select Password, Name, Authority from User where ID = ?";
		conn = dbConnector.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, XSS.prevention(userID));
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(checkPass(XSS.prevention(userPassword), rs.getString(1))) {
					return "success," + rs.getString(2) + "," + rs.getString(3);    //  로그인 성공
				} else {
					return "error,password";    //  비밀번호 오류
				}
			}
			return "error,ID";    // 아이디 오류
		} catch(SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("UserDAO login SQLException error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("UserDAO login close SQLException error");
			}
		}
		return "error,DB";    //  DB 오류
	}
	
	public boolean checkAuthority(String userAuthority) {    // 권한 확인
		try {
			authority = new Properties();
			fis_authority = new FileInputStream("/volume1/Security/LabWebSite/authority.properties");
			authority.load(new BufferedInputStream(fis_authority));
			
			if(XSS.prevention(userAuthority).equals(authority.getProperty("admin"))) {
				return true;    //  관리자일 경우
			} else {
				return false;    //  관리자가 아닐 경우
			}
		} catch (FileNotFoundException e) {    //  예외처리, 대응부재 제거
			System.err.println("UserDAO checkAuthority FileNotFoundException error");
		} catch (IOException e) {
			System.err.println("UserDAO checkAuthority IOException error");
		} finally {    //  자원 해제
			try {
				if(fis_authority != null) {fis_authority.close();}
			} catch (IOException e) {
				System.err.println("UserDAO checkAuthority close IOException error");
			}
		}
		return false;    //  관리자가 아닐 경우
	}
}