package com.song.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/calc2")
public class Calc2 extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		빈문자열이 오는지도 체크해야함.
		ServletContext application = request.getServletContext();
		HttpSession session = request.getSession();
		
		Cookie[] cookies = request.getCookies();
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String v_ = request.getParameter("v");
		String op = request.getParameter("operator");

		int v = 0;

		if (!v_.equals("")) v = Integer.parseInt(v_);
		
		// 계산
		if(op.equals("=")) {
			// application에 들어가있는 값을 꺼내옴
//			int x = (Integer)application.getAttribute("value");
//			int x = (Integer)session.getAttribute("value");
			
			int x = 0;
			for(Cookie c : cookies)
				if(c.getName().equals("value")) {
						x = Integer.parseInt(c.getValue());
					break;
				}
			
			
			// 사용자가 입력한 값을 가져옴
			int y = v;
				
//			String operator = application.getAttribute("operator") == null ?
//					"" : application.getAttribute("operator").toString();
			
			String operator = "";
			
			for(Cookie c : cookies)
				if(c.getName().equals("operator")) {
						operator = c.getValue();
					break;
				}

			
//			String operator = session.getAttribute("operator") == null ?
//					"" : session.getAttribute("operator").toString();
//			
			int result = 0;
			
			if(operator.equals("+"))
				result = x + y;
			else
				result = x - y;
			response.getWriter().printf("result is %d\n", result);
		} else { // 값을 저장
//			application.setAttribute("value", v);
//			application.setAttribute("operator", op);
			
//			session.setAttribute("value", v);
//			session.setAttribute("operator", op);
			
			Cookie valueCookie = new Cookie("value", String.valueOf(v));
			Cookie OpCookie = new Cookie("operator", op);
			valueCookie.setPath("/calc2"); // 모든페이지에 valueCookie를 가져옴
			valueCookie.setMaxAge(24*60*60);
			OpCookie.setPath("/calc2"); // 모든페이지에 valueCookie를 가져옴
			response.addCookie(valueCookie);
			response.addCookie(OpCookie);
			
			response.sendRedirect("calc2.html");
			
		}
		
	}
}
