package Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnect.DBConnector;
import Security.XSS;

public class PostDAO {
	//  DB 연결 변수
	private DBConnector dbConnector;
	private Connection conn;
	
	//  SQL 질의 결과 변수
	private ResultSet rs;
	
	public PostDAO() {
		dbConnector = new DBConnector();
	}
	
	private String getDate() {    //  게시글 등록 서버 시간 획득
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

	public int getNext(String category) {    //  게시판 저장시 게시글 번호 획득
		String sql = "select ID from " + XSS.prevention(category) + " order by ID desc";
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
	
	public int write(String category, String title, String writer, String content) {    //  게시글 등록
		String sql = "insert into " + XSS.prevention(category)
			+ "(ID, Title, Writer, Date, ReWriter, ReDate, Content) values(?, ?, ?, ?, ?, ?, ?)";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext(category));
			pstmt.setString(2, XSS.prevention(title));
			pstmt.setString(3, XSS.prevention(writer));
			pstmt.setString(4, getDate());
			pstmt.setString(5, XSS.prevention(writer));
			pstmt.setString(6, getDate());
			pstmt.setString(7, XSS.prevention(content));
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("PostDAO write SQLExceptoin error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch(SQLException e) {
				System.err.println("PostDAO write close SQLException error");
			}
		}
		return -1;    //  DB 오류
	}
	
	public int update(String category, int id, String title, String writer, String content) {    //  게시글 수정
		String sql = "update " + category + " set Title=?, ReWriter=?, ReDate=?, Content=? where ID=?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, XSS.prevention(title));
			pstmt.setString(2, XSS.prevention(writer));
			pstmt.setString(3, getDate());
			pstmt.setString(4, XSS.prevention(content));
			pstmt.setInt(5, id);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("PostDAO update SQLException error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch (SQLException e) {
				System.err.println("PostDAO update close SQLException error");
			}
		}
		return -1;
	}
	
	public int delete(String category, int id) {    //  게시글 삭제
		String sql = "select ID from " + category + " where Available=0";
		String filesql = "update " + category + "File set Available = 0 where BoardID = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement filepstmt = null;
		try {
			filepstmt = conn.prepareStatement(filesql);
			filepstmt.setInt(1, id);
			filepstmt.executeUpdate();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			PreparedStatement pstmt2 = null;
			if(rs.next()) {    //  삭제된 파일이 있을 경우
				int delID = rs.getInt(1) - 1;
				sql = "update " + category + " set ID=?, Available=0 where ID=?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, delID);
				pstmt2.setInt(2, id);
				return pstmt2.executeUpdate();    //  삭제 성공시
			} else {    //  삭제된 파일이 없을 경우
				sql = "update " + category + " set ID=-1, Available=0 where ID=?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, id);
				return pstmt2.executeUpdate();    //  삭제 성공시
			}
		} catch (SQLException e) {
			System.err.println("PostDAO delete SQLException error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch (SQLException e) {
				System.err.println("PostDAO delete close SQLException error");
			}
		}
		return -1;    //  DB 오류
	}
	
	public ArrayList<PostDTO> getList(String category, int pageNumber) {    //  게시글 리스트 획득
		String sql = "select bi.ID, bi.Title, ui.Name, bi.Date, u.Name, bi.ReDate, bi.Content, bi.Count "
				+ "from " + category + " bi join User ui on ui.ID = bi.Writer "
				+ "join User u on u.ID = bi.ReWriter where bi.ID < ? AND Available = 1 "
				+ "order by ID desc limit ?";
		conn = dbConnector.getConnection();
		ArrayList<PostDTO> list = new ArrayList<PostDTO>();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			if(category.equals("Notice") || category.equals("Library")) {    //  Board의 카테고리일 경우
				pstmt.setInt(1, getNext(category) - (pageNumber - 1) * 10);
				pstmt.setInt(2, 10);
			} else {    //  Activity의 카테고리일 경우
				pstmt.setInt(1, getNext(category) - (pageNumber - 1) * 6);
				pstmt.setInt(2, 6);
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {
				PostDTO postDTO = new PostDTO(category, rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8));
				list.add(postDTO);
			}
		} catch (SQLException e) {
			System.err.println("PostDAO getList SQLExceptoin error");
		} finally {    //  자원 해제
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
	
	public boolean nextPage(String category, int pageNumber) {    // 게시글 리스트 다음 페이지 확인
		String sql = "select * from "+ category +" where ID < ? AND Available = 1";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			if(category.equals("Notice") || category.equals("Library")) {    //  Board의 카테고리일 경우
				pstmt.setInt(1, getNext(category) - (pageNumber - 1) * 10);
			} else {    //  Activity의 카테고리일 경우
				pstmt.setInt(1, getNext(category) - (pageNumber - 1) * 6);
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {    //  다음 페이지가 있을 경우
				return true;
			}
		} catch (SQLException e) {
			System.err.println("PostDAO nextPage SQLExceptoin error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("PostDAO nextPage close SQLException error");
			}
		}
		return false;    //  다음 페이지가 없을 경우, DB 오류
	}

	private int setCount(String category, int ID, int count) {    // 조회수 설정
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
		String sql = "select bi.ID, bi.Title, ui.Name, bi.Date, u.Name, bi.ReDate, bi.Content, bi.Count from " + category + " as bi join User as ui on ui.id = bi.Writer join User as u on u.id = bi.ReWriter where bi.ID = ?";
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
		} finally {    //  자원 해제
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
	
	public boolean checkWriter(String category, int BoardID, String userID) {    //  게시글 작성자 확인
		String sql = "select Writer from " + category + " where ID = ?";
		String writer = "";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, BoardID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				writer = rs.getString(1);
			}
		} catch (SQLException e) {
			System.err.println("PostDAO getPost SQLExceptoin error");
		} finally {    //  자원 해제
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("PostDAO getPost close SQLException error");
			}
		}
		if(writer.equals(userID)) {    //  작성자일 경우
			return true;
		} else {    //  작성자와 다를 경우
			return false;
		}
	}
}