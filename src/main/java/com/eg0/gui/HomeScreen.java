package com.eg0.gui;

import javafx.scene.layout.Pane;
import javafx.stage.Screen;

public class HomeScreen extends Pane {

	double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
	double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
	double cardHeight = (screenHeight * 9) / 10;
	double cardWidth = (cardHeight * 63) / 88;
	double cardRadius = (cardHeight) / 22;
	double cardBorder = (cardWidth * 13) / 126;
	double cardTextSize = (cardWidth * 9) / 100;

	public HomeScreen() {
		this.setPrefSize(screenWidth, screenHeight);
		this.setId("background");
		Pane backgroundImage = new Pane();
		backgroundImage.setPrefSize(screenHeight, screenHeight);
		backgroundImage.setLayoutX(screenWidth - screenHeight);
		backgroundImage.setId("backgroundimage");
		this.getChildren().add(backgroundImage);
		Button play = new Button("Play.", "nameScreen");
		play.setLayoutX(screenHeight * 0.05);
		play.setLayoutY(screenHeight * 0.05);
		this.getChildren().add(play);
		Button options = new Button("Options.", "optionsScreen");
		options.setLayoutX(screenHeight * 0.05);
		options.setLayoutY(screenHeight * 0.1 + cardBorder * 2);
		this.getChildren().add(options);
		Button rules = new Button("Rules.", "homeScreen");
		rules.setLayoutX(screenHeight * 0.05);
		rules.setLayoutY(screenHeight * 0.15 + cardBorder * 4);
		this.getChildren().add(rules);
	}

}