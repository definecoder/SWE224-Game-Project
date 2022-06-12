package application;
	
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import view.ViewManager;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			ViewManager manager = new ViewManager();
			primaryStage = manager.getMainStage();
			primaryStage.setTitle("Spacer Runner");
			Image gameIcon = new Image("model/resources/gameIcon.png");
			primaryStage.getIcons().add(gameIcon);
			primaryStage.show();
			//primaryStage.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
