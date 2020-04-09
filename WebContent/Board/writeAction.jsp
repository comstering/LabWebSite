<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Board.PostDAO" %>
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
	} else {
		request.setCharacterEncoding("UTF-8");
		String Category = null;
		String Title = null;
		String Content = null;
		String Writer = null;
		
		Category = request.getParameter("category");
		Title = request.getParameter("title");
		Content = request.getParameter("content");
		Writer = (String)session.getAttribute("userID");
		
		PostDAO postDAO = new PostDAO();
		int result = postDAO.write(Category, Title, Writer, Content);
		/*
		String directory = application.getRealPath("Path");
		int maxSize = 1024 * 1024 * 100;
		String encoding = "UTF-8";
		
		MultipartRequest multipartRequest = new MultipartRequest(request, directory, maxSize, encoding,
				new DefaultFileRenamePolicy());
		
		String fileName = multipartRequest.getOriginalFileName("file");
		*/
		if(result == 1) {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = '../Board/"+ Category +".jsp'");
			script.println("</script>");
			script.close();
			return;
		}
	}
%>