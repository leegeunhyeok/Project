package astro_war;

import java.awt.Image;

import javax.swing.ImageIcon;

public class PlayerSkin {
	private Image[] list = new Image[5];
	
	public PlayerSkin() {
		list[0] = new ImageIcon(Main.class.getResource("../images/default_player.png")).getImage();
		list[1] = new ImageIcon(Main.class.getResource("../images/default_player.png")).getImage();
		list[2] = new ImageIcon(Main.class.getResource("../images/default_player.png")).getImage();
		list[3] = new ImageIcon(Main.class.getResource("../images/default_player.png")).getImage();
		list[4] = new ImageIcon(Main.class.getResource("../images/default_player.png")).getImage();
	}
	
	public Image SetSkin(int index) {
		return list[index];
	}
}
