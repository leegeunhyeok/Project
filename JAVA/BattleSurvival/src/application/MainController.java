package application;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MainController {
	private final String url = "../sound/hover.mp3";
	private final double selectedOpacity = 0.3;
	@FXML private Pane create;
	@FXML private Pane join;
	@FXML private Pane single;
	@FXML private Pane exit;
	
	@FXML
	private void initialize() {
	    create.setOnMouseEntered((e)->{
	    	create.setOpacity(selectedOpacity);
	    });
	    
	    create.setOnMouseClicked((e)->{
	    	Main.program.playClickSound();
	    	Main.program.changeScene(SceneCode.CREATE_SERVER);
	    });
	    
	    join.setOnMouseEntered((e)->{
	    	join.setOpacity(selectedOpacity);
	    });
	    
	    join.setOnMouseClicked((e)->{
	    	Main.program.playClickSound();
	    	Main.program.changeScene(SceneCode.JOIN);
	    });
	    
	    single.setOnMouseEntered((e)->{
	    	single.setOpacity(selectedOpacity);
	    });
	    
	    single.setOnMouseClicked((e)->{
	    	Main.program.playClickSound();
	    	System.out.println("Single Play");
	    });
	    
	    exit.setOnMouseEntered((e)->{
	    	exit.setOpacity(selectedOpacity);
	    });
	    
	    exit.setOnMouseClicked((e)->{
	    	System.exit(0);
	    });
	}
    
    public void exited(MouseEvent event) {
    	create.setOpacity(0.0);
    	join.setOpacity(0.0);
    	single.setOpacity(0.0);
    	exit.setOpacity(0.0);
    }
}
