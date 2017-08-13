package astro_war;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class AstroWarTest extends JFrame implements Runnable {
	private final int btn_width = 200;
	private final int btn_height = 75;
	private final int btn_x = 30;
	private final int start_btn_y = 450;
	private final int shop_btn_y = 550;
	private final int exit_btn_y = 650;
	private final int x_MAX = Main.WIDTH - Main.PLAYER_PX - 5;
	private final int y_MAX = Main.HEIGHT - Main.PLAYER_PX;

	private Thread thread = null;
	private boolean pause = false;

	private boolean shop = false;
	private boolean start = false;
	private int G_SCORE = 0;
	private int TIME_SCORE_CNT = 0;
	private int player_X, player_Y;
	private int player_life;

	private Font font = new Font("font", Font.ITALIC, 24);
	private Font coin_font = new Font("coin_font", Font.BOLD, 28);
	private Random random = new Random();

	//private int player_direction; //0: ±âº», 1: ÁÂ, 2: ¿ì
	private int bg_refresh = 0;
	private int meteo_spawn = 0;

	private boolean Move_UP;
	private boolean Move_DOWN;
	private boolean Move_LEFT;
	private boolean Move_RIGHT;
	
	private FileManager file_mgr = new FileManager();
	
	private Image shop_left_image = new ImageIcon(Main.class.getResource("../images/left.png")).getImage();
	private Image shop_right_image = new ImageIcon(Main.class.getResource("../images/right.png")).getImage();
	private Image shop_back_image = new ImageIcon(Main.class.getResource("../images/button_back.png")).getImage();
	
	private Image resume_normal_image = new ImageIcon(Main.class.getResource("../images/button_resume.png")).getImage();
	private Image resume_entered_image = new ImageIcon(Main.class.getResource("../images/button_resume_pressed.png")).getImage();
	private Image main_normal_image = new ImageIcon(Main.class.getResource("../images/button_main.png")).getImage();
	private Image main_entered_image = new ImageIcon(Main.class.getResource("../images/button_main_pressed.png")).getImage();

	private Image title = new ImageIcon(Main.class.getResource("../images/title.png")).getImage();
	private Image heart = new ImageIcon(Main.class.getResource("../images/life_heart.png")).getImage();
	private Image start_normal_image = new ImageIcon(Main.class.getResource("../images/button_start.png")).getImage();
	private Image start_pressed_image = new ImageIcon(Main.class.getResource("../images/button_start_pressed.png"))
			.getImage();
	private Image shop_normal_image = new ImageIcon(Main.class.getResource("../images/button_shop.png")).getImage();
	private Image shop_pressed_image = new ImageIcon(Main.class.getResource("../images/button_shop_pressed.png")).getImage();
	private Image exit_normal_image = new ImageIcon(Main.class.getResource("../images/button_exit.png")).getImage();
	private Image exit_pressed_image = new ImageIcon(Main.class.getResource("../images/button_exit_pressed.png"))
			.getImage();
	private boolean exit_clicked = false;
	private boolean start_clicked = false;
	private boolean shop_clicked = false;
	private boolean resume_clicked = false;
	private boolean main_clicked = false;

	private ArrayList<Meteorite> meteo_list = new ArrayList<Meteorite>();

	private ArrayList<Laser> laser_list = new ArrayList<Laser>();
	private Image laser = new ImageIcon(Main.class.getResource("../images/laser.png")).getImage();

	private boolean meteo_anim_flag = false;
	private int meteo_anim_cnt = 0;
	private Image meteo1 = new ImageIcon(Main.class.getResource("../images/meteorite_1.png")).getImage();
	private Image meteo2 = new ImageIcon(Main.class.getResource("../images/meteorite_2.png")).getImage();

	private int bg1Y, bg2Y;
	private Image bg1 = new ImageIcon(Main.class.getResource("../images/background.png")).getImage();
	private Image bg2 = new ImageIcon(Main.class.getResource("../images/background.png")).getImage();
	
	private Image Player;
	private PlayerSkin skin = new PlayerSkin();
	private int skin_number = 0; //0: default
	
	//private Image Player_left = new ImageIcon(Main.class.getResource("../images/default_left.png")).getImage();
	//private Image Player_right = new ImageIcon(Main.class.getResource("../images/default_right.png")).getImage();

	private Image title_background = new ImageIcon(Main.class.getResource("../images/title_image.png")).getImage();
	
	private SoundPlayer bgm_sound;

	public AstroWarTest() {
		setTitle("AstroWar");
		setSize(Main.WIDTH, Main.HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		setVisible(true);
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				if(!start){
					if(!shop) {
						if (x >= btn_x && x <= btn_width + btn_x && y >= start_btn_y && y <= btn_height + start_btn_y
								&& start_clicked) {
							bgm_sound.close();
							start_clicked = false;
							gameInit();
							start = true;
						} else if(x >= btn_x && x <= btn_width && y >= shop_btn_y && y <= btn_height + shop_btn_y
								&& shop_clicked) {
							shop = true;
						} else if (x >= btn_x && x <= btn_width && y >= exit_btn_y && y <= btn_height + exit_btn_y
								&& exit_clicked) {
							bgm_sound.close();
							System.exit(0);
						}
						start_clicked = false;
						shop_clicked = false;
						exit_clicked = false;
					} else {
						if(x >= 20 && x <= 70 && y >= 375 && y <=425) {
							System.out.println("left");
						} else if (x >= 530 && x <= 580 && y >= 375 && y <=425) {
							System.out.println("right");
						} else if(x >= 200 && x <= 400 && y >= 700 && y <=775) {
							shop = false;
						}
					}	
				} else {
					if(pause && resume_clicked && x >= 200 && x<= 400 && y >= 225 && y<= 300){
						resume_clicked = false;
						pause = false;
						thread.resume();
					} else if (pause && main_clicked && x >= 200 && x<= 400 && y >= 325 && y<= 400){
						main_clicked = false;
						start = false;
						mainInit();
					} else {
						resume_clicked = false;
						main_clicked = false;
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int x = e.getX();
				int y = e.getY();
				if(!start){
					if(!shop) {
						if (x >= btn_x && x <= btn_width + btn_x && y >= start_btn_y && y <= btn_height + start_btn_y) {
							start_clicked = true;
						} else if (x >= btn_x && x <= btn_width && y >= shop_btn_y && y <= btn_height + shop_btn_y) {
							shop_clicked = true;
						} else if (x >= btn_x && x <= btn_width && y >= exit_btn_y && y <= btn_height + exit_btn_y) {
							exit_clicked = true;
						} else {
							start_clicked = false;
							exit_clicked = false;
						}
					} else {
						
					}
				} else {
					if(x >= 200 && x<= 400 && y >= 225 && y<= 300){
						resume_clicked = true;
					} else if (x >= 200 && x<= 400 && y >= 325 && y<= 400){
						main_clicked = true;
					} else {
						resume_clicked = false;
						main_clicked = false;
					}
				}
			}
		});

		this.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					Move_UP = true;
					break;

				case KeyEvent.VK_DOWN:
					Move_DOWN = true;
					break;

				case KeyEvent.VK_LEFT:
					//player_direction = 1;
					Move_LEFT = true;
					break;

				case KeyEvent.VK_RIGHT:
					//player_direction = 2;
					Move_RIGHT = true;
					break;

				case KeyEvent.VK_SPACE:
					if (Main.LASER_MAX_COUNT > laser_list.size()) {
						laser_list.add(new Laser(player_X + Main.PLAYER_PX / 2 - 1, player_Y - 14));
						new SoundEffect("shoot.mp3");
					}
					break;

				case KeyEvent.VK_ESCAPE:
					pause = !pause;
					if (pause) {
						thread.suspend();
					} else if (!pause) {
						thread.resume();
					}
					break;
				}
			}

			public void keyReleased(KeyEvent e) {
				//player_direction = 0;
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					Move_UP = false;
					break;

				case KeyEvent.VK_DOWN:
					Move_DOWN = false;
					break;

				case KeyEvent.VK_LEFT:
					Move_LEFT = false;
					break;

				case KeyEvent.VK_RIGHT:
					Move_RIGHT = false;
					break;
				}
			}
		});
		file_mgr.loadData();
		mainInit();
	}

	@Override
	public void run() {
		while (start) {
			try {
				keyMove();
				gameMgr();
				Thread.sleep(15);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	}
	
	public void mainInit(){
		shop = false;
		start_clicked = false;
		exit_clicked = false;
		bgm_sound = new SoundPlayer("main.mp3", true);
		bgm_sound.start();
	}

	public void gameInit() {
		Player = skin.SetSkin(0);
		G_SCORE = TIME_SCORE_CNT = 0;
		pause = resume_clicked = main_clicked = false;
		//player_direction = 0;
		player_life = 5;
		player_X = 275;
		player_Y = 600;
		bg1Y = 0;
		bg2Y = -800;
		laser_list.clear();
		meteo_list.clear();
		thread = new Thread(this);
		thread.start();
	}

	public void keyMove() {
		if (Move_UP == true) {
			if (player_Y > 0)
				player_Y -= Main.PLAYER_SPEED;
		} else if (Move_DOWN == true) {
			if (player_Y < y_MAX)
				player_Y += Main.PLAYER_SPEED;
		} else if (Move_LEFT == true) {
			if (player_X > 0)
				player_X -= Main.PLAYER_SPEED;
		} else if (Move_RIGHT == true) {
			if (player_X < x_MAX)
				player_X += Main.PLAYER_SPEED;
		}
	}

	public void backgroundMoveDown() {
		if (bg1Y >= 800) {
			bg1Y = 0;
			bg2Y = -800;
		}

		if (bg_refresh % 2 == 0) {
			bg1Y += Main._bgSpeed;
			bg2Y += Main._bgSpeed;
			bg_refresh = 0;
		}
		bg_refresh++;
	}

	public void backgroundDraw(Graphics g) {
		g.drawImage(bg1, 0, bg1Y, null);
		g.drawImage(bg2, 0, bg2Y, null);
	}

	public void laserDraw(Graphics g) {
		for (int i = 0; i < laser_list.size(); i++) {
			Laser l = laser_list.get(i);
			g.drawImage(laser, l.laser_X, l.laser_Y, null);
		}
	}

	public void ObjectCtrl() {
		meteo_spawn++;
		meteo_anim_cnt++;
		TIME_SCORE_CNT++;
		
		if(TIME_SCORE_CNT % 30 == 0) G_SCORE++;

		if (meteo_anim_cnt % 5 == 0) {
			meteo_anim_flag = !meteo_anim_flag;
			meteo_anim_cnt = 0;
		}

		if (meteo_spawn == Main.METEO_SPAWN) {
			meteo_list.add(new Meteorite(random.nextInt(Main.METEO_X_MAX)));
			meteo_spawn = 0;
		}

		for (int i = 0; i < laser_list.size(); i++) {
			Laser pos = laser_list.get(i);
			pos.laser_Y -= Main.LASER_SPEED;
			if (pos.laser_Y <= -30)
				laser_list.remove(i);
		}

		for (int i = 0; i < meteo_list.size(); i++) {
			Meteorite m = meteo_list.get(i);
			m.MoveDown();

			if (m.meteo_Y >= 800) {
				meteo_list.remove(i);
			}

			if (m.crashCheck(player_X, player_Y)) {
				player_life--;
				new SoundEffect("explosive.mp3");
				if (player_life <= 0){
					file_mgr.saveData(G_SCORE + file_mgr.getData());
					start = false;
					mainInit();
				}
				meteo_list.remove(i);
				m = null;
			}

			for (int j = 0; j < laser_list.size(); j++) {
				Laser la = laser_list.get(j);
				if(m == null){
					break;
				}
				
				if (m.meteo_X <= la.laser_X && m.meteo_X + 85 >= la.laser_X
						&& m.meteo_Y + 100 >= la.laser_Y && m.meteo_Y <= la.laser_Y) {
					meteo_list.remove(i);
					laser_list.remove(j);
					G_SCORE += 10;
					new SoundEffect("explosive.mp3");
				}
			}
		}
	}

	public void meteoDraw(Graphics g) {
		for (int i = 0; i < meteo_list.size(); i++) {
			Meteorite m = meteo_list.get(i);
			if (meteo_anim_flag) {
				g.drawImage(meteo1, m.meteo_X, m.meteo_Y, null);
			} else {
				g.drawImage(meteo2, m.meteo_X, m.meteo_Y, null);
			}
		}
	}

	public void mainDraw(Graphics g) {
		g.drawImage(title, 50, 40, null);
		if (!start_clicked) {
			g.drawImage(start_normal_image, btn_x, start_btn_y, null);
		} else {
			g.drawImage(start_pressed_image, btn_x, start_btn_y, null);
		}
		
		if (!shop_clicked) {
			g.drawImage(shop_normal_image, btn_x, shop_btn_y, null);
		} else {
			g.drawImage(shop_pressed_image, btn_x, shop_btn_y, null);
		}
		
		if (!exit_clicked) {
			g.drawImage(exit_normal_image, btn_x, exit_btn_y, null);
		} else {
			g.drawImage(exit_pressed_image, btn_x, exit_btn_y, null);
		}
	}
	
	public void storeDraw(Graphics g) {
		g.setColor(new Color(255, 255, 50));
		g.setFont(coin_font);
		g.drawString("Coin: " + file_mgr.getData(), 240, 650);
		g.drawImage(shop_left_image, 20, 375, null);
		g.drawImage(shop_right_image, 530, 375, null);
		g.drawImage(shop_back_image, 200, 700, null);
	}

	public void pauseMenuDraw(Graphics g) {
		if(!resume_clicked){
			g.drawImage(resume_normal_image, 200, 225, null);
		} else {
			g.drawImage(resume_entered_image, 200, 225, null);
		}
		
		if(!main_clicked){
			g.drawImage(main_normal_image, 200, 325, null);
		} else {
			g.drawImage(main_entered_image, 200, 325, null);
		}
	}

	public void IngameUIDraw(Graphics g) {
		for (int i = 1; i <= player_life; i++) {
			g.drawImage(heart, 420 + 30 * i, 40, null);
		}
		g.setColor(new Color(255, 255, 255));
		g.setFont(font);
		g.drawString("SCORE: " + G_SCORE, 10, 50);
	}

	public void gameMgr() {
		backgroundMoveDown();
		ObjectCtrl();
	}

	public Image paintScreenMgr() {
		Image image = createImage(Main.WIDTH, Main.HEIGHT);
		Graphics image_g = image.getGraphics();
		if (start) {
			backgroundDraw(image_g);
			meteoDraw(image_g);
			laserDraw(image_g);
			image_g.drawImage(Player, player_X, player_Y, null);
			/*if(player_direction==0) image_g.drawImage(Player, player_X, player_Y, null);
			else if(player_direction==1) image_g.drawImage(Player_left, player_X, player_Y, null);
			else if(player_direction==2) image_g.drawImage(Player_right, player_X, player_Y, null);*/
			IngameUIDraw(image_g);
			if (pause) {
				pauseMenuDraw(image_g);
			}
		} else {
			image_g.drawImage(title_background, 0, 0, null);
			if(!shop) mainDraw(image_g);
			else storeDraw(image_g);
		}
		return image;
	}

	public void paint(Graphics g) {
		g.drawImage(paintScreenMgr(), 0, 0, null);
		this.repaint();
	}
}
