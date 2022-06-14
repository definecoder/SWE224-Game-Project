package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.text.Font;

public class SmallInfoLabel extends Label{
	public final static String FONT_PATH = "src/model/resources/kenvector_future.ttf";
	public final static String BACKGROUND_IMAGE = "/view/resources/yellow_small_panel.png";
	
	public SmallInfoLabel(String text) {
		setPrefWidth(130);
		setPrefHeight(50);
		Image backgroundImage = new Image(BACKGROUND_IMAGE, 130, 50, false, true);
		BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, 
						BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		setBackground(new Background(background));
		setAlignment(Pos.CENTER_LEFT);
		setPadding(new Insets(10, 10, 10, 10));
		setLabelFont();
		setText(text);
	}
	
	private void setLabelFont() {
		try {
			setFont(Font.loadFont(new FileInputStream(FONT_PATH), 15));
		} catch(FileNotFoundException e) {
			setFont(Font.font("Verdana", 15));
		}
	}
}
