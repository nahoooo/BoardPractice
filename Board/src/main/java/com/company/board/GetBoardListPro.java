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

import com.company.board.vo.BoardVO;
import com.company.common.JDBCConnection;

@WebServlet("/GetBoardListPro")
public class GetBoardListPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// dao에 하기 이전  서블릿으로 구현시킨 게시글 리스트.
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int startRow = Integer.parseInt(request.getParameter("startRow"));
		int endRow = Integer.parseInt(request.getParameter("endRow"));

		// 페이징 처리를 위한 sql / 인라인뷰, rownum 사용
		String sql ="select * from (select rownum rn, SEQ, TITLE, NICKNAME, CONTENT, REGDATE, CNT from (select * from board order by SEQ desc)) where rn between ? and ?";
		
		
		try {
			conn = JDBCConnection.getConnection();			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, startRow);
			stmt.setInt(2, endRow);
			rs = stmt.executeQuery();
			
			//여러 개의 데이터 값을 전달 할 떄는 컬렉션(arraylist같은...)을 사용하여 vo값을 전달한다.(while(rs.next())사용
			//단일 데이터 값을 전달 할 때는 vo값만 사용하여 전달한다.(if(rs.next())사용.
			
			ArrayList<BoardVO> boardList = new ArrayList<BoardVO>();
			

			while (rs.next()) {
				BoardVO board = new BoardVO();
											
				board.setSeq(rs.getInt("seq"));
				board.setTitle(rs.getString("title"));
				board.setNickname(rs.getString("nickname"));
				board.setContent(rs.getString("content"));
				board.setRegdate(rs.getDate("regdate"));
				board.setCnt(rs.getInt("cnt"));
				
				boardList.add(board);// 각 줄을 리스트에 담기. board의 하나의 객체를 생성하고 그걸 리스트에 하나씩 담음. 
			}
			
			//요구시 한번 보여주면 되기때문에 requset영역에 전달.	
			
			//1.전달할 데이터를 REQUEST에 담는다.
			request.setAttribute("boardList", boardList);
			//2. 지금 사용하는 request와 response를 지정한 페이지로 전달해서 
			//동일한 request와 response영역을 사용하도록 지정.
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

}


