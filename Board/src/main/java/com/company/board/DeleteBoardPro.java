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
		request.setCharacterEncoding("utf-8");
		System.out.println("/DeleteBoardPro");
		//로그인이 안되어있으면 로그인 페이지로 이동시킴. 주소창에 직접 주소를 입력해 업데이트 하려하는것을 방지.
		HttpSession session = request.getSession();
		String name = (String)session.getAttribute("name");
		if(name==null) {
			response.sendRedirect("login.jsp");
			return; //함수의 값을 반환하고, 종료한다는 의미.
		}
		
		//넘어오는 값.		
		int num = Integer.parseInt(request.getParameter("num"));
				
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn=JDBCConnection.getConnection();
			String sql = "delete from board where seq=?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, num);
			
		//반환값이 int임.  select 만 executequery임.
		int cnt = stmt.executeUpdate();
		//한줄 if 한줄로 쓰기.
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
