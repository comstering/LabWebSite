<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Post.PostDTO" %>
<%@ page import="Post.PostDAO" %>
<%@ page import="File.FileDAO" %>
<%@ page import="Security.XSS" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.io.PrintWriter" %>
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
 a, a:hover {
 	color: black;
 	text-decoration: none;
 }
</style>
</head>
<body>
	<div id="menu"></div>
	<div class="container">
		<div class="row">
			<nav class="col-md-2 d-none d-md-block bg-light sidebar" style="max-width: 200px">
				<h4 style="text-align: center;">Activity</h4>
				<div class="sidebar-stick">
					<ul class="nav flex-column" style="text-align: center;">
						<script src="../jsFolder/Submenu.js">
						</script>
					</ul>
				</div>
			</nav>
			<main role="main" class="col-md-9 px-4" style="max-width: 72%">
				<%
					String category = null;
					int ID = 0;
				if(request.getParameter("category") != null && request.getParameter("ID") != null) {
					category = XSS.prevention(request.getParameter("category"));
					ID = Integer.parseInt(XSS.prevention(request.getParameter("ID")));
				}
				if(category == null) {
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('유효하지 않은 카테고리')");
					script.println("history.back()");
					script.println("</script>");
				}
				if(ID == 0) {
					PrintWriter script = response.getWriter();
					script.println("<script>");
					script.println("alert('유효하지 않은 글입니다.')");
					script.println("history.back()");
					script.println("</script>");
				}
				PostDTO postDTO = new PostDAO().getPost(category, ID);
				%>
				<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center
				pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">
						<script src="../jsFolder/setH2.js"></script>
						<script>getH2("<%= category %>")</script>
					</h1>
				</div>
				<div>
				<div>
					<div class="text-right">
						<a href="reWrite.jsp?category=<%= category %>&id=<%= ID %>&title=<%=postDTO.getTitle() %>&content=<%= postDTO.getContent() %>" class="btn btn-secondary">글수정</a>
						<a href="delete.jsp?category=<%= category %>&id=<%= ID %>" class="btn btn-danger">글삭제</a>
					</div>
					<table class="table table-striped">
						<thead  class="table-info">
							<tr>
								<th class="text-center" colspan="2"><%= postDTO.getTitle() %></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td style="width: 20%";>등록일</td>
								<td><%= postDTO.getDate() %></td>
							</tr>
							<tr>
								<td style="width: 20%";>작성자</td>
								<td><%= postDTO.getWriter() %></td>
							</tr>
							<tr>
								<td style="width: 20%";>최종 수정일</td>
								<td><%= postDTO.getReDate() %></td>
							</tr>
							<tr>
								<td style="width: 20%";>최종 수정자</td>
								<td><%= postDTO.getReWriter() %></td>
							</tr>
							<tr>
								<td style="width: 20%";>조회수</td>
								<td><%= postDTO.getCount() %></td>
							</tr>
							<tr>
								<td colspan="2" class="text-center"><br>
									<div>
										<%
											FileDAO fileDAO = new FileDAO();
											ArrayList<String> fileNames = fileDAO.getFile(category, ID);
											for(int i = 0; i < fileNames.size(); i++) {
										%>
										<img src="/filepath<%= fileDAO.getPath() %><%= category %>/<%= fileNames.get(i).substring(fileNames.get(i).lastIndexOf(",") + 1, fileNames.get(i).length()) %>" >
										<%
											}
										%>
									</div>
									<br><br>
									<div>
										<%= postDTO.getContent() %>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			</main>
		</div>
	</div>
	<div id="imfooter"></div>
</body>
</html>