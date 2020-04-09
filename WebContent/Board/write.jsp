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
					<h1 class="h2">게시판 글쓰기</h1>
				</div>
				<div>
				<div>
					<form method="post" action="writeAction.jsp" enctype="multipart/form.data">
						<table class="table table-striped table-sm">
							<thead  class="table-info">
								<tr>
									<td style="width: 250px">
										<div class="input-group mb-1">
											<div class="input-group-prepend">
												<label class="input-group-text" for="inputGroupSelect01">카테고리</label>
											</div>
											<select class="custom-select" id="inputGroupSelect01" name="category">
												<option selected value="Notice">공지사항</option>
												<option value="Data">자료실</option>
											</select>
										</div>
									</td>
									<td><input type="text" class="form-control" placeholder="글 제목" name="title" maxlength="50" required></td>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td colspan="2"><textarea type="text" class="form-control" placeholder="글 내용" name="content" maxlength="4096" style="height: 450px;" required></textarea></td>
								</tr>
							</tbody>
						</table>
						<%
						//첨부파일: <input multiple="multiple" type="file" id="file" name="file"><br/><br/>
						%>
						<input type="submit" class="btn btn-primary" value="글쓰기">
					</form>
				</div>
			</div>
			</main>
		</div>
	</div>
</body>
</html>