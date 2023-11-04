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

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/calc3")
public class Calc3 extends HttpServlet {

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		빈문자열이 오는지도 체크해야함.
		ServletContext application = request.getServletContext();
		HttpSession session = request.getSession();
		
		Cookie[] cookies = request.getCookies();
		
		String value = request.getParameter("value");
		String operator = request.getParameter("operator");
		String dot = request.getParameter("dot");
			
		String exp = "";
		
		if(cookies != null) {
			for(Cookie c : cookies)
				if(c.getName().equals("exp")) {
					exp = c.getValue();
					break;
				}
		
		if(operator != null && operator.equals("=")) {
			ScriptEngine engine = new ScriptEngineManager().getEngineByName("graal.js");
					try {
						exp = String.valueOf(engine.eval(exp));
					} catch (ScriptException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		}else if(operator != null && operator.equals("C")) {
			exp = "";
		}else {
			exp += (value == null)? "" : value;
			exp += (operator == null)? "" : operator;
			exp += (dot == null)? "" : dot;
		}
		
		Cookie expCookie = new Cookie("exp", exp );
		if(operator != null && operator.equals("C")) {
			expCookie.setMaxAge(0);
		}
//		expCookie.setPath("/clac3"); 원래는이렇게 쓰는데 get, post를 각각 요청하려면 이방법으로는 url중복추가가 안됨.
		response.addCookie(expCookie);
		response.sendRedirect("calcpage");
		
		}
	}
}
