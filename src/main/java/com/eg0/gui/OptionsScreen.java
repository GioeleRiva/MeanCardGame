package com.eg0.gui;

import javafx.animation.ScaleTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class OptionsScreen extends Pane {

	static double screenWidth = Screen.width;
	static double screenHeight = Screen.height;
	double cardHeight = (screenHeight * 9) / 10;
	double cardWidth = (cardHeight * 63) / 88;
	double cardRadius = (cardHeight) / 22;
	double cardBorder = (cardWidth * 13) / 126;
	double cardTextSize = (cardWidth * 9) / 100;

	Button num1;
	Button num2;

	public OptionsScreen() {
		this.setPrefSize(screenWidth, screenHeight);
		this.setId("background");
		Pane backgroundImage = new Pane();
		backgroundImage.setPrefSize(screenHeight, screenHeight);
		backgroundImage.setLayoutX(screenWidth - screenHeight);
		backgroundImage.setId("backgroundimage");
		this.getChildren().add(backgroundImage);
		Button wins = new Button("End after __ wins.", null);
		wins.setLayoutX(screenHeight * 0.05);
		wins.setLayoutY(screenHeight * 0.05);
		wins.setOnMouseClicked(null);
		((Label) wins.getChildren().get(0)).setAlignment(Pos.CENTER);
		this.getChildren().add(wins);
		Pane back1 = new Pane();
		back1.setPrefSize(cardBorder, 2 * cardBorder);
		back1.setLayoutX(screenHeight * 0.1 + cardWidth);
		back1.setLayoutY(screenHeight * 0.05);
		back1.setId("backbutton");
		this.getChildren().add(back1);
		num1 = new Button("10", null);
		num1.setPrefWidth(cardWidth / 2);
		num1.setLayoutX(screenHeight * 0.15 + cardWidth + cardBorder);
		num1.setLayoutY(screenHeight * 0.05);
		num1.setOnMouseClicked(null);
		this.getChildren().add(num1);
		Pane forw1 = new Pane();
		forw1.setPrefSize(cardBorder, 2 * cardBorder);
		forw1.setLayoutX(screenHeight * 0.2 + cardWidth + cardBorder + cardWidth / 2);
		forw1.setLayoutY(screenHeight * 0.05);
		forw1.setId("forwardbutton");
		this.getChildren().add(forw1);
		Button turns = new Button("End after __ turns.", null);
		turns.setLayoutX(screenHeight * 0.05);
		turns.setLayoutY(screenHeight * 0.1 + cardBorder * 2);
		turns.setOnMouseClicked(null);
		((Label) turns.getChildren().get(0)).setAlignment(Pos.CENTER);
		this.getChildren().add(turns);
		Pane back2 = new Pane();
		back2.setPrefSize(cardBorder, 2 * cardBorder);
		back2.setLayoutX(screenHeight * 0.1 + cardWidth);
		back2.setLayoutY(screenHeight * 0.1 + cardBorder * 2);
		back2.setId("backbutton");
		this.getChildren().add(back2);
		num2 = new Button("-", null);
		num2.setPrefWidth(cardWidth / 2);
		num2.setLayoutX(screenHeight * 0.15 + cardWidth + cardBorder);
		num2.setLayoutY(screenHeight * 0.1 + cardBorder * 2);
		num2.setOnMouseClicked(null);
		this.getChildren().add(num2);
		Pane forw2 = new Pane();
		forw2.setPrefSize(cardBorder, 2 * cardBorder);
		forw2.setLayoutX(screenHeight * 0.2 + cardWidth + cardBorder + cardWidth / 2);
		forw2.setLayoutY(screenHeight * 0.1 + cardBorder * 2);
		forw2.setId("forwardbutton");
		this.getChildren().add(forw2);
		Button back = new Button("Back.", "homeScreen");
		back.setLayoutX(screenHeight * 0.05);
		back.setLayoutY(screenHeight * 0.15 + cardBorder * 4);
		this.getChildren().add(back);
		Label l1 = (Label) num1.getChildren().get(0);
		Label l2 = (Label) num2.getChildren().get(0);
		back1.setOnMouseClicked(b1 -> {
			ScaleTransition scaleTransition1 = new ScaleTransition(Duration.millis(100), back1);
			scaleTransition1.setByX(-0.1);
			scaleTransition1.setByY(-0.1);
			scaleTransition1.setOnFinished(play2 -> {
				ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(100), back1);
				scaleTransition2.setByX(0.15);
				scaleTransition2.setByY(0.15);
				scaleTransition2.setOnFinished(play3 -> {
					ScaleTransition scaleTransition3 = new ScaleTransition(Duration.millis(100), back1);
					scaleTransition3.setByX(-0.05);
					scaleTransition3.setByY(-0.05);
					scaleTransition3.setOnFinished(done -> {
						if (!l1.getText().equals("1") && !l1.getText().equals("-")) {
							l1.setText(Integer.toString(Integer.valueOf(l1.getText()) - 1));
						} else {
							l1.setText("-");
							l2.setText("10");
						}
					});
					scaleTransition3.play();
				});
				scaleTransition2.play();
			});
			scaleTransition1.play();
		});
		forw1.setOnMouseClicked(f1 -> {
			ScaleTransition scaleTransition1 = new ScaleTransition(Duration.millis(100), forw1);
			scaleTransition1.setByX(-0.1);
			scaleTransition1.setByY(-0.1);
			scaleTransition1.setOnFinished(play2 -> {
				ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(100), forw1);
				scaleTransition2.setByX(0.15);
				scaleTransition2.setByY(0.15);
				scaleTransition2.setOnFinished(play3 -> {
					ScaleTransition scaleTransition3 = new ScaleTransition(Duration.millis(100), forw1);
					scaleTransition3.setByX(-0.05);
					scaleTransition3.setByY(-0.05);
					scaleTransition3.setOnFinished(done -> {
						if (l1.getText().equals("-")) {
							l1.setText(Integer.toString(1));
							l2.setText("-");
						} else {
							if (!l1.getText().equals("99")) {
								l1.setText(Integer.toString(Integer.valueOf(l1.getText()) + 1));
							} else {
							}
						}
					});
					scaleTransition3.play();
				});
				scaleTransition2.play();
			});
			scaleTransition1.play();
		});
		back2.setOnMouseClicked(b2 -> {
			ScaleTransition scaleTransition1 = new ScaleTransition(Duration.millis(100), back2);
			scaleTransition1.setByX(-0.1);
			scaleTransition1.setByY(-0.1);
			scaleTransition1.setOnFinished(play2 -> {
				ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(100), back2);
				scaleTransition2.setByX(0.15);
				scaleTransition2.setByY(0.15);
				scaleTransition2.setOnFinished(play3 -> {
					ScaleTransition scaleTransition3 = new ScaleTransition(Duration.millis(100), back2);
					scaleTransition3.setByX(-0.05);
					scaleTransition3.setByY(-0.05);
					scaleTransition3.setOnFinished(done -> {
						if (!l2.getText().equals("1") && !l2.getText().equals("-")) {
							l2.setText(Integer.toString(Integer.valueOf(l2.getText()) - 1));
						} else {
							l2.setText("-");
							l1.setText("10");
						}
					});
					scaleTransition3.play();
				});
				scaleTransition2.play();
			});
			scaleTransition1.play();
		});
		forw2.setOnMouseClicked(f2 -> {
			ScaleTransition scaleTransition1 = new ScaleTransition(Duration.millis(100), forw2);
			scaleTransition1.setByX(-0.1);
			scaleTransition1.setByY(-0.1);
			scaleTransition1.setOnFinished(play2 -> {
				ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(100), forw2);
				scaleTransition2.setByX(0.15);
				scaleTransition2.setByY(0.15);
				scaleTransition2.setOnFinished(play3 -> {
					ScaleTransition scaleTransition3 = new ScaleTransition(Duration.millis(100), forw2);
					scaleTransition3.setByX(-0.05);
					scaleTransition3.setByY(-0.05);
					scaleTransition3.setOnFinished(done -> {
						if (l2.getText().equals("-")) {
							l2.setText(Integer.toString(1));
							l1.setText("-");
						} else {
							if (!l2.getText().equals("99")) {
								l2.setText(Integer.toString(Integer.valueOf(l2.getText()) + 1));
							} else {
							}
						}
					});
					scaleTransition3.play();
				});
				scaleTransition2.play();
			});
			scaleTransition1.play();
		});
	}

}