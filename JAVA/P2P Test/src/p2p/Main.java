package p2p;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class Main extends JFrame {
	private JTextField ip;
	private JTextField port;
	private JTextField serv_port;
	private JButton server_open;
	private JButton connect;
	private JButton disconnect;
	private JTextArea log;
	
	private Reciver reciver = null;
	private DataOutputStream out;
	private boolean connected = false;

	private Socket socket = null;	
	private ServerSocket server = null;
	
	public Main() throws Exception{
		init();
	}
	
	// 프레임 생성 및 레이아웃 설정, 이벤트 처리
	private void init() {
		setTitle("P2P Test");
		setSize(300, 400);
		setResizable(false);
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ip = new JTextField(9);
		port = new JTextField(4);
		serv_port = new JTextField(5);
		
		server_open = new JButton("Start");
		server_open.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					int _port = Integer.parseInt(serv_port.getText()); // 입력된 텍스트를 숫자로 변환
					server(_port); // 입력한 포트 번호로 서버 시작
				} catch(NumberFormatException e) { // 변환 실패
					logger("Port error");
				}
			}
		});
		
		connect = new JButton("Connect");
		connect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					int _port = Integer.parseInt(port.getText()); // 숫자 변환
					String _ip = ip.getText();
					if(!_ip.equals("")) { //공백이 아닐경우 요청 시도
						client(_ip, _port);
					} else {
						logger("You can't connect to server");
					}
				} catch(NumberFormatException e) { // 숫자 변환 실패
					logger("Error in port");
				}	
			}
		});
		
		disconnect = new JButton("Disconnect");
		disconnect.addActionListener(new ActionListener() { // 강제 접속끊기
			@Override
			public void actionPerformed(ActionEvent arg0) { 
				disconnect();
			}
		});
		
		log = new JTextArea();
		JScrollPane scroll = new JScrollPane(log); // 내용이 많아지면 스크롤이 가능하도록
		log.setEditable(false);
		
		JPanel client = new JPanel();
		JPanel server = new JPanel();
		JPanel top = new JPanel();
		
		JPanel menu = new JPanel(new GridLayout(2, 1));
		
		server.add(serv_port);
		server.add(server_open);
		server.setBorder(new TitledBorder(new EtchedBorder(), "Server"));
		top.add(disconnect);
		top.add(server);
		menu.add(top);
		
		client.add(ip);
		client.add(new JLabel(":"));
		client.add(port);
		client.add(connect);
		client.setBorder(new TitledBorder(new EtchedBorder(), "Client"));
		menu.add(client);
		menu.setBorder(new TitledBorder(new EtchedBorder(), "Menu"));
		
		log.setBorder(new TitledBorder(new EtchedBorder(), "Log"));
		
		JTextField input = new JTextField(3);
		input.addKeyListener(new KeyAdapter() { // 키 리스너
			@Override
			public void keyPressed(KeyEvent e) {
				input.setText("");
				if(connected) { // 연결되어있으면 상대에게 데이터 전송
					try {
						out.writeUTF(e.getKeyChar() + ""); 
					} catch(Exception e1) {
						disconnect();
					}
				}				
			}
		});
		add(scroll, BorderLayout.CENTER);
		add(menu, BorderLayout.SOUTH);
		add(input, BorderLayout.NORTH);
		setVisible(true);
	}
	
	// 클라이언트 소켓 생성 및 요청
	private void client(String ip, int port) {
		Thread t = new Thread(new Runnable() { // 별도의 스레드에서 연결작업 수행
			@Override
			public void run() {
				setAllenabled(false);
				try {
					socket = new Socket(ip, port);
					logger("Connected!");
					
					InputStream in = socket.getInputStream(); // Input 스트림 가져오기 (수신)
					out = new DataOutputStream(socket.getOutputStream()); // Output 스트림 가져오기 (전송)

					reciver = new Reciver(new DataInputStream(in));
					reciver.start();
					connected = true; // 연결됬을경우 true
				} catch(Exception e) {
					disconnect();
				}
			}
		});
		t.setDaemon(true); // 데몬스레드 
		t.start(); // 서버 연결방식도 거의 동일합니다
	}
	
	// 서버 소켓 생성 및 요청 대기
	private void server(int port) {
		Thread t = new Thread(new Runnable() {	
			@Override
			public void run() {
				setAllenabled(false);
				try {
					server = new ServerSocket(port);
					logger("Waiting client..");
					socket = server.accept();
					logger("Client connected!");
					
					InputStream in = socket.getInputStream();
					out = new DataOutputStream(socket.getOutputStream());

					reciver = new Reciver(new DataInputStream(in));
					reciver.start();
					connected = true; // 연결됬을경우 true
				} catch(Exception e) {
					disconnect();
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	// 모든 입력 컴포넌트 Disable, Enable
	private void setAllenabled(boolean enabled) {
		serv_port.setEnabled(enabled);
		server_open.setEnabled(enabled);
		ip.setEnabled(enabled);
		port.setEnabled(enabled);
		connect.setEnabled(enabled);	
	}
	
	// 로그 남기기
	public void logger(String msg) {
		log.append(msg + System.lineSeparator());
		log.setCaretPosition(log.getDocument().getLength()); // 맨 아래로 스크롤
	}
	
	// 소켓, 서버소켓 닫기
	private void disconnect() {
		// 소켓
		try {
			connected = false;
			setAllenabled(true);
			socket.close();
			logger("Disconnected");
		} catch (Exception e) {
			logger("Disconnect failed");
		}
		
		// 서버소켓
		try {
			server.close();
			logger("Server closed");
		} catch (Exception e) {
			logger("Server close error");
		}
	}
	
	// 데이터 수신 클래스
	class Reciver extends Thread {
		private DataInputStream in = null;
		
		public Reciver(DataInputStream in) {
			this.in= in; // InputStream 셋팅
		}
		
		@Override
		public void run() {
			while(in != null) {
				try {
					String s = in.readUTF(); // 읽어오기
					logger("Received: " + s);
				} catch(Exception e) {
					disconnect();
				}
			}
		}
	}
	
	// Main
	public static void main(String[] args) {
		try {
			new Main(); 
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

}
