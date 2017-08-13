package gmmahs_paser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 * GMMA High School Meal data parser
 * 
 * Version : Beta 0.1
 * Code By lghlove0509@naver.com
 * 
 * */

public class School {
	private static int count = 0;
	private static List<String> list = new ArrayList<>();
	
	public static void main(String[] args) {
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK); //1 일요일 ~ 7 토요일
		int day =c.get(Calendar.DAY_OF_MONTH); // 오늘 날짜
		System.out.println("요일: " + day_of_week + " / 날짜: " + day + "\n\n---이번주 급식---");
		
		try {
			Document doc = Jsoup.connect("http://stu.goe.go.kr/sts_sci_md00_001.do?schulCode=J100000488&schulCrseScCode=4&schulKndScCode=4")
					.timeout(5000).get(); // 광명경영회계고등학교의 이번달 급식 데이터 조회링크
			
			Elements e = doc.select("tbody"); // HTML의 tbody태그 내의 코드 수집 
			String str = e.toString().replaceAll("\\s+", ""); // 파싱한 데이터의 공백, 개행문자 모두 제거
			
			StringBuffer buf = new StringBuffer();
			
			boolean inDiv = false;
			for(int i=0; i<str.length(); i++) {
				if(str.charAt(i)=='v') { //div 태그 제거
					if(inDiv) {
						buf.delete(buf.length() - 4, buf.length());
                        if (buf.length() > 0) 
                        	//버퍼에 있는 데이터 길이가 > 0 이면
                        	if(++count >= day - (day_of_week-2) && list.size() < 5) {
                        		// 오늘 날짜가 포함된 주(Week)의 급식데이터 5개 불러오기
                        		parseDayMeal(buf.toString() + " ");
                        	}
                        buf.setLength(0);
                    } else {
                        i++;
                    } 
					inDiv = !inDiv;
                } else if (inDiv) {
                	buf.append(str.charAt(i));
                }
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Iterator<String> it = list.iterator();
		while(it.hasNext()) {
			System.out.println(it.next() + "\n\n-------------\n"); //리스트에 저장된 데이터 출력
		}
	}
	
	// 인자로 넘어온 data를 가공하는 작업
	public static void parseDayMeal(String data) {
		String parse = "";
		String day = "";
		
		if(data.charAt(1)==' ' || data.charAt(2)==' ') {
			// 데이터가 없는 경우
			parse = "급식 데이터가 없습니다.";
		} else {
			// br 태그를 모두 개행문자로 바꿈
			parse = data.replaceAll("<br>", "\n");
			
			//아래 작업은 추후에 사용할 날짜 데이터 추출작업
			day += parse.charAt(0);
			if(parse.charAt(1) != ' ') {
				day += parse.charAt(1) + "";
			}
		}
		list.add(parse); //리스트에 데이터 추가
	}
}
