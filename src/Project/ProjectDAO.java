package Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnect.DBConnector;
import Security.XSS;

public class ProjectDAO {
	//  DB 연결 변수
	private DBConnector dbConnector;
	private Connection conn;
	
	//  SQL 질의 결과 변수
	private ResultSet rs;
	
	public ProjectDAO() {
		dbConnector = DBConnector.getInstance();
	}
	
	private int yearCount() {    //  프로젝트 년도 수 획득
		String sql = "select count(distinct Year) from Project";
		conn = dbConnector.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("ProjectDAO getCount SQLExceptoin error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("ProjectDAO getCount close SQLException error");
			}
		}
		return -1;    //  DB 오류
	}
	
	public int[] yearList() {    //  년도 리스트 획득
		//  년도 수 획득
		int size = yearCount();
		int[] list = new int[size];
		int count = 0;
		
		String sql = "select distinct Year from Project order by Year desc";
		conn = dbConnector.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				list[count++] = rs.getInt(1);
			}
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("ProjectDAO yearList SQLExceptoin error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("ProjectDAO yearList close SQLException error");
			}
		}
		return list;    //  년도 리스트 반환
	}
	
	public ArrayList<ProjectDTO> getProjectList(int year) {    //  년도별 프로젝트 리스트 획득
		String sql = "select ID, Title, Content from Project where Year=? and Available = 1";
		conn = dbConnector.getConnection();
		
		ArrayList<ProjectDTO> list = new ArrayList<ProjectDTO>();
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ProjectDTO projectDTO = new ProjectDTO(rs.getInt(1), rs.getString(2), rs.getString(3));
				list.add(projectDTO);
			}
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("ProjectDAO getProject SQLExceptoin error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("ProjectDAO getProject close SQLException error");
			}
		}
		return list;    //  프로젝트 리스트 반환
	}
	
	public ProjectDTO getProject(int year, int id) {    //  프로젝트 획득
		String sql = "select ID, Title, Content from Project where Year = ? and ID = ?";
		conn = dbConnector.getConnection();
		
		ProjectDTO projectDTO = null;
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, id);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				projectDTO = new ProjectDTO(rs.getInt(1), rs.getString(2), rs.getString(3));
			}
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("ProjectDAO getProject SQLExceptoin error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("ProjectDAO getProject close SQLException error");
			}
		}
		return projectDTO;
	}
	
	private int getYearNextID(int year) {    //  프로젝트 입력될 ID
		String sql = "select ID form Project where Year = ? order by ID desc";
		conn = dbConnector.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) + 1;    //  DB에 저장되어 있는 게시글번호 + 1
			}
			return 1;    //  첫번째 게시글일 경우
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("PostDAO getNext SQLExceptoin error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("PostDAO getNext close SQLException error");
			}
		}
		return -1;    //  DB 오류
	}
	
	public int addProject(int year, String title, String content) {    //  프로젝트 추가
		String sql = "insert into Project(Year, ID, Title, Content) values(?, ?, ?, ?)";
		conn = dbConnector.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			pstmt.setInt(2, getYearNextID(year));
			pstmt.setString(3, XSS.prevention(title));
			pstmt.setString(4, XSS.prevention(content));
			return pstmt.executeUpdate();
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("ProjectDAO addProject SQLExceptoin error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch(SQLException e) {
				System.err.println("ProjectDAO addProject close SQLException error");
			}
		}
		return -1;    //  DB 오류
	}
	
	public int reviseProject(int year, String title, int newYear, String newTitle, String newContent) {    //  프로젝트 수정
		String sql = "update Project set Year = ?, Title = ?, Content = ? where Year = ? and Title = ?";
		conn = dbConnector.getConnection();
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, newYear);
			pstmt.setString(2, XSS.prevention(newTitle));
			pstmt.setString(3, XSS.prevention(newContent));
			pstmt.setInt(4, year);
			pstmt.setString(5, XSS.prevention(title));
			return pstmt.executeUpdate();
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("ProjectDAO reviseProject SQLExceptoin error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch(SQLException e) {
				System.err.println("ProjectDAO reviseProject close SQLException error");
			}
		}
		return -1;    //  DB 오류
	}
}
