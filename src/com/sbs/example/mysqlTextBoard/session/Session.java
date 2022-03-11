package com.sbs.example.mysqlTextBoard.session;

public class Session {

	private int loginedMemberId;
	
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
}
