package Board;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import DBConnect.DBConnector;
import Security.XSS;

public class PostDAO {
	//  DB ���ắ��
	private DBConnector dbConnector;
	private Connection conn;
	
	//  SQL ���� �������
	private ResultSet rs;
	
	public PostDAO() {
		dbConnector = new DBConnector();
	}
	
	private String getDate() {    //  �Խñ� ��� �ð� ȹ��
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
		return "";    //  DB ����
	}

	public int getNext(String category) {    //  �Խñ� ����� �Խñ� ��ȣ Ȯ��
		String sql = "select ID from " + XSS.prevention(category) + " order by ID desc";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				return rs.getInt(1) + 1;    //  DB�� ����Ǿ� �ִ� �Խñ۹�ȣ + 1
			}
			return 1;    //  ù��° �Խñ��� ���
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
		return -1;    //  DB ����
	}
	
	public int write(String category, String title, String writer, String content) {
		String sql = "insert into "+ XSS.prevention(category) +"(ID, Title, Writer, Date, ReWriter, ReDate, Content) values(?, ?, ?, ?, ?, ?, ?)";
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
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch(SQLException e) {
				System.err.println("PostDAO write close SQLException error");
			}
		}
		return -1;    //  DB ����
	}
	
	public int update(String category, int id, String title, String writer, String content) {
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
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
			} catch (SQLException e) {
				System.err.println("PostDAO update close SQLException error");
			}
		}
		return -1;
	}
	
	public int delete(String category, int id) {    //  �Խñ� ����
		String sql = "select ID from " + category + " where Available=0";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			PreparedStatement pstmt2 = null;
			if(rs.next()) {    //  ������ ������ �������
				int delID = rs.getInt(1) - 1;
				sql = "update " + category + " set ID=?, Available=0 where ID=?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, delID);
				pstmt2.setInt(2, id);
				return pstmt2.executeUpdate();    //  ���� ���� ��
			} else {    //  ������ ������ ���� ���
				sql = "update " + category + " set ID=-1, Available=0 where ID=?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, id);
				return pstmt2.executeUpdate();    //  ���� ���� ��
			}
		} catch (SQLException e) {
			System.err.println("PostDAO delete SQLException error");
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch (SQLException e) {
				System.err.println("PostDAO delete close SQLException error");
			}
		}
		return -1;    //  ���� ���� ��
	}
	
	public ArrayList<PostDTO> getList(String category, int pageNumber) {    //  �Խñ� ����Ʈ ȹ��
		String sql = "select bi.ID, bi.Title, ui.Name, bi.Date, u.Name, bi.ReDate, bi.Content, bi.Count from " + category + " as bi join User as ui on ui.id = bi.Writer join User as u on u.id = bi.ReWriter where bi.ID < ? AND Available = 1 order by ID desc limit 10";
		conn = dbConnector.getConnection();
		ArrayList<PostDTO> list = new ArrayList<PostDTO>();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext(category) - (pageNumber - 1) * 10);
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
		return list;    //  DB ����
	}
	
	public boolean nextPage(String category, int pageNumber) {    //  �Խñ� ����Ʈ ���� ������ Ȯ��
		String sql = "select * from "+ category +" where ID < ? AND Available = 1";
		conn = dbConnector.getConnection();
		PreparedStatement pstmt = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, getNext(category) - (pageNumber - 1) * 10);
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
		return false;    //  DB ����
	}

	private int setCount(String category, int ID, int count) {    //  ��ȸ�� ��Ʈ
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
		return -1;
	}
	private String setNewLine(String Content) {    //  view���� �ٹٲ� ������ ���� �Լ�
		String newContent = Content.replaceAll("\r\n", "<br>");
		return newContent;
	}
	
	public PostDTO getPost(String category, int ID) {    //  �Խñ� Ȯ��
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
		} finally {
			try {
				if(conn != null) {conn.close();}
				if(pstmt != null) {pstmt.close();}
				if(rs != null) {rs.close();}
			} catch(SQLException e) {
				System.err.println("PostDAO getPost close SQLException error");
			}
		}
		return null;    //  DB ����
	}
}