package view;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.media.*;
import model.BigNumberLabel;
import model.InfoLabel;
import model.SHIP;
import model.ShipPicker;
import model.SpaceRunnerButton;
import model.SpaceRunnerSubScene;
import model.TextLabel;




public class ViewManager {
	private static final int WIDTH = 1024;
	private static final int HEIGHT = 768;
	private AnchorPane mainPane;
	private Scene mainScene;
	private Stage mainStage;
	
	private final static int MENU_BUTTON_START_X = 100;
	private final static int MENU_BUTTON_START_Y = 150;
	
	private SpaceRunnerSubScene creditSubScene;
	private SpaceRunnerSubScene helpSubScene;
	private SpaceRunnerSubScene scoresSubScene;
	private SpaceRunnerSubScene shipChooserSubScene;
	
	private SpaceRunnerSubScene sceneToHide;
	
	
	List <SpaceRunnerButton> menuButtons;
	List <ShipPicker> shipList;
	
	private SHIP choosenShip; 
	
	static Media menuBgMusic, menuSFX;
	static MediaPlayer menuMusicPlayer, menuSFXPlayer;
	
	static String MENU_MUSIC_PATH = "src/view/resources/menuBgMusic.mp3";
	static String BUTTON_CLICK_PATH = "src/view/resources/click1.mp3";
	
	private String HIGH_SCORE;
	private File scoreFile;
	
	public ViewManager() {
		mainPane = new AnchorPane();
		mainScene = new Scene(mainPane, WIDTH, HEIGHT);
		mainStage = new Stage();
		mainStage.setScene(mainScene);
		menuButtons = new ArrayList<>();
		createSubScenes();
		createButtons();
		createLogo();
		createBackground();
		initMusicSFX();		
	}
	
	private void loadHighScore() {
		try {
			
			scoreFile = new File("src/view/resources/score.txt");
			
			Scanner myReader = new Scanner(scoreFile);
			
			HIGH_SCORE = myReader.nextInt() + "";
			//System.out.println("PRINTING IN LOAD HIGHSCORE: " + HIGH_SCORE);
			
			
			
			myReader.close();
			
		} catch (FileNotFoundException e) {
			
			System.out.println("File Read Failed ");
			
		}
	}
	
	public static void initMusicSFX() {
		
		// initiating menu background music player
		menuBgMusic = new Media(new File(MENU_MUSIC_PATH).toURI().toString());  
        menuMusicPlayer = new MediaPlayer(menuBgMusic);  
        menuMusicPlayer.setAutoPlay(true);

        // initiating menu background music player
     	menuSFX = new Media(new File(BUTTON_CLICK_PATH).toURI().toString());  
        menuSFXPlayer = new MediaPlayer(menuSFX);  
                
        
	}
	
	private void createSubScenes() {
		
		createCreditsSubScene();
		
		createHelpSubScene();
		
		createScoreSubScene();		
		
		createShipChooserSubScene();
			
	}
	
private void createCreditsSubScene() {
		
		creditSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(creditSubScene);
		
		InfoLabel creditTitle = new InfoLabel("CREDITS");
		creditTitle.setLayoutX(110);
		creditTitle.setLayoutY(25);
		
		TextLabel intro = new TextLabel("THIS GAME WAS DEVELOPED BY :",
				500, 50, 20);
		intro.setLayoutX(55);
		intro.setLayoutY(110);
		
		TextLabel shawon = new TextLabel("ABU MD ABDUL MAJID SHAWON",
				500, 50, 20);
		shawon.setLayoutX(55);
		shawon.setLayoutY(170);
		
		TextLabel mehraj = new TextLabel("MD. MEHRAJUL ISLAM",
				500, 50, 20);
		mehraj.setLayoutX(55);
		mehraj.setLayoutY(230);
		
		TextLabel ending = new TextLabel("FOR SWE 224 GAME PROJECT",
				500, 50, 20);
		ending.setLayoutX(55);
		ending.setLayoutY(290);
		
		creditSubScene.getPane().getChildren().add(creditTitle);
		creditSubScene.getPane().getChildren().add(intro);
		creditSubScene.getPane().getChildren().add(shawon);
		creditSubScene.getPane().getChildren().add(mehraj);
		creditSubScene.getPane().getChildren().add(ending);

	}
	
	private void createHelpSubScene() {
		
		helpSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(helpSubScene);
		
		InfoLabel helpTitle = new InfoLabel("HOW TO PLAY");
		helpTitle.setLayoutX(110);
		helpTitle.setLayoutY(25);
		
		TextLabel movementTitle = new TextLabel("USE ARROW KEYS TO MOVE THE PLANE",
				500, 50, 20);
		movementTitle.setLayoutX(55);
		movementTitle.setLayoutY(110);
		
		TextLabel pointsTitle = new TextLabel("COLLECT STARS TO GAIN POINTS",
				500, 50, 20);
		pointsTitle.setLayoutX(55);
		pointsTitle.setLayoutY(170);
		
		TextLabel eliminateTitle = new TextLabel("METIOR WILL DECREASE 1 LIFE",
				500, 50, 20);
		eliminateTitle.setLayoutX(55);
		eliminateTitle.setLayoutY(230);
		
		TextLabel laserTitle = new TextLabel("LASER WILL END THE GAME",
				500, 50, 20);
		laserTitle.setLayoutX(55);
		laserTitle.setLayoutY(290);
		
		helpSubScene.getPane().getChildren().add(helpTitle);
		helpSubScene.getPane().getChildren().add(movementTitle);
		helpSubScene.getPane().getChildren().add(pointsTitle);
		helpSubScene.getPane().getChildren().add(eliminateTitle);
		helpSubScene.getPane().getChildren().add(laserTitle);

	}
	
	private void createShipChooserSubScene() {
		shipChooserSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(shipChooserSubScene);
		
		
		InfoLabel chooseShipLabel = new InfoLabel("CHOOSE YOUE SHIP");
		chooseShipLabel.setLayoutX(110);
		chooseShipLabel.setLayoutY(25);
		
		shipChooserSubScene.getPane().getChildren().add(chooseShipLabel);
		shipChooserSubScene.getPane().getChildren().add(createShipToChoose());
		shipChooserSubScene.getPane().getChildren().add(createButtonToStart());
		
	}
	
	private void createScoreSubScene() {
		
		scoresSubScene = new SpaceRunnerSubScene();
		mainPane.getChildren().add(scoresSubScene);
		
		
		InfoLabel scoreTitle = new InfoLabel("YOUR HIGH SCORE");
		scoreTitle.setLayoutX(110);
		scoreTitle.setLayoutY(75);
		
		loadHighScore();
		
		BigNumberLabel scoreShow = new BigNumberLabel(HIGH_SCORE);
		scoreShow.setLayoutX(125);
		scoreShow.setLayoutY(45);
		
		scoresSubScene.getPane().getChildren().add(scoreTitle);
		scoresSubScene.getPane().getChildren().add(scoreShow);
		//shipChooserSubScene.getPane().getChildren().add(createShipToChoose());
		//shipChooserSubScene.getPane().getChildren().add(createButtonToStart());
		
	}
	
	
	private HBox createShipToChoose() {
		HBox box = new HBox();
		box.setSpacing(20);
		shipList = new ArrayList<>();
		for(SHIP ship : SHIP.values()) {
			
			ShipPicker shipToPick = new ShipPicker(ship);
			box.getChildren().add(shipToPick);
			shipList.add(shipToPick);
			shipToPick.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					// TODO Auto-generated method stub
					menuSFXPlayer.play();
					menuSFXPlayer = new MediaPlayer(menuSFX);
					for(ShipPicker ship: shipList) {
						ship.setIsCircleChoosen(false);
					}
					shipToPick.setIsCircleChoosen(true);
					choosenShip = shipToPick.getShip();
				}
				
			});
		}
		box.setLayoutX(300 - (118*2));
		box.setLayoutY(100);
		return box;
	}

	private void addMenuButton(SpaceRunnerButton button) {
		button.setLayoutX(MENU_BUTTON_START_X);
		button.setLayoutY(MENU_BUTTON_START_Y + menuButtons.size()*100);
		menuButtons.add(button);
		mainPane.getChildren().add(button);
		createLogo();
	}
	
	private void createButtons() {
		createStartButton();
		createScoresButton();
		createHelpButton();
		createCreditsButton();
		createExitButton();
	}
	
	private void showSubScene(SpaceRunnerSubScene subScene) {
		menuSFXPlayer.play();
		menuSFXPlayer = new MediaPlayer(menuSFX);
		
		if(sceneToHide != null) {
			sceneToHide.moveSubScene();
		}
		subScene.moveSubScene();
		sceneToHide = subScene;
	}
	
	private void createStartButton() {
		SpaceRunnerButton startButton = new SpaceRunnerButton("PLAY");
		addMenuButton(startButton);
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(shipChooserSubScene);
			}
		});
	}
	
	private void createScoresButton() {
		SpaceRunnerButton scoresButton = new SpaceRunnerButton("SCORES");
		addMenuButton(scoresButton);
		scoresButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {		
				createScoreSubScene();
				showSubScene(scoresSubScene);				
			}
		});
	}
	
	private void createHelpButton() {
		SpaceRunnerButton helpButton = new SpaceRunnerButton("HELP");
		addMenuButton(helpButton);
		helpButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				showSubScene(helpSubScene);
			}
		});
	}
	
	private void createCreditsButton() {
		SpaceRunnerButton creditsButton = new SpaceRunnerButton("CREDITS");
		addMenuButton(creditsButton);
		creditsButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				showSubScene(creditSubScene);
			}
		});
	}
	
	private void createExitButton() {
		SpaceRunnerButton exitButton = new SpaceRunnerButton("EXIT");
		addMenuButton(exitButton);
		exitButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				mainStage.close();
			}
		});
	}
	
	
	private void createBackground() {
		Image backgroundImage = new Image("view/resources/spaceBG.png", 1024, 768, false, false);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT, null);
		mainPane.setBackground(new Background(background));
	}
	
	private void createLogo() {
		ImageView logo = new ImageView("view/resources/logoMainMenu.png");
		logo.setLayoutX(400);
		logo.setLayoutY(50);
		logo.setOnMouseEntered(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(new DropShadow());
			}
			
		});
		logo.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				logo.setEffect(null);
			}
			
		});
		
		mainPane.getChildren().add(logo);
	}
	
	private SpaceRunnerButton createButtonToStart() {
		SpaceRunnerButton startButton = new SpaceRunnerButton("START");
		startButton.setLayoutX(350);
		startButton.setLayoutY(300);
		
		startButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				menuSFXPlayer.play();
				menuSFXPlayer = new MediaPlayer(menuSFX);
				if(choosenShip != null) {
					GameViewManager game = new GameViewManager(menuMusicPlayer);
					menuMusicPlayer.stop();
					game.createNewGame(mainStage, choosenShip);
				}
				else {
					Alert alert = new Alert(Alert.AlertType.WARNING);
					alert.setHeaderText(null);
					alert.setContentText("You did not choose your ship!");
					alert.show();
				}
				
				
			}
			
		});
		
		return startButton;
	}

	public Stage getMainStage() {
		return mainStage;
	}
}