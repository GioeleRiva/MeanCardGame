package com.eg0.gui;

import javafx.animation.ScaleTransition;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Button extends Pane {

	double screenWidth = 640;
	double screenHeight = 360;
	double cardHeight = (screenHeight * 9) / 10;
	double cardWidth = (cardHeight * 63) / 88;
	double cardRadius = (cardHeight) / 22;
	double cardBorder = (cardWidth * 13) / 126;
	double cardTextSize = (cardWidth * 9) / 100;

	public Button(String text, String screen) {
		this.setPrefSize(cardWidth, 2 * cardBorder);
		this.setId("button");
		this.setStyle("-fx-background-radius: " + Double.toString(cardRadius) + " " + Double.toString(cardRadius) + " "
				+ Double.toString(cardRadius) + " " + Double.toString(cardRadius) + ";");
		this.setCache(true);
		this.setOnMouseClicked(clicked -> {
			if (!Main.somethingSelected) {
				Main.somethingSelected = true;
				this.setDisable(true);
				ScaleTransition scaleTransition1 = new ScaleTransition(Duration.millis(100), this);
				scaleTransition1.setByX(-0.1);
				scaleTransition1.setByY(-0.1);
				scaleTransition1.setOnFinished(play2 -> {
					ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(100), this);
					scaleTransition2.setByX(0.15);
					scaleTransition2.setByY(0.15);
					scaleTransition2.setOnFinished(play3 -> {
						ScaleTransition scaleTransition3 = new ScaleTransition(Duration.millis(100), this);
						scaleTransition3.setByX(-0.05);
						scaleTransition3.setByY(-0.05);
						scaleTransition3.setOnFinished(done -> {
							this.setDisable(false);
							change(screen);
						});
						scaleTransition3.play();
					});
					scaleTransition2.play();
				});
				scaleTransition1.play();
			}
		});
		Label label = new Label(text);
		label.setLayoutX(cardBorder);
		label.setLayoutY(cardBorder / 2);
		label.setId("buttontext");
		label.setStyle("-fx-font-size: " + Double.toString(cardTextSize) + "px;");
		this.getChildren().add(label);
	}

	static void change(String nextScreen) {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				Thread.sleep(50);
				return null;
			}
		};
		task.setOnSucceeded(change -> {
			Main.changeScreen(nextScreen);
		});
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

}