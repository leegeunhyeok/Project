package gmmahs_paser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/*
 * GMMA High School Meal data parser
 * 
 * Version : Beta 0.2
 * Code By lghlove0509@naver.com
 * 
 * */

public class School {
	private static School instance = null;
	
	private School() {
		//Private
	}
	
	public static School getInstance() {
		if(instance == null) {
			instance = new School();
		}
		return instance;
	}
	
	// 이번주의 급식 데이터를 수집함
	public List<String> getWeekData(){
		Calendar c = Calendar.getInstance();
		int day_of_week = c.get(Calendar.DAY_OF_WEEK); //1 일요일 ~ 7 토요일
		int day =c.get(Calendar.DAY_OF_MONTH); // 오늘 날짜
		int day_count = 0; // 이번주 데이터만 골라내기 위해 몇일인지 카운트
		List<String> list = new ArrayList<>();
		
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
                        	if(++day_count >= day - (day_of_week-2) && list.size() < 5) {
                        		// 오늘 날짜가 포함된 주(Week)의 급식데이터 5개 불러오기
                        		list.add(parseDayMeal(buf.toString() + " ", day_count));
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
			list.clear();
			for(int i=0; i<5; i++) {
				list.add("데이터 불러오기 실패");
			}
			return null;
		}
		return list;
	}
	
	// 인자로 넘어온 data를 가공하는 작업
	public String parseDayMeal(String data, int day) {
		String parse = "";
		if(data.charAt(1)==' ' || data.charAt(2)==' ') {
			// 데이터가 없는 경우
			parse = "급식 데이터가 없습니다.";
		} else {
			// 1, 2번째 br 태그를 제외한 나머지를 모두 개행문자로 바꿈
			parse = data.replaceFirst(day + "", "");
			parse = parse.replaceAll("\\[중식\\]", ""); // [중식] 문자 제거
			parse = parse.replaceFirst("<br>", ""); // 1번째 br 제거
			parse = parse.replaceFirst("<br>", ""); // 2번째 br 제거
			parse = parse.replaceAll("<br>", "\n"); // 남은 br태그들을 개행으로 변환
		}
		return parse; //리스트에 데이터 추가
	}
}
