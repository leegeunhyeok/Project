package j02_variable;

public class Variable {

	public static void main(String[] args) {
		int number;
		char c;
		float f1 = 3.14f; //float 변수에 상수를 대입하려면 f를 뒤에 붙여야함
		double f2 = 3.14;
		String str = "안녕"; //문자열은 " 로 감싸준다.
		
		number = 123;
		c = 'A'; //문자는 ' 로 감싸준다
		
		System.out.printf("%d, %c, %f, %f, %s", number, c, f1, f2, str);
	}

}


