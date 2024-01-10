package com.myweb.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.myweb.util.JdbcUtil;

public class BoardDAO {

	//싱글톤
	//1.
	private static BoardDAO instance = new BoardDAO();


	//2.생성자 만들기
	public BoardDAO() {
		try {
			//1. 드라이버 호출
			
			//드라이버 호출문장 필요 없어짐
			//Class.forName("oracle.jdbc.driver.OracleDriver");
			
			//초기설정 값을 얻을 수 있는 객체
			InitialContext init = new InitialContext();
			dataSource = (DataSource) init.lookup("java:comp/env/jdbc/oracle");
			
			
		} catch (Exception e) {
		}
	}

	//3.getter메서드
	public static BoardDAO getInstance() {
		return instance;
	}

	//private String url = JdbcUtil.url;
	//private String uid = JdbcUtil.uid;
	//private String upw = JdbcUtil.upw;
	
	//커넥션풀에서 얻어온 디비객체 정보
	private DataSource dataSource;
	
	
	//
	public void insert(String writer, String title, String content) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "insert into board(bno, writer, title, content) "
				+ "values(board_seq.nextval, ?, ?, ?)";
		
		try {
			
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, writer);
			pstmt.setString(2, title);
			pstmt.setString(3, content);
			
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		
		
	}
	
	public ArrayList<BoardVO> getList(){
		
		ArrayList<BoardVO> list = new ArrayList <> ();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM BOARD ORDER BY BNO DESC";
		
		try {
			
			conn = dataSource.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				int bno = rs.getInt("bno");
				String title = rs.getString("title");
				String content = rs.getString("content");
				String writer = rs.getString("writer");
				int hit = rs.getInt("hit");
				Timestamp regdate = rs.getTimestamp("regdate");
				
				BoardVO vo = new BoardVO (bno, writer, title, content, hit, regdate);
				list.add(vo);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		
		return list;
	}

	//내용조회
	public BoardVO getContent(String bno) {
		
		BoardVO vo = new BoardVO();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM BOARD WHERE BNO = ?";
		
		try {
			
			conn = dataSource.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bno);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				int bno2 = rs.getInt("bno");
				String writer = rs.getString("writer");
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				Timestamp regdate = rs.getTimestamp("regdate");
				
				vo.setBno(bno2);
				vo.setWriter(writer);
				vo.setTitle(title);
				vo.setContent(content);
				vo.setHit(hit);
				vo.setRegdate(regdate);
				
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
				
		
		
		
		return vo;
	}
	
	
	
	public int update (String bno, String title, String content) {
		
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		
		String sql = "UPDATE BOARD SET TITLE = ?, CONTENT = ? WHERE BNO = ?";
		
		try {
			
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, title);
			pstmt.setString(2, content);
			pstmt.setString(3, bno);
			
			result = pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		
		
		return result;
	}
	
	public void delete(String bno)  {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "DELETE FROM BOARD WHERE BNO = ?";
		
		try {
			
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bno);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		
		
		
	}
	
	//조회수 작업
	public void hitUpdate (String bno) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE BOARD SET HIT = HIT + 1 WHERE BNO = ?";
		
		try {
			
			conn = dataSource.getConnection();
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, bno);
			
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
				
		
		
		
	}
	
	
	
}
