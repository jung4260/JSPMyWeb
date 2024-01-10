package com.myweb.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myweb.board.model.BoardVO;
import com.myweb.board.service.BoardService;
import com.myweb.board.service.BoardServiceImpl;

@WebServlet("*.board") ///
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public BoardController() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response); ///
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response); ///
	}
	
	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		
		
		request.setCharacterEncoding("utf-8");

		String uri = request.getRequestURI();
		String path = uri.substring( request.getContextPath().length() );

		System.out.println(path);
		
		///////////////////////////
		BoardService service = new BoardServiceImpl();
		
		
		if(path.equals("/board/list.board")) { //목록 컨트롤러
			
			//목록데이터를 가지고 화면으로 이동
			
			ArrayList<BoardVO> list = service.getList(request, response);
			request.setAttribute("list", list);
			
			request.getRequestDispatcher("board_list.jsp").forward(request, response);
			
		} else if(path.equals("/board/write.board")) {
			
			request.getRequestDispatcher("board_write.jsp").forward(request, response);
		
		} else if(path.equals("/board/registForm.board")) { //글 등록
			
			service.regist(request, response);
			
			//MVC2에서는 redirect를 다시 컨트롤러 연결하는 형태로 사용
			response.sendRedirect("list.board");
			
		} else if (path.equals("/board/content.board")) {
			
			service.hitUpdate(request, response); //조회수
			
			BoardVO vo = service.getContent(request, response); //내용조회
			request.setAttribute("vo", vo);
			
			request.getRequestDispatcher("board_content.jsp").forward(request, response);
			
		} else if (path.equals("/board/modify.board")) {
			
			/*
			 * 1.화면에서는 필요한 값을 넘겨주세요.
			 * 2.getContent 매서드 재활용
			 * 3.포워드 방식으로 board_modify.jsp로 이동합니다.
			 * 4.화면에 수정할 데이터를 미리보여주면 됩니다.
			 * 
			 */
			
			BoardVO vo = service.getContent(request, response);
			request.setAttribute("vo", vo);
			
			request.getRequestDispatcher("board_modify.jsp").forward(request, response);
			
		
		}else if (path.equals("/board/update.board")) {
			
			/*
			 * 1.service, dao에 int update(매개값)메소드 생성하세요.
			 * 2.update() 메서드에서는 sql문으로 수정을 진행합니다.
			 * 3.성공실패 여부는 정수형으로 반환을 받으세요.
			 * 4.컨트롤러에서는 
			 * 		성공시에 상세화면 redirect
			 * 		실패시에 수정화면으로 redirect 
			 */
			
			
			int result = service.update(request, response);
			
			if(result == 1) {
				response.sendRedirect("content.board?bno=" + request.getParameter("bno"));
			}else {
				response.sendRedirect("modify.board?bno=" + request.getParameter("bno"));
			}
			
			
		}else if (path.equals("/board/delete.board")) {
			
			service.delete(request, response);
			response.sendRedirect("list.board");
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
}
