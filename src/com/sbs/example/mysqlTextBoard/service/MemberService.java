package com.sbs.example.mysqlTextBoard.service;

import com.sbs.example.mysqlTextBoard.dao.MemberDao;

public class MemberService {

	private MemberDao memberDao;
	
	public MemberService() {
		memberDao = new MemberDao();
	}

	public int join(String loginId, String loginPw, String name) {
		return memberDao.add(loginId, loginPw, name);
	}

}
