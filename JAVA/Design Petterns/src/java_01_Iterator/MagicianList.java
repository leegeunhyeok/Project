package java_01_Iterator;
/*
 * Iterator 이터레이터 
 * 모든 컬렉션으로부터 정보를 얻을 수 있는 인터페이스 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MagicianList implements Iterable<String>{
	private List<String> list = new ArrayList<String>();
	
	public void add(String name){
		list.add(name);
	}
	
	@Override
	public Iterator<String> iterator() {
		return new Iterator<String>() {
			int seq = 0;
			public boolean hasNext(){
				return seq < list.size();
			}
			
			public String next(){
				return list.get(seq++);
			}
			
			public void remove(){
				throw new UnsupportedOperationException();
			}
		};
	}
	
	
	public static void main(String[] args) {
		MagicianList list = new MagicianList(); //인스턴스 생성
		list.add("AA"); //데이터 추가
		list.add("BB");
		list.add("CC");
		
		Iterator<String> iterator = list.iterator(); //MagicianList의 iterator를 받아옴
		
		while(iterator.hasNext()){ //다음 데이터가 있으면 진행
			String element = iterator.next(); //해당 원소의 데이터를 받고 인덱스 1증가
			System.out.println(element); //원소 출력
		}
	}
}
