<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Board.PostDTO" %>
<%@ page import="Board.PostDAO" %>
<%@ page import="java.util.ArrayList" %>
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
		$("#menu").load("../jsFolder/Menu.jsp")
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
					int pageNumber = 1;
					if(request.getParameter("pageNumber") != null) {
						pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
					}
				%>
				<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center
				pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">자료실</h1>
				</div>
				<table class="table table-striped table-sm">
					<thead class="table-info">
						<tr>
							<th style="background-color #eee; text-align: center;">번호</th>
							<th style="background-color #eee; text-align: center;">제목</th>
							<th style="background-color #eee; text-align: center;">작성자</th>
							<th style="background-color #eee; text-align: center;">등록일</th>
							<th style="background-color #eee; text-align: center;">조회수</th>
						</tr>
					</thead>
					<tbody>
						<%
							PostDAO postDAO = new PostDAO();
							ArrayList<PostDTO> list = postDAO.getList("Library", pageNumber);
							if(list.size() == 0) {
						%>
						<tr>
							<td colspan="5" class="text-center">데이터가 없습니다.</td>
						</tr>
						<%
							} else {
								for(int i = 0; i < list.size(); i++) {
						%>
						<tr>
							<td style="background-color #eee; text-align: center;"><%= list.get(i).getID() %></td>
							<td style="background-color #eee; text-align: center;"><a href="view.jsp?category=Library&ID=<%= list.get(i).getID() %>"><%= list.get(i).getTitle() %></a></td>
							<td style="background-color #eee; text-align: center;"><%= list.get(i).getWriter() %></td>
							<td style="background-color #eee; text-align: center;"><%= list.get(i).getDate().substring(0,11) %></td>
							<td style="background-color #eee; text-align: center;"><%= list.get(i).getCount() %></td>
						</tr>
						<%
								}
							}
						%>
					</tbody>
				</table>
				<%
					if(pageNumber != 1) {
				%>
					<a href="Library.jsp?pageNumber=<%= pageNumber - 1 %>" class="btn btn-success btn-arraw-left">이전</a>
				<%
					}
					if(postDAO.nextPage("Library", pageNumber + 1)) {
				%>
					<a href="Library.jsp?pageNumber=<%= pageNumber + 1 %>" class="btn btn-success btn-arraw-left">다음</a>
				<%
					}
				%>
				<div class="text-right">
					<a href="write.jsp" class="btn btn-primary">글쓰기</a>
				</div>
			</main>
		</div>
	</div>
</body>
</html>