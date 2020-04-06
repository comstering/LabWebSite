<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
		height: 450px;
		overflow: hidden;
	}
	.carousel-item a {
		max-width: 100%;
		height: auto;
	}
</style>

<script type="text/javascript">
	$(document).ready(function() {
		$("#menu").load("../Menu.jsp")
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
					<img src="../Image/Index/img1.jpg" class="d-block w-100" alt="...">
				</div>
				<div class="carousel-item">
					<img src="../Image/Index/img2.jpg" class="d-block w-100" alt="...">
				</div>
				<div class="carousel-item">
					<img src="../Image/Index/img3.jpg" class="d-block w-100" alt="...">
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
		
		<div class="row justify-content-between" style="padding: 3rem 6rem">
			<div class="jumbotron col-md-5">
				<h2>공지사항</h2>
				<a class="btn btn-dark" href="../Board/Notice.jsp">+더보기</a>
				<ul>
					<li>list</li>
					<li>list</li>
					<li>list</li>
					<li>list</li>
				</ul>
			</div>
			<div class="jumbotron col-md-5">
				<h2>Activity</h2>
				<a class="btn btn-dark" href="../Activity/Contest(Univ).jsp">+더보기</a>
				<img src=""> <img src="">
			</div>
		</div>
	</div>
</body>
</html>