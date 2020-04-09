<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Activity.PostDAO" %>
<%@ page import="Security.XSS" %>
<%@ page import="java.io.File" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
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
%>
<% request.setCharacterEncoding("UTF-8"); %>
<jsp:useBean id="postDTO" class="Activity.PostDTO" scope="page" />
<jsp:setProperty name="postDTO" property="category" />
<jsp:setProperty name="postDTO" property="title" />
<jsp:setProperty name="postDTO" property="content" />
<%
		PostDAO postDAO = new PostDAO();
		int result = postDAO.write(postDTO.getCategory(), postDTO.getTitle(), (String)session.getAttribute("userID"), postDTO.getContent());
		
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
			script.println("location.href = '../Activity/"+ postDTO.getCategory() +".jsp'");
			script.println("</script>");
			script.close();
			return;
		}
%>