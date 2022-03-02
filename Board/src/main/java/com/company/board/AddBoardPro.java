package com.company.board;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.company.common.JDBCConnection;


@WebServlet("/AddBoardPro")
public class AddBoardPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/AddBoardPro");
		HttpSession session=request.getSession();
		String name=(String)session.getAttribute("name");
		if(name==null) {
			response.sendRedirect("login.jsp");
			return;
		}
		
		// 넘어오는 seq값을 받는다.
				request.setCharacterEncoding("utf-8");
				String title=request.getParameter("title");
				String nickname=request.getParameter("nickname");
				String content=request.getParameter("content");
				
				Connection conn=null;
				PreparedStatement stmt=null;
				
				try {
					conn=JDBCConnection.getConnection();
					String sql="insert into board(seq,title,nickname,content) values((select nvl(max(seq),0)+1 from board),?,?,?)";
					stmt=conn.prepareStatement(sql);
					stmt.setString(1, title);
					stmt.setString(2, nickname);
					stmt.setString(3, content);
					
					int cnt=stmt.executeUpdate();
					if(cnt!=0) response.sendRedirect("GetBoardListPro");
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				} catch (SQLException e) {
					e.printStackTrace();
				}finally {
					JDBCConnection.close(stmt, conn);
				}
	}

}
