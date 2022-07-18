package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class TextLabel extends Label{
	public final static String FONT_PATH = "src/model/resources/kenvector_future.ttf";
	public final static String BACKGROUND_IMAGE = "view/resources/yellow_small_panel.png";
	
	public TextLabel(String text, int width, int height, int fontSize) {
		
		setPrefWidth(width);
		setPrefHeight(height);
		//setPadding(new Insets(40,40,40,40));
		setText(text);
		setWrapText(true);
		setLabelFont(fontSize);
		setAlignment(Pos.CENTER);
		
		/*BackgroundImage backgroundImage = new BackgroundImage(new Image(BACKGROUND_IMAGE, 380, 49, false, true), 
				BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, null);
		setBackground(new Background(backgroundImage));*/
		
	}
	
	private void setLabelFont(int x) {
		try {
			setFont(Font.loadFont(new FileInputStream(FONT_PATH), x));
			setTextFill(Color.color(0.3,0,0));
		} catch(FileNotFoundException e) {
			setFont(Font.font("Verdana", x));
		}
	}
}
