package com.company.board;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.company.board.vo.BoardVO;
import com.company.common.JDBCConnection;

@WebServlet("/Searchboard")
public class Searchboard extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*
	 * protected void doGet(HttpServletRequest request, HttpServletResponse
	 * response) throws ServletException, IOException {
	 * request.setCharacterEncoding("utf-8"); int startRow =
	 * Integer.parseInt(request.getParameter("startRow")); int endRow =
	 * Integer.parseInt(request.getParameter("endRow")); String select =
	 * request.getParameter("select"); String searchKeyword =
	 * request.getParameter("searchKeyword"); Connection conn = null;
	 * PreparedStatement stmt = null; ResultSet rs = null;
	 * 
	 * int searchcount = 0; try { conn = JDBCConnection.getConnection(); stmt=
	 * searchcount(conn, select, searchKeyword); rs = stmt.executeQuery();
	 * if(rs.next()){ searchcount = rs.getInt(1); } } catch (ClassNotFoundException
	 * e1) {
	 * 
	 * e1.printStackTrace(); } catch (SQLException e1) {
	 * 
	 * e1.printStackTrace(); }finally { JDBCConnection.close(rs, stmt, conn); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * try { conn = JDBCConnection.getConnection();
	 * 
	 * // 모듈화 한 검색기능 사용. stmt = search(conn, select, searchKeyword);
	 * 
	 * rs = stmt.executeQuery();
	 * 
	 * ArrayList<BoardVO> boardList = new ArrayList<BoardVO>();
	 * 
	 * while (rs.next()) { BoardVO vo = new BoardVO(); vo.setSeq(rs.getInt(1));
	 * vo.setTitle(rs.getString(2)); vo.setNickname(rs.getString(3));
	 * vo.setContent(rs.getString(4)); vo.setRegdate(rs.getDate(5));
	 * vo.setCnt(rs.getInt(6)); boardList.add(vo); }
	 * 
	 * request.setAttribute("boardList", boardList);
	 * 
	 * RequestDispatcher dispatcher =
	 * request.getRequestDispatcher("getBoardList.jsp"); dispatcher.forward(request,
	 * response);
	 * 
	 * } catch (ClassNotFoundException e) {
	 * 
	 * e.printStackTrace(); } catch (SQLException e) {
	 * 
	 * e.printStackTrace(); } finally { JDBCConnection.close(rs, stmt, conn); }
	 * 
	 * }
	 * 
	 * // 게시글 검색 모듈화. private PreparedStatement search(Connection conn, String
	 * select, String searchKeyword) throws SQLException { PreparedStatement stmt =
	 * null; String sql; //상수로 만들어서 사용. final String TITLE_SQL =
	 * "select * from (select rownum rn, SEQ, TITLE, NICKNAME, CONTENT, REGDATE, CNT from (select * from board where where title like '%' || ? || '%' order by SEQ desc)) where rn between ? and ?"
	 * ; final String CONTENT_SQL =
	 * "select * from (select rownum rn, SEQ, TITLE, NICKNAME, CONTENT, REGDATE, CNT from (select * from board where where contentlike '%' || ? || '%' order by SEQ desc)) where rn between ? and ?"
	 * ; final String NICKNAME_SQL =
	 * "select * from (select rownum rn, SEQ, TITLE, NICKNAME, CONTENT, REGDATE, CNT from (select * from board where where nicknamelike '%' || ? || '%' order by SEQ desc)) where rn between ? and ?"
	 * ; if (select.equals("title")) { stmt = conn.prepareStatement(TITLE_SQL);
	 * stmt.setString(1, searchKeyword); } else if (select.equals("content")) { stmt
	 * = conn.prepareStatement(CONTENT_SQL); stmt.setString(1, searchKeyword); }
	 * else if (select.equals("nickname")) { stmt =
	 * conn.prepareStatement(NICKNAME_SQL); stmt.setString(1, searchKeyword); }
	 * 
	 * return stmt; }
	 * 
	 * 
	 * private ArrayList<BoardVO> searchList(String select, String searchKeyword,int
	 * starRow, int endRow){ Connection conn = null; PreparedStatement stmt = null;
	 * ResultSet rs = null;
	 * 
	 * //상수로 만들어서 사용. String sql =
	 * "select * from (select rownum rn, SEQ, TITLE, NICKNAME, CONTENT, REGDATE, CNT from (select * from board where"
	 * +select+" like '%' ||"
	 * +searchKeyword+" || '%' order by seq desc)) where rn between ? and ?";
	 * 
	 * 
	 * 
	 * ArrayList<BoardVO> boardList = null;
	 * 
	 * try { conn = JDBCConnection.getConnection(); stmt =
	 * conn.prepareStatement(sql); stmt.setInt(1, starRow); stmt.setInt(2, endRow);
	 * rs = stmt.executeQuery();
	 * 
	 * boardList = new ArrayList<BoardVO>();
	 * 
	 * 
	 * while (rs.next()) { BoardVO board = new BoardVO();
	 * 
	 * board.setSeq(rs.getInt("seq")); board.setTitle(rs.getString("title"));
	 * board.setNickname(rs.getString("nickname"));
	 * board.setContent(rs.getString("content"));
	 * board.setRegdate(rs.getDate("regdate")); board.setCnt(rs.getInt("cnt"));
	 * 
	 * boardList.add(board); }
	 * 
	 * } catch (ClassNotFoundException e) {
	 * 
	 * e.printStackTrace(); } catch (SQLException e) {
	 * 
	 * e.printStackTrace(); }finally { JDBCConnection.close(rs, stmt, conn); }
	 * 
	 * return boardList;
	 * 
	 * }
	 * 
	 * 
	 * public int getScount(String select, String searchKeyword) { int scount=0;
	 * Connection conn = null; PreparedStatement stmt = null; ResultSet rs = null;
	 * String TITLE_SQL =
	 * "select count(*) from board where title like '%' || ? || '%' order by seq desc"
	 * ; String CONTENT_SQL =
	 * "select count(*) from board where content like '%' || ? || '%' order by seq desc"
	 * ; String NICKNAME_SQL =
	 * "select count(*) from board where nickname like '%' || ? || '%' order by seq desc"
	 * ;
	 * 
	 * try { conn = JDBCConnection.getConnection(); if (select.equals("title")) {
	 * stmt = conn.prepareStatement(TITLE_SQL); stmt.setString(1, searchKeyword); }
	 * else if (select.equals("content")) { stmt =
	 * conn.prepareStatement(CONTENT_SQL); stmt.setString(1, searchKeyword); } else
	 * if (select.equals("nickname")) { stmt = conn.prepareStatement(NICKNAME_SQL);
	 * stmt.setString(1, searchKeyword); } rs = stmt.executeQuery(); if(rs.next()) {
	 * scount = rs.getInt(1); }
	 * 
	 * } catch (ClassNotFoundException e) {
	 * 
	 * e.printStackTrace(); } catch (SQLException e) {
	 * 
	 * e.printStackTrace(); } return scount; }
	 */
	
	
	/*
	 * private PreparedStatement searchcount(Connection conn, String select, String
	 * searchKeyword) throws SQLException { int scount=0; PreparedStatement stmt =
	 * null; String sql; //상수로 만들어서 사용. final String TITLE_SQL =
	 * "select count(*) from board where title like '%' || ? || '%' order by seq desc"
	 * ; final String CONTENT_SQL =
	 * "select count(*) from board where content like '%' || ? || '%' order by seq desc"
	 * ; final String NICKNAME_SQL =
	 * "select count(*) from board where nickname like '%' || ? || '%' order by seq desc"
	 * ; if (select.equals("title")) { stmt = conn.prepareStatement(TITLE_SQL);
	 * stmt.setString(1, searchKeyword); } else if (select.equals("content")) { stmt
	 * = conn.prepareStatement(CONTENT_SQL); stmt.setString(1, searchKeyword); }
	 * else if (select.equals("nickname")) { stmt =
	 * conn.prepareStatement(NICKNAME_SQL); stmt.setString(1, searchKeyword); }
	 * 
	 * return scount; }
	 */


	
}
