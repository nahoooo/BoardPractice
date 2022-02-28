package com.company.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;

import com.company.board.vo.BoardVO;

public class JDBCConnection {
	
	//데이터 베이스 접속 메소드
	public static Connection getConnection() throws ClassNotFoundException, SQLException {		
		Class.forName("oracle.jdbc.driver.OracleDriver");
		String url="jdbc:oracle:thin:@localhost:1521:xe";
		Connection	conn =	DriverManager.getConnection(url, "scott", "tiger");		
		return conn;		
	}
	
	//접속 종료 메소드 rs,stmt,conn  
	public static void close(ResultSet rs,PreparedStatement stmt,Connection conn) {
		//오버리딩기능으로 인수 갯수가 다르기때문에 같은 메소드이름이지만 다른메소드로 인식.
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		if(stmt!=null) {
			try {
				stmt.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
	}
	//접속 종료 메소드 stmt,conn	
	public static void close(PreparedStatement stmt,Connection conn) {	
		if(stmt!=null) {
			try {
				stmt.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {				
				e.printStackTrace();
			}
		}
	}
	//ajax 아이디 중복 체크 함수.
	public int checkId(String id) throws ClassNotFoundException, SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		conn=getConnection();
		String sql = "select * from users where id=?";
		int idCheck = 0;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1,id);
			
			rs = stmt.executeQuery();
			
			if(rs.next()) {
				idCheck = 0; //이미 존재하는 경우, 생성 불가능.
			}else {
				idCheck = 1;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(rs, stmt, conn);
		}
		return idCheck;
		
	}
	
	// 총 레코드 수 구하는 로직
	public int getCount(){
		int count = 0;
		String sql = "select count(*) from board";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()){
				count = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rs, stmt, conn);
		}
		return count; // 총 레코드 수 리턴
	}
	
	//일반 게시글 리스트 불러오는 로직
	public ArrayList<BoardVO> getList(int startRow, int endRow){
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
	

		// 페이징 처리를 위한 sql / 인라인뷰, rownum 사용
		String sql ="select * from (select rownum rn, SEQ, TITLE, NICKNAME, CONTENT, REGDATE, CNT from (select * from board order by SEQ desc)) where rn between ? and ?";
		
		ArrayList<BoardVO> boardList = null;
		
		try {
			conn = JDBCConnection.getConnection();			
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, startRow);
			stmt.setInt(2, endRow);
			rs = stmt.executeQuery();
			
			//여러 개의 데이터 값을 전달 할 떄는 컬렉션(arraylist같은...)을 사용하여 vo값을 전달한다.(while(rs.next())사용
			//단일 데이터 값을 전달 할 때는 vo값만 사용하여 전달한다.(if(rs.next())사용.
			
			boardList = new ArrayList<BoardVO>();
			

			while (rs.next()) {
				BoardVO board = new BoardVO();
											
				board.setSeq(rs.getInt("seq"));
				board.setTitle(rs.getString("title"));
				board.setNickname(rs.getString("nickname"));
				board.setContent(rs.getString("content"));
				board.setRegdate(rs.getDate("regdate"));
				board.setCnt(rs.getInt("cnt"));
				
				boardList.add(board);// 각 줄을 리스트에 담기. board의 하나의 객체를 생성하고 그걸 리스트에 하나씩 담음. 
			}
			
			//요구시 한번 보여주면 되기때문에 requset영역에 전달.	
			
			
			
			
		} catch (ClassNotFoundException e) {

			e.printStackTrace();
		} catch (SQLException e) {

			e.printStackTrace();
		} finally {
			JDBCConnection.close(rs, stmt, conn);
		}
		return boardList;
	}
	
	//검색 레코드 개수 구하는 로직.
	public int getScount(String select, String searchKeyword) {
		
		int count=0;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		System.out.println(select +"검색 갯수 셀렉");
		System.out.println(searchKeyword +"검색 갯수 키워드");
		
		String sql = "select count(*) as cnt from board where ? like '%' || ? || '%' order by seq desc";
		
		
		try {
			conn = JDBCConnection.getConnection();			
			stmt = conn.prepareStatement(sql);	
			stmt.setString(1, select);
			stmt.setString(2, searchKeyword);
			rs = stmt.executeQuery();
			if(rs.next()) {
				count = rs.getInt("cnt");			
			}
			System.out.println(count +"개의 검색결과");
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			close(rs, stmt, conn);
		}
		return count;
	}
	
	//검색 레코드 리스트 불러오는 로직;
	
	public ArrayList<BoardVO> getSearchList(String select, String searchKeyword,int starRow, int endRow){
				
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
	
		//상수로 만들어서 사용.
		String sql = "select * from (select rownum rn, SEQ, TITLE, NICKNAME, CONTENT, REGDATE, CNT from (select * from board where ? like '%' || ? || '%' order by seq desc)) where rn between ? and ?";

		System.out.println(select +"검색 리스트 셀렉");
		System.out.println(searchKeyword+"검색 리스트 키워드");
		System.out.println(starRow +"검색 리스트 시작행");
		System.out.println(endRow +"검색 리스트 마지막행");
		
		ArrayList<BoardVO> boardList = null;
		
		try {
			conn = JDBCConnection.getConnection();
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, select);
			stmt.setString(2, searchKeyword);
			stmt.setInt(3, starRow);
			stmt.setInt(4, endRow);
			rs = stmt.executeQuery();
			
			boardList = new ArrayList<BoardVO>();
			

			while (rs.next()) {
				
				BoardVO board = new BoardVO();
											
				board.setSeq(rs.getInt("seq"));
				board.setTitle(rs.getString("title"));
				board.setNickname(rs.getString("nickname"));
				board.setContent(rs.getString("content"));
				board.setRegdate(rs.getDate("regdate"));
				board.setCnt(rs.getInt("cnt"));
				
				boardList.add(board);
			}
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}finally {
			JDBCConnection.close(rs, stmt, conn);
		}
		
		return boardList;
	
	}
	
}
