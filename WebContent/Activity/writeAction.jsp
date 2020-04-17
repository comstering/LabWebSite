<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="User.UserDAO" %>
<%@ page import="Board.PostDAO" %>
<%@ page import="File.FileDAO" %>
<%@ page import="Security.XSS" %>
<%@ page import="java.io.File" %>
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %>
<%@ page import="com.oreilly.servlet.MultipartRequest" %>
<%@ page import="java.util.Enumeration" %>
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
		script.println("location.href = '../Login/Login.jsp'");
		script.println("</script>");
		script.close();
		return;
	}
	
	String value[] = new String[3];    //  0: content, 1: category, 2: title
	
	FileDAO fileDAO = new FileDAO();
	//  파일 경로
	String directory = fileDAO.getPath();
	
	//  파일 경로: 테스트 환경
//	String directory = "D:/Programming/test/";
	
	int maxSize = 1024 * 1024 * 100;
	String encoding = "UTF-8";
	MultipartRequest multipartRequest = new MultipartRequest(request, directory, maxSize, encoding,
			new DefaultFileRenamePolicy());
	Enumeration<?> params = multipartRequest.getParameterNames();
	
	int i = 0;
	while(params.hasMoreElements()) {
		String name = (String)params.nextElement();
		value[i++] = XSS.prevention(multipartRequest.getParameter(name));
	}
	
	Enumeration<?> files = multipartRequest.getFileNames();
	System.out.println(files);
	
	String fileName = null;
	String fileRealName = null;
	
	while(files.hasMoreElements()) {
		String name = (String)files.nextElement();
		fileRealName = multipartRequest.getFilesystemName(name);
		fileName = multipartRequest.getOriginalFileName(name);
		String type = multipartRequest.getContentType(name);
		i++;
		System.out.println("name " + name);
		System.out.println("filename " + fileName);
		System.out.println("fileRealName " + fileRealName);
		System.out.println("type " + type);
	}
	
	PostDAO postDAO = new PostDAO();
	int result = postDAO.write(value[1], value[2], (String)session.getAttribute("userID"), value[0]);
	
	if(fileName != null) {
		fileDAO.upload(value[1], postDAO.getNext(value[1]) - 1, fileName, fileRealName);
	}
	
	if(result == 1) {
		
		//int BoardID = postDAO.getNext(XSS.prevention(postDTO.getCategory())) - 1;
		
		PrintWriter script = response.getWriter();
		script.println("<script>");
		script.println("location.href = '../Board/"+ value[1] +".jsp'");
		script.println("</script>");
		script.close();
		return;
	}
	
%>
