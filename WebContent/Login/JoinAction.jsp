<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="User.UserDAO" %>
<%@ page import="java.io.PrintWriter" %>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="user" class="User.User" scope="page" />
<jsp:setProperty name="user" property="userID" />
<jsp:setProperty name="user" property="userPassword" />
<jsp:setProperty name="user" property="userName" />
<jsp:setProperty name="user" property="userPhoneNumber" />
<jsp:setProperty name="user" property="userEmail" />
<jsp:setProperty name="user" property="userGender" />
<jsp:setProperty name="user" property="userAuthority" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Network Security Lab</title>
</head>
<body>
	<%
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('" + user.getUserID() + ", " + user.getUserPassword()+ ", "
			+ user.getUserName() + ", " + user.getUserPhoneNumber() + ", " + user.getUserEmail() + ", "
			+ user.getUserGender() + ", " + user.getUserAuthority() + "')");
		script.println("</script>");	
	%>
</body>
</html>