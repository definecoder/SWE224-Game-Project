package view;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class GameViewManager {
	
	private static final int GAME_HEIGHT = 800;
	private static final int GAME_WIDTH = 600;
	
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	public GameViewManager() {
		initializaeStage();
		createKeyListeners();
	}
	
	private void createKeyListeners() {
		
		gameScene.setOnKeyPressed(new EventHandler <KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.RIGHT) {
					
				} else if(event.getCode() == KeyCode.LEFT) {
					
				}
				
			}
			
		});
		
		gameScene.setOnKeyReleased(new EventHandler <KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.RIGHT) {
					
				} else if(event.getCode() == KeyCode.LEFT) {
					
				}
				
			}
			
		});
		
	}

	private void initializaeStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
	}
}
