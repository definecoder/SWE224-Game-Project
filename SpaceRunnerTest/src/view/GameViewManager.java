package view;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import model.SHIP;

public class GameViewManager {
	
	private static final int GAME_HEIGHT = 800;
	private static final int GAME_WIDTH = 600;
	
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private Stage menuStage;
	private ImageView ship;
	
	private boolean isLeftKeyPressed;
	private boolean isRightKeyPressed;
	private int angle;
	private AnimationTimer gameTimer;
	
	public GameViewManager() {
		initializaeStage();
		createKeyListeners();
	}
	
	private void createKeyListeners() {
		
		gameScene.setOnKeyPressed(new EventHandler <KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = true;
				} else if(event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = true;
				}
				
			}
			
		});
		
		gameScene.setOnKeyReleased(new EventHandler <KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed =false;
				} else if(event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = false;
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
	
	public void createNewGame(Stage menuStage, SHIP chosenShip) {
		this.menuStage = menuStage;
		createShip(chosenShip); 
		createGameLoop();
		this.menuStage.hide();
		this.gameStage.show();
	}
	
	private void createShip(SHIP chosenShip) {
		ship = new ImageView(chosenShip.getURL());
		ship.setLayoutX(GAME_WIDTH / 2);
		ship.setLayoutY(GAME_HEIGHT - 90);
		gamePane.getChildren().add(ship);
	}
	
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				moveShip();
			}
			
		};
		
		gameTimer.start();
	}
	
	private void moveShip() {
		if(isLeftKeyPressed && !isRightKeyPressed) {
			if(angle > -30) angle -= 5;
			ship.setRotate(angle);
			if(ship.getLayoutX() > -20) ship.setLayoutX(ship.getLayoutX() - 3);
		}
		else if(!isLeftKeyPressed && isRightKeyPressed) {
			if(angle < 30) angle += 5;
			ship.setRotate(angle);
			if(ship.getLayoutX() < 522) ship.setLayoutX(ship.getLayoutX() + 3);
		}
		else {
			if(angle > 0) angle -= 5;
			else if(angle < 0)  angle += 5;
			ship.setRotate(angle);
		}
	}
}
