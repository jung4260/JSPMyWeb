package com.myweb.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myweb.user.service.UserService;
import com.myweb.user.service.UserServiceImpl;


@WebServlet("*.user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public UserController() {
        super();
    }

   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	
	protected void doAction (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//3. url주소를 분기(각 요청별로 코드를 만듬)
		request.setCharacterEncoding("utf-8");
		
		String uri = request.getRequestURI();
		String path = uri.substring( request.getContextPath().length() );
		
		System.out.println(path);
		
		//서비스 영역을 전 조건문에 선언 
		UserService service = new UserServiceImpl();
		
		if(path.equals("/user/join.user")) {
			
			//화면이동의 기본값은 forward형이 되어야됨
			request.getRequestDispatcher("user_join.jsp").forward(request, response);
		
		}else if(path.equals("/user/login.user")) {
			request.getRequestDispatcher("user_login.jsp").forward(request, response);
		
		}else if(path.equals("/user/joinForm.user") ) { //회원가입
			//String id = request.getParameter("id");
			int result = service.join(request, response);
			System.out.println("실행결과: " + result);
			
		}
		
		
		
	}
}
