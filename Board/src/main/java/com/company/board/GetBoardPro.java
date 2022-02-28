package com.company.board;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.company.board.vo.BoardVO;
import com.company.common.JDBCConnection;


@WebServlet("/GetBoardPro")
public class GetBoardPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
   
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//로그인 없이 주소로 타고들어오는 사람을을 위한 로그인 코드
		HttpSession session = request.getSession();
		String name =(String)session.getAttribute("name");
		if(name==null) {
			response.sendRedirect("login.jsp");
			return;
		}
		
		//넘어오는 seq값을 받는다. 변수명은 num으로 지정.
		int num = Integer.parseInt(request.getParameter("num"));
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = JDBCConnection.getConnection();
			
			//1.조회수 1증가. cnt upgrade   하위쿼리 사용. 클릭해서 볼때마다 조회수 1씩 증가시키기.
			String sql = "update board set cnt =(select cnt+1 from board where seq=?) where seq=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, num);
			stmt.setInt(2, num);
			
			stmt.executeUpdate();
			stmt.close(); //stmt종료
			
			
			//2. 조건에 맞는 데이터 가져오기			
			sql="select * from board where seq=?";
			stmt=conn.prepareStatement(sql);
			stmt.setInt(1, num);
			
			rs=stmt.executeQuery();
			
			//여러 개의 데이터 값을 전달 할 떄는 컬렉션(arraylist같은...)을 사용하여 vo값을 전달한다.(while(rs.next())사용
			//단일 데이터 값을 전달 할 때는 vo값만 사용하여 전달한다.(if(rs.next())사용.
			BoardVO board = null;
			
			if(rs.next()) {
				board=new BoardVO();
				board.setSeq(rs.getInt("seq"));
				board.setNickname(rs.getString("nickname"));
				board.setContent(rs.getString("content"));
				board.setTitle(rs.getString("title"));
				board.setRegdate(rs.getDate("regdate"));
				board.setCnt(rs.getInt("cnt"));
			}
			
			
									
					
			request.setAttribute("board", board);
	
			RequestDispatcher view = request.getRequestDispatcher("getBoard.jsp");
			view.forward(request, response);
			
		
			
			
		
			
			
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, stmt, conn);
		}

	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

}
