package astro_war;

/* Æø¹ß ÀÌÆåÆ® ÀÌ¹ÌÁö °´Ã¼ */
public class Explosion {
	private boolean anim_flag = false;
	private boolean destroy = false;
	private int x, y;
	private int cnt = 0;

	public Explosion(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public void setFlag() {
		if (cnt % 8 == 0) {
			if (!anim_flag) {
				anim_flag = true;
			} else {
				destroy = true;
			}
		}
	}

	public boolean getFlag() {
		cnt++;
		return anim_flag;
	}

	public boolean getDestroy() {
		return destroy;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
}
