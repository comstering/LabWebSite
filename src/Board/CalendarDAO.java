package Board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnect.DBConnector;

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
	
	public ArrayList<CalendarDTO> getSchedule(int year, int month) {
		ArrayList<CalendarDTO> list = new ArrayList<CalendarDTO>();
		sql = "select StartDate, EndDate, Content from Calendar where StartDate like ? or EndDate like ?";
		conn = dbConnector.getConnection();
		try {
			pstmt = conn.prepareStatement(sql);
			if(month < 10) {
				pstmt.setString(1, year + "-0" + month + "%");
				pstmt.setString(2, year + "-0" + month + "%");
			} else {
				pstmt.setString(1, year + "-" + month + "%");
				pstmt.setString(2, year + "-" + month + "%");
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				String[] start = rs.getString(1).split("-");
				String[] end = rs.getString(2).split("-");
				String content = rs.getString(3);
				CalendarDTO calDTO = new CalendarDTO(Integer.parseInt(start[1]),
						Integer.parseInt(start[2]), Integer.parseInt(end[1]),
						Integer.parseInt(end[2]), content);
				list.add(calDTO);
			}
		} catch (SQLException e) {
			System.err.println("CalendarDAO getSchedule SQLException error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("CalendarDAO getSchedule close SQLException error");
			}
		}
		return list;
	}
}
