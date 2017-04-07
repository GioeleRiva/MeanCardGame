package com.eg0.gui;

import java.util.ArrayList;

import com.eg0.network.Card;
import com.eg0.network.Listener;
import com.eg0.network.Player;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class RoomScreen extends Pane {

	double screenWidth = 640;
	double screenHeight = 360;
	double cardHeight = (screenHeight * 9) / 10;
	double cardWidth = (cardHeight * 63) / 88;
	double cardRadius = (cardHeight) / 22;
	double cardBorder = (cardWidth * 13) / 126;
	double cardTextSize = (cardWidth * 9) / 100;

	Button play;

	boolean codeVisible = false;
	boolean playVisible = false;

	public RoomScreen() {
		this.setPrefSize(screenWidth, screenHeight);
		this.setId("background");
		Pane backgroundImage = new Pane();
		backgroundImage.setPrefSize(screenHeight, screenHeight);
		backgroundImage.setLayoutX(screenWidth - screenHeight);
		backgroundImage.setId("backgroundimage");
		this.getChildren().add(backgroundImage);
		Card bCard = new Card("Players: _______ .", "black", 0);
		CardPane blackCard = new CardPane(bCard);
		blackCard.setLayoutX(screenHeight * 0.05);
		blackCard.setLayoutY(screenHeight * 0.05);
		this.getChildren().add(blackCard);
	}

	public void playButton() {
		if (!Main.roomCode.equals("") && !codeVisible) {
			codeVisible = true;
			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					return null;
				}
			};
			task.setOnSucceeded(code -> {
				Label label = new Label(Main.roomCode);
				label.setPrefSize(cardWidth / 2, 2 * cardBorder);
				label.setLayoutX(screenWidth / 2 - cardWidth / 4);
				label.setLayoutY(screenHeight * 0.05);
				label.setId("buttontext");
				label.setAlignment(Pos.CENTER);
				label.setStyle("-fx-background-radius: " + Double.toString(cardRadius) + " "
						+ Double.toString(cardRadius) + " " + Double.toString(cardRadius) + " "
						+ Double.toString(cardRadius) + "; -fx-background-color: black; -fx-font-size: "
						+ Double.toString(cardTextSize) + "px;");
				this.getChildren().add(label);
			});
			Thread thread = new Thread(task);
			thread.setDaemon(true);
			thread.start();
		}
		if (!Main.roomCode.equals("") && Main.players.size() > 2) {
			playVisible = true;
			Task<Void> task = new Task<Void>() {
				@Override
				protected Void call() throws Exception {
					return null;
				}
			};
			task.setOnSucceeded(add -> {
				play = new Button("Play.", "gameScreen");
				play.setLayoutX(screenWidth - screenHeight * 0.05 - cardWidth);
				play.setLayoutY(screenHeight * 0.05);
				FadeTransition fadeTransition = new FadeTransition(Duration.millis(500));
				fadeTransition.setFromValue(0);
				fadeTransition.setToValue(1);
				fadeTransition.setNode(play);
				fadeTransition.play();
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
									Listener.startGame();
								});
								scaleTransition3.play();
							});
							scaleTransition2.play();
						});
						scaleTransition1.play();
					}
				});
				this.getChildren().add(play);
			});
			Thread thread = new Thread(task);
			thread.setDaemon(true);
			thread.start();
		} else {
			if (playVisible) {
				Task<Void> task = new Task<Void>() {
					@Override
					protected Void call() throws Exception {
						return null;
					}
				};
				task.setOnSucceeded(remove -> {
					this.getChildren().remove(play);
				});
				playVisible = false;
				Thread thread = new Thread(task);
				thread.setDaemon(true);
				thread.start();
			}
		}
	}

	public void drawPlayers(ArrayList<Player> players) {
		int old = Main.players.size();
		Main.players = players;
		Task<ArrayList<CardPane>> task = new Task<ArrayList<CardPane>>() {
			@Override
			protected ArrayList<CardPane> call() throws Exception {
				ArrayList<CardPane> cards = new ArrayList<>();
				for (int x = 0; x < players.size(); x++) {
					Card card = new Card(players.get(x).getUserName(), "white", 0);
					CardPane cardPane = new CardPane(card);
					if (x > old - 1) {
						FadeTransition fadeTransition = new FadeTransition(Duration.millis(500));
						fadeTransition.setFromValue(0);
						fadeTransition.setToValue(1);
						fadeTransition.setNode(cardPane);
						fadeTransition.play();
					}
					cards.add(cardPane);
				}
				return cards;
			}
		};
		task.setOnSucceeded(draw -> {
			for (int x = 0; x < this.getChildren().size(); x++) {
				if (this.getChildren().get(x).equals("whitecard")) {
					this.getChildren().remove(x);
					x--;
				}
			}
			for (int x = 0; x < task.getValue().size(); x++) {
				int temp1 = x % 2;
				int temp2 = ((int) x / 2);
				task.getValue().get(x)
						.setLayoutX(screenHeight * 0.1 + screenHeight * 0.1 * temp2 + screenHeight * 0.7 * temp1);
				task.getValue().get(x).setLayoutY(screenHeight * 0.2 + screenHeight * 0.15 * temp2);
				this.getChildren().add(task.getValue().get(x));
			}
		});
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

}