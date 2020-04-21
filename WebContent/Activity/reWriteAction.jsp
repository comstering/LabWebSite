<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="User.UserDAO" %>
<%@ page import="Post.PostDAO" %>
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
%>
<%
	request.setCharacterEncoding("UTF-8");
	String category = XSS.prevention(request.getParameter("category"));
	int id = Integer.parseInt(XSS.prevention(request.getParameter("id")));
	String title = XSS.prevention(request.getParameter("title"));
	String content = XSS.prevention(request.getParameter("content"));
	
	PostDAO postDAO = new PostDAO();
	int result = postDAO.update(category, id, title, (String)session.getAttribute("userID"), content);
	
	if(result == 1) {
		String directory = application.getRealPath("Path");
		int maxSize = 1024 * 1024 * 100;
		String encoding = "UTF-8";
		/*
		MultipartRequest multipartRequest = new MultipartRequest(request, directory, maxSize, encoding,
				new DefaultFileRenamePolicy());
		
		String fileName = multipartRequest.getOriginalFileName("file");
		
		int BoardID = postDAO.getNext(Category) - 1;
		*/
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("location.href = '../Activity/"+ category +".jsp'");
		script.println("</script>");
		script.close();
		return;
	}
%>