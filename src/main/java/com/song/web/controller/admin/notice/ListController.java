package com.song.web.controller.admin.notice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.song.web.entity.NoticeView;
import com.song.web.service.NoticeService;

@WebServlet("/admin/board/notice/list")
public class ListController extends HttpServlet{
	// 404 url이 없는경우.
	// 405 url은 있으나 받을수있는 메서드가 없는경우.
	// 둘다 있지만 권한이없는경우 403
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//문자열로 오는데 키가다 같은데 값이 다 다르게 올때? 배열로받아!!
		String[] openIds = request.getParameterValues("open-id");
		String[] delIds = request.getParameterValues("del-id");
		String cmd = request.getParameter("cmd");
		String ids_ = request.getParameter("ids");
		// ids를 받아서 ids_에 넣어서 String[]배열로 변경시키겠다. split해서 구분자는 공백으로
		String[] ids = ids_.trim().split(" ");
		
		NoticeService service = new NoticeService();
		
		switch(cmd) {
			case "일괄공개" :
				for(String openId : openIds)
					System.out.printf("openId: %s\n", openId);
				
				List<String> oids = Arrays.asList(openIds);
				List<String> cids = new ArrayList(Arrays.asList(ids));
				cids.removeAll(oids);
				System.out.println(Arrays.asList(ids));
				System.out.println(oids);
				System.out.println(cids);
				/*for(int i=0; i<ids.length; i++) {
					//현재 id가 오픈된 상태인지?
					if(oids.contains(ids[i]))
						
				}*/
				
				// 아래 두개의 메서드는 transaction 처리되어야함 
					
				service.pubNoticeAll(opnIds, clsIds);
				// service.closeNoticeList(clsIds);
				
				
				break;
			case "일괄삭제" :
				int[] ids1 = new int[delIds.length];
				for(int i=0; i<delIds.length; i++)
					ids1[i] = Integer.parseInt(delIds[i]);
				
				int result = service.deleteNoticeAll(ids1);
				break;
		}
		
		response.sendRedirect("list");
		
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String field_ = request.getParameter("f");
		String query_ = request.getParameter("q");
		String page_ = request.getParameter("p");
		
		int page = 1;
		if(page_ != null && !page_.equals(""))
			page = Integer.parseInt(page_);
		
		String field = "title";
		if(field_ != null && !field_.equals(""))
			field = field_;
		
		String query = "";
		if(query_ != null && !query_.equals(""))
			query = query_;
		
		NoticeService service = new NoticeService();
		List<NoticeView> list = service.getNoticeList(field, query, page);
		int count = service.getNoticeCount(field, query);	
		
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		
		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/list.jsp").forward(request, response);
	}
	
}
