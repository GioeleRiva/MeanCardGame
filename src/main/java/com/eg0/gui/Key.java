package com.eg0.gui;

import javafx.animation.ScaleTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class Key extends Label {

	public Key(double buttonSize, double layoutX, double layoutY, String keyName, double textSize, double radius,
			Pane keyboard, CardPane cardPane, int maxLenght) {
		this.setText(keyName);
		this.setMinSize(buttonSize, buttonSize);
		this.setLayoutX(layoutX);
		this.setLayoutY(layoutY);
		this.setCache(true);
		this.setId("key");
		this.setStyle("-fx-font-size: " + Double.toString(textSize * 2) + "px; -fx-background-radius: "
				+ Double.toString(radius / 3) + " " + Double.toString(radius / 3) + " " + Double.toString(radius / 3)
				+ " " + Double.toString(radius / 3) + ";");
		this.setAlignment(Pos.CENTER);
		this.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(50), (Label) event.getSource());
				scaleTransition.setByX(-0.1);
				scaleTransition.setByY(-0.1);
				scaleTransition.setOnFinished(done -> {
					if (((Label) cardPane.getChildren().get(0)).getText().length() < maxLenght) {
						((Label) cardPane.getChildren().get(0))
								.setText(((Label) cardPane.getChildren().get(0)).getText() + keyName);
					}
					ScaleTransition scaleDoneTransition = new ScaleTransition(Duration.millis(50),
							(Label) event.getSource());
					scaleDoneTransition.setByX(0.1);
					scaleDoneTransition.setByY(0.1);
					scaleDoneTransition.play();
				});
				scaleTransition.play();
			}
		});
		keyboard.getChildren().add(this);
	}

}