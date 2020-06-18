<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="Project.*" %>
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
</style>
</head>
<body>
	<div id="menu"></div>
	<div class="container">
		<div class="row">
			<nav class="col-md-2 d-none d-md-block bg-light sidebar" style="max-width: 200px">
				<h4 style="text-align: center;">PROJECT</h4>
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
					<h1 class="h2">프로젝트</h1>
				</div>
				<%
					ProjectDAO projectDAO = new ProjectDAO();
					int[] yearList = projectDAO.yearList();
					for(int i = 0; i < yearList.length; i++) {
						ArrayList<ProjectDTO> list = projectDAO.getProject(yearList[i]);
				%>
				<div class="my-3 p-3 bg-light rounded shadow-sm">
					<h6 class="pb-2 mb-0"><%= yearList[i] %></h6>
					<div class="media text-muted pt-3">
						<div class="media-body pb-3 mb-0 small lh-125">
					<%
						for(int j = 0; j < list.size(); j++) {
					%>
							<div class="pb-2">
								<div class="d-flex justify-content-between align-items-center w-100">
									<strong class="text-gray-dark"><%= list.get(j).getTitle() %></strong>
								</div>
								<span class="d-block"><%= list.get(j).getContent() %></span>
							</div>
					<%
						}
					%>
						</div>
					</div>
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