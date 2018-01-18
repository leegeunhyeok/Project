package application;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.net.Socket;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class Game {
	private boolean running;
	private final int playerSize = 20;
	private final int blockSize = 50;
	private final int SPEED = 1;
	
	private double staticX, staticY;
	private double x, y;
	private double x2, y2;
	private double showPosX, showPosY;
	private final char MAP[][] = new char[100][100];
	
	// 북, 남, 동, 서
	private boolean moveNorth, moveSouth, moveEast, moveWest;
	
	private DataInputStream in;
	private DataOutputStream out;
	private GraphicsContext gc;
	
	@SuppressWarnings("null")
	public Game(Socket socket) {
		loadMap("map1.dat");
		staticX = Main.width/2 - playerSize/2;
		staticY = Main.height/2 - playerSize/2;
		x = y = x2 = y2 = 0;
		if(socket == null) {
			try {
				in = new DataInputStream(socket.getInputStream());
				out = new DataOutputStream(socket.getOutputStream());
				Main.program.gameStart(null);
			
				AnimationTimer game = new AnimationTimer() {
					@Override
					public void handle(long now) {
						final int speed = running ? SPEED*2 : SPEED; // 달리기 = 이동속도*2
						if(moveNorth && y-speed >= 0) y-=speed;
						if(moveSouth && y+speed <= 5000) y+=speed;
						if(moveEast && x+speed <= 5000) x+=speed;
						if(moveWest && x-speed >= 0) x-=speed;
				    	draw();
					}
				};
				game.start();
				
			} catch(Exception e) {
				Main.program.changeScene(SceneCode.ERROR);
			}
		} else {
			System.out.println("Single play");
		}
	}
	
	private Scene gameScene() {
		Group root = new Group();
		Canvas canvas = new Canvas(Main.width, Main.height);
		gc = canvas.getGraphicsContext2D();
		root.getChildren().add(canvas);
		Scene scene = new Scene(root);
		
		// 키보드 키가 눌렸을 때 
		scene.setOnKeyPressed((event)->{
		    switch (event.getCode()) {
		    	case W:    
                    moveNorth = true;
                    break;
                    	
                case A: 
                    moveWest = true;
                    break;
                    	
                case S:
                    moveSouth = true;
                    break;
                    	
                case D: 
                    moveEast = true;
                    break;
                    
                case SHIFT: 
                    running = true;
                    break;
                    
                //case ESCAPE:
            }
		});
		
		// 키보드 키가 때졌을 때 
		scene.setOnKeyReleased((event)->{
		    switch (event.getCode()) {
		    case W:    
		    	moveNorth = false;
                break;
                    	
            case A: 
                moveWest = false;
                break;
                    	
            case S:
                moveSouth = false;
                break;
                    	
            case D: 
                moveEast = false;
                break;
                
            case SHIFT:
            	running = false;
            	break;
            }
		    draw();
		});
		
		// 마우스 이동 이벤트
		scene.setOnMouseMoved((event)->{
			showPosX = event.getX();
			showPosY = event.getY();
		});
		
		// 마우스 드래그 이벤트(클릭 + 이동)
		scene.setOnMouseDragged((event)->{
			showPosX = event.getX();
			showPosY = event.getY();
			if(MouseButton.PRIMARY == event.getButton()) {
				System.out.println("Shoot"); // 마우스 첫번째 키: 발사 
			}
		});
		return scene;
	}
	
	// 맵 로딩
	private void loadMap(final String name) {
		File target = new File("map/" + name);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(target));
			String s;
			int line = 0;
			while((s = reader.readLine()) != null) {
				for(int i=0; i<100; i++) MAP[line][i] = s.charAt(i);
				line++;
			}
			System.out.println("Map load done");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Map load fail");
		}
	}
	
	private void draw() {
		// 플레이어 위치기준으로 몇번째인덱스부터 출력할지 계산
		int x_ = (int)Math.floor(x/800 * 16)-8;
		int y_ = (int)Math.floor(y/600 * 12)-6;
		for(int height=0; height<13; height++) { // height block: 12 +1
			for(int width=0; width<25; width++) { // width block: 16 +1
				try {
					char tempBlock = MAP[y_+height][x_+width];
					if(tempBlock == '0') { // 잔디 
						gc.setFill(Color.YELLOWGREEN);
						gc.setStroke(Color.GREEN);
					} else if(tempBlock == '1') { // 바위  
						gc.setFill(Color.LIGHTGRAY);
						gc.setStroke(Color.GRAY);
					} else if(tempBlock == '2') { // 모래 
						gc.setFill(Color.BEIGE);
						gc.setStroke(Color.SANDYBROWN);
					}
				} catch(ArrayIndexOutOfBoundsException e) {
					gc.setFill(Color.DODGERBLUE);
					gc.setStroke(Color.ALICEBLUE);
				}
				
				double drawX = (width*blockSize-(x%50));
				double drawY = (height*blockSize-(y%50));
				gc.fillRect(drawX, drawY, blockSize, blockSize);
				gc.strokeRect(drawX, drawY, blockSize, blockSize);
			}
		}
		
		// 플레이어 
		gc.setFill(Color.BEIGE);
		gc.setStroke(Color.BROWN);
		gc.fillOval(staticX, staticY, playerSize, playerSize);
		gc.strokeOval(staticX, staticY, playerSize, playerSize);
		
		int circleSize = 600; // 원 크기(지름)
		//gc.setFill(Color.RED);
		gc.setStroke(Color.BLUE);
		gc.strokeOval((x2+400-circleSize/2)-x, (y2-300+circleSize/2)-y, circleSize, circleSize);
		
		
		
		// Line (Aim, 시야)
		gc.setFill(Color.RED);
		gc.setStroke(Color.RED);
		gc.strokeLine(staticX+playerSize/2, staticY+playerSize/2, showPosX, showPosY);
		gc.fillOval(showPosX-3, showPosY-3, 6, 6);
		gc.strokeOval(showPosX-10, showPosY-10, 20, 20);
	}
}
