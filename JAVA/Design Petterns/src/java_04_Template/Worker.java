package java_04_Template;

public abstract class Worker {
	
	//추상 메소드//
	//각각의 로직을 작성하는 메소드///
	protected abstract void doit();
	
	//final 선언은 자식클래스에서 수정을 막기위함//
	public final void work(){
		System.out.println("출근");
		doit();
		System.out.println("퇴근");
	}
}
