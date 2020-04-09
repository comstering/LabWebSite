<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="User.User" %>
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
	request.setCharacterEncoding("UTF-8");
	String ID = null;
	String Password = null;
	String Name = null;
	String PhoneNumber = null;
	String Email = null;
	String Gender = null;
	String Authority = null;
	
	if(request.getParameter("userID") != null) {
		ID = request.getParameter("userID");
	}
	if(request.getParameter("userPassword") != null) {
		Password = request.getParameter("userPassword");
	}
	if(request.getParameter("userName") != null) {
		Name = request.getParameter("userName");
	}
	if(request.getParameter("userPhoneNumber") != null) {
		PhoneNumber = request.getParameter("userPhoneNumber");
	}
	if(request.getParameter("userEmail") != null) {
		Email = request.getParameter("userEmail");
	}
	if(request.getParameter("userGender") != null) {
		Gender = request.getParameter("userGender");
	}
	if(request.getParameter("userAuthority") != null) {
		Authority = request.getParameter("userAuthority");
	}
	
	if(ID == null || Password == null || Name == null || PhoneNumber == null || Email == null || Gender == null || Authority == null) {
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("alert('입력이 안된 사항이 있습니다.')");
		script.println("history.back()");
		script.println("</script>");
		script.close();
		return;
	}
	
	UserDAO userDAO = new UserDAO();
	int result = userDAO.join(new User(ID, Password, Name, PhoneNumber, Email, Gender, Authority));
	if(result == 1) {
		session.setAttribute("userID", ID);
		session.setAttribute("userName", Name);
		session.setAttribute("userAuthority", Authority);
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
%>