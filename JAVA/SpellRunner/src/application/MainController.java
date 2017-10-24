package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/* 메인 Scene 컨트롤러 */
public class MainController implements Initializable {
	@FXML Button create_btn;
	@FXML Button join_btn;
	@FXML Button info_btn;
	@FXML Button exit_btn;
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		/* 버튼 이벤트 처리 */
		create_btn.setOnAction((e)->{
			Stage n = (Stage)((Node)e.getSource()).getScene().getWindow();
			n.setScene(RunApp.createScene);
		});
		
		join_btn.setOnAction((e)->{
			Stage n = (Stage)((Node)e.getSource()).getScene().getWindow();
			n.setScene(RunApp.joinScene);
		});
		
		info_btn.setOnAction((e)->{
			Stage n = (Stage)((Node)e.getSource()).getScene().getWindow();
			n.setScene(RunApp.infoScene);
		});
		
		exit_btn.setOnAction((e)->{
			System.exit(0);
		});
	}
}
