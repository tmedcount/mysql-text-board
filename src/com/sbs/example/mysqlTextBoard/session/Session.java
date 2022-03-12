package com.sbs.example.mysqlTextBoard.session;

public class Session {

	private int loginedMemberId;
	private String currentBoardCode;
	
	public Session() {
		// 공지사항을 기본적으로 선택된 게시판으로 지정.
		currentBoardCode = "notice";
	}
	
	public int getLoginedMemberId() {
		return loginedMemberId;
	}

	public void login(int id) {
		loginedMemberId = id;
	}
	
	public void logout() {
		loginedMemberId = 0;
	}

	public boolean isLogined() {
		return loginedMemberId > 0;
	}

	public String getCurrentBoardCode() {
		return currentBoardCode;
	}
}
