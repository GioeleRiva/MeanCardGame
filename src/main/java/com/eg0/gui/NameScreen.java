package com.eg0.gui;

import com.eg0.network.Card;

import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class NameScreen extends Pane {

	double screenWidth = 640;
	double screenHeight = 360;
	double cardHeight = (screenHeight * 9) / 10;
	double cardWidth = (cardHeight * 63) / 88;
	double cardRadius = (cardHeight) / 22;
	double cardBorder = (cardWidth * 13) / 126;
	double cardTextSize = (cardWidth * 9) / 100;

	Button play;

	static boolean blackClicked = false;

	public NameScreen() {
		this.setPrefSize(screenWidth, screenHeight);
		this.setId("background");
		Pane backgroundImage = new Pane();
		backgroundImage.setPrefSize(screenHeight, screenHeight);
		backgroundImage.setLayoutX(screenWidth - screenHeight);
		backgroundImage.setId("backgroundimage");
		this.getChildren().add(backgroundImage);
		play = new Button("Play.", "playscreen");
		play.setLayoutX(screenWidth - screenHeight * 0.05 - cardWidth);
		play.setLayoutY(screenHeight - screenHeight * 0.05 - 2 * cardBorder);
		play.setVisible(false);
		this.getChildren().add(play);
		Card bCard = new Card("My name is _______ .", "black", 0);
		CardPane blackCard = new CardPane(bCard);
		blackCard.setLayoutX(screenHeight * 0.05);
		blackCard.setLayoutY(screenHeight * 0.05);
		this.getChildren().add(blackCard);
		Card wCard = new Card(Main.playerName, "white", 0);
		CardPane whiteCard = new CardPane(wCard);
		whiteCard.setPrefSize(cardWidth, cardHeight);
		whiteCard.setLayoutX(-cardWidth);
		whiteCard.setLayoutY(screenHeight * 0.3);
		this.getChildren().add(whiteCard);
		Keyboard keyboard = new Keyboard(whiteCard, 8);
		this.getChildren().add(keyboard);
		Button back = new Button("Back.", "homeScreen");
		back.setLayoutX(screenWidth - screenHeight * 0.05 - cardWidth);
		back.setLayoutY(screenHeight * 0.05);
		this.getChildren().add(back);
		blackCard.setOnMouseClicked(show -> {
			if (!blackClicked) {
				blackClicked = true;
				PathTransition cardPathTransition = new PathTransition();
				cardPathTransition.setDuration(Duration.millis(250));
				Path cardPath = new Path();
				cardPath.getElements().add(new MoveTo(-cardWidth / 2, cardHeight / 2));
				cardPath.getElements().add(new LineTo((3 * cardWidth) / 2 + screenHeight * 0.2, cardHeight / 2));
				cardPathTransition.setPath(cardPath);
				cardPathTransition.setNode(whiteCard);
				cardPathTransition.play();
			}
			if (!keyboard.isKeyboardVisible()) {
				keyboard.setKeyboardVisible(true);
				PathTransition keyboardPathTransition = new PathTransition();
				keyboardPathTransition.setDuration(Duration.millis(250));
				Path keyboardPath = new Path();
				keyboardPath.getElements().add(new MoveTo(screenWidth / 2, screenHeight / 4));
				keyboardPath.getElements().add(new LineTo(screenWidth / 2, -screenHeight / 4));
				keyboardPathTransition.setPath(keyboardPath);
				keyboardPathTransition.setNode(keyboard);
				keyboardPathTransition.play();
				play.setVisible(true);
			}
		});
		whiteCard.setOnMouseClicked(show -> {
			if (!keyboard.isKeyboardVisible()) {
				keyboard.setKeyboardVisible(true);
				PathTransition keyboardPathTransition = new PathTransition();
				keyboardPathTransition.setDuration(Duration.millis(250));
				Path keyboardPath = new Path();
				keyboardPath.getElements().add(new MoveTo(screenWidth / 2, screenHeight / 4));
				keyboardPath.getElements().add(new LineTo(screenWidth / 2, -screenHeight / 4));
				keyboardPathTransition.setPath(keyboardPath);
				keyboardPathTransition.setNode(keyboard);
				keyboardPathTransition.play();
			}
		});
		play.setOnMouseClicked(clicked -> {
			if (!Main.somethingSelected) {
				Main.somethingSelected = true;
				play.setDisable(true);
				ScaleTransition scaleTransition1 = new ScaleTransition(Duration.millis(100), play);
				scaleTransition1.setByX(-0.1);
				scaleTransition1.setByY(-0.1);
				scaleTransition1.setOnFinished(play2 -> {
					ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(100), play);
					scaleTransition2.setByX(0.15);
					scaleTransition2.setByY(0.15);
					scaleTransition2.setOnFinished(play3 -> {
						ScaleTransition scaleTransition3 = new ScaleTransition(Duration.millis(100), play);
						scaleTransition3.setByX(-0.05);
						scaleTransition3.setByY(-0.05);
						scaleTransition3.setOnFinished(done -> {
							play.setDisable(false);
							Main.playerName = ((Label) whiteCard.getChildren().get(0)).getText();
							Button.change("playScreen");
						});
						scaleTransition3.play();
					});
					scaleTransition2.play();
				});
				scaleTransition1.play();
			}
		});
	}

}