package astro_war;

import javafx.scene.image.Image;

public class PlayerSkin {
	/* 스킨 능력치 */
	private final int[] price = new int[] {0, 10000, 25000, 50000, 100000};
	private final String[] info = new String[] {" 추가능력 없음", "이동속도 +2", "추가 코인 +20%", "공격 속도 +50%", "데미지 증가 아이템효과 +100%\n레이저 최대 수 +2 / 시작 공격력 +10"};
	private final Image[] list = new Image[5];
	private final int[] width = new int[] {50, 50, 50, 50, 70};
	private final int[] move_speed = new int[] {7, 9, 7, 7, 7};
	private final int[] shoot_speed = new int[] {25, 25, 25, 20, 25};
	private final int[] damage = new int[] {10, 10, 10, 10, 20};
	/*---------*/
	
	
	public PlayerSkin() {
		list[0] = new Image("images/default_player.png"); // 기본
		list[1] = new Image("images/lightning_player.png"); // 이동속도 +2
		list[2] = new Image("images/money_player.png"); // 추가 코인 +20%
 		list[3] = new Image("images/rapid_player.png"); // 공격속도 +50%
		list[4] = new Image("images/shadow_player.png"); // 데미지증가 아이템 효과 +100% + 레이저 최대갯수+2
	}
	
	public String getInfo(int n) {
		return info[n];
	}
	
	public int getPrice(int n) {
		return price[n];
	}
	
	public Image getSkin(int n) {
		return list[n];
	}
	
	public int getWidth(int n) {
		return width[n];
	}	
	
	public int getMoveSpeed(int n) {
		return move_speed[n];
	}
	
	public int getShootSpeed(int n) {
		return shoot_speed[n];
	}
	
	public int getDamage(int n) {
		return damage[n];
	}
}
