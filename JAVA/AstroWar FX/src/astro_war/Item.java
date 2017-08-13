package astro_war;

/* æ∆¿Ã≈€ ∞¥√º */
public class Item extends Box2D {
	private int type;
	
	public Item(int type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.width = 30;
		this.height = 30;
	}
	
	public int getType() {
		return type;
	}
}
