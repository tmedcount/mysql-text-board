package com.sbs.example.mysqlTextBoard.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.sbs.example.mysqlTextBoard.dto.Article;

public class ArticleDao {
	
	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();
		Connection con = null;
		
		try {
			
			String dbmsJdbcUrl = "jdbc:mysql://127.0.0.1:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String dbmsLoginId = "sbsst";
			String dbmsLoginPw = "sbs123414";
			
			// 기사 등록
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
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
	
	public Article getArticle(int inputedId) {
			Article article = null;
			Connection con = null;
			
			try {
				
				String dbmsJdbcUrl = "jdbc:mysql://127.0.0.1:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
				String dbmsLoginId = "sbsst";
				String dbmsLoginPw = "sbs123414";
				
				// 기사 등록
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				
				// 연결 생성
				try {
					con = DriverManager.getConnection(dbmsJdbcUrl, dbmsLoginId, dbmsLoginPw);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				String sql = "SELECT * FROM article WHERE id = ?";
				
				try {
					PreparedStatement pstmt = con.prepareStatement(sql);
					pstmt.setInt(1, inputedId);
					ResultSet rs = pstmt.executeQuery();			
					
					if(rs.next()) {
						int id = rs.getInt("id");
						String regDate = rs.getString("regDate");
						String updateDate = rs.getString("updateDate");
						String title = rs.getString("title");
						String body = rs.getString("body");
						int memberId = rs.getInt("memberId");
						int boardId = rs.getInt("boardId");
						
						article = new Article(id, regDate, updateDate, title, body, memberId, boardId);
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
			
			return article;
	}

	public int delete(int inputedId) {
		int affectedRows = 0;
		Connection con = null;
		
		try {
			
			String dbmsJdbcUrl = "jdbc:mysql://127.0.0.1:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String dbmsLoginId = "sbsst";
			String dbmsLoginPw = "sbs123414";
			
			// 기사 등록
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			// 연결 생성
			try {
				con = DriverManager.getConnection(dbmsJdbcUrl, dbmsLoginId, dbmsLoginPw);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String sql = "DELETE FROM article WHERE id = ?";
			
			try {
				PreparedStatement pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, inputedId);
				affectedRows = pstmt.executeUpdate();			
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
		
		return affectedRows;
	}

	public int add(int boardId, int memberId, String title, String body) {
		int id = 0;
		Connection con = null;
		
		try {
			
			String dbmsJdbcUrl = "jdbc:mysql://127.0.0.1:3306/textBoard?useUnicode=true&characterEncoding=utf8&autoReconnect=true&serverTimezone=Asia/Seoul&useOldAliasMetadataBehavior=true&zeroDateTimeNehavior=convertToNull&connectTimeout=60000&socketTimeout=60000";
			String dbmsLoginId = "sbsst";
			String dbmsLoginPw = "sbs123414";
			
			// 기사 등록
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
			// 연결 생성
			try {
				con = DriverManager.getConnection(dbmsJdbcUrl, dbmsLoginId, dbmsLoginPw);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			String sql = "INSERT INTO article";
			sql += " SET regDate = NOW(),";
			sql += " updateDate = NOW(),";
			sql += " boardId = ?,";
			sql += " memberId = ?,";
			sql += " title = ?,";
			sql += " body = ?";
			
			try {
				PreparedStatement pstmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				pstmt.setInt(1, boardId);
				pstmt.setInt(2, memberId);
				pstmt.setString(3, title);
				pstmt.setString(4, body);
				
				pstmt.executeUpdate();
				
				ResultSet rs = pstmt.getGeneratedKeys();
				rs.next();
				id = rs.getInt(1);
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
		
		return id;
	}
}
