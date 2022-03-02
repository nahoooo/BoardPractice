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

@WebServlet("/GetBoardListPro")
public class GetBoardListPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/GetBoardListPro");
		
		int page;
		if(request.getParameter("page")==null)
			page=1;
		else 
			page=Integer.parseInt(request.getParameter("page"));
		
		HttpSession session=request.getSession();
		String name=(String)session.getAttribute("name");
		if(name==null) {
			response.sendRedirect("login.jsp");
			return;
		}
		//DB접속
				Connection conn=null;
				PreparedStatement stmt=null;
				ResultSet rs=null;
				
				try {
					conn=JDBCConnection.getConnection();
//					String sql="select * from board order by seq desc";
					
					String sql="select * from (select rownum as rnum,B.* from (select * from board order by seq desc) B)"
							+ " where rnum between ? and ?";
					// page 1: 1-10
					// page 2: 11-20
					// page 3: 21-30
					stmt=conn.prepareStatement(sql);
					
					// page를 이용하여 각 페이지에 담기는 레코드의 rownum값을 환산
					stmt.setInt(1, page*10-9);
					stmt.setInt(2, page*10);
					
					rs=stmt.executeQuery();
					ArrayList<BoardVO> boardList=new ArrayList<BoardVO>();
					
					while(rs.next()) {
						int seq=rs.getInt("seq");
						String nickname=rs.getString("nickname");
						String content=rs.getString("content");
						String title=rs.getString("title");
						Date regdate=rs.getDate("regdate");
						int cnt=rs.getInt("cnt");
						
						BoardVO board=new BoardVO();
						board.setSeq(seq);
						board.setNickname(nickname);
						board.setContent(content);
						board.setTitle(title);
						board.setRegdate(regdate);
						board.setCnt(cnt);
						
						boardList.add(board);   // 각 줄을 리스트에 담는다.
					}
					
					// PrepareStatmement와 Resultset을 재활용하기 위해 자원을 닫고 다시 사용.
					stmt.close();
					rs.close();
					
					sql="select max(seq) from board";
					stmt=conn.prepareStatement(sql);
					rs=stmt.executeQuery();
					
					int totalCount=0; //전체 게시글 수 담는 변수
					if(rs.next()) {
						totalCount=rs.getInt(1);
					}
					System.out.println(totalCount);
					
					
					
					// 1. 전달할 데이터를 request에 담는다.
					request.setAttribute("boardList", boardList);
					request.setAttribute("totalRows", totalCount);
					
					// 2. 지금 사용하는 request와 response를 지정한 페이지로 전달해서
					// 동일한 request와 response를 사용하도록 지정
					RequestDispatcher view=request.getRequestDispatcher("getBoardList.jsp");
					view.forward(request, response);
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					JDBCConnection.close(rs, stmt, conn);
				}
				
				
				
	}

	
	

}
