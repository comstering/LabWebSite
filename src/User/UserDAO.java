package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnect.DBConnector;

public class UserDAO {
	//  DB 연결 변수
	private DBConnector dbConnector;
	private Connection conn;
	
	//  SQL 질의 변수
	private String sql = "";
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		dbConnector = new DBConnector();
	}
	
	public int join(User user) {
		sql = "select ID form User where ID = ?";
		conn = dbConnector.getConnection();
		try {
			sql = "insert into User (ID, Password, Name, PhoneNumber, Email, Gender, Authority) values(?,?,?,?,?,?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserPhoneNumber());
			pstmt.setString(5, user.getUserEmail());
			pstmt.setString(6, user.getUserGender());
			pstmt.setString(7, user.getUserAuthority());
			return pstmt.executeUpdate();    //  회원가입 성공
		} catch (SQLException e) {
			System.err.println("UserDAO SQLException error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch (SQLException e) {
				System.err.println("UserDAO close SQLException error");
			}
		}
		return 0;    //  회원가입 실패(아이디 중복, DB 오류)
	}
	
	public String login(String userID, String userPassword) {
		sql = "select Password, Name, Authority from User where ID = ?";
		conn = dbConnector.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals(userPassword)) {
					return "success," + rs.getString(2) + "," + rs.getString(3);    //  로그인 성공
				} else {
					return "error,password";    //  비밀번호 오류
				}
			}
			return "error,ID";    //  아이디 오류
		} catch (SQLException e) {
			System.err.println("UserDAO SQLException error");
		}
		return "error,DB";    //  DB 오류
	}
}
