package application;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

public class ErrorController {
	@FXML Text message;
	@FXML ImageView backBtn;
	
	@FXML
	private void initialize() {
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
	
	public void setMsg(final String msg) {
		message.setText(msg);
	}
}
