package com.company.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.common.JDBCConnection;


@WebServlet("/Idchkajax")
public class Idchkajax extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
  

	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("euc-kr");
		
	String userId =	request.getParameter("userId");
	PrintWriter out = response.getWriter();
	
	
	

		
		try {
			JDBCConnection jdbc = new JDBCConnection();
			int idCheck = jdbc.checkId(userId);
			
			if(idCheck ==0) {
				System.out.println("이미 존재하는 아이디");
			}else if(idCheck==1) {
				System.out.println("사용 가능한 아이디");
			}
			out.write(idCheck + "");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		
	
	
	
	}

}
