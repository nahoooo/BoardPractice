<%@page import="com.company.board.vo.BoardVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
//서블릿에서 전달한 값을 받는다.
BoardVO board = (BoardVO) request.getAttribute("board");
//접속한 유저 이름 추출, 세션영역활용.
String name = (String) session.getAttribute("name");
if (name == null) {//로그인을 안했으면 로그인 페이지로 이동시킴. 주소로 치고들오는 사람들을 위한 보안방책.
	response.sendRedirect("login.jsp");
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://unpkg.com/bootstrap@3.3.7/dist/css/bootstrap.min.css">
<title>글 상세</title>
</head>
<body>
	<div align="center" style="margin: auto auto">
		<h1>글 상세</h1>
		<h3>
			<%=name%>님 환영합니다....
			<a href="LogoutPro">Log-out</a>
			<!--세션을 활용한 로그인 아이디 표시  -->
		</h3>
		<hr>

		<form action="UpdateBoardPro" method="post">
			<!--상세 보기 페이지에서 수정 기능도 같이 만듬. 수정버튼을 누르면 수정페이지로 넘어감. -->
			<!-- 수정 서블릿에서 이 게시글을 식별할 키값이 부재  -->
			<input type="hidden" name="seq" value="<%=board.getSeq()%>">
			<table class="table" style="width: 800px">
				<tr>
					<td>제목</td>
					<td align="left">
						<input type="text" name="title" value="<%=board.getTitle()%>" class="form-control"><!--제목 수정이 가능하게 input안에 제목을 넣는다. -->
					</td>
				</tr>
				<tr>
					<td>작성자</td>
					<td align="left"><%=board.getNickname() %></td><!-- 작성자는 변경할 일이 없기 떄문에 td태그안에 바로 값을 넣어준다. -->
				</tr>
				<tr>
					<td>내용</td>
					<td align="left"> <textarea name="content" rows="10" cols="40"><%=board.getContent() %></textarea> </td>
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
					<input type="submit" value="글 수정" class="btn btn-primary">
					</td>					
				</tr>
			</table>

		</form>
		<hr>
		<a href="addBoard.jsp">글 등록</a>&nbsp;&nbsp;&nbsp;
		<a href="DeleteBoardPro?num=<%=board.getSeq()%>">글삭제</a>&nbsp;&nbsp;&nbsp;
		<a href="getBoardList.jsp">글목록</a>
	</div>
</body>
</html>