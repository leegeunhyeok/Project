package java_04_Template;
/*
 * Template 템플릿
 * 분기를 담당하는 부분은 상위클래스에서 구체적으로 구현하고,
 * 분기된 이후의 로직은 하위구현체에게 넘기는 방식 */

public class Test {

	public static void main(String[] args) {
		Worker designer = new Designer();
		designer.work();
		
		Worker gamer = new Gamer();
		gamer.work();
	}
}
