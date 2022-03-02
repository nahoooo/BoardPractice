<%@page import="com.company.board.vo.BoardVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
//1.접속한 유저 이름 추출
// 로그인을 안했으면 로그인 페이지로 이동시킨다.
String name = (String) session.getAttribute("name");
if (name == null)
	response.sendRedirect("login.jsp");

//2.Servlet이 전달한 데이터를 받는다.
BoardVO board = (BoardVO) request.getAttribute("board");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://unpkg.com/bootstrap@3.3.7/dist/css/bootstrap.min.css">
<title>글 상세</title>
</head>
<body>
	<div align="center" style="margin: auto auto">
		<h1>글 상세</h1>
		<h3>
			<%=name%>님 환영합니다..... <a href="LogoutPro">Log-out</a>
		</h3>
		<hr>

		<form action="UpdateBoardPro" method="post">
		
			<input type="hidden" name="seq" value="<%=board.getSeq()%>">

			<table class="table" style="width: 800px">
				<tr>
					<td>제목</td>
					<td align="left"><input type="text" name="title"
						value="<%=board.getTitle()%>" class="form-control" /></td>
				</tr>
				<tr>
					<td>작성자</td>
					<td align="left"><%=board.getNickname() %></td>
				</tr>
				<tr>
					<td>내용</td>
					<td align="left"> <textarea class="form-control" rows="10" name="content" rows="10" cols="40"><%=board.getContent() %>
					</textarea> </td>
				</tr>
				<tr>
					<td>등록일</td>
					<td align="left"><%=board.getRegdate() %></td>
				</tr>
				<tr>
					<td>조회수</td>
					<td align="left"><%=board.getCnt() %></td>	
				</tr>
				<tr>
					<td colspan="2" align="center">  
					<input type="submit" value="글 수정" class="btn btn-primary"/> 
					</td>
				</tr>

			</table>

		</form>
		<hr>
		<a href="addBoard.jsp">글 등록</a>&nbsp;&nbsp;&nbsp;
		<a href="DeleteBoardPro?num=<%=board.getSeq()%>">글삭제</a>&nbsp;&nbsp;&nbsp;
		<a href="GetBoardListPro">글목록</a>

	</div>
</body>
</html>