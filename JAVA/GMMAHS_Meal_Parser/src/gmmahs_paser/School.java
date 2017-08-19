package gmmahs_paser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/*
 * GMMA High School Meal data parser
 * 
 * Version : Beta 0.4
 * Code By lghlove0509@naver.com
 * 
 * */

public class School {
	private final String url = "http://stu.goe.go.kr/sts_sci_md00_001.do?schulCode=J100000488&schulCrseScCode=4&schulKndScCode=4";
	private List<DayMeal> month_list = new ArrayList<>();
	private int year, month, last_day, week_of_month;
	private int first_day_of_week;
	private static School instance = null;

	private School() {
		// Private 생성자
	}

	// getInstance 메소드로만 객체를 받을 수 있음
	public static School getInstance() {
		if (instance == null) {
			instance = new School();
		}
		return instance;
	}

	// 이번달의 급식 데이터를 수집함
	public List<DayMeal> getMonthData() {
		RefreshCalendar(); // 현재 시점의 달력 데이터로 수정
		int day_count = 1; // 1일 ~ 이번달의 마지막날
		int day_week = first_day_of_week;
		month_list.clear();
		
		try {
			String str = getDataFromUrl(new URL(url), "tbody").replaceAll("\\s+", "");
			// tbody 태그 내의 데이터를 불러온 후 공백, 개행문자 모두 삭제

			StringBuffer buf = new StringBuffer();

			boolean inDiv = false;
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) == 'v') { // div 태그 제거
					if (inDiv) {
						buf.delete(buf.length() - 4, buf.length());
						if (buf.length() > 0 && day_count < last_day) {
							if(day_week > 7) day_week = 1;
							month_list.add(new DayMeal(month, day_count, DayWeek_toString(day_week), parseDayMeal(buf.toString() + " ", day_count)));
							day_count++;
							day_week++;
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
		} catch (Exception e) {
			e.printStackTrace();
			month_list.clear();
			for (int i = 0; i < last_day; i++) {
				month_list.add(new DayMeal(month, i + 1, "", "데이터 불러오기 실패"));
			}
		}
		return month_list;
	}
	
	
	// 인자로 전달된 주(Week)의 급식데이터 리스트를 반환
	public List<DayMeal> getWeekDataInMonthData(int week){
		/* 
		 * 인자로 0을 전달하면 이번주의 데이터를 반환합니다.
		 * */
		if(month_list.size() == 0) return null;
		
		if(week == 0) week = week_of_month;
		
		List<DayMeal> week_list = new ArrayList<>();
		int temp_week = 1;
		boolean inWeek = false;
		
		for(int i = 0; i<month_list.size(); i++) {
			DayMeal temp = month_list.get(i);
			String d_week = temp.getDAY_OF_WEEK();
			
			if(inWeek) {
				week_list.add(new DayMeal(temp.getMONTH(), temp.getDAY(), d_week, temp.getMealData()));
			}	
			
			if(d_week.equals("토")) {
				temp_week++;
				if(inWeek) break;
				else if(temp_week == week) inWeek = true; 
			}	
		}
		return week_list;
	}
	

	// URL로 접속하여 tag안의 데이터 파싱
	public String getDataFromUrl(URL url, String tag) throws Exception {
		BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		StringBuffer buffer = new StringBuffer();
		String line;

		boolean inTable = false;

		while ((line = reader.readLine()) != null) {
			if (inTable) {
				if (line.contains("</" + tag + ">")) {
					break;
				}
				buffer.append(line);
			} else if (line.contains("<" + tag + ">")) {
				inTable = true;
			}
		}
		reader.close();
		return buffer.toString();
	}

	// 인자로 넘어온 하루 급식 데이터를 가공하는 작업
	public String parseDayMeal(String data, int day) {
		String parse = "";
		if (data.charAt(1) == ' ' || data.charAt(2) == ' ') {
			// 데이터가 없는 경우
			parse = "급식 데이터가 없습니다.";
		} else {
			// 1, 2번째 br 태그를 제외한 나머지를 모두 개행문자로 바꿈
			parse = data.replaceFirst(day + "", "");
			parse = parse.replaceAll("\\[중식\\]", ""); // [중식] 문자 제거
			parse = parse.replaceFirst("<br/>", ""); // 1번째 br 제거
			parse = parse.replaceFirst("<br/>", ""); // 2번째 br 제거
			parse = parse.replaceAll("<br/>", "\n"); // 남은 br태그들을 개행으로 변환

			// 음식9.13.5 이런 형식으로 급식 메뉴와 알레르기 정보가 합쳐져 있음.
			// 메뉴와 알레르기 정보 사이에 공백을 집어넣는 작업
			StringBuffer sb = new StringBuffer();
			sb.append(parse.charAt(0));
			boolean first = true;
			for (int i = 0; i < parse.length() - 1; i++) {
				char temp = parse.charAt(i + 1);
				if (temp == '\n') {
					sb.append(temp);
					first = true;
				} else if (temp >= 48 && temp <= 57 && first) {
					sb.append(" ");
					sb.append(temp);
					first = false;
				} else {
					sb.append(temp);
				}
			}
			parse = sb.toString();
		}
		return parse; // 리스트에 데이터
	}
	
	// Calendar의 주(Week) 데이터를 문자열로 변환 (1일 ~ 7토)
	public String DayWeek_toString(int day_week) {
		String s = "";
		switch (day_week) {
		case 1:
			s = "일";
			break;
			
		case 2:
			s = "월";
			break;
			
		case 3:
			s = "화";
			break;
			
		case 4:
			s = "수";
			break;
			
		case 5:
			s = "목";
			break;
			
		case 6:
			s = "금";
			break;
			
		case 7:
			s = "토";
			break;
		}
		return s;
	}

	// 현재 시점의 달력데이터 갱신
	public void RefreshCalendar() {
		Calendar c = Calendar.getInstance();
		week_of_month = c.get(Calendar.WEEK_OF_MONTH); // 이번달의 몇번째 주
		year = c.get(Calendar.YEAR); // 년도
		month = c.get(Calendar.MONTH) + 1; // 이번 달
		last_day = c.getMaximum(Calendar.DAY_OF_MONTH); // 이번달의 마지막 날

		c.set(year, month - 1, 1); // 날짜를 이번달 1일로 설정
		first_day_of_week = c.get(Calendar.DAY_OF_WEEK); // 이번달의 첫째날 요일 (일요일 ~ 7 토요일)
		
//		System.out.printf("%d년 %d월 %d일 / %d 요일 / %d번째 주 / 이번달의 마지막 날 : %d\n\n", year, month, day, day_of_week,
//				week_of_month, last_day);
	}
}
