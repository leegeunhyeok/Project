package gmmahs_paser;

import java.util.Iterator;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		School sc = School.getInstance(); // 객체 받아오기
		List<String> list = sc.getWeekData(); // 이번주 데이터 수집
		
		Iterator<String> it = list.iterator();
		while(it.hasNext()) { //출력
			System.out.println(it.next() + "\n-----------");
		}
	}
}
