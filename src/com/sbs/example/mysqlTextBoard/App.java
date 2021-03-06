package com.sbs.example.mysqlTextBoard;


import java.util.Scanner;

import com.sbs.example.mysqlTextBoard.controller.Controller;
import com.sbs.example.mysqlutil.MysqlUtil;

public class App {
	public void run() {
		Scanner sc = Container.scanner;
				
		while(true) {
			System.out.print("명령어) ");
			
			String cmd = sc.nextLine();
			
			MysqlUtil.setDBInfo("127.0.0.1", "sbsst", "sbs123414", "textBoard");
			// MysqlUtil.setDevMode(true);
			
			boolean needToExit = false;
			
			if(cmd.equals("system exit")) {
				System.out.print("== 프로그램 종료 ==");
				needToExit = true;
			} else {
				Controller controller = getControllerByCmd(cmd);
				
				if(controller != null) {
					controller.doCommand(cmd);
				}
			}
			
			MysqlUtil.closeConnection();
			
			if(needToExit) {
				break;
			}
		}
	}

	private Controller getControllerByCmd(String cmd) {
		if(cmd.startsWith("member ")) {
			return Container.memberController;
		} else if(cmd.startsWith("article ")) {
			return Container.articleController;
		} else if(cmd.startsWith("build ")) {
			return Container.buildController;
		}

		return null;
	}

}
