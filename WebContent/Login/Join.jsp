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
<title>Network Security Lab</title>
<script type="text/javascript">
	$(document).ready(function() {
		$("#menu").load("../jsFolder/Menu.jsp")
	});
	function inputPhoneNumber(obj) {
	    var number = obj.value.replace(/[^0-9]/g, "");
	    var phone = "";
	    if(number.length < 4) {
	        return number;
	    } else if(number.length < 7) {
	        phone += number.substr(0, 3);
	        phone += "-";
	        phone += number.substr(3);
	    } else if(number.length < 11) {
	        phone += number.substr(0, 3);
	        phone += "-";
	        phone += number.substr(3, 3);
	        phone += "-";
	        phone += number.substr(6);
	    } else {
	        phone += number.substr(0, 3);
	        phone += "-";
	        phone += number.substr(3, 4);
	        phone += "-";
	        phone += number.substr(7);
	    }
	    obj.value = phone;
	}
</script>
<style>
	.form-signup {
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
		<form class="form-signup" method="post" action="JoinAction.jsp">
			<h1 class="h3 mb-4 font-weight-normal text-center">회원가입</h1>
			<div class="form-group">
				<label for="ID">아이디</label>
				<input type="text" id="ID" name="userID" class="form-control" placeholder="아이디(학번)" maxlength="20" required autofocus>
			</div>
			<div class="form-group">
				<label for="Password">비밀번호</label>
				<input type="password" id="Password" name="userPassword" class="form-control" placeholder="비밀번호" maxlength="20" required>
			</div>
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="Name">이름</label>
					<input type="text" id="Name" name="userName" class="form-control" placeholder="이름" maxlength="20" required>
				</div>
				<div class="form-group col-md-6">
					<label for="Phnoe">핸드폰번호</label>
					<input type="text" onKeyup="inputPhoneNumber(this)" id="Phone" name="userPhoneNumber" class="form-control" placeholder="010-1234-5678" maxlength="13" required>
				</div>
			</div>
			<div class="form-group">
				<label for="Eamil">Email</label>
				<input type="email" id="Eamil" name="userEmail" class="form-control" placeholder="example@example.com" maxlength="30" required autofocus>
			</div>
			<div class="form-group row justify-content-between" style="padding: 0rem 7rem;">
				<label class="btn btn-info">
					<input type="radio" name="userGender" autocomplete="off" value="남자">남자
				</label>
				<label class="btn btn-info">
					<input type="radio" name="userGender" autocomplete="off" value="여자">여자
				</label>
			</div>
			<div class="form-group row justify-content-between" style="padding: 0rem 2rem;">
				<label class="btn btn-dark">
					<input type="radio" name="userAuthority" autocomplete="off" value="힉부생">학부생
				</label>
				<label class="btn btn-success">
					<input type="radio" name="userAuthority" autocomplete="off" value="대학원생">대학원생
				</label>
				<label class="btn btn-warning">
					<input type="radio" name="userAuthority" autocomplete="off" value="게스트">게스트
				</label>
			</div>
			<button class="btn btn-lg btn-primary btn-block mt-4" type="submit">Sign up</button>
		</form>
	</div>
</body>
</html>