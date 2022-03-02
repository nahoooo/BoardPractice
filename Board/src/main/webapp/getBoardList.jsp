<%@page import="com.company.board.vo.BoardVO"%>
<%@page import="java.util.ArrayList"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
//1.접속한 유저 이름 추출
// 로그인을 안했으면 로그인 페이지로 이동시킨다.
String name = (String) session.getAttribute("name");
int pg;  // page변수로 현재 페이지 값을 받아서 페이징 처리에 이용..
int totalCount;



// 현재 페이지 변수 받음.
if(request.getParameter("page")==null){
	pg=1;
}else{
	pg=Integer.parseInt(request.getParameter("page")); 
}

//전체 레코드의 수 구하기  차후 구하기...일단 기본 값으로 1로 해놓겠다.
if(request.getAttribute("totalRows")==null){
	totalCount=1;
}else{
	totalCount= (Integer)request.getAttribute("totalRows");
}

if (name == null)
	response.sendRedirect("login.jsp");

String searchKeyword = request.getParameter("searchKeyword");
String searchCondition = request.getParameter("searchCondition");


//2.request에 사용할 데이터가 들어가 있다.

ArrayList<BoardVO> boardList = (ArrayList<BoardVO>) request.getAttribute("boardList");

%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://unpkg.com/bootstrap@3.3.7/dist/css/bootstrap.min.css">
<title>글 목록</title>
</head>
<body>
	<div align="center" style="margin: auto auto">
		<h1>글 목록</h1>
		<h3>
			<%=name%>님 환영합니다..... <a href="LogoutPro">Log-out</a>
		</h3>

		<!-- 검색 시작 -->
		<form method="get" action="SearchPro">
			<table class="table" style="width: 800px">
				<tr>
					<td align="right">
					<select name="searchCondition">
					<option value="title">제목
					<option value="content">내용
					<option value="nickname">작성자
					</select>
					<input type="text" name="searchKeyword"/>
					<input type="submit" value="검색"/>
					</td>
				</tr>
			</table>
		</form>
		<!-- 검색 종료 -->

		<table class="table" style="width: 800px">
			<tr>
				<th width="100">번호</th>
				<th width="200">제목</th>
				<th width="150">작성자</th>
				<th width="150">등록일</th>
				<th width="100">조회수</th>
			</tr>

			<%
			for (int i = 0; i < boardList.size(); i++) {
				BoardVO board = boardList.get(i);
			%>
			<tr>
				<td><%=board.getSeq()%></td>
				<td><a href="GetBoardPro?num=<%=board.getSeq()%>"><%=board.getTitle()%></a> </td>
				<td><%=board.getNickname()%></td>
				<td><%=board.getRegdate()%></td>
				<td>&nbsp;&nbsp;&nbsp;&nbsp;<%=board.getCnt()%></td>
			</tr>
			<%
			}
			%>
		</table>
		<!-- 페이지 리스트 삽입 시작 부분-->
		<%
		int countList = 10; // 한 페이지에 출력될 게시물 수(10개를 기준으로 잡음)
		int countPage = 10; // 한 화면에 출력될 페이지 수(통상적으로 10개 페이지를 나오게 함)
					
		int totalPage = totalCount / countList; // totalPage는 전체 페이지 수 (전체 게시물 / 한 페이지에 출력될 게시물 수)
		
		if (totalCount % countList > 0) {
			// totalCount를 countList로 나눈 나머지 값이 존재한다는 것은 한 페이지가 더 있다는 의미이다.
			totalPage++;
		}
		
		if (totalPage < pg) {// 현재 페이지가 전체 페이지보다 크다면 이는 출력될 페이지 범위를 벗어난 현제 페이지를 의미
			// 따라서 현재페이지를 가장 끝 페이지인 totalPage로 이동시킨다.
			pg = totalPage;
		}
		
		int startPage = ((pg - 1) / 10) * 10 + 1;
		// 현재 페이지를 기준으로 한 화면에서 시작 페이지 값을 보여준다.

		int endPage = startPage + countPage - 1;
		// 현재 페이지를 기준으로 한 화면에서 끝 페이지 값을 보여준다.
		
		// 전체 게시물이 255개 기준으로 할 경우 전체 페이지는 26개가 나온다. 내가 보려고 하는 현재
		// 페이지가 22페이지라고 했을 때 21에서 30까지 나오면 전체 페이지는 26까지 있기 때문에 실제
		// 리스트를 가지고 있지 않은 페이지가 발생한다. (27,28,29,30 페이지 해당)
		if (endPage > totalPage) {
			endPage = totalPage;
		}
		
		if(searchKeyword==null){
			if (startPage > 1) {%>
			<a href="GetBoardListPro?page=1">처음</a>
		<% }
		
		if (pg > 1) {%>
			<a href="GetBoardListPro?page=<%=pg-1%>">이전</a>
		<% }
		
		for (int iCount = startPage; iCount <= endPage; iCount++) {
			if (iCount == pg) {%>
				
				<b><%=iCount %></b>&nbsp;
			
			<% } else { %>
				
				&nbsp;<a href="GetBoardListPro?page=<%=iCount%>"><%=iCount %></a>&nbsp;
			
			<% }
		}
		
		if (pg < totalPage) { %>
			<a href="GetBoardListPro?page=<%=pg+1%>">다음</a>
		<% }

		if (endPage < totalPage) { %>
			<a href="GetBoardListPro?page=<%=totalPage%>">끝</a>
		<% }
		}else if(searchKeyword!=null){
			if (startPage > 1) {%>
			<a href="SearchPro?page=1&searchCondition=<%=searchCondition%>&searchKeyword=<%=searchKeyword%>">처음</a>
		<% }
		
		if (pg > 1) {%>
			<a href="SearchPro?page=<%=pg-1%>&searchCondition=<%=searchCondition%>&searchKeyword=<%=searchKeyword%>">이전</a>
		<% }
		
		for (int iCount = startPage; iCount <= endPage; iCount++) {
			if (iCount == pg) {%>
				
				<b><%=iCount %></b>&nbsp;
			
			<% } else { %>
				
				&nbsp;<a href="SearchPro?page=<%=iCount%>&searchCondition=<%=searchCondition%>&searchKeyword=<%=searchKeyword%>"><%=iCount %></a>&nbsp;
			
			<% }
		}
		
		if (pg < totalPage) { %>
			<a href="SearchPro?page=<%=pg+1%>&searchCondition=<%=searchCondition%>&searchKeyword=<%=searchKeyword%>">다음</a>
		<% }

		if (endPage < totalPage) { %>
			<a href="SearchPro?page=<%=totalPage%>&searchCondition=<%=searchCondition%>&searchKeyword=<%=searchKeyword%>">끝</a>
		<% }
		}			
		
		%>
		<!-- 페이지 리스트 삽입 끝 부분-->
		
		<br> <a href="addBoard.jsp">새 글 등록</a> &nbsp;&nbsp; <a href="GetBoardListPro">글 목록</a>
	</div>
</body>
</html>