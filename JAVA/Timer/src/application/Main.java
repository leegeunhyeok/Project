package application;

import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application implements Runnable {
	private long sec; // start ~ stop 까지의 초(sec) 저장변수 
	private Thread t;
	private Main mainClass; // 해당 클래스의 인스턴스를 저장하는 변수 
	private Scene main; // 타이머가 있는 화면 
	private Font textFont; // 글자 폰트 
	private Font timeFont; // 숫자 폰트 
	private boolean start; // 시작 여부 
	@FXML Text flow_time; // 시간 
	@FXML ImageView background; // 배경 
	@FXML Button start_btn; // 시작버튼
	@FXML Button stop_btn; // 정지버튼 

	@Override
	public void start(Stage primaryStage) {
		mainClass = this; // 자신 객체를 저장 
		try {
			Scene loading = new Scene(new Pane(), 500, 400); // 크기는 가로 500, 세로 400 픽셀 
			primaryStage.setScene(loading); //빈 화면으로 화면 설정 
			primaryStage.setResizable(false); // 창 크기 변경 불가능 
			primaryStage.sizeToScene(); // Scene의 크기에 알맞게 창 크기 재조정 
			primaryStage.setTitle("Deemo Timer"); // 타이틀 제목 설정 
			primaryStage.show(); // 창 보이기 

			// 이미지 로딩 등 작업을 완료하지 못했을 경우 프로그램 종료 
			if (!Init()) {
				System.exit(0);
			}
			
			// 정상적으로 완료되었으면 타이머 화면 출력 
			primaryStage.setScene(main);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	// 이미지, 폰트 등 초기 작업 
	private boolean Init() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml")); // fxml 파일을 로딩 
			loader.setController(this); // 컨트롤러를 자신으로 설정 
			Parent root = (Parent) loader.load(); 
			main = new Scene(root, 500, 400); // 크기 500, 400
			textFont = Font.loadFont(this.getClass().getResource("../font/word.ttf").toExternalForm(), 28); // 폰트 이름, 28pt
			timeFont = Font.loadFont(this.getClass().getResource("../font/number.ttf").toExternalForm(), 72); // 폰트 이름, 72pt

			background.setImage(new Image(getClass().getResource("../image/background.png").toString())); // 이미지 불러오기 

			start_btn.setFont(textFont); // 폰트설정 
			start_btn.setOnAction(new EventHandler<ActionEvent>() { // 시작버튼을 눌렀을 때 
				@Override
				public void handle(ActionEvent event) {
					start = true; // 반복 조건 true 
					start_btn.setDisable(true); // 시작버튼 비활성화 
					t = new Thread(mainClass); // 타이머 쓰레드 생성 
					t.setDaemon(true); 
					t.start(); // 쓰레드 시작 
				}
			});

			stop_btn.setFont(textFont);
			stop_btn.setOnAction(new EventHandler<ActionEvent>() { // 정지버튼을 눌렀을 때 
				@Override
				public void handle(ActionEvent event) {
					if(t != null) {
						t.interrupt(); // 쓰레드에 인터럽트 (종료)
					}
				}
			});
			flow_time.setFont(timeFont);
			return true; // 모든 작업이 끝났으면 true 
		} catch (Exception e) {
			return false; // 문제가 있으면 false 
		}
	}

	@Override
	public void run() {
		sec = 0; // 시작 시 0초부터 카운트 시작 
		while (start) {
			try {
				TimeUnit.SECONDS.sleep(1); // 1초 대기 
				sec++; // 1증가 
				flow_time.setText(getTime(sec)); // 누적된 초를 시:분:초 로 출력 
			} catch (InterruptedException e) {
				start_btn.setDisable(false);
				start = false; // 인터럽트 발생 시 반복 종료
			}
		}
	}

	// 초를 시, 분, 초로 변환 
	private String getTime(long time) {
		int hour = (int) (time / 3600);
		int min = (int) (time % 3600 / 60);
		int sec = (int) (time % 60);
		return String.format("%02d : %02d : %02d", hour, min, sec);
	}

	// 메인 
	public static void main(String[] args) {
		launch(args);
	}
}
