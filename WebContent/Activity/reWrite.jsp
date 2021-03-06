<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="User.UserDAO" %>
<%@ page import="Post.*" %>
<%@ page import="File.FileDAO" %>
<%@ page import="Security.XSS" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.ArrayList" %>
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
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel="stylesheet" href="../StyleCSS/Base.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<script src="../jsFolder/fixing.js"></script>
<title>Network Security Lab</title>
<style>
</style>
</head>
<body>
	<div id="menu"></div>
	<div class="container">
		<div class="row">
			<nav class="col-md-2 d-none d-md-block bg-light sidebar" style="max-width: 200px">
				<h4 style="text-align: center;">BOARD</h4>
				<div class="sidebar-stick">
					<ul class="nav flex-column" style="text-align: center;">
						<script src="../jsFolder/Submenu.js">
						</script>
					</ul>
				</div>
			</nav>
			<main role="main" class="col-md-9 px-4" style="max-width: 72%">
				<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center
				pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">게시판 글쓰기</h1>
				</div>
				<div>
					<%
						request.setCharacterEncoding("UTF-8");
						String category = XSS.prevention(request.getParameter("category"));
						int ID = Integer.parseInt(XSS.prevention(request.getParameter("id")));
						PostDAO postDAO = new PostDAO();
						PostDTO postDTO = postDAO.getPost(category, ID);
						String title = postDTO.getTitle();
						String content = postDTO.getContent().replaceAll("<br>", "\r\n");
					%>
					<div>
						<form method="post" action="<%= application.getContextPath() %>/ReWrite" enctype="multipart/form-data">
							<input type="hidden" name="category" value="<%= category %>">
							<input type="hidden" name="ID" value="<%= ID %>">
							<table class="table table-striped table-sm">
								<thead  class="table-info">
									<tr>
										<td class="text-center" width="15%"><label class="col-form-label">글제목</label></td>
										<td><input type="text" class="form-control" placeholder="글 제목" value="<%= title %>" name="title" maxlength="50" required></td>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td colspan="2"><textarea class="form-control" placeholder="글 내용" name="content" maxlength="4096" style="height: 450px;" required><%= content %></textarea></td>
									</tr>
								</tbody>
							</table>
							<input type="hidden" id="writer" name="writer" value="<%= (String)session.getAttribute("userID") %>">
							<%
								FileDAO fileDAO = new FileDAO();
								ArrayList<String> file = new ArrayList<String>();
								file = fileDAO.getFile(category, ID);
								if(file.size() > 0) {
							%>
								<hr>
								첨부파일
							<%
									for(int i = 0; i < file.size(); i++) {
										String[] fileNames = file.get(i).split(",");
							%>
							<div><%= fileNames[0] %></div>
							<div class="form-check">
								<input class="form-check-input" type="checkbox" name="del<%= i %>" value="del_<%= fileNames[0] %>" id="delCheck">
								<label class="form-check-label" for="delCheck"> 삭제 </label>
							</div>
							<br>
								<%
										}
									}
								%>
							<hr>
							추가 첨부파일: <input multiple="multiple" type="file" id="file" name="file" accept="image/*"><br/><br/>
							<input type="submit" class="btn btn-primary" value="글쓰기">
						</form>
					</div>
				</div>
			</main>
		</div>
	</div>
	<div id="imfooter"></div>
</body>
</html>