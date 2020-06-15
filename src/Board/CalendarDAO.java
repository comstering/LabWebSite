package Board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnect.DBConnector;
import Post.PostDAO;

public class CalendarDAO {
	//  DB 연결 변수
	private DBConnector dbConnector;
	private Connection conn;
	
	//  SQL 질의 변수
	private String sql = "";
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public CalendarDAO() {
		dbConnector = new DBConnector();
	}
	
	public ArrayList<CalendarDTO> getSchedule(int year, int month) {    //  등록된 일정 가져오기
		ArrayList<CalendarDTO> list = new ArrayList<CalendarDTO>();
		sql = "select StartDate, EndDate, Content from Calendar where StartDate like ? or EndDate like ?";
		conn = dbConnector.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			if(month < 10) {    //  월이 1자리일 때
				pstmt.setString(1, year + "-0" + month + "%");
				pstmt.setString(2, year + "-0" + month + "%");
			} else {    //  월이 2자리일 때
				pstmt.setString(1, year + "-" + month + "%");
				pstmt.setString(2, year + "-" + month + "%");
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String[] start = rs.getString(1).split("-");    //  시작 년월일 구분
				String[] end = rs.getString(2).split("-");    //  끝 년월일 구분
				String content = rs.getString(3);
				CalendarDTO calDTO = new CalendarDTO(Integer.parseInt(start[1]),
						Integer.parseInt(start[2]), Integer.parseInt(end[1]),
						Integer.parseInt(end[2]), content);
				list.add(calDTO);
			}
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("CalendarDAO getSchedule SQLException error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("CalendarDAO getSchedule close SQLException error");
			}
		}
		return list;    //  일정 리스트 반환
	}
	
	public int registration(String start, String end, String content) {    //  일정 등록
		sql = "insert into Calendar(ID, StartDate, EndDate, Content) values(?, ?, ?, ?)";
		conn = dbConnector.getConnection();
		try {
			PostDAO postDAO = new PostDAO();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, postDAO.getNext("Calendar"));
			pstmt.setString(2, start);
			pstmt.setString(3, end);
			pstmt.setString(4, content);
			return pstmt.executeUpdate();    //  정상 등록
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("Calendar registrationSQLException error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch(SQLException e) {
				System.err.println("Calendar registration close SQLException error");
			}
		}
		return -1;    //  DB 오류
	}
}
