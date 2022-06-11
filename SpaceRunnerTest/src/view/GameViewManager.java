package view;

import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.SHIP;
import model.SmallInfoLabel;

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
	
	private GridPane gridPane1, gridPane2;
	private final static String BACKGROUND_IMAGE = "view/resources/purpleBackground.png";
	
	private final static String METEROR_BROWN_IMAGE = "view/resources/meteorBrown.png";
	private final static String METEROR_GRAY_IMAGE = "view/resources/meteorGrey.png";
	
	private ImageView[] brownMeteors, grayMeteors;
	Random randomPositionGenarator;
	
	private ImageView star;
	private SmallInfoLabel pointsLabel;
	private ImageView[] playerLifes;
	private int playerLife, points;
	private final static String GOLD_STAR_IMAGE = "view/resources/star_gold.png";			
	
	public GameViewManager() {
		initializaeStage();
		createKeyListeners();
		randomPositionGenarator = new Random(); 
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
	
	private void createGameElements(SHIP chosenShip) {
		playerLife = 2;
		star = new ImageView(GOLD_STAR_IMAGE);
		setNewElementPosition(star);
		gamePane.getChildren().add(star);
		pointsLabel = new SmallInfoLabel("POINTS: 00");
		pointsLabel.setLayoutX(460);
		pointsLabel.setLayoutY(20);
		gamePane.getChildren().add(pointsLabel);
		
		playerLifes = new ImageView[3];
		for(int i = 0; i < 3; i++) {
			playerLifes[i] = new ImageView(chosenShip.getUrlLife());
			playerLifes[i].setLayoutX(455 + (i * 50));
			playerLifes[i].setLayoutY(80);
			gamePane.getChildren().add(playerLifes[i]);
		}
		
		brownMeteors = new ImageView[3];
		for(int i = 0; i < brownMeteors.length; i++) {
			brownMeteors[i] = new ImageView(METEROR_BROWN_IMAGE);
			setNewElementPosition(brownMeteors[i]);
			gamePane.getChildren().add(brownMeteors[i]);
		}
		
		grayMeteors = new ImageView[3];
		for(int i = 0; i < grayMeteors.length; i++) {
			grayMeteors[i] = new ImageView(METEROR_GRAY_IMAGE);
			setNewElementPosition(grayMeteors[i]);
			gamePane.getChildren().add(grayMeteors[i]);
		}
	}
	
	private void moveGameElements() {
		
		star.setLayoutY(star.getLayoutY() + 5);
		
		for(int i = 0; i < brownMeteors.length; i++) {
			brownMeteors[i].setLayoutY(brownMeteors[i].getLayoutY() + 7);
			brownMeteors[i].setRotate(brownMeteors[i].getRotate() + 4);
		}
		
		for(int i = 0; i < grayMeteors.length; i++) {
			grayMeteors[i].setLayoutY(grayMeteors[i].getLayoutY() + 7);
			grayMeteors[i].setRotate(grayMeteors[i].getRotate() + 4);
		}
	}
	
	private void checkIfElementsAreBehindTheSceneAndRelocateThem() {
		if(star.getLayoutY() > 1200) {
			setNewElementPosition(star);
		}
		
		for(int i = 0; i < brownMeteors.length; i++) {
			if(brownMeteors[i].getLayoutY() > 900) {
				setNewElementPosition(brownMeteors[i]);
			}
		}
		
		for(int i = 0; i < grayMeteors.length; i++) {
			if(grayMeteors[i].getLayoutY() > 900) {
				setNewElementPosition(grayMeteors[i]);
			}
		}
	}
	
	private void setNewElementPosition(ImageView image) {
		image.setLayoutX(randomPositionGenarator.nextInt(370));
		image.setLayoutY(-(randomPositionGenarator.nextInt(3200)+600));
	}

	private void initializaeStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
	}
	
	public void createNewGame(Stage menuStage, SHIP chosenShip) {
		this.menuStage = menuStage;
		createBackground();
		createShip(chosenShip);
		createGameElements(chosenShip);
		createGameLoop();
		this.menuStage.hide();
		this.gameStage.show();
	}
	
	private void createShip(SHIP chosenShip) {
		ship = new ImageView(chosenShip.getUrl());
		ship.setLayoutX(GAME_WIDTH / 2);
		ship.setLayoutY(GAME_HEIGHT - 90);
		gamePane.getChildren().add(ship);
	}
	
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {

			@Override
			public void handle(long now) {
				moveBackground();
				moveShip();
				moveGameElements();
				checkIfElementsAreBehindTheSceneAndRelocateThem();
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
	
	private void createBackground() {
		gridPane1 = new GridPane();
		gridPane2 = new GridPane();
		
		for(int i = 0; i < 12; i++) {
			ImageView backgroundImage1 = new ImageView(BACKGROUND_IMAGE);
			ImageView backgroundImage2 = new ImageView(BACKGROUND_IMAGE);
			
			GridPane.setConstraints(backgroundImage1, i%3, i/3);
			GridPane.setConstraints(backgroundImage2, i%3, i/3);
			
			gridPane1.getChildren().add(backgroundImage1);
			gridPane2.getChildren().add(backgroundImage2);
		}
		
		gridPane2.setLayoutY(-1024);
		
		gamePane.getChildren().addAll(gridPane1, gridPane2);
	}
	
	private void moveBackground() {
		gridPane1.setLayoutY(gridPane1.getLayoutY() + 0.5);
		gridPane2.setLayoutY(gridPane2.getLayoutY() + 0.5);
		
		if(gridPane1.getLayoutY() >= 1024) gridPane1.setLayoutY(-1024);
		if(gridPane2.getLayoutY() >= 1024) gridPane2.setLayoutY(-1024);
	}
}
