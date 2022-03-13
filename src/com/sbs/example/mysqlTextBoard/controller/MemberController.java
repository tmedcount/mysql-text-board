package com.sbs.example.mysqlTextBoard.controller;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.service.MemberService;
import com.sbs.example.mysqlTextBoard.session.Session;

public class MemberController extends Controller {

	private MemberService memberService;
	
	public MemberController() {
		memberService = Container.memberService;
	}

	public void doCommand(String cmd) {
		if(cmd.equals("member join")) {
			doJoin(cmd);
		} else if(cmd.equals("member login")) {
			doLogin(cmd);
		} else if(cmd.equals("member logout")) {
			dologout(cmd);
		} else if(cmd.equals("member whoami")) {
			whoami(cmd);
		} 
	}

	private void whoami(String cmd) {
		System.out.println("== 로그인 상세 정보 ==");
		
		if(!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}
		
		int memberId = Container.session.getLoginedMemberId();
		
		Member member = memberService.getMemberById(memberId);
		
		System.out.printf("번호 : %d\n", member.id);
		System.out.printf("가입날짜 : %s\n", member.regDate);
		System.out.printf("아이디 : %s\n", member.loginId);
		System.out.printf("이름 : %s\n", member.name);		
		System.out.printf("회원등급 : %s\n", member.getType());		
	}

	private void dologout(String cmd) {
		System.out.println("== 로그아웃 ==");
		
		if(!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}
		
		System.out.println("로그아웃 되었습니다.");
		
		Container.session.logout();
	}

	private void doLogin(String cmd) {
		System.out.println("== 로그인 ==");
		
		if(Container.session.isLogined()) {
			System.out.println("로그아웃 후 이용해 주세요.");
			return;
		}
		
		Scanner sc = Container.scanner;
				
		System.out.print("아이디 : " );
		String loginId = sc.nextLine().trim();
		
		if(loginId.length() == 0) {
			System.out.println("아이디를 입력해 주세요.");
			return;
		}
		
		Member member = memberService.getMemberByLoginId(loginId);
		
		if(member == null) {
			System.out.println("존재하지 않는 아이디입니다.");
			return;
		}
				
		System.out.print("비밀번호 : " );
		String loginPW = sc.nextLine().trim();
		
		if(loginId.length() == 0) {
			System.out.println("비밀번호를 입력해 주세요.");
			return;
		}
		
		if(!member.loginPw.equals(loginPW)) {
			System.out.println("비밀번호가 맞지 않습니다.");
			return;
		}
		
		Container.session.login(member.id);
		
		System.out.printf("%s님 환영합니다!\n", member.name);
	}

	private void doJoin(String cmd) {
		System.out.println("== 회원가입 ==");
		
		if(Container.session.isLogined()) {
			System.out.println("로그아웃 후 이용해 주세요.");
			return;
		}
		
		Scanner sc = Container.scanner;
		
		String loginId;
		String loginPw;
		String loginPwConfirm;
		String name;
		
		System.out.printf("아이디 : ");
		loginId = sc.nextLine().trim();
		
		if(loginId.length() == 0) {
			System.out.println("아이디를 입력해 주세요.");
			return;
		}
		
		System.out.printf("비밀번호 : ");
		loginPw = sc.nextLine().trim();
		
		if(loginPw.length() == 0) {
			System.out.println("비밀번호를 입력해 주세요.");
			return;
		}
		
		System.out.printf("비밀번호 확인 : ");
		loginPwConfirm = sc.nextLine().trim();
		
		if(loginPwConfirm.length() == 0) {
			System.out.println("비밀번호 확인을 입력해 주세요.");
			return;
		}
		
		if(!loginPw.equals(loginPwConfirm)) {
			System.out.println("비밀번호가 일치하지 않습니다.");
			return;
		}
		
		System.out.printf("이름 : ");
		name = sc.nextLine().trim();
		
		if(name.length() == 0) {
			System.out.println("이름을 입력해 주세요.");
			return;
		}
		
		int id = memberService.join(loginId, loginPw, name);
		
		System.out.printf("%d번 회원이 생성되었습니다.\n", id);
	}

}
