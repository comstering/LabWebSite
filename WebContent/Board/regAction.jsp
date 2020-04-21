<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="User.UserDAO" %>
<%@ page import="Board.CalendarDAO" %>
<%@ page import="Security.XSS" %>
<%@ page import="java.io.File" %>
<%@ page import="java.io.PrintWriter" %>
<%
	if(session.getAttribute("userID") == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('로그인 후 이용해주세요.')");
		script.println("location.href = '../Login/Login.jsp'");
		script.println("</script>");
		script.close();
		return;
	}
	UserDAO userDAO = new UserDAO();
	
	if(!userDAO.checkAuthority((String)session.getAttribute("userAuthority"))) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('권한이 없습니다.')");
		script.println("history.back()");
		script.println("</script>");
		script.close();
		return;
	}
	
	request.setCharacterEncoding("UTF-8");
	String content = XSS.prevention(request.getParameter("content"));
	String start = XSS.prevention(request.getParameter("startDate").toString());
	String end = XSS.prevention(request.getParameter("endDate").toString());
	
	CalendarDAO calendarDAO = new CalendarDAO();
	
	int result = calendarDAO.registration(start, end, content);
	
	if(result == 1) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("location.href = '../Board/Calendar.jsp'");
		script.println("</script>");
		script.close();
		return;
	}
%>