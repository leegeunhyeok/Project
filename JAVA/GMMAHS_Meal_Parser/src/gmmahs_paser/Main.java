package gmmahs_paser;

import java.util.Iterator;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		School sc = School.getInstance(); // 객체 받아오기
		List<DayMeal> list = sc.getMonthData(); // 이번주 데이터 수집
		List<DayMeal> week_list = sc.getWeekDataInMonthData(0); // 2째주의 급식 데이터

		System.out.println("<<<<< 이번달의 급식데이터 >>>>>");
		Iterator<DayMeal> it = list.iterator();
		while (it.hasNext()) { // 출력
			DayMeal temp = it.next();
			System.out.printf("[%d월 %d일 %s요일]\n", temp.getMONTH(), temp.getDAY(), temp.getDAY_OF_WEEK());
			System.out.println(temp.getMealData() + "\n------------");
		}

		System.out.println("\n\n\n<<<<< 이번주의 급식데이터 >>>>>");
		Iterator<DayMeal> it_week = week_list.iterator();
		while (it_week.hasNext()) { // 출력
			DayMeal temp = it_week.next();
			System.out.printf("[%d월 %d일 %s요일]\n", temp.getMONTH(), temp.getDAY(), temp.getDAY_OF_WEEK());
			System.out.println(temp.getMealData() + "\n------------");
		}
	}
}
