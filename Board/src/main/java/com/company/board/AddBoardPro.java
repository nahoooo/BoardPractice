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
		
		request.setCharacterEncoding("utf-8");
		System.out.println("/AddBoardPro");
		//로그인이 안되어있으면 로그인 페이지로 이동시킴. 주소창에 직접 주소를 입력해 업데이트 하려하는것을 방지.
		HttpSession session = request.getSession();
		String name = (String)session.getAttribute("name");
		if(name==null) {
			response.sendRedirect("login.jsp");
			//post 일경우에는 return 안해도 됨. post는 주소를 타고 들어오지 않기때문.
		}
		
		//넘어오는 값.		
		String title = request.getParameter("title");
		String nickname = request.getParameter("nickname");
		String content = request.getParameter("content");
		
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn=JDBCConnection.getConnection();
			String sql = "insert into board(seq,title,nickname,content) values((select nvl(max(seq),0)+1 from board),?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, title);
			stmt.setString(2, nickname);
			stmt.setString(3, content);
			
		//반환값이 int임.
		int cnt = stmt.executeUpdate();
		//한줄 if 한줄로 쓰기.
		if(cnt!=0) response.sendRedirect("getBoardList.jsp");
		
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCConnection.close(stmt, conn);
		}
		
	}
	}


