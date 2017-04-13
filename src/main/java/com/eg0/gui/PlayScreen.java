package com.eg0.gui;

import com.eg0.network.Listener;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PlayScreen extends Pane {

	static double screenWidth = Util.width;
	static double screenHeight = Util.height;
	double cardHeight = (screenHeight * 9) / 10;
	double cardWidth = (cardHeight * 63) / 88;
	double cardRadius = (cardHeight) / 22;
	double cardBorder = (cardWidth * 13) / 126;
	double cardTextSize = (cardWidth * 9) / 100;

	public PlayScreen() {
		this.setPrefSize(screenWidth, screenHeight);
		this.setId("background");
		Pane backgroundImage = new Pane();
		backgroundImage.setPrefSize(screenHeight, screenHeight);
		backgroundImage.setLayoutX(screenWidth - screenHeight);
		backgroundImage.setId("backgroundimage");
		this.getChildren().add(backgroundImage);
		Button create = new Button("Create game.", "roomScreen");
		create.setLayoutX(screenHeight * 0.05);
		create.setLayoutY(screenHeight * 0.05);
		this.getChildren().add(create);
		Button join = new Button("Join game.", "joinScreen");
		join.setLayoutX(screenHeight * 0.05);
		join.setLayoutY(screenHeight * 0.1 + cardBorder * 2);
		this.getChildren().add(join);
		Button back = new Button("Back.", "nameScreen");
		back.setLayoutX(screenHeight * 0.05);
		back.setLayoutY(screenHeight * 0.15 + cardBorder * 4);
		this.getChildren().add(back);
		create.setOnMouseClicked(clicked -> {
			if (!Main.somethingSelected) {
				Main.somethingSelected = true;
				create.setDisable(true);
				ScaleTransition scaleTransition1 = new ScaleTransition(Duration.millis(100), create);
				scaleTransition1.setByX(-0.1);
				scaleTransition1.setByY(-0.1);
				scaleTransition1.setOnFinished(play2 -> {
					ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(100), create);
					scaleTransition2.setByX(0.15);
					scaleTransition2.setByY(0.15);
					scaleTransition2.setOnFinished(play3 -> {
						ScaleTransition scaleTransition3 = new ScaleTransition(Duration.millis(100), create);
						scaleTransition3.setByX(-0.05);
						scaleTransition3.setByY(-0.05);
						scaleTransition3.setOnFinished(done -> {
							try {
								Thread thread = new Thread(new Listener(Util.ip, 15000, Main.playerName, true,
										null, ((Label) Main.optionsScreen.num1.getChildren().get(0)).getText(),
										((Label) Main.optionsScreen.num2.getChildren().get(0)).getText()));
								thread.start();
							} catch (Exception e) {
								System.out.println(e);
							}
							create.setDisable(false);
							Main.changeScreen("roomScreen", false);
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