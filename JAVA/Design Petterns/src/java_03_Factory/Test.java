package java_03_Factory;
/*
 * Factory 팩토리
 * new 를 통해 직접 객체를 생성하지 않고, Factory 클래스에서
 * 해당하는 객체를 생성한 후 돌려받는 방식이다. */


public class Test {

	public static void main(String[] args) {
		Animal a1 = AnimalFactory.create("소");
		a1.printDescription();
		Animal a2 = AnimalFactory.create("고양이");
		a2.printDescription();
		Animal a3 = AnimalFactory.create("강아지");
		a3.printDescription();
	}
}
