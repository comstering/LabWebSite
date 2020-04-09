<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Board.PostDTO" %>
<%@ page import="Board.PostDAO" %>
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
<title>Network Security Lab</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#menu").load("../Menu.jsp")
	});
</script>
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
				<h4 style="text-align: center;">BOARD</h4>
				<div class="sidebar-stick">
					<ul class="nav flex-column" style="text-align: center;">
						<script src="../Submenu.js">
						</script>
					</ul>
				</div>
			</nav>
			<main role="main" class="col-md-9 px-4" style="max-width: 72%">
				<%
					String category = null;
					int ID = 0;
				if(request.getParameter("category") != null && request.getParameter("ID") != null) {
					category = request.getParameter("category");
					ID = Integer.parseInt(request.getParameter("ID"));
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
					<h1 class="h2">공지사항</h1>
				</div>
				<div>
				<div>
					<table class="table table-striped table-sm">
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
								<td colspan="2"><br><%= postDTO.getContent() %></td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
			</main>
		</div>
	</div>
</body>
</html>