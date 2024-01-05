package com.myweb.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myweb.user.model.UserVO;
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
			
			if (result == 1) { //아이디 중복
				request.setAttribute("msg", "아이디가 중복되었습니다.");
				request.getRequestDispatcher("user_join.jsp").forward(request, response);
			}else { //회원가입 성공
				response.sendRedirect("login.user"); //MVC2 방식에서 redirect는 다시 controller로 이동시켜야됨.
			}
			
			
		}else if(path.equals("/user/loginForm.user")) {
			
			UserVO vo = service.login(request, response);
			if(vo != null) { //로그인 성공
				
				//서블릿에서는 request.getSession 현제세션을 얻을 수 있습니다.
				HttpSession session = request.getSession();
				session.setAttribute("user_id", vo.getId());
				session.setAttribute("user_name", vo.getName());
				
				response.sendRedirect(request.getContextPath());
				
			} else { //로그인 실패
				request.setAttribute("msg", "아이디가 비밀번호를 확인하세요");
				request.getRequestDispatcher("user_login.jsp").forward(request, response);
			}
			
			
		}else if(path.equals("/user/logout.user")) {
			HttpSession session = request.getSession(); //세션 지우고
			session.invalidate();
			
			response.sendRedirect(request.getContextPath()); //홈화면 이동
		
		
		}else if(path.equals("/user/mypage.user")) { //마이페이지 화면 이동
			
			request.getRequestDispatcher("user_mypage.jsp").forward(request, response);
			
		}else if(path.equals("/user/update.user")) { //정보 수정 화면
			
			//여기에서 회원에 대한 데이터를 가지고 화면으로 나감.
			/*
			 * 1. DAO에서는 id기준으로 회원정보를 조회해서 UserVO저장
			 * 2. service영역에서는 리턴해서 컨트롤러까지 회원정보를 가지고 나옵니다.
			 * 3. 컨트롤러에서는 vo를 request에 저장합니다.
			 * 4. 화면에서 EL태그를 사용해서 value속성에 찍어주면 됩니다.
			 * 
			 * 
			 */
			
			UserVO vo = service.getUserInfo(request, response);
			
			request.setAttribute("vo", vo);
	
			
			request.getRequestDispatcher("user_update.jsp").forward(request, response);
			
		}else if(path.equals("/user/updateForm.user")) {  //회원정보수정
			
			//0이면 실패, 1이면 성공
			int result = service.update(request, response);
			
			if(result == 1 ){
				/
				
				//out객체 활용
				//고전적으로 사용되는 브라우저 화면에 직접 응답을 해주는 형태
				response.setContentType("text/html; charset=UTF-8");
				PrintWriter out = response.getWriter();
				out.println("<script>");
				out.println("alert('회원정보 업데이트 성공!!')");
				out.println("location.href='mypage.user';");
				out.println("</script>");
				
				
			}else {
				response.sendRedirect("mypage.user");
			}
			
			
		}
		
		
		
	}
}
