package gmmahs_paser;

/* DayMeal 클래스는
 * 급식 데이터를 저장하는
 * 클래스입니다. */
public class DayMeal {
	private int MONTH; // 월
	private int DAY; // 일
	private String DAY_OF_WEEK;
	private String MealData; // 급식 정보

	public DayMeal(int month, int day, String day_of_week, String mealData) {
		MONTH = month;
		DAY = day;
		DAY_OF_WEEK = day_of_week;
		MealData = mealData;
	}

	public int getMONTH() {
		return MONTH;
	}

	public void setMONTH(int mONTH) {
		MONTH = mONTH;
	}

	public String getDAY_OF_WEEK() {
		return DAY_OF_WEEK;
	}

	public void setDAY_OF_WEEK(String dAY_OF_WEEK) {
		DAY_OF_WEEK = dAY_OF_WEEK;
	}

	public int getDAY() {
		return DAY;
	}

	public void setDAY(int dAY) {
		DAY = dAY;
	}

	public String getMealData() {
		return MealData;
	}

	public void setMealData(String mealData) {
		MealData = mealData;
	}
}
