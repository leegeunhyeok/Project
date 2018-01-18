package application;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class JoinController {
	@FXML ImageView backBtn;
	
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
	}
}
