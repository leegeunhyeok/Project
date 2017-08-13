package astro_war;

/*
 * Developer : 이근혁
 * E-Mail : lghlove0509 @ naver.com
 * AstroWar FX Edition
 * 
 * */

import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AstroWar extends Application {
	/* -----게임내 고정값----- */
	private final String VERSION = "1.02";
	private final int btn_width = 200;
	private final int btn_height = 75;
	private final int btn_x = 30;
	private final int start_btn_y = 450;
	private final int shop_btn_y = 550;
	private final int exit_btn_y = 650;
	private final int laser_speed = 9;
	/*---------------------*/
	
	private int player_width; //플레이어 스킨별 폭(px) 저장 변수
	
	private Timeline gameLoop = new Timeline();

	/*----- 게임 내 사용하는 이미지 리소스 -----*/
	private Image mainBackground = new Image("images/main_background.png");
	private Image bg = new Image("images/background.png");

	private Image Player_skin;
	private Image Meteo_1 = new Image("images/meteorite_1.png");
	private Image Meteo_2 = new Image("images/meteorite_2.png");
	private Image Explosion_1 = new Image("images/explosion_1.png");
	private Image Explosion_2 = new Image("images/explosion_2.png");
	private Image Laser = new Image("images/laser.png");

	private Image Title = new Image("images/title.png");
	private Image button_start = new Image("images/button_start.png");
	private Image button_start_pressed = new Image("images/button_start_pressed.png");

	private Image button_shop = new Image("images/button_shop.png");
	private Image button_shop_pressed = new Image("images/button_shop_pressed.png");

	private Image button_exit = new Image("images/button_exit.png");
	private Image button_exit_pressed = new Image("images/button_exit_pressed.png");

	private Image button_resume = new Image("images/button_resume.png");
	private Image button_resume_pressed = new Image("images/button_resume_pressed.png");

	private Image button_main = new Image("images/button_main.png");
	private Image button_main_pressed = new Image("images/button_main_pressed.png");

	private Image button_help = new Image("images/help.png");

	private Image Shield = new Image("images/shield.png");
	private Image Heart = new Image("images/life_heart.png");
	private Image MeteoLaser = new Image("images/meteo_laser.png");
	private Image[] Item_list = new Image[5];

	private Image shop_left_image = new Image("images/left.png");
	private Image shop_right_image = new Image("images/right.png");
	private Image shop_buy_image = new Image("images/button_buy.png");
	private Image shop_already_own = new Image("images/already_own.png");
	/*---------------------*/

	
	/*----- 동적으로 생성되고 삭제되는 게임 오브젝트 관리 -----*/
	private ArrayList<Meteorite> meteo_list = new ArrayList<>();
	private ArrayList<MeteoLaser> mLaser_list = new ArrayList<>();
	private ArrayList<Explosion> Explosion_list = new ArrayList<>();
	private ArrayList<Laser> laser_list = new ArrayList<>();
	private ArrayList<Item> item_list = new ArrayList<>();
	private ArrayList<String> input = new ArrayList<>();
	/*---------------------*/

	private FXSound sound = new FXSound(); //소리 출력 
	private FileManager file_mgr = new FileManager(); //파일 입출력 
	private PlayerSkin skin_mgr = new PlayerSkin(); //플레이어 스킨

	private int[] unlock_state = new int[5];
	private int G_SCORE; //게임 내 점수
	private int G_COIN; //코인
	private int player_skin_number = 0; //현재 플레이어 스킨 번호 (0~4)
	private int player_x, player_y; //플레이어 좌표 x, y
	private int player_life; //플레이어 체력
	private int player_speed; //플레이어 이동속도
	private int player_shoot_speed; //플레이어 발사 속도
	private int player_damage; //플레이어 레이저 데미지
	private int laser_count; //현재 발사하는 레이저 수 (초기: 1)
	private int meteo_time_speed; 
	private int bg1, bg2, bg_refresh; //배경 움직이는 작업을 위한 변수
	private int meteo_spawn, meteo_anim_cnt; 
	private int laser_delay; 
	private int TIME_SCORE_CNT = 0;
	private int shield_time = 0;
	private int slow_time = 0;
	private int meteo_hp;
	private int hit_delay;
	private int destroy_meteo_count = 0;
	private int get_item_count = 0;
	private int shop_show_number;
	
	private boolean inGame = false;
	private boolean pause = false;
	private boolean shop = false;
	private boolean shield = false;
	private boolean slow = false;
	private boolean gameOver = false;
	private boolean meteo_anim_flag = false;
	private boolean start_clicked = false;
	private boolean shop_clicked = false;
	private boolean exit_clicked = false;
	private boolean resume_clicked = false;
	private boolean main_clicked = false;

	/* 시작 */
	@Override
	public void start(Stage theStage) {
		/* 아이템 이미지 로딩 */
		Item_list[0] = new Image("images/life_up_item.png");
		Item_list[1] = new Image("images/damage_up_item.png");
		Item_list[2] = new Image("images/slow_item.png");
		Item_list[3] = new Image("images/shield_item.png");
		Item_list[4] = new Image("images/laser_count_up_item.png");
		
		file_mgr.loadData(); //데이터 불러오기
		unlock_state = file_mgr.getUnlockState();
		for (int i = 0; i < 5; i++)
			G_COIN = file_mgr.getCoin();
		player_skin_number = file_mgr.getSkinNum();

		theStage.setTitle("AstroWar FX Edition " + VERSION);

		Group root = new Group();
		Scene theScene = new Scene(root);
		theStage.setScene(theScene);

		Popup popup = new Popup();
		try {
			popup.getContent().add(FXMLLoader.load(getClass().getResource("Help.fxml")));
		} catch (IOException e2) {
			e2.printStackTrace();
		}

		Canvas canvas = new Canvas(600, 800);
		root.getChildren().add(canvas);

		/* 키보드 이벤트 처리 */
		theScene.setOnKeyPressed(e -> {
			String code = e.getCode().toString();
			if (code.equals("ESCAPE")) {
				if (inGame)
					if (pause) {
						System.out.println("INFO: Resume");
					} else {
						System.out.println("INFO: Pause");
					}
				pause = !pause;
				return;
			}

			if (!input.contains(code))
				input.add(code);
		});

		theScene.setOnKeyReleased(e -> {
			String code = e.getCode().toString();
			input.remove(code);
		});
		/*--------------*/

		/* 마우스 이벤트 처리 */
		theScene.setOnMousePressed(e -> {
			int x = (int) e.getX();
			int y = (int) e.getY();

			if (inGame) {
				if (pause) {
					if (x >= 200 && x <= 400 && y >= 225 && y <= 300) {
						resume_clicked = true;
					} else if (x >= 200 && x <= 400 && y >= 325 && y <= 400) {
						main_clicked = true;
					} else {
						resume_clicked = false;
						main_clicked = false;
					}
				}

			} else {
				if (popup.isShowing()) {
					popup.hide();
				}
				if (!shop) {
					if (x >= btn_x && x <= btn_width + btn_x && y >= start_btn_y && y <= btn_height + start_btn_y) {
						start_clicked = true;
					} else if (x >= btn_x && x <= btn_width + btn_x && y >= shop_btn_y
							&& y <= btn_height + shop_btn_y) {
						shop_clicked = true;
					} else if (x >= btn_x && x <= btn_width + btn_x && y >= exit_btn_y
							&& y <= btn_height + exit_btn_y) {
						exit_clicked = true;
					} else {
						start_clicked = false;
						exit_clicked = false;
					}
				}
			}
		});

		theScene.setOnMouseReleased(e -> {
			int x = (int) e.getX();
			int y = (int) e.getY();
			if (inGame) { //인게임
				if (pause) { //일시정지
					if (resume_clicked && x >= 200 && x <= 400 && y >= 225 && y <= 300) {
						resume_clicked = false;
						pause = false;
					} else if (main_clicked && x >= 200 && x <= 400 && y >= 325 && y <= 400) {
						main_clicked = false;
						pause = false;
						inGame = false;
						mainInit();
					} else {
						resume_clicked = false;
						main_clicked = false;
					}
				} else if (gameOver) { //게임 종료
					if (x >= 200 && x <= 400 && y >= 700 && y <= 775) {
						inGame = false;
						mainInit();
					}
				}
			} else {
				if (!shop) { //상점이 아닐 때 (메인화면)
					if (x >= btn_x && x <= btn_width + btn_x && y >= start_btn_y && y <= btn_height + start_btn_y
							&& start_clicked) {
						//게임 시작
						System.out.println("INFO: start game");
						sound.BGMstop("main");
						start_clicked = false;
						gameInit();
						inGame = true;
					} else if (x >= btn_x && x <= btn_width + btn_x && y >= shop_btn_y && y <= btn_height + shop_btn_y
							&& shop_clicked) {
						//상점 진입
						System.out.println("INFO: shop");
						shop = true;
					} else if (x >= btn_x && x <= btn_width + btn_x && y >= exit_btn_y && y <= btn_height + exit_btn_y
							&& exit_clicked) {
						//게임 종료
						Platform.exit();
					} else if (x >= 525 && x <= 575 && y >= 730 && y <= 780) {
						// 팝업창
						if (popup.isShowing()) {
							popup.hide();
						} else {
							popup.setX(theStage.getX() + 140);
							popup.setY(theStage.getY() + 200);
							popup.show(theStage);
						}
					}
					start_clicked = false;
					shop_clicked = false;
					exit_clicked = false;
				} else { //상점
					if (x >= 20 && x <= 70 && y >= 375 && y <= 425) {
						//이전 스킨 버튼
						if (shop_show_number > 0)
							shop_show_number--;
					} else if (x >= 530 && x <= 580 && y >= 375 && y <= 425) {
						//다음 스킨 버튼
						if (shop_show_number < 4)
							shop_show_number++;
					} else if (x >= 250 && x <= 350 && y >= 520 && y <= 555) {
						//구매하기 버튼
						if (unlock_state[shop_show_number] == 0) {
							if (G_COIN >= skin_mgr.getPrice(shop_show_number)) {
								System.out.println(
										"INFO: Buy player skin (Price: " + skin_mgr.getPrice(shop_show_number) + ")");
								G_COIN = G_COIN - skin_mgr.getPrice(shop_show_number);
								unlock_state[shop_show_number] = 1;
								file_mgr.saveData(G_COIN, player_skin_number, unlock_state);
							}
						}
					} else if (x >= 200 && x <= 400 && y >= 700 && y <= 775) {
						//메인화면으로 돌아가기
						if (unlock_state[shop_show_number] == 1)
							player_skin_number = shop_show_number;
						else
							player_skin_number = 0;
						System.out.println("INFO: Choose skin: " + player_skin_number);
						shop = false;
					}
				}
			}
		});
		/*--------------*/

		gameLoop.setCycleCount(Timeline.INDEFINITE);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// 0.017 = 60 fps//
		/*----게임 루프----*/
		KeyFrame kf = new KeyFrame(Duration.seconds(0.017), event -> {
			KeyMgr();
			ScreenDraw(gc);
		});
		/*--------------*/

		gameLoop.getKeyFrames().add(kf);
		gameLoop.play();
		mainInit();
		theStage.setWidth(606);
		theStage.setHeight(828);
		theStage.setResizable(false);
		theStage.show();
	}

	/* 메인화면, 상점 그리기 */
	public void ScreenDraw(GraphicsContext g) {
		g.clearRect(0, 0, 600, 800); // 화면 지우기 //
		if (inGame) {
			if (gameOver) {
				g.drawImage(bg, 0, 0);
				gameOverDraw(g);
			} else {
				g.drawImage(bg, 0, bg1);
				g.drawImage(bg, 0, bg2);
				if (!pause) {
					backgroundMoveDown();
					ObjectMgr();
					inGameDraw(g);
					inGameUIDraw(g);
				} else {
					inGameDraw(g);
					inGameUIDraw(g);
					pauseMenuDraw(g);
				}
			}
		} else {
			g.drawImage(mainBackground, 0, 0);
			if (!shop) {
				mainScreenDraw(g);
			} else {
				shopDraw(g);
			}
		}
	}

	/* 배경 움직이게 좌표값 조정 */
	public void backgroundMoveDown() {
		if (bg1 >= 800) {
			bg1 = 0;
			bg2 = -800;
		}

		if (bg_refresh % 2 == 0) {
			bg1 += 5;
			bg2 += 5;
			bg_refresh = 0;
		}
		bg_refresh++;
	}

	/* 키보드 입력 제어 */
	public void KeyMgr() {
		if (!pause) {
			if (input.contains("A") && player_x > -20)
				player_x -= player_speed;
			else if (input.contains("D") && player_x < 570)
				player_x += player_speed;
			else if (input.contains("W") && player_y > -20)
				player_y -= player_speed;
			else if (input.contains("S") && player_y < 780)
				player_y += player_speed;
		}
	}

	/* 게임 요소들의 데이터를 관리, 제어 */
	public void ObjectMgr() {
		meteo_anim_cnt++;
		meteo_spawn++;
		laser_delay++;
		TIME_SCORE_CNT++;
		shield_time--;
		slow_time--;
		
		if(hit_delay>0) {
			hit_delay--;
		}

		if (shield_time <= 0) {
			shield = false;
		}

		if (slow_time <= 0) {
			slow = false;
		}

		if (TIME_SCORE_CNT % 40 == 0) {
			G_SCORE++;
		}

		if (meteo_anim_cnt % 5 == 0) {
			meteo_anim_flag = !meteo_anim_flag;
			meteo_anim_cnt = 0;
		}

		if (meteo_spawn % (int) (Math.random() * 70 + 30) == 0) {
			meteo_list.add(new Meteorite((int) (Math.random() * 515) + 1, meteo_hp));
			meteo_spawn = 0;
		}

		if (laser_delay % player_shoot_speed == 0) {
			if (laser_count % 2 == 0) {
				laser_list.add(new Laser(player_x + player_width / 2 - 11, player_y - 14, player_damage));
				laser_list.add(new Laser(player_x + player_width / 2 + 9, player_y - 14, player_damage));

				if (laser_count == 4) {
					laser_list.add(new Laser(player_x + player_width / 2 - 21, player_y - 10, player_damage));
					laser_list.add(new Laser(player_x + player_width / 2 + 19, player_y - 10, player_damage));
				}
			} else {
				laser_list.add(new Laser(player_x + player_width / 2 - 1, player_y - 20, player_damage));
				if (laser_count > 2) {
					laser_list.add(new Laser(player_x + player_width / 2 - 11, player_y - 14, player_damage));
					laser_list.add(new Laser(player_x + player_width / 2 + 9, player_y - 14, player_damage));
					if (laser_count > 4) {
						laser_list.add(new Laser(player_x + player_width / 2 - 21, player_y - 10, player_damage));
						laser_list.add(new Laser(player_x + player_width / 2 + 19, player_y - 10, player_damage));
					}
				}
			}
			laser_delay = 0;
			sound.EffectPlay("shoot");
		}

		/* 레이저 관리 */
		for (int i = 0; i < laser_list.size(); i++) {
			Laser pos = laser_list.get(i);
			pos.setLaserY(pos.getLaserY() - laser_speed);
			if (pos.getLaserY() <= -30)
				laser_list.remove(pos);
		}
		
		for (int i = 0; i < mLaser_list.size(); i++) {
			MeteoLaser mLaser = mLaser_list.get(i);
			mLaser.setMove();
			if(mLaser.getX()>=600 || mLaser.getX()<= -20 || mLaser.getY()>=800) {
				mLaser_list.remove(mLaser);
				continue;
			}
			
			if (mLaser.crashCheck(player_x, player_y, player_width, 40) && hit_delay == 0) {
				if(!shield) {
					hit_delay = 30;
					player_life--;
				}
				mLaser_list.remove(mLaser);
				sound.EffectPlay("hit");
			}
		}

		/* 폭발 이펙트 관리 */
		for (int i = 0; i < Explosion_list.size(); i++) {
			if (Explosion_list.get(i).getDestroy()) {
				Explosion_list.remove(i);
			} else {
				Explosion_list.get(i).setFlag();
			}
		}

		/* 아이템 제어 */
		for (int i = 0; i < item_list.size(); i++) {
			Item temp = item_list.get(i);
			if (temp.crashCheck(player_x, player_y, player_width, 60)) {
				switch (temp.getType()) {
				case 0:
					if (player_life < 10) {
						player_life++;
						System.out.println("INFO: Life +1");
					}
					break;

				case 1:
					if (player_skin_number == 4) {
						System.out.println("INFO: Damage +10*2");
						player_damage += 20;
					} else {
						System.out.println("INFO: Damage +10");
						player_damage += 10;
					}
					break;

				case 2:
					System.out.println("INFO: Meteo speed down");
					slow = true;
					slow_time = 400;
					break;

				case 3:
					System.out.println("INFO: Shield ON");
					shield = true;
					shield_time = 300;
					break;

				case 4:
					if (player_skin_number == 4) {
						if (laser_count < 5) {
							System.out.println("INFO: Laser count +1");
							laser_count++;
						}
					} else if (laser_count < 3) {
						System.out.println("INFO: Laser count +1");
						laser_count++;
					}
					break;
				}
				item_list.remove(temp);
				get_item_count++;
				G_SCORE += 10;
				sound.EffectPlay("item");
			}
		}

		/* 운석 제어(플레이어와 충돌, 레이저와 충돌) */
		for (int i = 0; i < meteo_list.size(); i++) {
			Meteorite m = meteo_list.get(i);

			if (m.shootPermission()) {
				mLaser_list.add(new MeteoLaser(player_x + (player_width / 2), player_y + 40, m.x + 40, m.y + 50));
			}

			if (slow) {
				m.MoveDown(2);
			} else
				m.MoveDown(meteo_time_speed);

			if (m.y >= 800) {
				meteo_list.remove(m);
			}

			if (m.crashCheck(player_x, player_y, player_width, 60)) {
				System.out.print("INFO: Crash ");
				if (!shield && hit_delay == 0) {
					hit_delay = 30;
					System.out.println("(Life -1)");
					player_life--;
				} else {
					System.out.println("(Shield skill : Score +40)");
					G_SCORE += 40;
				}
				sound.EffectPlay("crash");
				meteo_list.remove(m);
				m = null;
			}

			for (int j = 0; j < laser_list.size(); j++) {
				Laser la = laser_list.get(j);
				if (m == null) {
					break;
				}

				if (m.crashCheck(la.getLaserX(), la.getLaserY(), 3, 20)) {
					m.attacked(la.getDamage());
					laser_list.remove(la);
					if (!(m.getLiveStatus())) {
						int item = m.dropItem();
						if (item != 0) {
							System.out.println("INFO: " + item + " Item Drop");
							item_list.add(new Item(item - 1, m.x + 30, m.y + 60));
							// 아이템 갯수가 5개를 초과하면 오래된 아이템 1개 삭제//
							if (item_list.size() > 5) {
								item_list.remove(0);
							}
						}
						Explosion_list.add(new Explosion(m.x, m.y));
						meteo_list.remove(m);
						G_SCORE += 20 * laser_count * -(meteo_time_speed - 1);
						meteo_hp += 2;

						if (G_SCORE >= 3000 && meteo_time_speed > -1) {
							System.out.println("INFO: ## Meteo speed up ## (Default Score x2)");
							meteo_time_speed--;
						} else if (G_SCORE >= 7500 && meteo_time_speed > -2) {
							System.out.println("INFO: ## Meteo speed up ## (Default Score x4)");
							meteo_time_speed--;
						}

						destroy_meteo_count++;
						sound.EffectPlay("explosion");
					}
				}
			}
		}

		if (player_life <= 0) {
			System.out.println("INFO: Game Over");
			int temp_coin = G_SCORE + destroy_meteo_count + get_item_count;
			System.out.print("INFO: Get Coin: " + temp_coin);
			if (player_skin_number == 2) {
				System.out.println(" + Skin Bonus: " + (temp_coin * 20) / 100);
				file_mgr.saveData(G_COIN + temp_coin + (temp_coin * 20) / 100, player_skin_number, unlock_state);
			} else {
				System.out.println("");
				file_mgr.saveData(G_COIN + temp_coin, player_skin_number, unlock_state);
			}
			G_COIN = file_mgr.getCoin();
			gameOver = true;
		}
	}

	/* 메인화면 그리기 */
	public void mainScreenDraw(GraphicsContext g) {
		g.drawImage(Title, 50, 40);
		if (start_clicked) {
			g.drawImage(button_start_pressed, btn_x, start_btn_y);
		} else {
			g.drawImage(button_start, btn_x, start_btn_y);
		}

		if (shop_clicked) {
			g.drawImage(button_shop_pressed, btn_x, shop_btn_y);
		} else {
			g.drawImage(button_shop, btn_x, shop_btn_y);
		}

		if (exit_clicked) {
			g.drawImage(button_exit_pressed, btn_x, exit_btn_y);
		} else {
			g.drawImage(button_exit, btn_x, exit_btn_y);
		}
		g.drawImage(button_help, 525, 730);
	}

	/* 상점 그리기 */
	public void shopDraw(GraphicsContext g) {
		g.setFill(Color.YELLOW);
		g.setFont(new Font(30));
		g.setStroke(Color.GHOSTWHITE);
		g.fillText("Coin: " + G_COIN, 220, 690);
		g.strokeText("Coin: " + G_COIN, 220, 690);
		if (unlock_state[shop_show_number] == 1) {
			g.drawImage(shop_already_own, 250, 320);
		} else {
			g.drawImage(shop_buy_image, 250, 520);
			if (skin_mgr.getPrice(shop_show_number) == 0) {
				g.fillText("가격: -----", 210, 500);
				g.strokeText("가격: -----", 210, 500);
			} else {
				g.fillText("가격: " + skin_mgr.getPrice(shop_show_number), 210, 500);
				g.strokeText("가격: " + skin_mgr.getPrice(shop_show_number), 210, 500);
			}
		}

		g.setFill(Color.GHOSTWHITE);
		g.fillText("" + (shop_show_number + 1), 288, 80);
		g.setFont(new Font(24));
		if (shop_show_number == 1) {
			g.fillText(skin_mgr.getInfo(shop_show_number), 225, 300);
		} else if (shop_show_number == 4) {
			g.fillText(skin_mgr.getInfo(shop_show_number), 110, 250);
		} else {
			g.fillText(skin_mgr.getInfo(shop_show_number), 216, 300);
		}

		g.fillRect(250, 365, 100, 100);
		if (shop_show_number == 4)
			g.drawImage(skin_mgr.getSkin(shop_show_number), 265, 370);
		else
			g.drawImage(skin_mgr.getSkin(shop_show_number), 275, 380);
		g.drawImage(shop_left_image, 20, 375);
		g.drawImage(shop_right_image, 530, 375);
		g.drawImage(button_main, 200, 700);
	}

	/* 게임내의 모든 데이터를 그리는 메소드 */
	public void inGameDraw(GraphicsContext g) {
		/* 운석 그리기 */
		for (int i = 0; i < meteo_list.size(); i++) {
			Meteorite m = meteo_list.get(i);
			if (meteo_anim_flag) {
				g.drawImage(Meteo_1, m.x, m.y);
			} else {
				g.drawImage(Meteo_2, m.x, m.y);
			}

			float per = (float) m.getHP() / m.getMaxHP();
			g.setFill(Color.GREENYELLOW);
			g.fillRect(m.x, m.y - 10, per * 85, 10);
		}

		/* 폭발 이펙트 그리기 */
		for (int i = 0; i < Explosion_list.size(); i++) {
			Explosion ex = Explosion_list.get(i);
			if (ex.getFlag()) {
				g.drawImage(Explosion_2, ex.getX(), ex.getY() + 10);
			} else {
				g.drawImage(Explosion_1, ex.getX(), ex.getY() + 10);
			}
		}

		/* 아이템 그리기 */
		for (int i = 0; i < item_list.size(); i++) {
			Item temp = item_list.get(i);
			g.drawImage(Item_list[temp.getType()], temp.x, temp.y);
		}

		/* 레이저 그리기 */
		for (int i = 0; i < laser_list.size(); i++) {
			Laser l = laser_list.get(i);
			g.drawImage(Laser, l.getLaserX(), l.getLaserY());
		}

		for (int i = 0; i < mLaser_list.size(); i++) {
			MeteoLaser ml = mLaser_list.get(i);
			g.drawImage(MeteoLaser, ml.getX(), ml.getY());
		}

		/* 플레이어 그리기 */
		g.drawImage(Player_skin, player_x, player_y);
		if (shield)
			g.drawImage(Shield, player_x - (64 - player_width) / 2, player_y);
	}

	/* 게임 내부 UI 그리기 */
	public void inGameUIDraw(GraphicsContext g) {
		/* 생명(하트) 그리기 */
		for (int i = 0; i < player_life; i++) {
			g.drawImage(Heart, 550 - (i * 30), 16);
		}

		/* 점수, 공격력 그리기 */
		g.setFont(new Font(30));
		g.setStroke(Color.RED);
		g.setFill(Color.YELLOW);
		g.fillText("점수 : " + G_SCORE, 24, 40);
		g.strokeText("점수 : " + G_SCORE, 24, 40);
		g.setFont(new Font(24));
		g.setStroke(Color.WHITE);
		g.fillText("공격력: " + player_damage, 24, 64);
	}

	/* 일시정지 메뉴 그리기 */
	public void pauseMenuDraw(GraphicsContext g) {
		if (resume_clicked) {
			g.drawImage(button_resume_pressed, 200, 225);
		} else {
			g.drawImage(button_resume, 200, 225);
		}

		if (main_clicked) {
			g.drawImage(button_main_pressed, 200, 325);
		} else {
			g.drawImage(button_main, 200, 325);
		}
	}

	/* 게임오버 그리기 */
	public void gameOverDraw(GraphicsContext g) {
		g.setFont(new Font(30));
		g.setStroke(Color.RED);
		g.setFill(Color.BLACK);
		g.fillRect(100, 80, 400, 340);
		g.setFill(Color.WHITE);
		g.fillText("[ 게임오버 ]", 220, 120);
		g.setFont(new Font(24));
		g.fillText("파괴한 운석 수: " + destroy_meteo_count, 160, 160);
		g.fillText("획득한 아이템 수: " + get_item_count, 160, 200);
		g.fillText("점수: " + G_SCORE, 160, 240);
		g.fillText("운석 수 + 아이템 수 + 점수", 160, 320);
		g.fillText("획득 코인: " + (G_SCORE + destroy_meteo_count + get_item_count), 160, 360);
		if (player_skin_number == 2) {
			g.fillText("추가 코인: " + ((G_SCORE + destroy_meteo_count + get_item_count) * 20) / 100, 160, 400);
		} else {
			g.fillText("추가 코인: 0", 160, 400);
		}
		g.drawImage(button_main, 200, 700);
	}

	/* 메인화면 진입 시 초기화(버튼 플래그 변수) */
	public void mainInit() {
		sound.BGMstop("game");
		start_clicked = false;
		shop_clicked = false;
		exit_clicked = false;
		shop = false;
		/* BGM 재생 */
		sound.BGMPlay("main");
	}

	/* 게임 시작 전 좌표, 점수등 초기화 */
	public void gameInit() {
		player_width = skin_mgr.getWidth(player_skin_number);
		Player_skin = skin_mgr.getSkin(player_skin_number);
		player_speed = skin_mgr.getMoveSpeed(player_skin_number);
		player_shoot_speed = skin_mgr.getShootSpeed(player_skin_number);
		player_damage = skin_mgr.getDamage(player_skin_number);
		player_life = 5;
		player_x = 275;
		player_y = 600;
		laser_count = 1;
		meteo_hp = 16;
		hit_delay = 0;
		pause = false;
		shield = false;
		slow = false;
		gameOver = false;
		bg1 = 0;
		bg2 = -800;
		TIME_SCORE_CNT = 0;
		meteo_time_speed = 0;
		destroy_meteo_count = 0;
		get_item_count = 0;
		item_list.clear();
		laser_list.clear();
		meteo_list.clear();
		mLaser_list.clear();
		Explosion_list.clear();
		G_SCORE = 0;
		sound.BGMPlay("game");
	}

	/* 프로그램 종료 */
	@Override
	public void stop() {
		file_mgr.saveData(G_COIN, player_skin_number, unlock_state);
		gameLoop.stop();
		try {
			sound.BGMstop("main");
			sound.BGMstop("game");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}