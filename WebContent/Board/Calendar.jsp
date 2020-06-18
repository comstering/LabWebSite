<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="User.UserDAO" %>
<%@ page import="Board.CalendarDTO" %>
<%@ page import="Board.CalendarDAO" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width", initial-scale="1">
<link rel="stylesheet" href="../StyleCSS/Base.css">
<link rel="stylesheet" href="../StyleCSS/Calendar.css">
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<script src="../jsFolder/fixing.js"></script>
<script src="../jsFolder/reload.js"></script>
<title>Network Security Lab</title>
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
						<script src="../jsFolder/Submenu.js">
						</script>
					</ul>
				</div>
			</nav>
			<main role="main" class="col-md-9 px-4" style="max-width: 72%">
				<div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center
				pt-3 pb-2 mb-3 border-bottom">
					<h1 class="h2">일정</h1>
				</div>
				<%
					UserDAO userDAO = new UserDAO();
					if((session.getAttribute("userID") != null) && userDAO.checkAuthority((String)session.getAttribute("userAuthority"))) {
				%>
				<div class="text-right">
					<a href="registration.jsp" class="btn btn-info">일정등록</a>
				</div>
				<%
					}
				%>
				
				<%
					Calendar start_day = Calendar.getInstance();
					Calendar end_day = Calendar.getInstance();
					Calendar cal = Calendar.getInstance();
					start_day.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), 1);
					end_day.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), start_day.getActualMaximum(Calendar.DATE));
					int sday = start_day.get(Calendar.DAY_OF_WEEK);
					CalendarDAO calDAO = new CalendarDAO();
					ArrayList<CalendarDTO> list = calDAO.getSchedule(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
				%>
				<div class="container">
					<div class="calendar shadow bg-white p-5">
						<div class="d-flex align-items-center pb-3">
							<h2 class="month font-weight-bold mb-0 text-uppercase"><%= cal.get(Calendar.YEAR) %>년 <%= cal.get(Calendar.MONTH) + 1 %>월</h2>
						</div>
						<ol class="day-names list-unstyled">
							<li class="font-weight-bold text-uppercase text-center">Sun</li>
							<li class="font-weight-bold text-uppercase text-center">Mon</li>
							<li class="font-weight-bold text-uppercase text-center">Tue</li>
							<li class="font-weight-bold text-uppercase text-center">Wed</li>
							<li class="font-weight-bold text-uppercase text-center">Thu</li>
							<li class="font-weight-bold text-uppercase text-center">Fri</li>
							<li class="font-weight-bold text-uppercase text-center">Sat</li>
						</ol>

						<ol class="days list-unstyled">
							<%
								int day = 1;
								for(int i = 1; i < sday; i++) {
									day++;
							%>
							<li style="min-height: 10rem;">
								<div class="date"></div>
							</li>
							<%
								}
								for(int i = 1; i <= end_day.get(Calendar.DATE); i++) {
							%>
							<li style="min-height: 10rem;">
								<div class="date"><%= i %></div>
							<%
									for(int j = 0; j < list.size(); j++) {
										if(list.get(j).getStart_month() != (start_day.get(Calendar.MONTH) + 1)) {
											int end = list.get(j).getEnd_day();
											String content = list.get(j).getContent();
											if(i == 1) {
												if(end <= 8-sday) {
							%>
								<div class="event all-day end bg-secondary" style="width: <%= end %>00%"><%= content %></div>
							<%
												} else if(end > 8 - sday) {
							%>
								<div class="event all-day bg-secondary" style="width: <%= 8-sday %>00%; border-radius: 0 0 0 0;"><%= content %></div>
							<%
												}
											} else {
												if(day % 7 == 1) {
													if(end <= i + 6 && end >= i) {
							%>
								<div class="event all-day end bg-secondary" style="width: <%= end - i + 1 %>00%;"><%= content %></div>
							<%
													} else if(end > i + 6) {
							%>
								<div class="event all-day bg-secondary" style="width: <%= 7 %>00%; border-radius: 0 0 0 0;"><%= content %></div>
							<%
													}
												}
											}
										} else if(list.get(j).getEnd_month() != (start_day.get(Calendar.MONTH) + 1)) {
											int start = list.get(j).getStart_day();
											String content = list.get(j).getContent();
											int start_end = end_day.get(Calendar.DATE) - start + 1;
											if(start_end <= end_day.get(Calendar.DAY_OF_WEEK)) {
												if(i == start) {
							%>
								<div class="event all-day begin bg-secondary" style="width: <%= start_end %>00%"><%= content %></div>
							<%
												}
											}
										} else {
											int start = list.get(j).getStart_day();
											int end = list.get(j).getEnd_day();
											String content = list.get(j).getContent();
											if(i == start) {
												if(start == end) {
							%>
      							<div class="event bg-secondary"><%= content %></div>
							<%
												} else if(day % 7 == 0) {
							%>
								<div class="event all-day begin bg-secondary"><%= content %></div>
							<%
												} else {
													if((end - start + 1) <= (8 - (day % 7))) {
							%>
								<div class="event all-day bg-secondary" style="width: <%= end - start + 1 %>00%"><%= content %></div>
							<%
													} else {
							%>
								<div class="event all-day begin bg-secondary" style="width: <%= 8 - day % 7 %>00%"><%= content %></div>
							<%
													}
												}
											} else if(day % 7 == 1) {
												if(i > start && end >= i && end < i + 7) {
							%>
								<div class="event all-day end bg-secondary" style="width: <%= end - i + 1 %>00%"><%= content %></div>
							<%
												} else if(start < i && end >= i + 7) {
							%>
								<div class="event all-day bg-secondary" style="width: <%= 7 %>00%; border-radius: 0 0 0 0;"><%= content %></div>
							<%
												}
											}
										}
									}
							%>
							</li>
							<%
									day++;
								}
							%>
						</ol>
					</div>
				</div>
			</main>
		</div>
	</div>
	<div id="imfooter"></div>
</body>
</html>