package com.sbs.example.mysqlTextBoard;

import com.sbs.example.mysqlTextBoard.util.Util;

public class Main {

	public static void main(String[] args) {
		testApi();		
		
		// new App().run();
	}

	private static void testApi() {
		String url = "https://disqus.com/api/3.0/forums/listThreads.json";

		String rs = Util.callApi(url, "api_key=7etmI9A5O6B4mJ1AoakRWtHUchhmGkNvmUjc0Jb4CnEy367qo67hZg1Rm2jBznEq", "forum=yamto", "thread:ident=article_detail_1.html");
		
		System.out.println(rs);
	}

}
