package com.song.web.controller.admin.notice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.song.web.entity.Notice;
import com.song.web.service.NoticeService;

@MultipartConfig(
		/* location="/tmp", */
	fileSizeThreshold=1024*1024, //1mb가 넘어갈경우에 디스크를 쓰자
	maxFileSize=1024*1024*5, // 하나의 사이즈가 맥시멈 얼마나될것인가. 최대크기 5mb 
	maxRequestSize=1024*1024*5*5 // 전체 합친 사이즈가 25mb를 초과할수없다.
)
@WebServlet("/admin/board/notice/reg")
public class RegController extends HttpServlet {
	
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/view/admin/board/notice/reg.jsp").forward(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String title = request.getParameter("title");
		
		System.out.print("title: ");
		System.out.println(title);
		
		String content = request.getParameter("content");
		String isOpen = request.getParameter("open");
		
		Collection<Part> parts = request.getParts();
		StringBuilder builder = new StringBuilder();
		for(Part p : parts) {
			if(!p.getName().equals("file")) continue;
			if(p.getSize() == 0) continue; // 다중파일중 하나만 선택되었을 때도
			
			Part filePart = p;
			String fileName = filePart.getSubmittedFileName();
			builder.append(fileName);
			builder.append(",");
			InputStream fis = filePart.getInputStream();
			
			
			// 현재 웹 루트를 통한 상대경로를 전달하면 물리경로를 얻어줌
			String realPath = request.getServletContext().getRealPath("/member/upload");
			System.out.println(realPath);
			
			File path = new File(realPath);
			if(!path.exists())
				path.mkdirs();
			
			String filePath = realPath + File.separator + fileName;
			FileOutputStream fos = new FileOutputStream(filePath);

			byte[] buf = new byte[1024];
			int size = 0;
			while((size=fis.read(buf)) != -1) //반환값은 크기
				fos.write(buf,0,size);
			fos.close();
			fis.close();
		}
//		파일명 나열 후 마지막 ,는 빼주기
		builder.delete(builder.length()-1, builder.length());
		
		boolean pub = false;
		if(isOpen != null) 
			pub = true;
		
		
		Notice notice = new Notice();
		notice.setTitle(title);
		notice.setContent(content);
		notice.setPub(pub);
		notice.setWriterId("aaa");
		notice.setFiles(builder.toString());
		
		NoticeService service = new NoticeService();
		int result = service.insertNotice(notice);
		
		response.sendRedirect("list");
	}
}
