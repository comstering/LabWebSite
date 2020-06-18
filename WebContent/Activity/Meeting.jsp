<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="User.UserDAO" %>
<%@ page import="Post.PostDTO" %>
<%@ page import="Post.PostDAO" %>
<%@ page import="File.FileDAO" %>
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
<script src="../jsFolder/fixing.js"></script>
<script src="../jsFolder/reload.js"></script>
<title>Network Security Lab</title>
<style>
</style>
</head>
<body>
	<div id="menu"></div>
	<div class="container">
		<div class="row">
			<nav class="col-md-2 d-none d-md-block bg-light sidebar" style="max-width: 200px">
				<h4 style="text-align: center;">ACTIVITY</h4>
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
					<h1 class="h2">모임</h1>
				</div>
				<div class="row">
					<%
						PostDAO postDAO = new PostDAO();
						FileDAO fileDAO = new FileDAO();
						ArrayList<PostDTO> list = postDAO.getList("Meeting", pageNumber);
						if(list.size() == 0) {
					%>
					<div class="text-center">
						데이터가 없습니다.
					</div>
					<%
						} else {
							for(int i = 0; i < list.size(); i++) {
								ArrayList<String> fileNames = fileDAO.getFile("Meeting", list.get(i).getID());
					%>
					<div class="col-md-4">
						<div class="card mb-4 shadow-sm">
							<img src="/filepath<%= fileDAO.getPath() %>Meeting/<%= fileNames.get(0).substring(fileNames.get(0).lastIndexOf(",") + 1, fileNames.get(0).length()) %>" class="bd-placeholder-img card-img-top" width="100%">
							<div class="card-body">
								<p class="card-text abb"><%= list.get(i).getTitle() %></p>
								<div class="d-flex justify-content-between align-items-center">
									<div class="btn-group">
										<a href="view.jsp?category=Meeting&ID=<%= list.get(i).getID() %>" type="button" class="btn btn-sm btn-outline-secondary">View</a>
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
					<a href="Meeting.jsp?pageNumber=<%= pageNumber - 1 %>" class="btn btn-success btn-arraw-left">이전</a>
				<%
					}
					if(postDAO.nextPage("Meeting", pageNumber + 1)) {
				%>
					<a href="Meeting.jsp?pageNumber=<%= pageNumber + 1 %>" class="btn btn-success btn-arraw-left">다음</a>
				<%
					}
				%>
				<%
					UserDAO userDAO = new UserDAO();
					if((session.getAttribute("userID") != null) && userDAO.checkAuthority((String)session.getAttribute("userAuthority"))) {
				%>
				<div class="text-right">
					<a href="write.jsp" class="btn btn-primary">글쓰기</a>
				</div>
				<%
					}
				%>
			</main>
		</div>
	</div>
	<div id="imfooter"></div>
</body>
</html>