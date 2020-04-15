<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="User.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<%
	if(session.getAttribute("userID") != null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("history.back()");
		script.println("</script>");
		script.close();
		return;
	}
%>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="user" class="User.User" scope="page" />
<jsp:setProperty name="user" property="userID" />
<jsp:setProperty name="user" property="userPassword" />
<jsp:setProperty name="user" property="userName" />
<jsp:setProperty name="user" property="userPhoneNumber" />
<jsp:setProperty name="user" property="userEmail" />
<jsp:setProperty name="user" property="userGender" />
<jsp:setProperty name="user" property="userAuthority" />

<%
	if(user.getUserID() == null || user.getUserPassword() == null || user.getUserName() == null ||
		user.getUserPhoneNumber() == null || user.getUserEmail() == null || user.getUserGender() == null ||
		user.getUserAuthority() == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('입력이 안된 사항이 있습니다.')");
		script.println("history.back()");
		script.println("</script>");
		script.close();
		return;
	} else {
		UserDAO userDAO = new UserDAO();
		int result = userDAO.join(user);
		if(result == 1) {
			session.setAttribute("userID", user.getUserID());
			session.setAttribute("userName", user.getUserName());
			session.setAttribute("userAuthority", user.getUserAuthority());
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href='../index.jsp'");
			script.println("</script>");
			script.close();
			return;
		} else {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('이미 존재하는 아이디입니다.')");
			script.println("history.back()");
			script.println("</script>");
			script.close();
			return;
		}
	}
	
%>