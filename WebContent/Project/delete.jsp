<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="User.UserDAO" %>
<%@ page import="Project.ProjectDAO" %>
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
	int year = Integer.parseInt(XSS.prevention(request.getParameter("year")));
	int id = Integer.parseInt(XSS.prevention(request.getParameter("ID")));
	
	ProjectDAO projectDAO = new ProjectDAO();
	int result = projectDAO.deleteProject(year, id);
	
	if(result == 1) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('삭제되었습니다.')");
		script.println("location.href = 'Project.jsp'");
		script.println("</script>");
		script.close();
		return;
	}
%>
