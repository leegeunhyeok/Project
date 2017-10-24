package application;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GameLogger {
	
	public GameLogger() {
		System.out.println(getTime() + "Game Logger init.");
	}
	
	/* 개행 있는 로그남기기 */
	public void printlnLog(String msg) {
		System.out.println(getTime() + msg);
	}
	
	/* 개행 없는 로그남기기 */
	public void printLog(String msg) {
		System.out.print(getTime() + msg);
	}
	
	/* 완료 */
	public void printDoneLog() {
		System.out.println(" done!");
	}
	
	/* 에러 */
	public void printErrorLog() {
		System.out.println(" error!");
	}
	
	/* 현재 시간 */
	private String getTime() {
		SimpleDateFormat time = new SimpleDateFormat("[hh:mm:ss.SSS] ");
		return time.format(new Date());
	}
}
