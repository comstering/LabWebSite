<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Search.*" %>
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
<title>Network Security Lab</title>
<style>
	div>h2 {
		display: inline;
	}
	.carousel-inner {
		width: 100%;
		overflow: hidden;
	}
	.carousel-item a {
		max-width: 100%;
		height: auto;
	}
	
	a, a:hover {
		color: black;
		text-decoration: none;
	}
</style>
</head>
<body>
	<div id="menu"></div>
	<div class="container mt-4">
	<%
		request.setCharacterEncoding("UTF-8");
		String search = request.getParameter("search");
		SearchDAO searchDAO = new SearchDAO();
		ArrayList<SearchDTO> list = searchDAO.searchPost(search);
	%>
	
	<form class="form-inline" method="post" action=../Search/Search.jsp>
		<input type="text" name="search" class="form-control" value="<%= search %>" required autofocus>
		<button class="btn btn-outline-primary my-2 my-sm-0" type="submit">Search</button>
	</form>
	<hr>
	<%
		if(list.size() == 0) {
	%>
	데이터가 없습니다.
	<%
		} else {
			for(int i = 0; i < list.size(); i++) {
				String menu = "";
				if(list.get(i).getCategory().equals("Notice") || list.get(i).getCategory().equals("Library")) {
					menu = "Board/view.jsp?category=" + list.get(i).getCategory() + "&ID=" + list.get(i).getId();
				} else {
					menu = "Activity/view.jsp?category=" + list.get(i).getCategory() + "&ID=" + list.get(i).getId();
				}
	%>
	<div>
		<a href="../<%= menu %>" class="abb"><b><%= list.get(i).getTitle() %></b></a>
		<p class="abb"><%= list.get(i).getContent() %></p>
		<hr style="border-style: dotted">
	</div>
	<%
			}
		}
	%>
	</div>
	<div id="imfooter"></div>
</body>
</html>