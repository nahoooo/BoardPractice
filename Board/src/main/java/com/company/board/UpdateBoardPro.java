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


@WebServlet("/UpdateBoardPro")
public class UpdateBoardPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//로그인을 안했으면 로그인 페이지로 이동..(서블릿 이름을 직접 기입하여 로그인 하지 않고 글을 업데이트하는 
		// 것을 방지
		System.out.println("/UpdateBoardPro");
		HttpSession session=request.getSession();
		String name=(String)session.getAttribute("name");
		if(name==null) {
			response.sendRedirect("login.jsp");
			return;
		}
		
		// 넘어오는 seq값을 받는다.
		request.setCharacterEncoding("utf-8");
		int num=Integer.parseInt(request.getParameter("seq"));
		String title=request.getParameter("title");
		String content=request.getParameter("content");
		
		Connection conn=null;
		PreparedStatement stmt=null;
		
		try {
			conn=JDBCConnection.getConnection();
			String sql="update board set title=?,content=? where seq=?";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, title);
			stmt.setString(2, content);
			stmt.setInt(3, num);
			
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
