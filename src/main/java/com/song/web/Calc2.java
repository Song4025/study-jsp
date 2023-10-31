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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/calc2")
public class Calc2 extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		빈문자열이 오는지도 체크해야함.
		ServletContext application = request.getServletContext();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		String v_ = request.getParameter("v");
		String op = request.getParameter("operator");

		int v = 0;

		if (!v_.equals("")) v = Integer.parseInt(v_);
		
		// 계산
		if(op.equals("=")) {
			// application에 들어가있는 값을 꺼내옴
			int x = (Integer)application.getAttribute("value");
			// 사용자가 입력한 값을 가져옴
			int y = v;
				
			String operator = application.getAttribute("operator") == null ?
					"" : application.getAttribute("operator").toString();
			
			int result = 0;
			
			if(operator.equals("+"))
				result = x + y;
			else
				result = x - y;
			response.getWriter().printf("result is %d\n", result);
		} else { // 값을 저장
			application.setAttribute("value", v);
			application.setAttribute("operator", op);			
		}

		
	}
}
