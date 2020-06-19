package Search;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnect.DBConnector;
import Security.XSS;

public class SearchDAO {
	//  DB 연결 변수
	private DBConnector dbConnector;
	private Connection conn;
	
	//  SQL 질의 결과 변수
	private ResultSet rs;
	
	public SearchDAO() {
		dbConnector = DBConnector.getInstance();
	}
	
	public ArrayList<SearchDTO> searchPost(String searchString) {    //  자료 검색
		String sql = "select Category, ID, Title, Content from Post "
				+ "where (Title like ? or Content like ?) and Available=1";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		ArrayList<SearchDTO> list = new ArrayList<SearchDTO>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + XSS.prevention(searchString) + "%");
			pstmt.setString(2, "%" + XSS.prevention(searchString) + "%");
			rs = pstmt.executeQuery();
			while(rs.next()) {    //  검색결과 저장
				SearchDTO searchDTO = new SearchDTO(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getString(4));
				list.add(searchDTO);
			}
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("SearchDAO searchPost SQLExceptoin error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("SearchDAO searchPost close SQLException error");
			}
		}
		return list;    //  DB 오류
	}
}
