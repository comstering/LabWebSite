<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%
	if(session.getAttribute("userID") != null) {
	PrintWriter script = response.getWriter();
	script.println("<script>");
	script.println("history.back()");
	script.println("</script>");
	script.close();
	return;
}
%>
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
	.form-signin {
		width: 100%;
		max-width: 450px;
		padding: 15px;
		margin: auto;
	}
</style>
</head>
<body>
	<div id="menu"></div>
	<div class="text-center">
		<form class="form-signin" method="post" action="LoginAction.jsp">
			<h1 class="h3 mb-4 font-weight-normal text-center">로그인</h1>
			<div class=form-group>
				<label for="ID">아이디</label>
				<input type="text" id="ID" name="userID" class="form-control" placeholder="아이디(학번)" maxlength="20" required autofocus>
			</div>
			<div class="form-group">
				<label for="Password">비밀번호</label>
				<input type="password" id="Password" name="userPassword" class="form-control" placeholder="비밀번호" maxlength="20" required>
			</div>
			<button class="btn btn-lg btn-secondary btn-block mt-4" type="submit">Sign in</button>
		</form>
	</div>
	<div id="imfooter"></div>
</body>
</html>