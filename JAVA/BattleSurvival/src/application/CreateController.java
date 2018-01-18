package application;

import java.net.Socket;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class CreateController {
	@FXML TextField portField;
	@FXML Text portError;
	@FXML Button createBtn;
	@FXML ImageView backBtn;
	
	Socket socket;
	
	@FXML
	private void initialize() {
		backBtn.setOnMouseClicked((e)->{;
			Main.program.changeScene(SceneCode.MAIN);
		});
		
		backBtn.setOnMouseEntered((e)->{
			backBtn.setOpacity(0.5);
		});
		
		backBtn.setOnMouseExited((e)->{
			backBtn.setOpacity(1);
		});
		
		createBtn.setOnAction((event)->{
			String text = portField.getText();
			try {
				int port = Integer.parseInt(text);
				portError.setVisible(false);
				createServer(port);
			} catch(NumberFormatException e) {
				portError.setVisible(true);
			}
		});
	}
	
	// 서버 소켓작업은 다른 스레드에서 진행
	private void createServer(final int port) {
		Main.program.changeScene(SceneCode.LOADING);
		Main.program.setLoadingMsg("클라이언트 접속 대기 중..");
		socket = null;
		Connection conn = new Connection(false);
		Thread thread = new Thread(()-> {
			socket = conn.createServer(port);
			System.out.println("연결 됨.");
		});
		thread.setDaemon(true);
		thread.start();
	}
}
