package astro_war;

/* ·¹ÀÌÀú °´Ã¼ */
public class Laser {
	private int laser_X;
	private int laser_Y;
	private int damage = 10;
	
	public Laser(int x, int y, int damage){
		laser_X = x;
		laser_Y = y;
		this.damage = damage;
	}
	
	public void setLaserY(int y) {
		laser_Y = y;
	}
	
	public void setLaserX(int x) {
		laser_X = x;
	}
	
	public int getLaserX() {
		return laser_X;
	}
	
	public int getLaserY() {
		return laser_Y;
	}
	
	public int getDamage() {
		return damage;
	}
}
