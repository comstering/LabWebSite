<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Network Security Lab</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<link rel="stylesheet" href="../StyleCSS/Base.css">

<style>
</style>
</head>
<body>
	<div class="wrapper">
		<h1 class="title">
			<a href="../index.jsp"> <img src="../Image/base/logo.png" alt="">
			</a>
		</h1>

		<div style="height: 30px">
			<div class="login">
				<button>로그인</button>
				<button>회원가입</button>
			</div>
		</div>
		<p style="clear: both"></p>
	</div>	
	<div class="menu-box">
		<ul class="menu">
			<li><a href="../Introduction/NSLab.jsp">INTRODUCTION</a></li>
			<li><a href="../Project/Project.jsp">PROJECT</a></li>
			<li><a href="../Activity/Institute.jsp">ACTIVITY</a></li>
			<li style="border-right: 1px solid #000"><a href="../Board/Notice.jsp">BOARD</a></li>
		</ul>
	</div>
	<div class="wrapper">
		<div class="submenu-box">
			<h2>INTODUCTION</h2>
			<ul class="submenu">
				<li><a href="NSLab.jsp">NSLab</a></li>
				<li><a href="Professor.jsp">Professor</a></li>
				<li><a href="History.jsp">History</a></li>
				<li><a href="Member.jsp">Member</a></li>
			</ul>
		</div>
		
		<article class="contents">
			<h1>Professor</h1>
			<div style="display: block;">
				<img src="Professor.png" style="float: left; margin-right: 50px; width: 120px;">
				<div>
					<p>김황래 교수님</p>
					<p>전화번호 041-521-9227</p>
					<p>E-mail plusone@kongju.ac.kr</p>
					<p>연구실 충남 천안시 공주대학교공과대학 제 8공학관 902호</p>
				</div>
			</div>
			<div>
			<p>
				● 연구분야
				<div>
					컴퓨터네트워크
					정보보안
					IoT 보안
				</div>
			</p>
			</div>
		</article>

	</div>
</body>
</html>