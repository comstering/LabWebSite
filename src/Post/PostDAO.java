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
		dbConnector = DBConnector.getInstance();
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
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
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
		String sql = "select ID from Post where Category=? order by ID desc";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
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
	
	public int write(String category, String title, String writer, String content) {    //  게시글 등록
		String sql = "insert into Post(Category, ID, Title, Writer, Date, ReWriter, ReDate, Content) values(?, ?, ?, ?, ?, ?, ?, ?)";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setInt(2, getNext(category));
			pstmt.setString(3, XSS.prevention(title));
			pstmt.setString(4, writer);
			pstmt.setString(5, getDate());
			pstmt.setString(6, writer);
			pstmt.setString(7, getDate());
			pstmt.setString(8, XSS.prevention(content));
			return pstmt.executeUpdate();
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
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
		String sql = "update Post set Title=?, ReWriter=?, ReDate=?, Content=? where Category=? and ID=?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, XSS.prevention(title));
			pstmt.setString(2, writer);
			pstmt.setString(3, getDate());
			pstmt.setString(4, XSS.prevention(content));
			pstmt.setString(5, category);
			pstmt.setInt(6, id);
			return pstmt.executeUpdate();
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
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
		String sql = "select ID from Post where Category=? and Available=0";
		String filesql = "update PostFile set Available = 0 where Category=? and BoardID=?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		PreparedStatement filepstmt = null;
		try {
			filepstmt = conn.prepareStatement(filesql);
			filepstmt.setString(1, category);
			filepstmt.setInt(2, id);
			filepstmt.executeUpdate();
			pstmt = conn.prepareStatement(sql);
			pstmt.setNString(1, category);
			rs = pstmt.executeQuery();
			PreparedStatement pstmt2 = null;
			if(rs.next()) {    //  삭제된 파일이 있을 경우
				int delID = rs.getInt(1) - 1;
				sql = "update Post set ID=?, Available=0 where Category=? and ID=?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, delID);
				pstmt2.setString(2, category);
				pstmt2.setInt(3, id);
				return pstmt2.executeUpdate();    //  삭제 성공시
			} else {    //  삭제된 파일이 없을 경우
				sql = "update Post set ID=-1, Available=0 where Category=? and ID=?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setString(1, category);
				pstmt2.setInt(2, id);
				return pstmt2.executeUpdate();    //  삭제 성공시
			}
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
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
		String sql = "select pi.ID, pi.Title, ui.Name, pi.Date, u.Name, pi.ReDate, pi.Content, pi.Count "
				+ "from Post pi join User ui on ui.ID = pi.Writer "
				+ "join User u on u.ID = pi.ReWriter where pi.Category=? and pi.ID < ? and Available = 1 "
				+ "order by ID desc limit ?";
		conn = dbConnector.getConnection();
		ArrayList<PostDTO> list = new ArrayList<PostDTO>();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			if(category.equals("Notice") || category.equals("Library")) {    //  Board의 카테고리일 경우
				pstmt.setInt(2, getNext(category) - (pageNumber - 1) * 10);
				pstmt.setInt(3, 10);
			} else {    //  Activity의 카테고리일 경우
				pstmt.setInt(2, getNext(category) - (pageNumber - 1) * 6);
				pstmt.setInt(3, 6);
			}
			rs = pstmt.executeQuery();
			while(rs.next()) {    //  게시글 리스트 추가
				PostDTO postDTO = new PostDTO(category, rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8));
				list.add(postDTO);
			}
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
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
		String sql = "select * from Post where Category=? and ID < ? and Available = 1";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			if(category.equals("Notice") || category.equals("Library")) {    //  Board의 카테고리일 경우
				pstmt.setInt(2, getNext(category) - (pageNumber - 1) * 10);
			} else {    //  Activity의 카테고리일 경우
				pstmt.setInt(2, getNext(category) - (pageNumber - 1) * 6);
			}
			rs = pstmt.executeQuery();
			if(rs.next()) {    //  다음 페이지가 있을 경우
				return true;
			}
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
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
		String sql = "update Post set Count = ? where Category=? and ID = ?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, count);
			pstmt.setString(2, category);
			pstmt.setInt(3, ID);
			pstmt.executeUpdate();
			return count;
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
			System.err.println("PostDAO setCount SQLException error");
		}
		return -1;    //  DB 오류
	}
	
	private String setNewLine(String content) {    //  view에서 줄바꿈, space 적용을 위한 함수
		return content.replaceAll("\r\n", "<br>").replaceAll(" ", "&nbsp;");
	}
	
	public PostDTO getPost(String category, int ID) {    //  게시글 확인
		String sql = "select pi.ID, pi.Title, ui.Name, pi.Date, u.Name, pi.ReDate, pi.Content, pi.Count "
				+ "from Post pi join User ui on ui.id = pi.Writer join User u on u.id = pi.ReWriter "
				+ "where pi.Category=? and  pi.ID=?";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setInt(2, ID);
			rs = pstmt.executeQuery();
			if(rs.next()) {    //  게시글 정보 반환
				return new PostDTO(category, rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5), rs.getString(6), setNewLine(rs.getString(7)), setCount(category, ID, rs.getInt(8) + 1));
			}
		} catch (SQLException e) {    //  에외처리, 대응부재 제거
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
		String sql = "select Writer from Post where Category=? and ID = ?";
		String writer = "";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, category);
			pstmt.setInt(2, BoardID);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				writer = rs.getString(1);
			}
		} catch (SQLException e) {    //  예외처리, 대응부재 제거
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