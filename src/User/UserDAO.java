package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import DBConnect.DBConnector;
import Security.org.mindrot.jbcrypt.BCrypt;

public class UserDAO {
	//  DB ���� ����
	private DBConnector dbConnector;
	private Connection conn;
	
	//  SQL ���� ����
	private String sql = "";
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO() {
		dbConnector = new DBConnector();
	}
	
	private String hashPassword(String plainPassword) {
		return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
	}
	
	private boolean checkPass(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);
	}
	
	public int join(User user) {
		sql = "insert into User (ID, Password, Name, PhoneNumber, Email, Gender, Authority) values(?,?,?,?,?,?,?)";
		conn = dbConnector.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, hashPassword(user.getUserPassword()));
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserPhoneNumber());
			pstmt.setString(5, user.getUserEmail());
			pstmt.setString(6, user.getUserGender());
			pstmt.setString(7, user.getUserAuthority());
			return pstmt.executeUpdate();    //  ȸ������ ����
		} catch(SQLException e) {
			System.err.println("UserDAO join SQLException error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch(SQLException e) {
				System.err.println("UserDAO join close SQLException error");
			}
		}
		return 0;    //  ȸ������ ����(���̵� �ߺ�, DB ����)
	}
	
	public String login(String userID, String userPassword) {
		sql = "select Password, Name, Authority from User where ID = ?";
		conn = dbConnector.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(checkPass(userPassword, rs.getString(1))) {
					return "success," + rs.getString(2) + "," + rs.getString(3);    //  �α��� ����
				} else {
					return "error,password";    //  ��й�ȣ ����
				}
			}
			return "error,ID";    //  ���̵� ����
		} catch(SQLException e) {
			System.err.println("UserDAO login SQLException error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("UserDAO login close SQLException error");
			}
		}
		return "error,DB";    //  DB ����
	}
	
	public boolean checkAuthority(String userID, String userAuthority) {
		sql = "select Authority from User where ID = ?";
		conn = dbConnector.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(rs.getString(1).equals("test1") || rs.getString(1).equals("test2")) {
					return true;
				} else {
					return false;
				}
			}
		} catch(SQLException e) {
			System.err.println("UserDAO checkAuthority SQLException error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("UserDAO checkAuthority close SQLException error");
			}
		}
		return false;
	}
}
