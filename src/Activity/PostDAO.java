package Activity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnect.DBConnector;

public class PostDAO {
	private DBConnector dbConnector;
	private Connection conn;
	
	private ResultSet rs;
	
	public PostDAO() {
		dbConnector = new DBConnector();
	}
	
	private String getDate() {    //  게시글 등록 시간 획득
		String sql = "select now()";
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
		return "";    //  DB 오류
	}

	public int getNext(String category) {    //  게시글 저장시 게시글 번호 획득
		String sql = "select ID from " + category + " order by ID desc";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) + 1;    //  DB에 저장되어 있는 게시글번호 + 1
			}
			return 1;    //  첫번째 게시글일 경우
		} catch (SQLException e) {
			System.err.println("PostDAO getNext SQLExceptoin error");
		} finally {
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
	
	public int write(String category, String title, String writer, String content) {    //  게시글 작성
		String sql = "insert into "+ category +"(ID, Title, Writer, Date, ReWriter, ReDate, Content) values(?, ?, ?, ?, ?, ?, ?)";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext(category));
			pstmt.setString(2, title);
			pstmt.setString(3, writer);
			pstmt.setString(4, getDate());
			pstmt.setString(5, writer);
			pstmt.setString(6, getDate());
			pstmt.setString(7, content);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("PostDAO write SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch(SQLException e) {
				System.err.println("PostDAO write close SQLException error");
			}
		}
		return -1;    //  DB 오류
	}
	
	public ArrayList<PostDTO> getList(String category, int pageNumber) {    //  게시글 리스트 획득
		String sql = "select ai.ID, ai.Title, ui.Name, ai.Date, u.Name, ai.ReDate, ai.Content, ai.Count from " + category + " as ai join User as ui on ui.id = ai.Writer join User as u on u.id = ai.ReWriter where ai.ID < ? AND Available = 1 order by ID desc limit 6";
		conn = dbConnector.getConnection();
		ArrayList<PostDTO> list = new ArrayList<PostDTO>();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext(category) - (pageNumber - 1) * 6);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				PostDTO postDTO = new PostDTO(category, rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8));
				list.add(postDTO);
			}
		} catch (SQLException e) {
			System.err.println("PostDAO getList SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("PostDAO getList close SQLException error");
			}
		}
		return list;
	}
	
	public boolean nextPage(String category, int pageNumber) {    //  게시글 리스트 다음 페이지 확인
		String sql = "select * from "+ category +" where ID < ? AND Available = 1";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext(category) - (pageNumber - 1) * 6);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			System.err.println("PostDAO nextPage SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("PostDAO nextPage close SQLException error");
			}
		}
		return false;    //  DB 오류
	}

	private int setCount(String category, int ID, int count) {    //  조회수 세트
		String sql = "update " + category + " set Count = ? where ID = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, count);
			pstmt.setInt(2, ID);
			pstmt.executeUpdate();
			return count;
		} catch (SQLException e) {
			System.err.println("PostDAO setCount SQLException error");
		}
		return -1;    //  DB 오류
	}
	private String setNewLine(String Content) {    //  view에서 줄바꿈 적용을 위한 함수
		String newContent = Content.replaceAll("\r\n", "<br>");
		return newContent;
	}
	
	public PostDTO getPost(String category, int ID) {    //  게시글 확인
		String sql = "select ai.ID, ai.Title, ui.Name, ai.Date, u.Name, ai.ReDate, ai.Content, ai.Count from " + category + " as ai join User as ui on ui.id = ai.Writer join User as u on u.id = ai.ReWriter where ai.ID = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return new PostDTO(category, rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), setNewLine(rs.getString(7)), setCount(category, ID, rs.getInt(8) + 1));
			}
		} catch (SQLException e) {
			System.err.println("PostDAO getPost SQLExceptoin error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("PostDAO getPost close SQLException error");
			}
		}
		return null;    //  DB 오류
	}
}