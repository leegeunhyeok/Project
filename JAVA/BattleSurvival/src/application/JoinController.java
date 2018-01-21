package application;

import java.net.Socket;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class JoinController {
	@FXML TextField hostField;
	@FXML TextField portField;
	@FXML Text portError;
	@FXML Button connectBtn;
	@FXML ImageView backBtn;
	
	Socket socket;
	
	@FXML
	private void initialize() {
		connectBtn.setOnAction((event)->{
			String host = hostField.getText();
			String text = portField.getText();
			
			try {
				int port = Integer.parseInt(text);
				portError.setVisible(false);
				joinServer(host, port);
			} catch(NumberFormatException e) {
				portError.setVisible(true);
			}
		});
		
		backBtn.setOnMouseClicked((e)->{
			Main.program.playClickSound();
			Main.program.changeScene(SceneCode.MAIN);
		});
		
		backBtn.setOnMouseEntered((e)->{
			backBtn.setOpacity(0.5);
		});
		
		backBtn.setOnMouseExited((e)->{
			backBtn.setOpacity(1);
		});
	}
	
	private void joinServer(final String host, final int port) {
		socket = null;
		Connection conn = new Connection(false);
		Main.program.changeScene(SceneCode.LOADING);
		Main.program.setLoadingMsg("서버에 접속 중..", conn);
		Thread thread = new Thread(()-> {
			socket = conn.connectServer(host, port);
			if(socket != null) {
				Platform.runLater(new Runnable() {
	                @Override 
	                public void run() {
	                	Game game = new Game(socket, host, port);
	                }
	            });
			}
		});
		thread.setDaemon(true);
		thread.start();
	}
}
