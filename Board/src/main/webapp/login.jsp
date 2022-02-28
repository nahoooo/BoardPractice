<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!--css부트스트랩 사용.  -->
<link rel="stylesheet" href="https://unpkg.com/bootstrap@3.3.7/dist/css/bootstrap.min.css">
<title>로그인</title>
</head>
<body>

	<div align="center" style="margin:auto auto">
		<h1>로그인</h1>
		<hr>
		<!--get은 주소표시줄에 비밀번호 노출가능. 한글이 인코딩이 안되고 변환되어서 전달, 내용이 길면 제대로 전달이 안됨.사이즈가 정해져있기 때문.  -->
		<form action="LoginPro" method="post">
			<table class="table" style="width:400px">
				<tr>
					<td>아이디</td>
					<td><input type="text" name="id" value="abc"></td>
				</tr>
				<tr>
					<td>비밀번호</td>
					<td><input type="password" name="password" value="abc123"></td>
				</tr>
				<tr>
					<td colspan="2" align="center">
					<input type="submit" value="로그인" class="btn btn-primary">
					<input type="button" value="회원가입" onclick="location.href='joinForm.jsp';" class="btn btn-primary">
					</td>					
				</tr>
			</table>
		</form>
	</div>

</body>
</html>