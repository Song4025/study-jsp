package com.song.web.entity;

import java.util.Date;

public class NoticeView extends Notice {

	private int cmtCount;
	
	public int getCmtCount() {
		return cmtCount;
	}

	public void setCmtCount(int cmtCount) {
		this.cmtCount = cmtCount;
	}

	public NoticeView() {
	}
	
	public NoticeView(int id, String title, String writerId, Date regDate, int hit, String files, int cmtCount, boolean pub) {
		super(id, title, writerId, regDate, hit, files, "", pub);
		this.cmtCount = cmtCount;
	}

}
