package java_05_Singleton;

public class Counter2 {
	private static Counter2 mCounter = new Counter2();
	public static Counter2 getInstance(){
		return mCounter;
	}

	private Counter2(){
		
	}
}
