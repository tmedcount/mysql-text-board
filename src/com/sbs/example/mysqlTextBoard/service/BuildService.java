package com.sbs.example.mysqlTextBoard.service;

import java.util.List;

import com.sbs.example.mysqlTextBoard.Container;
import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlTextBoard.util.Util;

public class BuildService {

	private ArticleService articleService;

	public BuildService() {
		articleService = Container.articleService;
	}

	public void buildSite() {
		System.out.println("site 폴더 생성");

		Util.rmdir("site");
		Util.mkdirs("site");

		Util.copy("site_template/app.css", "site/app.css");

		buildIndexPage();
		buildArticleListPages();
		buildArticleDetailPages();
	}
	
	private void buildArticleListPage(Board board, int itemsInAPage, int pageBoxSize, List<Article> articles, int page) {
		StringBuilder sb = new StringBuilder();
		
		// 헤더 시작
		sb.append(getHeadHtml("article_list_" + board.code));
		
		// 바디 시작
		String bodyTemplate = Util.getFileContents("site_template/article_list.html");
				
		StringBuilder mainCotent = new StringBuilder();
		
		int articlesCount = articles.size();
		int start = (page - 1) * itemsInAPage;
		int end = start + itemsInAPage - 1;

		if (end >= articlesCount) {
			end = articlesCount - 1;
		}
		
		for (int i = start; i <= end; i++) {
			Article article = articles.get(i);
			
			String link = "article_detail_" + article.id + ".html";
			
			mainCotent.append("<div>");
			mainCotent.append("<div class=\"article-list__cell-id\">" + article.id + "</div>");
			mainCotent.append("<div class=\"article-list__cell-reg-date\">" + article.regDate + "</div>");
			mainCotent.append("<div class=\"article-list__cell-writer\">" + article.extra__writer + "</div>");
			mainCotent.append("<div class=\"article-list__cell-title\">");
			mainCotent.append("<a href=\"" + link + "\" class=\"hover-underline\">" + article.title + "</a>");
			mainCotent.append("</div>");
			mainCotent.append("</div>");
		}
		
		StringBuilder pageMenuContent = new StringBuilder();
		
		// 토탈 페이지 계산
		int totalPage = (int)Math.ceil((double)articlesCount / itemsInAPage);
		
		// 현재 페이지 계산
		if ( page < 1 ) {
			page = 1;
		}
		
		if ( page > totalPage ) {
			page = totalPage;
		}
		
		// 현재 페이지 박스 시작, 끝 계산
		int previousPageBoxesCount = (page - 1) / pageBoxSize;
		int pageBoxStartPage = pageBoxSize * previousPageBoxesCount + 1;
		int pageBoxEndPage = pageBoxStartPage + pageBoxSize - 1;
		
		if ( pageBoxEndPage > totalPage ) {
			pageBoxEndPage = totalPage;
		}
		
		// 이전버튼 페이지 계산
		int pageBoxStartBeforePage = pageBoxStartPage - 1;
		if ( pageBoxStartBeforePage < 1 ) {
			pageBoxStartBeforePage = 1;
		}
		
		// 다음버튼 페이지 계산
		int pageBoxEndAfterPage = pageBoxEndPage + 1;
		
		if ( pageBoxEndAfterPage > totalPage ) {
			pageBoxEndAfterPage = totalPage;
		}
		
		// 이전버튼 노출여부 계산
		boolean pageBoxStartBeforeBtnNeedToShow = pageBoxStartBeforePage != pageBoxStartPage;
		// 다음버튼 노출여부 계산
		boolean pageBoxEndAfterBtnNeedToShow = pageBoxEndAfterPage != pageBoxEndPage;
		
		if(pageBoxStartBeforeBtnNeedToShow) {
			pageMenuContent.append("<li><a href=\"" + getArticleListFileName(board, pageBoxStartBeforePage) + "\" class=\"flex flex-ai-c\"> &lt; 이전</a></li>");
		}
		
		for(int i = pageBoxStartPage; i <= pageBoxEndPage; i++) {
			String selectedClass = "";
			
			if(i == page) {
				selectedClass = "article-page-menu__link-selected";
			}
				
			pageMenuContent.append("<li><a href=\"" + getArticleListFileName(board, i) + "\" class=\"flex flex-ai-c " + selectedClass + "\">" + i + "</a></li>");
		}
		
		if(pageBoxEndAfterBtnNeedToShow) {
			pageMenuContent.append("<li><a href=\"" + getArticleListFileName(board, pageBoxEndAfterPage) + "\" class=\"flex flex-ai-c\">다음 &gt; </a></li>");
		}
		
		String body = bodyTemplate.replace("${article-list__main-content}", mainCotent.toString());
		body = body.replace("${article-page-menu__content}", pageMenuContent.toString());
		
		/*
		 *
			<li><a href="#" class="flex flex-ai-c">3</a></li>
			<li><a href="#" class="flex flex-ai-c">4</a></li>
			<li><a href="#" class="flex flex-ai-c">5</a></li>
			<li><a href="#" class="flex flex-ai-c">6</a></li>
			<li><a href="#" class="flex flex-ai-c">7</a></li>
			<li><a href="#" class="flex flex-ai-c">8</a></li>
			<li><a href="#" class="flex flex-ai-c">9</a></li>
			<li><a href="#" class="flex flex-ai-c">10</a></li>
		 */
		
		sb.append(body);
		
		// 푸터 시작
		sb.append(Util.getFileContents("site_template/foot.html"));
				
		// 파일 생성 시작
		String fileName = getArticleListFileName(board, page);
		String filePath = "site/" + fileName;
		
		Util.writeFile(filePath, sb.toString());
		System.out.println(filePath + " 생성");
	}

	private String getArticleListFileName(Board board, int page) {
		return "article_list_" + board.code + "_" + page + ".html";
	}

	private void buildArticleListPages() {
		List<Board> boards = articleService.getForPrintBoards();
		
		int itemsInAPage = 10;
		int pageBoxMenuSize = 10;
		
		for(Board board : boards) {
			List<Article> articles = articleService.getForPrintArticles(board.id);
			
			int articlesCount = articles.size();
			int totalPage = (int)Math.ceil((double)articlesCount / itemsInAPage);
			
			for(int i=1; i<=totalPage; i++) {
				buildArticleListPage(board, itemsInAPage, pageBoxMenuSize, articles, i);
			}
		}
	}

	private void buildIndexPage() {
		StringBuilder sb = new StringBuilder();

		String head = getHeadHtml("index");
		String foot = Util.getFileContents("site_template/foot.html");

		String mainHtml = Util.getFileContents("site_template/index.html");

		sb.append(head);
		sb.append(mainHtml);
		sb.append(foot);
		
		String filePath = "site/index.html";
		Util.writeFile(filePath, sb.toString());
		System.out.println(filePath + " 생성");
	}

	private void buildArticleDetailPages() {
		List<Article> articles = articleService.getArticles();

		String head = getHeadHtml("article_detail");
		String foot = Util.getFileContents("site_template/foot.html");

		// 게시물 상세페이지 생성
		for (Article article : articles) {
			StringBuilder sb = new StringBuilder();

			sb.append(head);

			sb.append("<div>");

			sb.append("번호 : " + article.id + "<br>");
			sb.append("작성날짜 : " + article.regDate + "<br>");
			sb.append("갱신날짜 : " + article.updateDate + "<br>");
			sb.append("제목 : " + article.title + "<br>");
			sb.append("내용 : " + article.body + "<br>");
			sb.append("<a href=\"article_detail_" + (article.id - 1) + ".html\">이전글</a><br>");
			sb.append("<a href=\"article_detail_" + (article.id + 1) + ".html\">다음글</a><br>");

			sb.append("</div>");

			sb.append(foot);

			String fileName = "article_detail_" + article.id + ".html";
			String filePath = "site/" + fileName;

			System.out.println(filePath + " 생성");
			Util.writeFile(filePath, sb.toString());
		}
	}

	private String getHeadHtml(String pageName) {
		String head = Util.getFileContents("site_template/head.html");

		StringBuilder boardMenuContentHtml = new StringBuilder();
		List<Board> forPrintBoards = articleService.getForPrintBoards();

		for (Board board : forPrintBoards) {
			boardMenuContentHtml.append("<li>");

			String link = getArticleListFileName(board, 1);

			boardMenuContentHtml.append("<a href=\"" + link + "\" class=\"block\">");
				
			boardMenuContentHtml.append(getTitleBarContentByPageName("article_list_" + board.code));
			
			boardMenuContentHtml.append("</a>");

			boardMenuContentHtml.append("</li>");
		}

		head = head.replace("${menu-bar__menu-1__board-menu-content}", boardMenuContentHtml.toString());

		String titleBarContentHtml = getTitleBarContentByPageName(pageName);

		head = head.replace("${title-bar__content}", titleBarContentHtml);

		return head;
	}

	private String getTitleBarContentByPageName(String pageName) {
		if(pageName.equals("index")) {
			return "<i class=\"fas fa-home\"></i> <span>HOME</span>";
		} else if (pageName.equals("article_detail")) {
			return "<i class=\"fas fa-file-alt\"></i> <span>ARTICLE DETAIL</span>";
		} else if (pageName.startsWith("article_list_free")) {
			return "<i class=\"fab fa-free-code-camp\"></i> <span>FREE LIST</span>";
		} else if (pageName.startsWith("article_list_notice")) {
			return "<i class=\"fas fa-flag\"></i> <span>NOTICE LIST</span>";
		} else if (pageName.startsWith("article_list_")) {
			return "<i class=\"fas fa-clipboard-list\"></i> <span>NOTICE LIST</span>";
		}

		return "";
	}
}
