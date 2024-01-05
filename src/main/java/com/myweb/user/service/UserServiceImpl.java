package com.myweb.user.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.user.model.UserDAO;
import com.myweb.user.model.UserVO;

public class UserServiceImpl implements UserService {

	
	private UserDAO dao = UserDAO.getInstance();
	
	
	@Override
	public int join(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String gender = request.getParameter("gender");
		
		System.out.println("넘어온 값: " + id);
		
		//가입에 대한 프로세스 ->생각해보기
		//id값 중복 체크
		//id가 중복되지 않으면 insert ->
		
		int result = dao.idCheck(id);
		
		if(result == 1) { //아이디 중복
			return result;
		}else { //회원가입 진행
			UserVO vo = new UserVO(id, pw, name, email, address, gender, null);
			dao.insertUser(vo);
			
			
			return 0;			
		}
		
	}

	
	
	
	@Override
	public UserVO login(HttpServletRequest request, HttpServletResponse response) {
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		UserVO vo = dao.login(id, pw);
		
		
		
		return vo;
	}




	@Override
	public UserVO getUserInfo(HttpServletRequest request, HttpServletResponse response) {
		
		HttpSession session = request.getSession();
		String id = (String) session.getAttribute("user_id");
		
		UserVO vo = dao.getUserInfo(id);
		
		
		return vo;
	}




	
	
	public int update(HttpServletRequest request, HttpServletResponse response) {

		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String gender = request.getParameter("gender");
		
		UserVO vo = new UserVO(id, pw, name, email, address, gender, null);
		int result = dao.update(vo);
		
		if(result == 1) { //회원정보 수정이 성공이 되면 세션의 값도 변경 시켜준다.
			HttpSession session = request.getSession();
			session.setAttribute("user_name", name);
			
		}
		
		
		return result;
	}

	

	
	
	
}
