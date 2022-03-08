package com.sbs.example.mysqlTextBoard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
			
			String sql = "SELECT * FROM article ORDER BY id DESC";
			
			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				// update, delete => execute()
				ResultSet rs = pstmt.executeQuery();			
				
				while(rs.next()) {
					int id = rs.getInt("id");
					String regDate = rs.getString("regDate");
					String updateDate = rs.getString("updateDate");
					String title = rs.getString("title");
					String body = rs.getString("body");
					int memberId = rs.getInt("memberId");
					int boardId = rs.getInt("boardId");
					
					Article article = new Article(id, regDate, updateDate, title, body, memberId, boardId);

					articles.add(article);
				}
				
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
		article = new Article(1, "2022-02-26 12:12:12", "2022-02-26 12:12:12", "제목1", "내용1", 1, 1);
		articles.add(article);
		
		//두번째 가짜 게시물 만들기
		article = new Article(2, "2022-02-26 12:12:13", "2022-02-26 12:12:13", "제목2", "내용2", 1, 1);
		articles.add(article);
		
		return articles;
	}

}
