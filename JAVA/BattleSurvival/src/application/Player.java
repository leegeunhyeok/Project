package application;

public class Player {
	private int x, y;
	private int hp = 100;
	private int boost = 0;
	
	public Player(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setPos(final int x, final int y) {
		this.x = x;
		this.y = y;
	}
}
