<%@page import="com.company.common.JDBCConnection"%>
<%@page import="com.company.board.vo.BoardVO"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
//1.접속한 유저 이름 추출, 세션영역활용.
String name = (String) session.getAttribute("name");
if (name == null) {//로그인을 안했으면 로그인 페이지로 이동시킴. 주소로 치고들오는 사람들을 위한 보안방책.
	response.sendRedirect("login.jsp");
}

//페이징 구현 시작.

int pageSize = 10; //한번에 보여줄 게시글 수.

String pageNum = request.getParameter("pageNum"); //몇 페이지인지 알기 위한 변수
if (pageNum == null) { // 클릭한게 없으면 1번 페이지. 처음 시작은 1페이지로.
	pageNum = "1";
}

// 연산을 하기 위한 pageNum 형변환 / 현재 페이지  currentPage 
int currentPage = Integer.parseInt(pageNum);

//해당 페이지에서 시작할 레코드 / 마지막 레코드 --1번게시글에서 10번게시글까지.
int startRow = (currentPage - 1) * pageSize + 1;// (현재페이지 -1) * 한번에 보여줄 게시글 수 + 1;
int endRow = currentPage * pageSize;//현재페이지 * 한번에 보여줄 게시글 수;

//데이터베이스에 저장된 게시글의 총 개수 /  검색된 게시글의 총 개수.
int count = 0;

String select = request.getParameter("select");
String searchKeyword = request.getParameter("searchKeyword");

JDBCConnection jdbc = new JDBCConnection();

ArrayList<BoardVO> boardList = null;

if (searchKeyword == null) {
	count = jdbc.getCount();
	boardList = jdbc.getList(startRow, endRow);
} else if (searchKeyword != null) {
	count = jdbc.getScount(select, searchKeyword);
	boardList = jdbc.getSearchList(select, searchKeyword, startRow, endRow);
}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://unpkg.com/bootstrap@3.3.7/dist/css/bootstrap.min.css">
<title>글 목록</title>
</head>
<body>
	<div align="center" style="margin: auto auto">
		<h1>글 목록</h1>
		(총 게시글 수 :
		<%=count%>
		,
		<%=select%>
		,
		<%=searchKeyword%>)
		<h3>
			<%=name%>님 환영합니다....
			<a href="LogoutPro">Log-out</a>
			<!--세션을 활용한 로그인 아이디 표시  -->
		</h3>

		<!--검색 시작  -->
		<form action="getBoardList.jsp" method="get">
			<table class="table" style="width: 800px">
				<tr>
					<td align="right">
						<select name="select">
							<option value="title">제목</option>
							<option value="content">내용</option>
							<option value="nickname">작성자</option>
						</select>
						<input type="text" name="searchKeyword" id="searchKeyword" /> <input type="submit" value="검색" />
					</td>
				</tr>
			</table>
		</form>
		<!--검색 종료  -->
		<!--검색값이 없으면 기본적으로 게시글 리스트가 나옴.  -->

		<table class="table" style="width: 800px">
			<tr>
				<th width="100">번호</th>
				<th width="200">제목</th>
				<th width="150">작성자</th>
				<th width="150">등록일</th>
				<th width="100">조회수</th>
			</tr>
			<%
			if (count > 0) {
				//int number = count - (currentPage - 1) * pageSize; 글번호 순번을 위한 코드.
				for (int i = 0; i < boardList.size(); i++) {
					BoardVO board = boardList.get(i); //boardList의 값을 하나씩 다시 BoardVO로 넣어서 포장을 푼다.
			%>
			<tr>
				<td><%=board.getSeq()%></td>
				<td>
					<a href="GetBoardPro?num=<%=board.getSeq()%>"><%=board.getTitle()%></a>
				</td>
				<!--해당 게시글의 seq를 받아서 GetBoardPro의 num값으로 전달해준다. 해당글의 내용을 상세보기 하기위해 만든것.  -->
				<td><%=board.getNickname()%></td>
				<td><%=board.getRegdate()%></td>
				<td><%=board.getCnt()%></td>
			</tr>
			<%
			}
			} else {
			%>
			<tr>
				<td colspan="6" align="center">게시글이 없습니다.</td>
			</tr>
			<%
			}
			%>

			<tr>
				<td colspan="6" align="right">
					<%-- 버튼을 클릭하면 addBoard.jsp로 이동 --%>
					<input type="button" value="글작성" onclick="location.href='addBoard.jsp'">
				</td>
			</tr>
			<tr>
				<td colspan="6" align="center">
					<%
					// 페이징  처리 (밑에 번호 페이지번호)
					if (count > 0) {
						// 총 페이지의 수
						int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);
						// 한 페이지에 보여줄 페이지 블럭(링크) 수
						int pageBlock = 3;
						// 한 페이지에 보여줄 시작 및 끝 번호(예 : 1, 2, 3 ~ 10 / 11, 12, 13 ~ 20)
						int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
						int endPage = startPage + pageBlock - 1;

						// 마지막 페이지가 총 페이지 수 보다 크면 endPage를 pageCount로 할당
						if (endPage > pageCount) {
							endPage = pageCount;
						}

						if (startPage > pageBlock) { // 페이지 블록수보다 startPage가 클경우 이전 링크 생성
					%>
					<a href="getBoardList.jsp?pageNum=<%=startPage - 3%>">[이전]</a>
					<%
					}

					for (int i = startPage; i <= endPage; i++) { // 페이지 블록 번호
					if (i == currentPage) { // 현재 페이지에는 링크를 설정하지 않음
					%>
					[<%=i%>]
					<%
					} else { // 현재 페이지가 아닌 경우 링크 설정
					%>
					<a href="getBoardList.jsp?pageNum=<%=i%>">
						[<%=i%>]
					</a>
					<%
					}
					} // for end

					if (endPage < pageCount) { // 현재 블록의 마지막 페이지보다 페이지 전체 블록수가 클경우 다음 링크 생성
					%>
					<a href="getBoardList.jsp?pageNum=<%=startPage + 3%>">[다음]</a>
					<%
					}
					}
					%>
				</td>
			</tr>
		</table>
		<br>
		<a href="addBoard.jsp">새 글 등록</a>
		<a href="getBoardList.jsp">글목록</a>

	</div>
</body>
</html>