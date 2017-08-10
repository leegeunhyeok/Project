package j01_print;

public class PrintTest {

	public static void main(String[] args) {
		System.out.print("1"); //print는 출력만 함
		System.out.print("2");
		System.out.print("3\n"); // \n 개행문자를 삽입하여 임의로 개행 가능
		System.out.println("1 안녕하세요."); //println은 출력하고 자동 개행
		System.out.println("2 안녕하세요.");
		System.out.printf("숫자는 : %d 입니다. \n", 10);
		System.out.printf("2 + 7 = %.2f 입니다. \n", (float)43 / 6);
		int number = 40;
		System.out.println("number 변수의 값 : " + number); //+ 기호를 사용하여 변수 또는 상수를 문자열과 함께 출력가능
	}
}



