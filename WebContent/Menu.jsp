<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header>
	<nav class="navbar navbar-expand-lg navbar-light" style="background-color: skyblue;">
		<div class="container">
			<a class="navbar-brand" href="#">Home</a>
			<button class="navbar-toggler collapsed" type="button" data-toggle="collapse" data-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="navbar-collapse collapse show" id="navbarColor01" style>
				<ul class="navbar-nav mr-auto">
					<li class="nav-item">
						<a class="nav-link" href="#">INTRODUCTION</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="#">PROJECT</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="#">ACTIVITY</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="#">BOARD</a>
					</li>
				</ul>
				<form class="form-inline">
					<input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
					<button class="btn btn-outline-primary my-2 my-sm-0" type="submint">Search</button>
				</form>
			</div>
		</div>
	</nav>
</header>

<div class="blog-header py-3">
	<div class="container">
	<div class="row flex-nowrab justify-content-between align-items-center">
		<div class="text-center" style="width: 100%; position: relative; padding-right: 15px; padding-left: 15px;">
			<a class="blog-header-logo text-dark" href="../Index/index.jsp">
				<img src="../Image/Base/logo.png" style="height: 100px;"alt="">
			</a>
		</div>
		<%
			if(session.getAttribute("userID") == null) {    //  로그인이 안되어 있을경우
		%>
		<div class="col-11 d-flex justify-content-end align-item-center">
			<a class="btn btn-outline-secondary mr-3" href="../Login/Login.jsp">Sign In</a>
			<a class="btn btn-outline-primary" href="../Login/Join.jsp">Sign Up</a>
		</div>
		<%
			} else if(session.getAttribute("userID") != null &&
			session.getAttribute("userName") != null
			&& session.getAttribute("userAuthority") != null) {    //  로그인이 되어있을경우
		%>
		<div class="col-11 d-flex justify-content-end align-item-center">
			<a class="btn btn-outline-primary" href="../Login/LogoutAction.jsp">Sign Out</a>
		</div>
		<%} %>
	</div>
	
	</div>
</div>

<nav class="site-header sticky-top py-1" style="background-color: skyblue;">
	<div class="container d-flex flex-column flex-md-row justify-content-between">
		<a class="py-2 d-md-inline-block" href="../Introduction/NSLab.jsp">INTRODUCTION</a>
		<a class="py-2 d-md-inline-block" href="../Project/Project.jsp">PROJECT</a>
		<a class="py-2 d-md-inline-block" href="../Activity/Contest(Univ).jsp">ACTIVITY</a>
		<a class="py-2 d-md-inline-block" href="../Board/Notice.jsp">BOARD</a>
	</div>
</nav>