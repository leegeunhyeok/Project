package java_05_Singleton;

public class Counter5 {
	private Counter5(){
		
	}
	
	private static class CounterHolder{
		private static final Counter5 mCounter = new Counter5();
	}
	
	public static Counter5 getInstance(){
		return CounterHolder.mCounter;
	}
}
