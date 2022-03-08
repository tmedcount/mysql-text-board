package com.sbs.example.mysqlTextBoard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.sbs.example.mysqlTextBoard.dto.Article;

public class ArticleDao {

	private List<Article> articles;
	
	public ArticleDao() {
		articles = new ArrayList<>();
	}

	public List<Article> getArticles() {
		Connection con = null;
		
		try {
			
			String dbmsJdbcUrl = "jdbc:mysql://127.0.0.1:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String dbmsLoginId = "sbsst";
			String dbmsLoginPw = "sbs123414";
			
			// 기사 등록
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			// 연결 생성
			try {
				con = DriverManager.getConnection(dbmsJdbcUrl, dbmsLoginId, dbmsLoginPw);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String sql = "UPDATE article";
			sql += " SET updateDate = NOW()";
			sql += " WHERE id = 3;";
			
			String sql1 = "UPDATE article SET updateDate = NOW() WHERE id = 3";
			
			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.execute();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} finally {
			try {
				// System.out.println("여기는 항상 실행 됨!!!!!!!!!!!!!!!!");
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return articles;
	}
	
	private List<Article> getFakeArticles() {
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
