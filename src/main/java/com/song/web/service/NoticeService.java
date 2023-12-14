package com.song.web.service;

import java.util.List;

import com.song.web.entity.Notice;

public class NoticeService {
	public List<Notice> getNoticeList(){
		return getNoticeList("title", "", 1);
	}
	
	public List<Notice> getNoticeList(int page){
		return getNoticeList("title", "", page);
	}
	
	public List<Notice> getNoticeList(String field, String query, int page){
		
		String sql = "SELECT * FROM ("
				+ "	SELECT ROW_NUMBER() OVER(ORDER BY REGDATE DESC) NUM,"
				+ "	NOTICE.* FROM NOTICE"
				+ ")"
				+ "WHERE NUM BETWEEN 1 AND 5";
				
		
		return null;
	}
	
	public int getNoticeCount() {
		return getNoticeCount("title", "");
	}
	
	public int getNoticeCount(String field, String query) {
		
		String sql = "SELECT * FROM ("
				+ "	SELECT ROW_NUMBER() OVER(ORDER BY REGDATE DESC) NUM,"
				+ "	NOTICE.* FROM NOTICE"
				+ ")"
				+ "WHERE NUM BETWEEN 1 AND 5";
		
		return 0;
	}
	
	public Notice getNotice(int id) {
		String sql = "SELECT * FROM  NOTICE WHERE ID = ?";
		
		return null;
	}
	
	public Notice getPrevNotice(int id) {
		
		String sql = "SELECT ID FROM (SELECT * FROM NOTICE ORDER BY REGDATE DESC) "
				+ "WHERE REGDATE < (SELECT REGDATE FROM NOTICE WHERE ID=3) "
				+ "AND ROWNUM = 1";
		
		return null;
	}
	
	public Notice getNextNotice(int id) {
		String sql = "SELECT ID "
				+ "FROM ("
				+ "FROM NOTICE"
				+ "SELECT ID"
				+ "WHERE REGDATE > (SELECT REGDATE FROM NOTICE WHERE ID=3) "
				+ "ORDER BY REGDATE, ID "
				+ ") "
				+ "WHERE ROWNUM = 1;";
		
		return null;
	}
}
