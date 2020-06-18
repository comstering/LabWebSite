<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="User.UserDAO" %>
<%@ page import="Post.PostDTO" %>
<%@ page import="Post.PostDAO" %>
<%@ page import="Security.XSS" %>
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
						<script src="../jsFolder/Submenu.js">
						</script>
					</ul>
				</div>
			</nav>
			<main role="main" class="col-md-9 px-4" style="max-width: 72%">
				<%
					int pageNumber = 1;
					if(request.getParameter("pageNumber") != null) {
						pageNumber = Integer.parseInt(XSS.prevention(request.getParameter("pageNumber")));
					}
				%>
				<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center
				pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">공지사항</h1>
				</div>
				<table class="table table-striped table-sm" style="table-layout: fixed;">
					<thead  class="table-info">
						<tr>
							<th class="abb" style="background-color #eee; text-align: center;">번호</th>
							<th class="abb" width="45%" style="background-color #eee; text-align: center;">제목</th>
							<th class="abb" style="background-color #eee; text-align: center;">작성자</th>
							<th class="abb" style="background-color #eee; text-align: center;">등록일</th>
							<th class="abb" style="background-color #eee; text-align: center;">조회수</th>
						</tr>
					</thead>
					<tbody>
						<%
							PostDAO postDAO = new PostDAO();
							ArrayList<PostDTO> list = postDAO.getList("Notice", pageNumber);
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
							<td class="abb" style="background-color #eee; text-align: center;"><%= list.get(i).getID() %></td>
							<td class="abb" style="background-color #eee; text-align: center;"><a href="view.jsp?category=Notice&ID=<%= list.get(i).getID() %>"><%= list.get(i).getTitle() %></a></td>
							<td class="abb" style="background-color #eee; text-align: center;"><%= list.get(i).getWriter() %></td>
							<td class="abb" style="background-color #eee; text-align: center;"><%= list.get(i).getDate().substring(0,11) %></td>
							<td class="abb" style="background-color #eee; text-align: center;"><%= list.get(i).getCount() %></td>
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
					<a href="Notice.jsp?pageNumber=<%= pageNumber - 1 %>" class="btn btn-success btn-arraw-left">이전</a>
				<%
					}
					if(postDAO.nextPage("Notice", pageNumber + 1)) {
				%>
					<a href="Notice.jsp?pageNumber=<%= pageNumber + 1 %>" class="btn btn-success btn-arraw-left">다음</a>
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