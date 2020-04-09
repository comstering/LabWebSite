<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Activity.PostDTO" %>
<%@ page import="Activity.PostDAO" %>
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
					int pageNumber = 1;
					if(request.getParameter("pageNumber") != null) {
						pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
					}
				%>
				<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center
				pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">행사</h1>
				</div>
				<div class="row">
					<%
						PostDAO postDAO = new PostDAO();
						ArrayList<PostDTO> list = postDAO.getList("Event", pageNumber);
						if(list.size() == 0) {
					%>
					<div class="text-center">
						데이터가 없습니다.
					</div>
					<%
						} else {
							for(int i = 0; i < list.size(); i++) {
					%>
					<div class="col-md-4">
						<div class="card mb-4 shadow-sm">
							<img  src="../Image/Introduction/Professor.png" class="bd-placeholder-img card-img-top" width="100%">
							<div class="card-body">
								<p class="card-text"><%= list.get(i).getTitle() %></p>
								<div class="d-flex justify-content-between align-items-center">
									<div class="btn-group">
										<button type="button" class="btn btn-sm btn-outline-secondary">View</button>
										<button type="button" class="btn btn-sm btn-outline-secondary">Edit</button>
									</div>
									<small class="text-muted"><%= list.get(i).getDate().substring(0,11) %></small>
								</div>
							</div>
						</div>
					</div>
					<%
							}
						}
					%>
				</div>
				<%
					if(pageNumber != 1) {
				%>
					<a href="Event.jsp?pageNumber=<%= pageNumber - 1 %>" class="btn btn-success btn-arraw-left">이전</a>
				<%
					}
					if(postDAO.nextPage("Event", pageNumber + 1)) {
				%>
					<a href="Event.jsp?pageNumber=<%= pageNumber + 1 %>" class="btn btn-success btn-arraw-left">다음</a>
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