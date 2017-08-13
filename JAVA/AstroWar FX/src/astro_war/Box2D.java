package astro_war;

public class Box2D {
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	
	/*  충돌 체크  */
	public boolean crashCheck(int x, int y, int width, int height) {
		if(this.x - width <= x && this.x + this.width >= x) {
			if(this.y <= y && this.y + this.height >= y) {
				return true;
			} else if( y <= this.y && y + height >= this.y) {
				return true;
			}
		}
		return false;
	}
}
