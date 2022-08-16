package view;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.event.IIOReadWarningListener;
import javax.sound.midi.MidiChannel;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.effect.Light;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.SHIP;
import model.SmallInfoLabel;

public class GameViewManager {
	private AnchorPane gamePane;
	private Scene gameScene;
	private Stage gameStage;
	
	private Stage menuStage;
	private ImageView ship;
	
	private static final int GAME_WIDTH = 600;
	private static final int GAME_HEIGHT = 800;
	
	private boolean isLeftKeyPressed;
	private boolean isRightKeyPressed;
	private boolean isUpKeyPressed;
	private boolean isDownKeyPressed;
	private boolean isSpacePressed;
	
	
	private int angle;
	private int bulletAngle;
	
	private AnimationTimer gameTimer;
	
	private GridPane gridPane1;
	private GridPane gridPane2;
	private static final String BACKGROUND_IMAGE = "view/resources/darkPurple.png";
	private static final String METEOR_BROWN_IMAGE = "view/resources/meteorBrown.png";
	private static final String METEOR_GREY_IMAGE = "view/resources/meteorGrey.png";
	private static final String HORIZONTAL_LASER = "view/resources/laserHorizonotal.png";
	private static final String VERTICAL_LASER = "view/resources/laserVertical.png";
	private static final String LIGHT_IMAGE = "view/resources/bolt_gold.png";
	private static final String BULLET_IMAGE = "view/resources/bullet.png";
	
	
//	private ImageView[] brownMeteors;
//	private ImageView[] greyMeteors;
	
	private ArrayList <ImageView> brownMeteors;
	private ArrayList <ImageView> greyMeteors;
 	
	private Random randomPositionGenerator;
	
	private ImageView[] stars;
	private ImageView light1, light2;
	private ImageView horizontalLaser;
	private ImageView verticalLaser;
	private ImageView bullet;
	
	
	private SmallInfoLabel pointsLabel;
	private SmallInfoLabel levelUpText;
	
	private ImageView[] playerLifes;
	private int playerLife;
	private int points;
	private int numberOfBrownMeteors = 3;
	private int numberOfGreyMeteors = 3;
	
	
	private final static String GOLD_STAR_IMAGE = "view/resources/star_gold.png";
	
	// we will consider every objects as a circle
	
	private final static int STAR_RADIUS = 12;
	private final static int SHIP_RADIUS = 27;
	private final static int METEOR_RADIUS = 20;
	
	Media gameBgMusic, gameWarningSFX, gamePointSFX, gameCollisionSFX;
	MediaPlayer gameMusicPlayer, gameWarningSFXPlayer, gamePointSFXPlayer, menuMusicPlayer, gameCollisionSFXPlayer;
	
	String GAME_MUSIC_PATH = "src/view/resources/gamebgtest.mp3";
	String GAME_WARNING_PATH = "src/view/resources/alarm-small.mp3";
	String GAME_POINT_PATH = "src/view/resources/point.mp3";
	String GAME_COLLISION_PATH = "src/view/resources/collision.mp3";
	
	private File scoreFile;
	private int highestScore;
	
	public GameViewManager(MediaPlayer menubgMusic) {
		initializeStage();
		createKeyListeners();
		initMusicSFX(menubgMusic);
		randomPositionGenerator = new Random();
	}
	
	private void initMusicSFX(MediaPlayer menubgMusic) {
		
		menuMusicPlayer = menubgMusic;
			
		gameBgMusic = new Media(new File(GAME_MUSIC_PATH).toURI().toString());  
        gameMusicPlayer = new MediaPlayer(gameBgMusic);  
        gameMusicPlayer.setAutoPlay(true);

     	gameWarningSFX = new Media(new File(GAME_WARNING_PATH).toURI().toString());  
     	gameWarningSFXPlayer = new MediaPlayer(gameWarningSFX); 
     	
     	gamePointSFX = new Media(new File(GAME_POINT_PATH).toURI().toString());  
     	gamePointSFXPlayer = new MediaPlayer(gamePointSFX); 
     	
     	gameCollisionSFX = new Media(new File(GAME_COLLISION_PATH).toURI().toString());
     	gameCollisionSFXPlayer = new MediaPlayer(gameCollisionSFX);
        
	}

	private void createKeyListeners() {
		gameScene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = true;
				}
				else if(event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = true;
				}
				else if(event.getCode() == KeyCode.UP) {
					isUpKeyPressed = true;
				}
				else if(event.getCode() == KeyCode.DOWN) {
					isDownKeyPressed = true;
				}
				if(event.getCode() == KeyCode.SPACE) {
					isSpacePressed = true;
				}
			}
		});
		
		gameScene.setOnKeyReleased(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent event) {
				if(event.getCode() == KeyCode.LEFT) {
					isLeftKeyPressed = false;
				}
				else if(event.getCode() == KeyCode.RIGHT) {
					isRightKeyPressed = false;
				}
				else if(event.getCode() == KeyCode.UP) {
					isUpKeyPressed = false;
				}
				else if(event.getCode() == KeyCode.DOWN) {
					isDownKeyPressed = false;
				}
				if(event.getCode() == KeyCode.SPACE) {
					isSpacePressed = false;
				}
			}
			
		});
	}

	private void initializeStage() {
		gamePane = new AnchorPane();
		gameScene = new Scene(gamePane, GAME_WIDTH, GAME_HEIGHT);
		gameStage = new Stage();
		gameStage.setScene(gameScene);
		
	}
	
	public void createNewGame(Stage menuStage, SHIP choosenShip) {
		this.menuStage = menuStage; 
		this.menuStage.hide();
		createBackground();
		createShip(choosenShip);
		createGameElements(choosenShip);
		createGameLoop();
		
		
		gameStage.show();
	}
	
	private void createGameElements(SHIP choosenShip) {
		playerLife = 2;
		
		
		
		horizontalLaser = new ImageView(HORIZONTAL_LASER);
		verticalLaser = new ImageView(VERTICAL_LASER);
		light1 = new ImageView(LIGHT_IMAGE);
		light2 = new ImageView(LIGHT_IMAGE);
		bullet = new ImageView(BULLET_IMAGE);
		
		
	
		gamePane.getChildren().add(horizontalLaser);
		gamePane.getChildren().add(verticalLaser);
		gamePane.getChildren().add(light1);
		gamePane.getChildren().add(light2);
		gamePane.getChildren().add(bullet);
		
		
		
		
		horizontalLaser.setVisible(false);
		verticalLaser.setVisible(false);
		light1.setVisible(false);
		light2.setVisible(false);
		bullet.setVisible(false);
		
		
		pointsLabel = new SmallInfoLabel("POINTS : 00");
		pointsLabel.setLayoutX(460);
		pointsLabel.setLayoutY(20);
		gamePane.getChildren().add(pointsLabel);
		playerLifes = new ImageView[3];
		
		
		
		for(int i = 0; i < playerLifes.length; i++) {
			playerLifes[i] = new ImageView(choosenShip.getUrlLife());
			playerLifes[i].setLayoutX(455 + (50*i));
			playerLifes[i].setLayoutY(80);
			gamePane.getChildren().add(playerLifes[i]);
 		}
		
		
		
		
		
		brownMeteors = new ArrayList<ImageView>();
		for(int i = 0; i < numberOfBrownMeteors; i++) {
			brownMeteors.add(new ImageView(METEOR_BROWN_IMAGE));
			setNewElementPos(brownMeteors.get(i));
			gamePane.getChildren().add(brownMeteors.get(i));
		}
		
		greyMeteors = new ArrayList<ImageView>();
		

		
		for(int i = 0; i < numberOfGreyMeteors; i++) {
			greyMeteors.add(new ImageView(METEOR_GREY_IMAGE));
			setNewElementPos(greyMeteors.get(i));
			gamePane.getChildren().add(greyMeteors.get(i));
		}
		
		stars = new ImageView[10];
		for(int i = 0; i < stars.length; i++) {
			stars[i] = new ImageView(GOLD_STAR_IMAGE);
			setNewElementPos(stars[i]);
			gamePane.getChildren().add(stars[i]);
		}
		
	}
	
	
	
	private void moveGameElements() {
	
		
		for(int i = 0; i < stars.length; i++) {
			stars[i].setLayoutY(stars[i].getLayoutY()+ 5);
		}
		
		
		for(int i = 0; i < brownMeteors.size(); i++) {
			brownMeteors.get(i).setLayoutY(brownMeteors.get(i).getLayoutY() + 7);
			brownMeteors.get(i).setRotate(brownMeteors.get(i).getRotate() + 4);
		}
		
		for(int i = 0; i < greyMeteors.size(); i++) {
			greyMeteors.get(i).setLayoutY(greyMeteors.get(i).getLayoutY() + 7);
			greyMeteors.get(i).setRotate(greyMeteors.get(i).getRotate() + 4);
		}
	}
	
	private void checkIfElementsAreBehindTheShipAndRelocate() {
		
		
		for(int i = 0; i < stars.length; i++) {
			if(stars[i].getLayoutY() > 1200) {
				setNewElementPos(stars[i]);
			}
		}
		
		for(int i = 0; i < brownMeteors.size(); i++) {
			if(brownMeteors.get(i).getLayoutY() > 900) {
				setNewElementPos(brownMeteors.get(i));
			}
		}
		
		for(int i = 0; i < greyMeteors.size(); i++) {
			if(greyMeteors.get(i).getLayoutY() > 900) {
				setNewElementPos(greyMeteors.get(i));
			}
		}
	}
	
	private void setNewElementPos(ImageView image) {
		image.setLayoutX(randomPositionGenerator.nextInt(370));
		image.setLayoutY(-(randomPositionGenerator.nextInt(3200) + 600));
	}
	
	
	private void setHorizontalLaserPosition(ImageView image, int laserY) {
		image.setLayoutY(laserY);
		image.setLayoutX(0);
	}
	
	private void setVerticalLaserPosition(ImageView image, int laserX) {
		image.setLayoutY(0);
		image.setLayoutX(laserX);
	}
	
	
	private void createShip(SHIP ChoosenShip) {
		ship = new ImageView(ChoosenShip.getUrl());
		ship.setLayoutX(GAME_WIDTH/2 - 50);
		ship.setLayoutY(GAME_HEIGHT-90);
		
		gamePane.getChildren().add(ship);
		
	}
	
	private void createGameLoop() {
		gameTimer = new AnimationTimer() {
			int k = 1;
			int laserX, laserY;
			@Override
			public void handle(long now) {
				
				k = (k+1) % 500;
				
				moveBackground();
				moveGameElements();
				checkIfElementsAreBehindTheShipAndRelocate();
				checkIfElementCollided();
				checkIfHorizontalLaserCollided();
				checkIfVerticalLaserCollided();
				moveShip();
				
//				if(isSpacePressed) {
//					shootBullet(angle);
//				}
//				if(bullet.isVisible()) {
//					moveBullet();
//				}

				
				if(k == 0) showLight(laserX, laserY);
				if(k == 100) hideLight();
				if(k == 400) {
					// keeping the laser more towards the centre
					laserX = 100 + randomPositionGenerator.nextInt(GAME_WIDTH - 200);  // 600
					laserY = 100 + randomPositionGenerator.nextInt(GAME_HEIGHT - 200); // 800
					showCaution(laserX, laserY);
				}
				
			}

		};
		
		gameTimer.start();
	}
	
	private void showCaution(int laserX, int laserY) {
		
		gameWarningSFXPlayer.play();
     	gameWarningSFXPlayer = new MediaPlayer(gameWarningSFX);
		
		light1.setVisible(true);
		light1.setLayoutX(laserX);
		light1.setLayoutY(10);
		
		light2.setVisible(true);
		light2.setLayoutX(10);
		light2.setLayoutY(laserY);
	}
	

	
	private void hideLight() {
		
		setHorizontalLaserPosition(horizontalLaser, -100);
		setVerticalLaserPosition(verticalLaser, -100);
		
		// although upper code hides the laser from user i still set visibility of laser to false
		horizontalLaser.setVisible(false);
		verticalLaser.setVisible(false);
	}
	
	private void showLight(int laserX, int laserY) {
		light1.setVisible(false);
		light2.setVisible(false);
		
		showHorizontalLaser(laserY);
		showVerticalLaser(laserX);
	}
	
	private void showHorizontalLaser(int laserY) {
		horizontalLaser.setVisible(true);
		setHorizontalLaserPosition(horizontalLaser, laserY);
	}
	
	private void showVerticalLaser(int laserX) {
		verticalLaser.setVisible(true);
		setVerticalLaserPosition(verticalLaser, laserX);
	}
	
	private void shootBullet(int curAngle) {
		bullet.setVisible(true);
		bullet.setLayoutX(ship.getLayoutX()+SHIP_RADIUS+15);
		bullet.setLayoutY(ship.getLayoutY()-SHIP_RADIUS-20);
		
		 
		bulletAngle = curAngle;
		bullet.setRotate(bulletAngle);
		
	}
	
	private void moveBullet() {
	
		
		double c = -bullet.getLayoutY() - Math.tan(Math.toRadians(bulletAngle))*bullet.getLayoutX();
		
		double y = bullet.getLayoutY()-10;
		
		double x = bullet.getLayoutX();
		
		if(bulletAngle != 0) x = (-y-c)/Math.tan(Math.toRadians(bulletAngle));
	
		bullet.setLayoutX(x);
		bullet.setLayoutY(y);
		
		
	}
	
	
	
	private void moveShip() {
		if(isLeftKeyPressed && !isRightKeyPressed) {
			if(angle > -30) {
				angle -= 5;
			}
			ship.setRotate(angle);
			if(ship.getLayoutX() > -20) {
				ship.setLayoutX(ship.getLayoutX() - 3);
			}
		}
		else if(!isLeftKeyPressed && isRightKeyPressed) {
			if(angle < 30) {
				angle += 5;
			}
			ship.setRotate(angle);
			if(ship.getLayoutX() < GAME_WIDTH-80) {
				ship.setLayoutX(ship.getLayoutX() + 3); 
			}
		}
		else if(!isLeftKeyPressed && !isRightKeyPressed) {
			if(angle < 0) {
				angle += 5;
			}
			else if(angle > 0) {
				angle -= 5;
			}
			
			ship.setRotate(angle);
		}
		else if(isLeftKeyPressed && isRightKeyPressed) {
			if(angle < 0) {
				angle += 5;
			}
			else if(angle > 0) {
				angle -= 5;
			}
			
			ship.setRotate(angle);
		}
		if(isUpKeyPressed && !isDownKeyPressed) {
			if(ship.getLayoutY() > 0) {
				ship.setLayoutY(ship.getLayoutY()-3);
			}
		}
		if(!isUpKeyPressed && isDownKeyPressed) {
			if(ship.getLayoutY() < GAME_HEIGHT-85) {
				ship.setLayoutY(ship.getLayoutY()+3);
			}
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
		gridPane1.setLayoutY(gridPane1.getLayoutY()+1);
		gridPane2.setLayoutY(gridPane2.getLayoutY()+1);
		
		if(gridPane1.getLayoutY() >= 1024) {
			gridPane1.setLayoutY(-1024);
		}
		
		if(gridPane2.getLayoutY() >= 1024) {
			gridPane2.setLayoutY(-1024);
		}
	}
	
	private void checkIfHorizontalLaserCollided() {
		if(ship.getLayoutY()+SHIP_RADIUS >= horizontalLaser.getLayoutY() && ship.getLayoutY()+SHIP_RADIUS <= horizontalLaser.getLayoutY()+30) {
			removeLife();
			
			// we can hide Horizontal laser here to avoid gameOver for laser collision
			// hideLight();
		}
	}
	
	private void checkIfVerticalLaserCollided() {
		if(ship.getLayoutX()+SHIP_RADIUS >= verticalLaser.getLayoutX() && ship.getLayoutX()+SHIP_RADIUS <= verticalLaser.getLayoutX()+30) {
			removeLife();
			
			// we can hide vertical laser here to avoid gameOver for laser collision
			// hideLight();
		}
	}
	
	private void levelUp() {
		
		for(int i = numberOfBrownMeteors; i < numberOfBrownMeteors+4; i++) {
			brownMeteors.add(new ImageView(METEOR_BROWN_IMAGE));
			setNewElementPos(brownMeteors.get(i));
			gamePane.getChildren().add(brownMeteors.get(i));
		}
		
		for(int i = numberOfGreyMeteors; i < numberOfGreyMeteors+4; i++) {
			greyMeteors.add(new ImageView(METEOR_GREY_IMAGE));
			setNewElementPos(greyMeteors.get(i));
			gamePane.getChildren().add(greyMeteors.get(i));
		}
		
		numberOfBrownMeteors+=4;
		numberOfGreyMeteors+=4;
		
	}
	
	
	private void showLevelUpMessage() {
		levelUpText = new SmallInfoLabel("Level Up!");
		levelUpText.setLayoutY(GAME_HEIGHT/2);
		levelUpText.setLayoutX(GAME_WIDTH/2 - levelUpText.getPrefWidth()/2);
		gamePane.getChildren().add(levelUpText);
	}
	
	private void checkIfElementCollided() {
		
		for(int i = 0; i < stars.length; i++) {
			if(SHIP_RADIUS + STAR_RADIUS > calculateDistance(ship.getLayoutX()+49, ship.getLayoutY() + 37, stars[i].getLayoutX()+15, stars[i].getLayoutY()+15)) {
				setNewElementPos(stars[i]);
				points++;
				gamePointSFXPlayer.play();
				gamePointSFXPlayer = new MediaPlayer(gamePointSFX);
				String textToSet = "POINTS: ";
				if(points < 10) {
					textToSet = textToSet + "0";
				}
				pointsLabel.setText(textToSet + points);
				
				if(points == 10) {
					levelUp();
					points++;
					
					showLevelUpMessage();
					
				}
				
				if(points == 12) {
					gamePane.getChildren().remove(levelUpText);
				}
				
				if(points == 20) {
					levelUp();
					points++;
					
					showLevelUpMessage();
	
				}
				
				if(points == 22) {
					gamePane.getChildren().remove(levelUpText);
				}
				
				if(points == 30) {
					levelUp();
					points++;
					
					showLevelUpMessage();
				}
				
				if(points == 32) {
					gamePane.getChildren().remove(levelUpText);
				}
				
			}
		}
		
		
		for(int i = 0; i < brownMeteors.size(); i++) {
			if(SHIP_RADIUS + METEOR_RADIUS > calculateDistance(ship.getLayoutX()+49, ship.getLayoutY() + 37, brownMeteors.get(i).getLayoutX()+20, brownMeteors.get(i).getLayoutY()+20)) {
				setNewElementPos(brownMeteors.get(i));
				removeLife();
			}
		}
		
		for(int i = 0; i < greyMeteors.size(); i++) {
            if(SHIP_RADIUS + METEOR_RADIUS > calculateDistance(ship.getLayoutX()+49, ship.getLayoutY() + 37, greyMeteors.get(i).getLayoutX()+20, greyMeteors.get(i).getLayoutY()+20)) {
                setNewElementPos(greyMeteors.get(i));
                removeLife();
            }
        }
	}
	
	private void saveScore() {
		
		scoreFile = new File("src/view/resources/score.txt");
		
		if(!scoreFile.exists()) {
			try {
				scoreFile.createNewFile();
				
				FileWriter myWriter = new FileWriter(scoreFile);
				myWriter.write("0");
				myWriter.close();
				
			} catch (IOException e) {
				
				System.out.println("FILE CREATION FAILED =<");
				
			}
		}		
		
		try {
			
			Scanner myReader = new Scanner(scoreFile);
			
			highestScore = myReader.nextInt();
			
			myReader.close();
			
		} catch (FileNotFoundException e) {
			
			System.out.println("File Read Failed ");
			
		}
		
		
		if(highestScore < points) {
			
			scoreFile = new File("src/view/resources/score.txt");
			
			FileWriter myWriter;
			
			try {
				
				myWriter = new FileWriter(scoreFile);
				myWriter.write(points+"");
				myWriter.close();
				
			} catch (IOException e) {
				
				System.out.println("File Cannot be overriden");
				
			}
			
			
		}
		
	
	}
	
	private void removeLife() {
		gamePane.getChildren().remove(playerLifes[playerLife]);
		playerLife--;
		
		gameCollisionSFXPlayer.play();
		gameCollisionSFXPlayer = new MediaPlayer(gameCollisionSFX);
		
		if(playerLife < 0) {
			gameStage.close();
			gameTimer.stop();
			gameMusicPlayer.stop();
			ViewManager.initMusicSFX();
			menuStage.show();
			saveScore();
		}
	}
	
	private double calculateDistance(double x1, double y1, double x2, double y2) {
		return Math.sqrt( Math.pow(x1-x2, 2) + Math.pow(y1-y2, 2) );
	}
	
}