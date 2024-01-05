package com.myweb.user.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import com.myweb.util.JdbcUtil;

public class UserDAO {

	//싱글톤
	//1.
	private static UserDAO instance = new UserDAO();


	//2.생성자 만들기
	public UserDAO() {
		try {
			//1. 드라이버 호출
			Class.forName("oracle.jdbc.driver.OracleDriver");

		} catch (Exception e) {
		}
	}

	//3.getter메서드
	public static UserDAO getInstance() {
		return instance;
	}
	
	private String url = JdbcUtil.url;
	private String uid = JdbcUtil.uid;
	private String upw = JdbcUtil.upw;
	
	
	//insert 메서드 만들기
	//가입에 대한 프로세스 ->생각해보기
	//id값 중복 체크
	//id가 중복되지 않으면 insert ->
	public int idCheck(String id) {
		int result = 0;

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select * from users where id = ?";

		try {
			
			//Connection 객체 생성
			conn = DriverManager.getConnection(url, uid, upw);
			//pstmt 객체 생성
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			//sql 실행 -> select _. executeQuery // DML ->executeUpdate
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //true라는 것은 -> db안에 값이 있다
				result = 1; //중복 0
			} else { //false라는 것은 db안에 값이 없다
				result = 0; //중복 X
			} 	

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}



		return result;
	}

	public void insertUser(UserVO vo) {
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "insert into users (id, pw, name, email, address, gender) values(?, ?, ?, ?, ?, ?)";
				
		try {
			
			conn = DriverManager.getConnection(url, uid, upw);
		
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getId());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getName());
			pstmt.setString(4, vo.getEmail());
			pstmt.setString(5, vo.getAddress());
			pstmt.setString(6, vo.getGender());
			
			pstmt.executeUpdate();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally	{
			JdbcUtil.close(conn, pstmt, null);
		}
		
		
		
	}

	public UserVO login(String id, String pw) {
		
		UserVO vo = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "Select * from users where  id = ? and pw = ?";
		
		try {
			
			conn = DriverManager.getConnection(url, uid, upw);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) { //로그인 성공(UserVO에 필요한 값을 저장)
				
				vo = new UserVO();
				vo.setId(id);
				vo.setName(rs.getString("name"));
				
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
		
		
		
		return vo;
	}
	
	
	public UserVO getUserInfo(String id) {
	
		UserVO vo = null;
		
		
		String sql = "Select * from users where id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
				
		
		try {
			
			conn = DriverManager.getConnection(url, uid, upw);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			
			
			if(rs.next()) {
				 String name = rs.getString("name");
				 String email = rs.getString("email");
				 String address = rs.getString("address");
				 String gender = rs.getString("gender");
				
				 Timestamp regdate = rs.getTimestamp("regdate");
				 
				 vo = new UserVO(id, null, name, email, address, gender, regdate);
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, rs);
		}
				
		
		
		return vo;
	}
	
	
	
	public int update(UserVO vo) {
		int result = 0;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "update users set pw = ?, name = ?, email = ?, address = ?, gender = ? where id = ?";
		
		try {
			
			conn = DriverManager.getConnection(url, uid, upw);
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getPw());
			pstmt.setString(2, vo.getName());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getAddress());
			pstmt.setString(5, vo.getGender());
			pstmt.setString(6, vo.getId());
			
			result = pstmt.executeUpdate(); //0이면 실패, 1이면 성공
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcUtil.close(conn, pstmt, null);
		}
		
		
		
		
		
		
		return result;
	}
	
	
}
