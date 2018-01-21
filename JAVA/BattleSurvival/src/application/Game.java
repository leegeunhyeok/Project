package application;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;
import java.util.StringTokenizer;

import javafx.animation.AnimationTimer;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;

public class Game {
	private boolean playing, running;
	private final int playerSize = 20;
	private final int blockSize = 50;
	private final int SPEED = 1;
	
	private double staticX, staticY;
	private double showPosX, showPosY;
	private Player player1, player2;
	private final char MAP[][] = new char[100][100];
	
	// 북, 남, 동, 서
	private boolean moveNorth, moveSouth, moveEast, moveWest;
	private StringTokenizer token;
	private AnimationTimer game;
	private GraphicsContext gc;
	private Reciver reciver;
	private Sender sender;

	
	public Game() {
		//Single play
	}
	
	// Server
	public Game(Socket socket, final int port) {
		staticX = Main.width/2 - playerSize/2; // 플레이어는 항상 화면 중앙에 출력 
		staticY = Main.height/2 - playerSize/2; // 중앙에 출력하기 위한 좌표값 계산작업 
		Random random = new Random();
		final int mapCode = random.nextInt(1);
		int x = 100;//random.nextInt(5000)+1;
		int y = 100;//random.nextInt(500)+1;
		int x2 = 200; //random.nextInt(5000)+1;
		int y2 = 200; //random.nextInt(500)+4501;
		player1 = new Player(x, y); // 0~5000, 0~500
		player2 = new Player(x2, y2); // 0~5000, 4500~5000
		sendInfo(socket);
		
		loadMap("map1.dat");
		
		try {
			reciver = new Reciver(port);
			sender = new Sender(socket.getInetAddress(), 7777);
			playing = true;
			reciver.start();
			sender.start();
		} catch(Exception e) {
			System.out.println("Data stream error");
		}
		start();
	}
	
	// Client
	public Game(Socket socket, final String host, final int port) {
		staticX = Main.width/2 - playerSize/2; // 플레이어는 항상 화면 중앙에 출력 
		staticY = Main.height/2 - playerSize/2; // 중앙에 출력하기 위한 좌표값 계산작업 
		loadMap("map1.dat");
		reciveInfo(socket);
		
		try {
			reciver = new Reciver(7777);
			sender = new Sender(socket.getInetAddress(), port);
			playing = true;
			reciver.start();
			sender.start();
		} catch(Exception e) {
			System.out.println("Data stream error");
		}
		start();
	}
	
	private void start() {
		game = new AnimationTimer() {
			@Override
			public void handle(long now) {
				int x = player1.getX();
				int y = player1.getY();
					
				final int speed = running ? SPEED*2 : SPEED; // 달리기 = 이동속도*2
				if(moveNorth && y-speed >= 0) y-=speed;
				if(moveSouth && y+speed <= 5000) y+=speed;
				if(moveEast && x+speed <= 5000) x+=speed;
				if(moveWest && x-speed >= 0) x-=speed;
				player1.setPos(x, y);
			    draw();
			}
		};
		game.start();
		Main.program.gameStart(gameScene());
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
			e.printStackTrace();
			System.out.println("Map load fail");
		}
	}
	
	private void sendInfo(Socket socket) {
		try {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(player1.getX() + "," + player1.getY() + "," + player2.getX() + "," +player2.getY());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void reciveInfo(Socket socket) {
		try {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			String[] recived = in.readUTF().split(",");
			player1 = new Player(toInt(recived[2]), toInt(recived[3]));
			player2 = new Player(toInt(recived[0]), toInt(recived[1]));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private int toInt(final String str) {
		return Integer.parseInt(str);	
	}
	
	private void draw() {
		int x = player1.getX();
		int y = player1.getY();
		int x2 = player2.getX();
		int y2 = player2.getY();
		
		// 플레이어 위치기준으로 몇번째인덱스부터 출력할지 계산
		int x_ = (int)Math.floor(x/50)-8;
		int y_ = (int)Math.floor(y/50)-6;
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
		gc.setFill(Color.DODGERBLUE);
		gc.setStroke(Color.BLUE);
		gc.fillOval(staticX, staticY, playerSize, playerSize);
		gc.strokeOval(staticX, staticY, playerSize, playerSize);
		
		gc.setFill(Color.CRIMSON);
		gc.setStroke(Color.BROWN);
		gc.fillOval((x2+400-playerSize/2)-x, (y2+280+playerSize/2)-y, playerSize, playerSize);
		gc.strokeOval((x2+400-playerSize/2)-x, (y2+280+playerSize/2)-y, playerSize, playerSize);
		
		
		// Line (Aim, 시야)
		gc.setFill(Color.RED);
		gc.setStroke(Color.RED);
		gc.strokeLine(staticX+playerSize/2, staticY+playerSize/2, showPosX, showPosY);
		gc.fillOval(showPosX-3, showPosY-3, 6, 6);
		gc.strokeOval(showPosX-10, showPosY-10, 20, 20);
	}
	
	private String getPlayers() {
		return player1.getX()+","+player1.getY();
	}
	
	private class Reciver extends Thread {
		private DatagramSocket socket;
		private DatagramPacket packet;
		
		public Reciver(final int port) {
			this.setDaemon(true);
			try {
				socket = new DatagramSocket(port);
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			byte[] buf = new byte[64];
			while(playing) {
				try {
					packet = new DatagramPacket(buf, buf.length);
					socket.receive(packet);
					
					String[] msg = new String(packet.getData()).split(",");
					player2.setPos(toInt(msg[0].trim()), toInt(msg[1].trim()));
				} catch(Exception e) {
					e.printStackTrace();
				} 
			}
		}
	}
	
	private class Sender extends Thread {
		private InetAddress address;
		private DatagramSocket socket;
		private int port;
		
		public Sender(InetAddress address, final int port) {
			this.address = address;
			this.port = port;
			this.setDaemon(true);
			
			try {
				socket = new DatagramSocket();
			} catch (SocketException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			byte[] buf = new byte[64];
			while(playing) {
				try {
					buf = getPlayers().getBytes();
					socket.send(new DatagramPacket(buf, buf.length, address, port));
					Thread.sleep(10);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
