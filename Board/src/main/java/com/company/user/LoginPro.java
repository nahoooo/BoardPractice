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

// 애노테이션. 알아서 메핑이 되는 기능. web.xml에 이 이름으로 LoginPro 클래스를 등록해준다. 
//애노테이션을 이용한 mapping url기술.
@WebServlet("/LoginPro")
public class LoginPro extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
   
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8"); //post방식에서 이걸 넣어주지않으면 한글로 전송시 깨짐. 
		String id =	request.getParameter("id");
		String password = request.getParameter("password");
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn=JDBCConnection.getConnection();
			String sql = "select * from users where id=? and password=?";
			stmt =	conn.prepareStatement(sql);
			stmt.setString(1, id);
			stmt.setString(2, password);
			
			rs=stmt.executeQuery();
			
			//여러 개의 데이터 값을 전달 할 떄는 컬렉션(arraylist같은...)을 사용하여 vo값을 전달한다.(while(rs.next())사용
			//단일 데이터 값을 전달 할 때는 vo값만 사용하여 전달한다.(if(rs.next())사용.
			
			if(rs.next()) {//로그인이 성공했을 경우.
			String name =	rs.getString("name"); //해당 이름을 불러와서 변수 저장.
			HttpSession session = request.getSession();//세션 객체 생성,  페이지가 열릴때마다 ~님 환영합니다는 세션영역.
			session.setAttribute("name", name);//세션객체에 name 저장 (객체이름,객체 object타입.)-> object타입이니까 형변환을 받는페이지에서 해줘야함.
			response.sendRedirect("getBoardList.jsp"); //get방식으로 전송.
			}else {//로그인이 실패했을 경우.
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
