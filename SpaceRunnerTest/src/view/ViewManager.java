package view;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.stage.Stage;
import model.SpaceRunnerButton;

public class ViewManager {
	
	private static final int HEIGHT = 600;
	private static final int WIDTH = 800;
	
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	public ViewManager() {
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		createButtons();
		createBackground();
	}
	
	public Stage getMainStage() {
		return mainStage;
	}
	
	private void createButtons() {
		SpaceRunnerButton button = new SpaceRunnerButton("TAWHID");
		mainPane.getChildren().add(button);
		
		button.setLayoutX(300);
		button.setLayoutY(300);
	}
	
	private void createBackground() {
		
		Image backgroundImage = new Image("view/resources/purpleBackground.png", 256, 256, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
		
	}
}
