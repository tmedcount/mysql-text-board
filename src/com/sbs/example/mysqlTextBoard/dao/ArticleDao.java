package com.sbs.example.mysqlTextBoard.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.sbs.example.mysqlTextBoard.dto.Article;
import com.sbs.example.mysqlTextBoard.dto.Board;
import com.sbs.example.mysqlutil.MysqlUtil;
import com.sbs.example.mysqlutil.SecSql;

public class ArticleDao {
	
	public List<Article> getArticles() {
		List<Article> articles = new ArrayList<>();
		
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM article ORDER BY id DESC");
		
		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);
		
		for(Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));
		}
		
		return articles;
	}
	
	public Article getArticle(int id) {
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM article WHERE id = ?", id);
		
		Map<String, Object> articleMap = MysqlUtil.selectRow(sql);
		
		if(articleMap.isEmpty()) {
			return null;
		}
		
		return new Article(articleMap);
	}

	public int delete(int id) {
		SecSql sql = new SecSql();
		sql.append("DELETE FROM article WHERE id = ?", id);
		
		return MysqlUtil.delete(sql);
	}

	public int add(int boardId, int memberId, String title, String body) {

		SecSql sql = new SecSql();
		
		sql.append("INSERT INTO article");
		sql.append(" SET regDate = NOW(),");
		sql.append(" updateDate = NOW(),");
		sql.append( " boardId = ?,", boardId);
		sql.append( " memberId = ?,", memberId);
		sql.append(" title = ?,", title);
		sql.append(" body = ?", body);

		return MysqlUtil.insert(sql);
	}

	public int modify(int id, String title, String body) {
		
		SecSql sql = new SecSql();
		
		sql.append("UPDATE article");
		sql.append(" SET updateDate = NOW(),");
		sql.append(" title = ?,", title);
		sql.append(" body = ?", body);
		sql.append(" WHERE id = ?", id);

		return MysqlUtil.update(sql);
	}

	public List<Article> getForPrintArticles(int boardId) {
		List<Article> articles = new ArrayList<>();
		
		SecSql sql = new SecSql();
		sql.append("SELECT A.*, M.name AS extra__writer");
		sql.append(" FROM article AS A");
		sql.append(" INNER JOIN `member` AS M");
		sql.append(" ON A.memberId = M.id");
		if(boardId != 0) {
			sql.append(" WHERE A.boardId = ?", boardId);
		}
		sql.append(" ORDER BY A.id DESC;");
		
		List<Map<String, Object>> articleMapList = MysqlUtil.selectRows(sql);
		
		for(Map<String, Object> articleMap : articleMapList) {
			articles.add(new Article(articleMap));
		}
		
		return articles;
	}

	public Board getBoardByCode(String code) {
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM board WHERE `code` = ?", code);
		
		Map<String, Object> map = MysqlUtil.selectRow(sql);
		
		if(map.isEmpty()) {
			return null;
		}
		
		return new Board(map);
	}

	public Board getBoardByName(String name) {
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM board WHERE `name` = ?", name);
		
		Map<String, Object> map = MysqlUtil.selectRow(sql);
		
		if(map.isEmpty()) {
			return null;
		}
		
		return new Board(map);
	}

	public int makeBoard(String code, String name) {

		SecSql sql = new SecSql();
		
		sql.append("INSERT INTO board");
		sql.append(" SET regDate = NOW(),");
		sql.append(" updateDate = NOW(),");
		sql.append(" `code` = ?,", code);
		sql.append(" `name` = ?", name);

		return MysqlUtil.insert(sql);
	}

	public List<Board> getForPrintBoards() {
		List<Board> boards = new ArrayList<>();
		
		SecSql sql = new SecSql();
		sql.append("SELECT * FROM board ORDER BY id DESC");
		
		List<Map<String, Object>> mapList = MysqlUtil.selectRows(sql);
		
		for(Map<String, Object> map : mapList) {
			boards.add(new Board(map));
		}
		
		return boards;
	}

	public int getArticlesCount(int boardId) {
		SecSql sql = new SecSql();
		sql.append("SELECT count(*) AS cnt FROM article WHERE boardId = ?", boardId);
		
		return MysqlUtil.selectRowIntValue(sql);
	}
}
