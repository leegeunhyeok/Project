package java_05_Singleton;

public class Counter1 {
	private static Counter1 mCounter = null;
	private int count;
	
	private Counter1(){
		count = 0;
	}
	
	public static Counter1 getInstance(){
		if(mCounter == null){
			mCounter = new Counter1();
		}
		return mCounter;
	}
	
	public int getNextInt(){
		return ++count;
	}

}
