package application;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class MainController {
	private final double selectedOpacity = 0.1;
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
	    	System.out.println("Create server");
	    });
	    
	    join.setOnMouseEntered((e)->{
	    	join.setOpacity(selectedOpacity);
	    });
	    
	    join.setOnMouseClicked((e)->{
	    	System.out.println("Join server");
	    });
	    
	    single.setOnMouseEntered((e)->{
	    	single.setOpacity(selectedOpacity);
	    });
	    
	    single.setOnMouseClicked((e)->{
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
