<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String name = (String) session.getAttribute("name");
if(name==null) response.sendRedirect("login.jsp");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://unpkg.com/bootstrap@3.3.7/dist/css/bootstrap.min.css">
<title>새 글 등록</title>
</head>
<body>
	<div align="center" style="margin: auto auto">
		<h3>새 글 등록하기..... <a href="LogoutPro">LOG-OUT</a> </h3>
		<hr>

		<form action="AddBoardPro" method="post">
			<table class="table" style="width: 800px">
				<tr>
					<td>제목</td>
					<td align="left"><input type="text" name="title"
						class="form-control" /></td>
				</tr>
				<tr>
					<td>작성자</td>
					<td align="left"><input type="text" name="nickname" size="10"
						class="form-control"></td>
				</tr>
				<tr>
					<td>내용</td>
					<td align="left"><textarea name="content" rows="10" cols="40"
							class="form-control">
					</textarea></td>
				</tr>
				<tr>
					<td colspan="2" align="center"><input type="submit"
						value="새 글 등록" class="btn btn-primary" /></td>
				</tr>

			</table>

		</form>
		<hr>


	</div>
</body>
</html>