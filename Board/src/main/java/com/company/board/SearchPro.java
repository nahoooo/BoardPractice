package com.company.board;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.company.board.vo.BoardVO;
import com.company.common.JDBCConnection;

@WebServlet("/SearchPro")
public class SearchPro extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("/SearchPro");
		// 세션 아이디 확인, 없으면 로그인 페이지로
		HttpSession session = request.getSession();
		String name = (String) session.getAttribute("name");
		System.out.println(name);
		if (name == null) {
			response.sendRedirect("login.jsp");
			return; // 함수의 리턴의 의미는 함수의 값을 반환과 함수의 종료를 의미.
		}
		
		int page;
		if(request.getParameter("page")==null)
			page=1;
		else 
			page=Integer.parseInt(request.getParameter("page"));

		String searchCondition = request.getParameter("searchCondition");
		String searchKeyword = request.getParameter("searchKeyword");
		System.out.println(searchCondition);
		System.out.println(searchKeyword);

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		
		try {
			conn = JDBCConnection.getConnection();
			stmt = search(conn, searchCondition, searchKeyword, page);
			// search메소드를 만들어서 검색방법에 따라 결과값을 나오게 하는 기능을 별도로 메소드화 하였음.
			// 검색방법 추가 시 seacrh메소드 참고하여 추가하면 됨.
			rs = stmt.executeQuery();

			ArrayList<BoardVO> boardList = new ArrayList<BoardVO>();
			while (rs.next()) {
				
				int seq = rs.getInt("seq");
				String nickname = rs.getString("nickname");
				String content = rs.getString("content");
				String title = rs.getString("title");
				Date regdate = rs.getDate("regdate");
				int cnt = rs.getInt("cnt");

				BoardVO board = new BoardVO();
				board.setSeq(seq);
				board.setNickname(nickname);
				board.setContent(content);
				board.setTitle(title);
				board.setRegdate(regdate);
				board.setCnt(cnt);

				boardList.add(board); // 각 줄을 리스트에 담는다.

			}
			
			stmt.close();
			rs.close();
			
			String sql = "select count(*) from board where " + searchCondition + " like '%'|| ? ||'%'";
			
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, searchKeyword);
			
			rs=stmt.executeQuery();
			
			int totalCount=0; //전체 게시글 수 담는 변수
			if(rs.next()) {
				totalCount=rs.getInt(1);
			}
			System.out.println(totalCount);
			
			// 1. 전달할 데이터를 request에 담는다.
			request.setAttribute("boardList", boardList);
			request.setAttribute("totalRows", totalCount);
			request.setAttribute("searchCondition", searchCondition);
			request.setAttribute("searchKeyword", searchKeyword);
			
			
			// 2. 지금 사용하는 request와 response를 지정한 페이지로 전달해서
			// 동일한 request와 response를 사용하도록 지정
			RequestDispatcher view = request.getRequestDispatcher("getBoardList.jsp");
			view.forward(request, response);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, stmt, conn);
		}

	}

	private PreparedStatement search(Connection conn, String searchCondition, String searchKeyword, int page) throws SQLException {
		PreparedStatement stmt=null;
		final String TITLE_SEARCHING_SQL="select * from (select rownum rn, SEQ, TITLE, NICKNAME, CONTENT, REGDATE, CNT from (select * from board where title like '%' || ? || '%' order by seq desc)) where rn between ? and ?";
		
		  final String CONTENT_SEARCING_SQL="select * from (select rownum rn, SEQ, TITLE, NICKNAME, CONTENT, REGDATE, CNT from (select * from board where content like '%' || ? || '%' order by seq desc)) where rn between ? and ?";
		 final String
		  NICKNAME_SEARCING_SQL="select * from (select rownum rn, SEQ, TITLE, NICKNAME, CONTENT, REGDATE, CNT from (select * from board where nickname like '%' || ? || '%' order by seq desc)) where rn between ? and ?";
	
		 
		// 검색 방식이 제목 검색일 경우 처리
		
		  if (searchCondition.equals("title")) {
			  stmt =conn.prepareStatement(TITLE_SEARCHING_SQL); 
			  stmt.setString(1, searchKeyword);
			  stmt.setInt(2, page*10-9);
				stmt.setInt(3, page*10);
			  
		  } else if(searchCondition.equals("content")){ stmt =
		  conn.prepareStatement(CONTENT_SEARCING_SQL); stmt.setString(1,searchKeyword);
		  stmt.setInt(2, page*10-9);
			stmt.setInt(3, page*10);
		  } 
		  else { 
			  stmt = conn.prepareStatement(NICKNAME_SEARCING_SQL); 
			  stmt.setString(1, searchKeyword); 
			  stmt.setInt(2, page*10-9);
				stmt.setInt(3, page*10);
		  }

		
		
		return stmt;
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
