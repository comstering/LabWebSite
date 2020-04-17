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
	//  DB ���� ����
	private DBConnector dbConnector;
	private Connection conn;
	
	//  SQL ���� ����
	private String sql = "";
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	//  ������ Ȯ�� ����
	private Properties authority;
	private FileInputStream fis_authority;
	
	public UserDAO() {
		dbConnector = new DBConnector();
	}
	
	private String hashPassword(String plainPassword) {
		return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
	}
	
	private boolean checkPass(String plainPassword, String hashedPassword) {
		return BCrypt.checkpw(plainPassword, hashedPassword);
	}
	
	private String getDate() {    //  ȸ������ �ð�
		sql = "select now()";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getString(1);
			}
		} catch (SQLException e) {
			System.err.println("PostDAO getDate SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("PostDAO getDate close SQLException error");
			}
		}
		return "";    //  DB ����
	}
	public int join(User user) {    //  ȸ������
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
	
	public String login(String userID, String userPassword) {    //  �α���
		sql = "select Password, Name, Authority from User where ID = ?";
		conn = dbConnector.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, XSS.prevention(userID));
			rs = pstmt.executeQuery();
			if(rs.next()) {
				if(checkPass(XSS.prevention(userPassword), rs.getString(1))) {
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
	
	public boolean checkAuthority(String userAuthority) {    //  ���� Ȯ��
		//����Ȯ��
		try {
			authority = new Properties();
			fis_authority = new FileInputStream("/volume1/Security/LabWebSite/authority.properties");
			authority.load(new BufferedInputStream(fis_authority));
			
			if(XSS.prevention(userAuthority).equals(authority.getProperty("admin"))) {
//			//  ����Ȯ��: �׽�Ʈȯ��
//			if(XSS.prevention(userAuthority).equals("zWhhvAqRxVNg1cgomXiQew==")) {
				return true;
			} else {
				return false;
			}
		} catch (FileNotFoundException e) {
			System.err.println("UserDAO checkAuthority FileNotFoundException error");
		} catch (IOException e) {
			System.err.println("UserDAO checkAuthority IOException error");
		} finally {
			try {
				if(fis_authority != null) {
					fis_authority.close();
				}
			} catch (IOException e) {
				System.err.println("UserDAO checkAuthority close IOException error");
			}
		}
		return false;
	}
}