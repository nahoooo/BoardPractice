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


@WebServlet("/DeleteBoardPro")
public class DeleteBoardPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/DeleteBoardPro");
		HttpSession session=request.getSession();
		String name=(String)session.getAttribute("name");
		System.out.println(name);
		if(name==null) {
			response.sendRedirect("login.jsp");
			return; //함수의 리턴의 의미는 함수의 값을 반환과 함수의 종료를 의미.
		}
		
		
		// 넘어오는 seq값을 받는다.
				int num=Integer.parseInt(request.getParameter("num"));
				
				Connection conn=null;
				PreparedStatement stmt=null;
				
				try {
					conn=JDBCConnection.getConnection();
					String sql="delete from board where seq=?";
					stmt=conn.prepareStatement(sql);
					stmt.setInt(1, num);
					
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

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
