package com.company.user;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.company.common.JDBCConnection;

// web.xml에 이 이름으로 LoginPro 클래스를 등록해준다. 에노테이션을 이용한 mapping url 기술
@WebServlet("/LoginPro")
public class LoginPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("/LoginPro");
		request.setCharacterEncoding("utf-8");   //없으면 한글로 넘어오면 깨짐...
		String id=request.getParameter("id");
		String password=request.getParameter("password");
		
		//DB접속
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		try {
			conn=JDBCConnection.getConnection();
			String sql="select * from users where id=? and password=?";
			stmt=conn.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, password);
			
			rs=stmt.executeQuery();
			if(rs.next()) {//로그인 성공
				String name=rs.getString("name");
				HttpSession session=request.getSession();
				session.setAttribute("name", name);
				response.sendRedirect("GetBoardListPro");
			}else { //로그인 실패
				response.sendRedirect("login.jsp");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCConnection.close(rs, stmt, conn);
		}
	}

}
