package java_05_Singleton;

public class Counter3 {
	private static Counter3 mCounter;
	public static synchronized Counter3 getInstance(){
		if(mCounter == null){
			mCounter = new Counter3();
		}
		return mCounter;
	}
	
	private Counter3(){
		
	}
}
