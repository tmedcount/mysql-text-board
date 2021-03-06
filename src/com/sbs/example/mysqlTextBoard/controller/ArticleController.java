package com.sbs.example.mysqlTextBoard.controller;

import java.util.List;
import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.dto.Member;
import com.sbs.example.mysqlTextBoard.service.ArticleService;
import com.sbs.example.mysqlTextBoard.service.MemberService;

public class ArticleController extends Controller {
	
	private ArticleService articleService;
	private MemberService memberService;
	
	public ArticleController() {
		articleService = Container.articleService;
		memberService = Container.memberService;
	}
	
	public void doCommand(String cmd) {
		if(cmd.startsWith("article selectBoard")) {
			selectBoard(cmd);
		} else if(cmd.startsWith("article makeBoard")) {
			doMakeBoard(cmd);
		} else if(cmd.startsWith("article modify ")) {
			doModify(cmd);
		} else if(cmd.startsWith("article write")) {
			doWrite(cmd);
		} else if(cmd.startsWith("article delete ")) {
			doDelete(cmd);
		} else if(cmd.startsWith("article detail ")) {
			showDetail(cmd);
		} else if(cmd.startsWith("article list")) {
			showList(cmd);
		}
		
	}

	private void selectBoard(String cmd) {
		System.out.println("== 게시판 선택 ==");
		System.out.println("= 게시판 목록 =");
		System.out.println("번호 / 생성날짜 / 코드 / 이름 / 게시물 수");
		
		List<Board> boards = articleService.getForPrintBoards();
		
		for(Board board : boards) {
			int articlesCount = articleService.getArticlesCount(board.id);
			System.out.printf("%d / %s / %s / %s / %d\n", board.id, board.regDate, board.code, board.name, articlesCount);
		}
		
		System.out.print("게시판 코드 : ");
		String inputedBoardCode = Container.scanner.nextLine().trim();
		
		Board board= articleService.getBoardByCode(inputedBoardCode);
		
		if(board == null) {
			System.out.println("코드를 잘 못 입력했습니다.");
			return;
		}
		
		Container.session.setCurrentBoardCode(board.code);
		
		System.out.printf("%s 게시판으로 변경합니다.\n", board.name);
	}

	private void doMakeBoard(String cmd) {
		System.out.println("== 게시판 생성 ==");
		
		if(!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}
		
		Member member = memberService.getMemberById(Container.session.getLoginedMemberId());
		
		if(!member.isAdmin()) {
			System.out.println("관리자만 게시판을 생성할 수 있습니다.");
			return;
		}
		
		Scanner sc = Container.scanner;
		
		System.out.print("코드 : ");
		String code = sc.nextLine();
		
		if(!articleService.isMakeBoardAvailableCode(code)) {
			System.out.println("해당 코드는 사용 중입니다.");
			return;
		}
		
		System.out.print("이름 : ");
		String name = sc.nextLine();
		
		if(!articleService.isMakeBoardAvailableName(name)) {
			System.out.println("해당 이름은 사용 중입니다.");
			return;
		}
		

		int id = articleService.makeBoard(code, name);

		System.out.printf("%d번 게시판을 생성하였습니다.\n", id);
		
	}

	private void doModify(String cmd) {
		System.out.println("== 게시물 수정 ==");
		
		if(!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}
		
		int inputedId = Integer.parseInt(cmd.split(" ")[2]);
		
		Article article = articleService.getArticle(inputedId);
		
		if(article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}
		
		if(article.memberId != Container.session.getLoginedMemberId()) {
			System.out.println("권한이 없는 아이디입니다.");
			return;
		}
		
		Member member = memberService.getMemberById(article.memberId);
		
		String writer = member.name;
			
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성날짜 : %s\n", article.regDate);
		System.out.printf("작성자 : %s\n", writer);
		
		Scanner sc = Container.scanner;
		
		System.out.print("제목 : ");
		String title = sc.nextLine();
		
		System.out.print("내용 : ");
		String body = sc.nextLine();
		
		articleService.modify(inputedId, title, body);
		
		System.out.printf("%d번 게시물을 수정하였습니다.\n", inputedId);
	}

	private void doWrite(String cmd) {
		System.out.println("== 게시물 작성 ==");
		
		if(!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}
		
		Scanner sc = Container.scanner;
		
		System.out.print("제목 : ");
		String title = sc.nextLine();
		System.out.print("내용 : ");
		String body = sc.nextLine();
		
		// 임시
		int boardId = 1;
		int memberId = Container.session.getLoginedMemberId();

		int id = articleService.write(boardId, memberId, title, body);

		System.out.printf("%d번 게시물을 생성하였습니다.\n", id);
		
	}

	private void doDelete(String cmd) {
		System.out.println("== 게시물 삭제 ==");
		
		if(!Container.session.isLogined()) {
			System.out.println("로그인 후 이용해 주세요.");
			return;
		}
		
		int inputedId = Integer.parseInt(cmd.split(" ")[2]);
		
		Article article = articleService.getArticle(inputedId);
		
		if(article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}
		
		if(article.memberId != Container.session.getLoginedMemberId()) {
			System.out.println("권한이 없는 아이디입니다.");
			return;
		}
		
		articleService.delete(inputedId);

		System.out.printf("%d번 게시물을 삭제되었습니다.\n", inputedId);
	}

	private void showDetail(String cmd) {
		System.out.println("== 게시물 상세 페이지 ==");
		
		int inputedId = Integer.parseInt(cmd.split(" ")[2]);
		
		Article article = articleService.getArticle(inputedId);
		
		if(article == null) {
			System.out.println("존재하지 않는 게시물입니다.");
			return;
		}
		
		Member member = memberService.getMemberById(article.memberId);
		
		String writer = member.name;
		
		System.out.printf("번호 : %d\n", article.id);
		System.out.printf("작성날짜 : %s\n", article.regDate);
		System.out.printf("수정날짜 : %s\n", article.updateDate);
		System.out.printf("작성자 : %s\n", writer);
		System.out.printf("제목 : %s\n", article.title);
		System.out.printf("내용 : %s\n", article.body);
	}

	public void showList(String cmd) {
		String boardCode = Container.session.getCurrentBoardCode();
		
		Board board = articleService.getBoardByCode(boardCode);
		
		System.out.printf("== %s 게시판 리스트 ==\n", board.name);
		
		List<Article> articles = articleService.getForPrintArticles(board.id);
		
		System.out.println("번호/ 작성 / 수정 / 작성자 / 제목");
		
		for(Article article : articles) {			
			String writer = article.extra__writer;
			
			System.out.printf("%d / %s / %s / %s / %s\n", article.id, article.regDate, article.updateDate, writer, article.title);
		}
	}
}
