package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	private final String VERSION = "v1.0";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			System.out.println("·Îµù Áß..");
			Parent root = FXMLLoader.load(this.getClass().getResource("Main.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Graph Game " + VERSION);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
