<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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

<script type="text/javascript">
	$(document).ready(function() {
		$("#menu").load("../jsFolder/Menu.jsp")
	});
</script>
</head>
<body>
	<div id="menu"></div>
	<div class="container mt-4">
		<div id="carouselExampleFade" class="carousel slide carousel-fade"
			data-ride="carousel">
			<ol class="carousel-indicators">
				<li data-target="#carouselExampleFade" data-slide-to="0" class="active"></li>
				<li data-target="#carouselExampleFade" data-slide-to="1"></li>
				<li data-target="#carouselExampleFade" data-slide-to="2"></li>
			</ol>
			<div class="carousel-inner">
				<div class="carousel-item active">
					<img src="../Image/Index/img1.jpg" class="d-block w-100" height="450" alt="...">
				</div>
				<div class="carousel-item">
					<img src="../Image/Index/img2.jpg" class="d-block w-100" height="450" alt="...">
				</div>
				<div class="carousel-item">
					<img src="../Image/Index/img3.jpg" class="d-block w-100" height="450" alt="...">
				</div>
			</div>
			<a class="carousel-control-prev" href="#carouselExampleFade" role="button" data-slide="prev">
				<span class="carousel-control-prev-icon" aria-hidden="true"></span>
				<span class="sr-only">Previous</span>
			</a>
			<a class="carousel-control-next" href="#carouselExampleFade" role="button" data-slide="next">
				<span class="carousel-control-next-icon" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>
		
		<div class="row justify-content-between" style="padding: 3rem 1rem">
			<div class="jumbotron col-md-5" style="padding: 2rem 1rem;">
				<div class="d-flex justify-content-between align-items-center">
					<h2>공지사항</h2>
					<a class="btn btn-dark" href="../Board/Notice.jsp">+더보기</a>
				</div>
				<table class="table table-striped table-sm mt-4">
					<tbody>
						<%
							PostDAO postDAO = new PostDAO();
							ArrayList<PostDTO> list = postDAO.getList("Notice", 1);
							if(list.size() == 0) {
						%>
							<tr>
								<td class="pl-3">데이터가 없습니다.</td>
							</tr>
						<%
							} else {
								for(int i = 0; i < list.size(); i++) {
						%>					
							<tr>
								<td class="pl-3"><a href="../Board/view.jsp?category=Notice&ID=<%= list.get(i).getID() %>"><%= list.get(i).getTitle() %></a></td>
								<td><%= list.get(i).getDate().substring(0,11) %></td>
							</tr>
						<%
								}
								
							}
						%>
					</tbody>
				</table>
			</div>
			<div class="jumbotron col-md-6" style="padding: 2rem 1rem">
				<div class="d-flex justify-content-between align-items-center">
					<h2>Activity</h2>
					<a class="btn btn-dark" href="../Activity/UnivContest.jsp">+더보기</a>
				</div>
				<div class="row">
					<%
						list = postDAO.getList("UnivContest", 1);
						if(list.size() == 0) {
					%>
					<div class="text-center">
						데이터가 없습니다.
					</div>
					<%
						} else {
							FileDAO fileDAO = new FileDAO();
							if(list.size() > 2) {
								for(int i = 0; i < 2; i++) {
									ArrayList<String> fileNames = fileDAO.getFile("UnivContest", list.get(i).getID());
					%>
					<div class="col-md-6">
						<div class="card mt-4 shadow-sm">
							<img src="/filepath<%= fileDAO.getPath() %>UnivContest/<%= fileNames.get(0).substring(fileNames.get(0).lastIndexOf(",") + 1, fileNames.get(0).length()) %>" class="bd-placeholder-img card-img-top" width="100%" height=210>
							<div class="card-body">
								<p class="card-text" style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;"><%= list.get(i).getTitle() %></p>
								<div class="d-flex justify-content-between align-items-center">
									<div class="btn-group">
										<a href="../Activity/view.jsp?category=UnivContest&ID=<%= list.get(i).getID() %>" type="button" class="btn btn-sm btn-outline-secondary">View</a>
									</div>
									<small class="text-muted"><%= list.get(i).getDate().substring(0,11) %></small>
								</div>
							</div>
						</div>
					</div>
					<%	
								}
							} else {
								for(int i = 0; i < list.size(); i++) {
									ArrayList<String> fileNames = fileDAO.getFile("UnivContest", list.get(i).getID());
					%>
					<div class="col-md-6">
						<div class="card mt-4 shadow-sm">
							<img src="/filepath<%= fileDAO.getPath() %>UnivContest/<%= fileNames.get(0).substring(fileNames.get(0).lastIndexOf(",") + 1, fileNames.get(0).length()) %>" class="bd-placeholder-img card-img-top" width="100%" height=210>
							<div class="card-body">
								<p class="card-text" style="overflow: hidden; text-overflow: ellipsis; white-space: nowrap;"><%= list.get(i).getTitle() %></p>
								<div class="d-flex justify-content-between align-items-center">
									<div class="btn-group">
										<a href="../Activity/view.jsp?category=UnivContest&ID=<%= list.get(i).getID() %>" type="button" class="btn btn-sm btn-outline-secondary">View</a>
									</div>
									<small class="text-muted"><%= list.get(i).getDate().substring(0,11) %></small>
								</div>
							</div>
						</div>
					</div>
					<%
								}
							}
						}
					%>
				</div>
				<img src=""> <img src="">
			</div>
		</div>
	</div>
</body>
</html>