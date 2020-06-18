package Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnect.DBConnector;

public class ProjectDAO {
	//  DB 연결 변수
	private DBConnector dbConnector;
	private Connection conn;
	
	//  SQL 질의 결과 변수
	private ResultSet rs;
	
	public ProjectDAO() {
		dbConnector = new DBConnector();
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
	
	public ArrayList<ProjectDTO> getProject(int year) {    //  년도별 프로젝트 리스트 획득
		String sql = "select Title, Content from Project where Year=?";
		conn = dbConnector.getConnection();
		
		ArrayList<ProjectDTO> list = new ArrayList<ProjectDTO>();
		
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, year);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ProjectDTO projectDTO = new ProjectDTO(rs.getString(1), rs.getString(2));
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
}
