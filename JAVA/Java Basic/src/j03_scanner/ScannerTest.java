package j03_scanner;

import java.util.Scanner;

public class ScannerTest {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in); //표준 입력
		
		String str = sc.nextLine(); //개행문자(엔터)까지 문자열 입력
		char ch = sc.nextLine().charAt(0); //개행문자(엔터)까지 문자열 입력받고 첫번째 문자
		int n = sc.nextInt(); // int(정수)형태로 입력
		
		System.out.printf("%d, %c, %s", n, ch, str);
	}
}





