package com.myweb.user.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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




}
