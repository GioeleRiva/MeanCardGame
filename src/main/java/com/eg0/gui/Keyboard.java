package com.eg0.gui;

import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Screen;
import javafx.util.Duration;

public class Keyboard extends Pane {

	static double screenWidth = Screen.getPrimary().getVisualBounds().getWidth();
	static double screenHeight = Screen.getPrimary().getVisualBounds().getHeight();
	static double cardHeight = (screenHeight * 9) / 10;
	static double cardWidth = (cardHeight * 63) / 88;
	static double cardRadius = (cardHeight) / 22;
	static double cardBorder = (cardWidth * 13) / 126;
	static double cardTextSize = (cardWidth * 9) / 100;
	private double buttonSize = ((screenHeight / 2) - 20) / 3;
	private double firstRowSize = (buttonSize * 10) + 50;
	private double secondRowSize = (buttonSize * 9) + 45;
	private double thirdRowSize = (buttonSize * 7) + 35;

	private boolean keyboardVisible = false;

	@SuppressWarnings("unused")
	public Keyboard(CardPane cardPane, int maxLenght) {
		this.setPrefSize(screenWidth, screenHeight / 2);
		this.setLayoutY(screenHeight);
		this.setCache(true);
		this.setId("background");
		Key Q = new Key(buttonSize, (screenWidth - firstRowSize) / 2, 5, "Q", cardTextSize, cardRadius, this, cardPane,
				maxLenght);
		Key W = new Key(buttonSize, (screenWidth - firstRowSize) / 2 + 1 * buttonSize + 1 * 5, 5, "W", cardTextSize,
				cardRadius, this, cardPane, maxLenght);
		Key E = new Key(buttonSize, (screenWidth - firstRowSize) / 2 + 2 * buttonSize + 2 * 5, 5, "E", cardTextSize,
				cardRadius, this, cardPane, maxLenght);
		Key R = new Key(buttonSize, (screenWidth - firstRowSize) / 2 + 3 * buttonSize + 3 * 5, 5, "R", cardTextSize,
				cardRadius, this, cardPane, maxLenght);
		Key T = new Key(buttonSize, (screenWidth - firstRowSize) / 2 + 4 * buttonSize + 4 * 5, 5, "T", cardTextSize,
				cardRadius, this, cardPane, maxLenght);
		Key Y = new Key(buttonSize, (screenWidth - firstRowSize) / 2 + 5 * buttonSize + 5 * 5, 5, "Y", cardTextSize,
				cardRadius, this, cardPane, maxLenght);
		Key U = new Key(buttonSize, (screenWidth - firstRowSize) / 2 + 6 * buttonSize + 6 * 5, 5, "U", cardTextSize,
				cardRadius, this, cardPane, maxLenght);
		Key I = new Key(buttonSize, (screenWidth - firstRowSize) / 2 + 7 * buttonSize + 7 * 5, 5, "I", cardTextSize,
				cardRadius, this, cardPane, maxLenght);
		Key O = new Key(buttonSize, (screenWidth - firstRowSize) / 2 + 8 * buttonSize + 8 * 5, 5, "O", cardTextSize,
				cardRadius, this, cardPane, maxLenght);
		Key P = new Key(buttonSize, (screenWidth - firstRowSize) / 2 + 9 * buttonSize + 9 * 5, 5, "P", cardTextSize,
				cardRadius, this, cardPane, maxLenght);
		Key A = new Key(buttonSize, (screenWidth - secondRowSize) / 2, 10 + buttonSize, "A", cardTextSize, cardRadius,
				this, cardPane, maxLenght);
		Key S = new Key(buttonSize, (screenWidth - secondRowSize) / 2 + 1 * buttonSize + 1 * 5, 10 + buttonSize, "S",
				cardTextSize, cardRadius, this, cardPane, maxLenght);
		Key D = new Key(buttonSize, (screenWidth - secondRowSize) / 2 + 2 * buttonSize + 2 * 5, 10 + buttonSize, "D",
				cardTextSize, cardRadius, this, cardPane, maxLenght);
		Key F = new Key(buttonSize, (screenWidth - secondRowSize) / 2 + 3 * buttonSize + 3 * 5, 10 + buttonSize, "F",
				cardTextSize, cardRadius, this, cardPane, maxLenght);
		Key G = new Key(buttonSize, (screenWidth - secondRowSize) / 2 + 4 * buttonSize + 4 * 5, 10 + buttonSize, "G",
				cardTextSize, cardRadius, this, cardPane, maxLenght);
		Key H = new Key(buttonSize, (screenWidth - secondRowSize) / 2 + 5 * buttonSize + 5 * 5, 10 + buttonSize, "H",
				cardTextSize, cardRadius, this, cardPane, maxLenght);
		Key J = new Key(buttonSize, (screenWidth - secondRowSize) / 2 + 6 * buttonSize + 6 * 5, 10 + buttonSize, "J",
				cardTextSize, cardRadius, this, cardPane, maxLenght);
		Key K = new Key(buttonSize, (screenWidth - secondRowSize) / 2 + 7 * buttonSize + 7 * 5, 10 + buttonSize, "K",
				cardTextSize, cardRadius, this, cardPane, maxLenght);
		Key L = new Key(buttonSize, (screenWidth - secondRowSize) / 2 + 8 * buttonSize + 8 * 5, 10 + buttonSize, "L",
				cardTextSize, cardRadius, this, cardPane, maxLenght);
		Key Z = new Key(buttonSize, (screenWidth - thirdRowSize) / 2, 15 + buttonSize * 2, "Z", cardTextSize,
				cardRadius, this, cardPane, maxLenght);
		Key X = new Key(buttonSize, (screenWidth - thirdRowSize) / 2 + 1 * buttonSize + 1 * 5, 15 + buttonSize * 2, "X",
				cardTextSize, cardRadius, this, cardPane, maxLenght);
		Key C = new Key(buttonSize, (screenWidth - thirdRowSize) / 2 + 2 * buttonSize + 2 * 5, 15 + buttonSize * 2, "C",
				cardTextSize, cardRadius, this, cardPane, maxLenght);
		Key V = new Key(buttonSize, (screenWidth - thirdRowSize) / 2 + 3 * buttonSize + 3 * 5, 15 + buttonSize * 2, "V",
				cardTextSize, cardRadius, this, cardPane, maxLenght);
		Key B = new Key(buttonSize, (screenWidth - thirdRowSize) / 2 + 4 * buttonSize + 4 * 5, 15 + buttonSize * 2, "B",
				cardTextSize, cardRadius, this, cardPane, maxLenght);
		Key N = new Key(buttonSize, (screenWidth - thirdRowSize) / 2 + 5 * buttonSize + 5 * 5, 15 + buttonSize * 2, "N",
				cardTextSize, cardRadius, this, cardPane, maxLenght);
		Key M = new Key(buttonSize, (screenWidth - thirdRowSize) / 2 + 6 * buttonSize + 6 * 5, 15 + buttonSize * 2, "M",
				cardTextSize, cardRadius, this, cardPane, maxLenght);
		Label delete = new Label("Delete");
		delete.setMinSize(buttonSize * 1.5, buttonSize);
		delete.setLayoutX(5);
		delete.setLayoutY(15 + 2 * buttonSize);
		delete.setId("key");
		delete.setStyle("-fx-font-size: " + Double.toString(cardTextSize) + "px; -fx-background-radius: "
				+ Double.toString(cardRadius / 3) + " " + Double.toString(cardRadius / 3) + " "
				+ Double.toString(cardRadius / 3) + " " + Double.toString(cardRadius / 3) + ";");
		delete.setAlignment(Pos.CENTER);
		delete.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(50), (Label) event.getSource());
				scaleTransition.setByX(-0.1);
				scaleTransition.setByY(-0.1);
				scaleTransition.setOnFinished(done -> {
					ScaleTransition scaleDoneTransition = new ScaleTransition(Duration.millis(50),
							(Label) event.getSource());
					scaleDoneTransition.setByX(0.1);
					scaleDoneTransition.setByY(0.1);
					String currentName = ((Label) cardPane.getChildren().get(0)).getText();
					if (currentName.length() > 0) {
						String reducedName = currentName.substring(0, currentName.length() - 1);
						((Label) cardPane.getChildren().get(0)).setText(reducedName);
					}
					scaleDoneTransition.play();
				});
				scaleTransition.play();
			}
		});
		Label done = new Label("Done");
		done.setMinSize(buttonSize * 1.5, buttonSize);
		done.setLayoutX(screenWidth - 5 - buttonSize * 1.5);
		done.setLayoutY(15 + 2 * buttonSize);
		done.setId("key");
		done.setStyle("-fx-font-size: " + Double.toString(cardTextSize) + "px; -fx-background-radius: "
				+ Double.toString(cardRadius / 3) + " " + Double.toString(cardRadius / 3) + " "
				+ Double.toString(cardRadius / 3) + " " + Double.toString(cardRadius / 3) + ";");
		done.setAlignment(Pos.CENTER);
		done.setOnMouseClicked(new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(50), (Label) event.getSource());
				scaleTransition.setByX(-0.1);
				scaleTransition.setByY(-0.1);
				scaleTransition.setOnFinished(finished -> {
					ScaleTransition scaleDoneTransition = new ScaleTransition(Duration.millis(50),
							(Label) event.getSource());
					scaleDoneTransition.setByX(0.1);
					scaleDoneTransition.setByY(0.1);
					if (((Label) cardPane.getChildren().get(0)).getText().length() > 0) {
						PathTransition keyboardPathTransition = new PathTransition();
						keyboardPathTransition.setDuration(Duration.millis(250));
						Path keyboardPath = new Path();
						keyboardPath.getElements().add(new MoveTo(screenWidth / 2, -screenHeight / 4));
						keyboardPath.getElements().add(new LineTo(screenWidth / 2, screenHeight / 4));
						keyboardPathTransition.setPath(keyboardPath);
						keyboardPathTransition.setNode(((Label) event.getSource()).getParent());
						keyboardPathTransition.setOnFinished(reset -> {
							setKeyboardVisible(false);
							;
						});
						keyboardPathTransition.play();
					}
					scaleDoneTransition.play();
				});
				scaleTransition.play();
			}
		});
		this.getChildren().add(delete);
		this.getChildren().add(done);
	}

	public boolean isKeyboardVisible() {
		return keyboardVisible;
	}

	public void setKeyboardVisible(boolean keyboardVisible) {
		this.keyboardVisible = keyboardVisible;
	}

}