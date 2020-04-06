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
<script type="text/javascript">
	$(document).ready(function() {
		$("#menu").load("../Menu.jsp")
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
				<h4 style="text-align: center;">BOARD</h4>
				<div class="sidebar-stick">
					<ul class="nav flex-column" style="text-align: center;">
						<script src="../Submenu.js">
						</script>
					</ul>
				</div>
			</nav>
			<main role="main" class="col-md-9 px-4" style="max-width: 72%">
				<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center
				pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">자료실</h1>
				</div>
				<div>
				<div class="table-responsive">
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
							<tr>
								<td style="background-color #eee; text-align: center;">1</td>
								<td style="background-color #eee; text-align: center;">안녕하세요</td>
								<td style="background-color #eee; text-align: center;">홍길동</td>
								<td style="background-color #eee; text-align: center;">2020-04-04</td>
								<td style="background-color #eee; text-align: center;">20</td>
							</tr>
							<tr>
								<td style="background-color #eee; text-align: center;">2</td>
								<td style="background-color #eee; text-align: center;">안녕하세요</td>
								<td style="background-color #eee; text-align: center;">홍길동</td>
								<td style="background-color #eee; text-align: center;">2020-04-04</td>
								<td style="background-color #eee; text-align: center;">20</td>
							</tr>
							<tr>
								<td style="background-color #eee; text-align: center;">3</td>
								<td style="background-color #eee; text-align: center;">안녕하세요</td>
								<td style="background-color #eee; text-align: center;">홍길동</td>
								<td style="background-color #eee; text-align: center;">2020-04-04</td>
								<td style="background-color #eee; text-align: center;">20</td>
							</tr>
							<tr>
								<td style="background-color #eee; text-align: center;">4</td>
								<td style="background-color #eee; text-align: center;">안녕하세요</td>
								<td style="background-color #eee; text-align: center;">홍길동</td>
								<td style="background-color #eee; text-align: center;">2020-04-04</td>
								<td style="background-color #eee; text-align: center;">20</td>
							</tr>
						</tbody>
					</table>
					<div class="text-right">
						<a href="write.jsp" class="btn btn-primary">글쓰기</a>
					</div>
				</div>
			</div>
			</main>
		</div>
	</div>
</body>
</html>