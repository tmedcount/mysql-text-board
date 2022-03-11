package com.sbs.example.mysqlTextBoard;

import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.service.ArticleService;
import com.sbs.example.mysqlTextBoard.service.MemberService;

public class Container {

	public static Scanner scanner;
	public static MemberService memberService;
	public static ArticleService articleService;
	
	static {
		scanner = new Scanner(System.in);
		
		memberService = new MemberService();
		articleService = new ArticleService();
	}

}
