package com.company.user;

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

import com.company.common.JDBCConnection;


@WebServlet("/Idchk")
public class Idchk extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	
	
	
	

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id =	request.getParameter("id");
		
		request.setCharacterEncoding("utf-8");
		Connection conn=null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn=JDBCConnection.getConnection();
			String sql = "select * from users where id=?";
			stmt =	conn.prepareStatement(sql);
			stmt.setString(1, id);
			rs=stmt.executeQuery();
			
			
			
			
			  //팝업창에 결과 문구를 띄워주는 방식. 
			if(rs.next()) {//아이디가 중복이면 
				request.setAttribute("id", id);
				RequestDispatcher dispatcher = request.getRequestDispatcher("idchkForm.jsp?filePath=-1");
				dispatcher.forward(request, response); } 
			else{//사용할 수 있는 아이디라면
			  request.setAttribute("id", id); 
			  RequestDispatcher  dispatcher = request.getRequestDispatcher("idchkForm.jsp?filePath=1");
			  dispatcher.forward(request, response); }
			 
			
			/*//별도 페이지 이동방식.
			  if(rs.next()) {//id중복 발생. idchkTrue페이지로 이동
			  response.sendRedirect("idchkTrue.jsp"); }
			  else {//id중복 없음. 사용가능.idchkFalse페이지로 이동. 
			request.setAttribute("id", id); 
			RequestDispatcher dispatcher = request.getRequestDispatcher("idchkFalse.jsp");
			  dispatcher.forward(request, response); }
			 */
			
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCConnection.close(rs, stmt, conn);
		}
		
		
		
		
	}

}
