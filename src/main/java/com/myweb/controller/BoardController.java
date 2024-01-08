package com.myweb.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
			
			request.getRequestDispatcher("board_list.jsp").forward(request, response);
			
		} else if(path.equals("/board/write.board")) {
			
			request.getRequestDispatcher("board_write.jsp").forward(request, response);
		
		} else if(path.equals("/board/registForm.board")) { //글 등록
			
			service.regist(request, response);
			response.sendRedirect("board_list.jsp");
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
}
