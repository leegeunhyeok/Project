package astro_war;

/* 운석 레이저 객체 */
public class MeteoLaser extends Box2D {
	private int SPEED = 8;
	private double x_, y_;
	
	public MeteoLaser(int px, int py, int mx, int my) {
		width = 20;
		height = 20;
		x = mx;
		y = my;
		x_ = px - mx;
		y_ = py - my;
		x_ = Math.sin(Math.atan2((double)x_, (double)y_));
		y_ = Math.cos(Math.atan2((double)x_, (double)y_));
	}
	
	public void setMove() {
		if(x_ > 0) x += x_ * SPEED;
		else x += -(-x_ * SPEED);
		if(y_ > 0) y += y_ * SPEED;
		else y += -(-y_ * SPEED);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
