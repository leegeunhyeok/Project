package java_02_Adapter;

/*
 * Adapter 어댑터
 * 사용하고자 하는 메소드는 goodMethod이며 매개변수는 Enumeration이다.
 * 하지만 우리에겐 Iterator만 있으므로 Enumeration으로 변환시켜줘야한다. */

// Adapter 패턴은 어떤 오브젝트를 캐스팅이 불가능한 다른 클래스의 형태로 변환시켜주는 것입니다 //

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class Test {
	
	public static void goodMethod(Enumeration<String> enu){
		while(enu.hasMoreElements()){
			System.out.println(enu.nextElement()); 
		}
	}

	public static void main(String[] args) {
		List<String> list = new ArrayList<String>(); //ArrayList 생성
		
		list.add("AA"); //리스트에 데이터 추가
		list.add("BB");
		list.add("CC");
		
		Enumeration<String> ite = new IteratorToEnumeration(list.iterator());
		Test.goodMethod(ite);
	}
}
