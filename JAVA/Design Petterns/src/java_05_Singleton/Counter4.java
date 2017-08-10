package java_05_Singleton;

public class Counter4 {
	private volatile static Counter4 mCounter;
	public static Counter4 getInstance(){
		if(mCounter == null){
			synchronized (Counter4.class) {
				if(mCounter == null){
					mCounter = new Counter4();
				}
			}
		}
		return mCounter;
	}
	
	private Counter4(){
		
	}
}
