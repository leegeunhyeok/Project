package java_04_Template;

public class Gamer extends Worker{

	//오버라이드하여 실행할 로직 작성//
	@Override
	protected void doit() {
		System.out.println("게임 중..");
	}
}
