package com.song.web.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.song.web.entity.Notice;
import com.song.web.entity.NoticeView;

public class NoticeService {
	
	public int removeNoticeAll(int[] ids){
		return 0;
	}
	public int pubNoticeAll(int[] ids){
		return 0;
	}
	public int insertNotice(Notice notice){
		int result = 0;
		
		String sql = "INSERT INTO NOTICE(TITLE, CONTENT, WRITER_ID, PUB, FILES) "
				+ "VALUES(?,?,?,?,?)";

		String url = "jdbc:oracle:thin:@localhost:1521/xe";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "SONG", "gjgkgl135");
			PreparedStatement st = con.prepareStatement(sql);

			st.setString(1, notice.getTitle());
			st.setString(2, notice.getContent());
			st.setString(3, notice.getWriterId());
			st.setBoolean(4, notice.getPub());
			st.setString(5, notice.getFiles());
			
			result = st.executeUpdate();
			
			st.close();
			con.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteNotice(int id){
		return 0;
	}
	public int updateNotice(Notice notice){
		return 0;
	}
	List<Notice> getNoticeNewestList(){
		return null;
	}
	
	
	public List<NoticeView> getNoticeList(){
		return getNoticeList("title", "", 1);
	}
	
	public List<NoticeView> getNoticeList(int page){
		return getNoticeList("title", "", page);
	}
	
	public List<NoticeView> getNoticeList(String field, String query, int page){
		
		List<NoticeView> list = new ArrayList<NoticeView>();
		String sql = "SELECT * FROM ("
				+ " SELECT ROWNUM NUM, N.* "
				+ " FROM (SELECT * FROM NOTICE_VIEW "
				+ "WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N"
				+ ") "
				+ "WHERE NUM BETWEEN ? AND ?";
		
		// 1, 11, 21, 31 -> 등차수열 -> an = 1+(page-1)*10
		// 10, 20, 30 page*10
		
		String url = "jdbc:oracle:thin:@localhost:1521/xe";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "SONG", "gjgkgl135");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, "%"+query+"%");
			st.setInt(2, 1+(page-1)*10);
			st.setInt(3, page*10);
			ResultSet rs = st.executeQuery();

			while(rs.next()){
				
				int id = rs.getInt("ID");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regDate = rs.getDate("REGDATE");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				int cmtCount = rs.getInt("CMT_COUNT");
				boolean pub = rs.getBoolean("PUB");
				
				NoticeView notice = new NoticeView(
					id,
					title,
					writerId,
					regDate,
					hit,
					files,
					//content,
					cmtCount,
					pub
				);
				list.add(notice);
			}

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public List<NoticeView> getNoticePubList(String field, String query, int page) {
		List<NoticeView> list = new ArrayList<NoticeView>();
		String sql = "SELECT * FROM ("
				+ " SELECT ROWNUM NUM, N.* "
				+ " FROM (SELECT * FROM NOTICE_VIEW "
				+ "WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N"
				+ ") "
				+ "WHERE PUB=1 AND NUM BETWEEN ? AND ?";
		
		// 1, 11, 21, 31 -> 등차수열 -> an = 1+(page-1)*10
		// 10, 20, 30 page*10
		
		String url = "jdbc:oracle:thin:@localhost:1521/xe";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "SONG", "gjgkgl135");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, "%"+query+"%");
			st.setInt(2, 1+(page-1)*10);
			st.setInt(3, page*10);
			ResultSet rs = st.executeQuery();

			while(rs.next()){
				
				int id = rs.getInt("ID");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regDate = rs.getDate("REGDATE");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				int cmtCount = rs.getInt("CMT_COUNT");
				boolean pub = rs.getBoolean("PUB");
				
				NoticeView notice = new NoticeView(
					id,
					title,
					writerId,
					regDate,
					hit,
					files,
					//content,
					cmtCount,
					pub
				);
				list.add(notice);
			}

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public int getNoticeCount() {
		return getNoticeCount("title", "");
	}
	
	public int getNoticeCount(String field, String query) {
		
		int count = 0;
		
		String sql = "SELECT COUNT(ID) COUNT FROM ("
				+ " SELECT ROWNUM NUM, N.* "
				+ " FROM (SELECT * FROM NOTICE "
				+ "WHERE "+field+" LIKE ? ORDER BY REGDATE DESC) N"
				+ ") ";
		
		List<Notice> list = new ArrayList<Notice>();

		
		String url = "jdbc:oracle:thin:@localhost:1521/xe";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "SONG", "gjgkgl135");
			PreparedStatement st = con.prepareStatement(sql);
			st.setString(1, "%"+query+"%");
			ResultSet rs = st.executeQuery();
			
			if(rs.next())
				count = rs.getInt("count");
			
			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return count;
	}
	
	public Notice getNotice(int id) {
		
		Notice notice = null;
		
		String sql = "SELECT * FROM  NOTICE WHERE ID=?";
		
		String url = "jdbc:oracle:thin:@localhost:1521/xe";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "SONG", "gjgkgl135");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if(rs.next()){
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regDate = rs.getDate("REGDATE");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("PUB");
				
				notice = new Notice(
					nid,
					title,
					writerId,
					regDate,
					hit,
					files,
					content,
					pub
				);
			}

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return notice;
	}
	
	public Notice getPrevNotice(int id) {
		
		Notice notice = null;
		String sql = "SELECT ID FROM (SELECT * FROM NOTICE ORDER BY REGDATE DESC) "
				+ "WHERE REGDATE < (SELECT REGDATE FROM NOTICE WHERE ID=?) "
				+ "AND ROWNUM = 1";
		String url = "jdbc:oracle:thin:@localhost:1521/xe";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "SONG", "gjgkgl135");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if(rs.next()){
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regDate = rs.getDate("REGDATE");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("PUB");
				
				notice = new Notice(
					nid,
					title,
					writerId,
					regDate,
					hit,
					files,
					content,
					pub
				);
			}

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return notice;
	}
	
	public Notice getNextNotice(int id) {
		
		Notice notice = null;
		String sql = "SELECT ID "
				+ "FROM ("
				+ "FROM NOTICE"
				+ "SELECT ID"
				+ "WHERE REGDATE > (SELECT REGDATE FROM NOTICE WHERE ID=?) "
				+ "ORDER BY REGDATE, ID "
				+ ") "
				+ "WHERE ROWNUM = 1;";

		String url = "jdbc:oracle:thin:@localhost:1521/xe";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "SONG", "gjgkgl135");
			PreparedStatement st = con.prepareStatement(sql);
			st.setInt(1, id);

			ResultSet rs = st.executeQuery();

			if(rs.next()){
				int nid = rs.getInt("ID");
				String title = rs.getString("TITLE");
				String writerId = rs.getString("WRITER_ID");
				Date regDate = rs.getDate("REGDATE");
				int hit = rs.getInt("HIT");
				String files = rs.getString("FILES");
				String content = rs.getString("CONTENT");
				boolean pub = rs.getBoolean("PUB");
				
				notice = new Notice(
					nid,
					title,
					writerId,
					regDate,
					hit,
					files,
					content,
					pub
				);
			}

			rs.close();
			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return notice;
	}

	public int deleteNoticeAll(int[] ids) {
		int result = 0;
		
		String params = "";
		
		for(int i=0; i<ids.length; i++) {
			params += ids[i];
			
			if(i < ids.length-1) 
				params += ",";
		}
				
		
		String sql = "DELETE NOTICE WHERE ID IN ("+params+")";

		String url = "jdbc:oracle:thin:@localhost:1521/xe";

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection(url, "SONG", "gjgkgl135");
			Statement st = con.createStatement();
			
			result = st.executeUpdate(sql);

			st.close();
			con.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
