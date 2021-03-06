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
<script src="../jsFolder/join.js"></script>
<script src="../jsFolder/fixing.js"></script>
<title>Network Security Lab</title>
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
		<form class="form-signup">
			<h1 class="h3 mb-4 font-weight-normal text-center">회원가입</h1>
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="ID">아이디</label>
					<input type="text" id="ID" name="userID" class="form-control" placeholder="아이디(학번)" maxlength="20" required autofocus>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="Password">비밀번호</label>
					<input type="password" onKeyup="checkPass()" id="Password" name="userPassword" class="form-control" placeholder="비밀번호" maxlength="20" required>
				</div>
				<div class="col-md-6 themed-container text-center" >
					<br>
					<small id="checkPass1"></small>
					<small id="checkPass2"></small>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="Password">비밀번호확인</label>
					<input type="password" onKeyup="doubleCheckPass()" id="checkPassword" class="form-control" placeholder="비밀번호확인" maxlength="20" required>
				</div>
				<div class="col-md-6 themed-container text-center" >
					<br>
					<small id="doublePass"></small>
				</div>
			</div>
			<div class="form-row">
				<div class="form-group col-md-6">
					<label for="Name">이름</label>
					<input type="text" id="Name" name="userName" class="form-control" placeholder="이름" maxlength="20" required>
				</div>
				<div class="form-group col-md-6">
					<label for="Phnoe">핸드폰번호</label>
					<input type="text" id="Phone" name="userPhoneNumber" class="form-control" placeholder="010-1234-5678" maxlength="13" required>
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
	<div id="imfooter"></div>
	
	<script>		
		var form = document.forms[0];
		
		form.onsubmit = function() {
			event.preventDefault();    //  일단 멈춤
			
			if(!checkPassword($("#Password").val())) {
				document.getElementById("Password").focus();
				return;
			}
			if(!doubleCheck($("#checkPassword").val())) {
				document.getElementById("checkPassword").focus();
				return;
			}
			
			this.action = "JoinAction.jsp";
			this.method = "post";
			this.submit();
		}
	</script>
	
	
</body>
</html>