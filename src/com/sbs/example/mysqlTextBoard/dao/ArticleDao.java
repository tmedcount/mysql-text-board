package com.sbs.example.mysqlTextBoard.dao;

import java.util.ArrayList;
import java.util.List;

import com.sbs.example.mysqlTextBoard.dto.Article;

public class ArticleDao {

	public List<Article> getArticles() {
		
		List<Article> articles = new ArrayList<>();
		
		Article article;
		
		//첫번째 가짜 게시물 만들기
		article = new Article();
		article.id = 1;
		article.regDate = "2022-02-26 12:12:12";
		article.updateDate = "2022-02-26 12:12:12";
		article.title = "제목1";
		article.body = "내용1";
		article.memberId = 1;
		article.boardId = 1;
		
		articles.add(article);
		
		//두번째 가짜 게시물 만들기
		article = new Article();
		article.id = 2;
		article.regDate = "2022-02-26 12:12:13";
		article.updateDate = "2022-02-26 12:12:13";
		article.title = "제목2";
		article.body = "내용2";
		article.memberId = 1;
		article.boardId = 1;
		
		articles.add(article);
		
		return articles;
	}

}
