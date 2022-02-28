<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://unpkg.com/bootstrap@3.3.7/dist/css/bootstrap.min.css">
<title>Index Page</title>
</head>
<body>
	<div align="center" style="margin: auto auto; height: 100%;">
		<h1>게시판 제작 예제</h1>
		<br> <br>
		<%
		String name = (String) session.getAttribute("name"); //세션에 로그인 데이터가 남아있을 경우.
		if (name == null) {//없다면 다시 로그인
		%>
		<div style="widt: 800px">
			<a href="login.jsp">로그인</a>
			<a href="getBoardList.jsp">글 목록</a>
			<a href="addBoard.jsp">글 쓰기</a>
		</div>
		<%
		} else {
		%>
		<!--로그인 데이터가 남아있을 경우.  -->
		<div style="widt: 800px">
			<a href="LogoutPro">로그아웃</a>
			<a href="getBoardList.jsp">글 목록</a>
			<a href="addBoard.jsp">글 쓰기</a>
		</div>
		<%
		}
		%>
	</div>
</body>
</html>