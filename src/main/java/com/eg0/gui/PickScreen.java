package com.eg0.gui;

import java.util.ArrayList;

import com.eg0.network.Card;
import com.eg0.network.Listener;

import javafx.animation.ScaleTransition;
import javafx.concurrent.Task;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class PickScreen extends Pane {

	double screenWidth = 640;
	double screenHeight = 360;
	double cardHeight = (screenHeight * 9) / 10;
	double cardWidth = (cardHeight * 63) / 88;
	double cardRadius = (cardHeight) / 22;
	double cardBorder = (cardWidth * 13) / 126;
	double cardTextSize = (cardWidth * 9) / 100;

	private ArrayList<ArrayList<Card>> picks = new ArrayList<>();
	public int pick = 0;

	Pane back;
	Pane forward;
	Button sendPicks;

	public PickScreen() {
		this.setPrefSize(screenWidth, screenHeight);
		this.setId("background");
		Pane backgroundImage = new Pane();
		backgroundImage.setPrefSize(screenHeight, screenHeight);
		backgroundImage.setLayoutX(screenWidth - screenHeight);
		backgroundImage.setId("backgroundimage");
		this.getChildren().add(backgroundImage);
		back = new Pane();
		back.setPrefSize(cardBorder * 3, cardBorder * 3);
		back.setLayoutX(screenHeight * 0.025);
		back.setLayoutY(screenHeight - screenHeight * 0.025 - cardBorder * 3);
		back.setId("backbutton");
		back.setVisible(false);
		this.getChildren().add(back);
		forward = new Pane();
		forward.setPrefSize(cardBorder * 3, cardBorder * 3);
		forward.setLayoutX(screenWidth - screenHeight * 0.025 - cardBorder * 3);
		forward.setLayoutY(screenHeight - screenHeight * 0.025 - cardBorder * 3);
		forward.setId("forwardbutton");
		forward.setVisible(false);
		this.getChildren().add(forward);
		sendPicks = new Button("Ok, you win.", "gameScreen");
		sendPicks.setLayoutX(screenWidth / 2 - cardWidth / 2);
		sendPicks.setLayoutY(screenHeight - screenHeight * 0.025 - cardBorder * 2);
		sendPicks.setVisible(false);
		sendPicks.setOnMouseClicked(send -> {
			if (!Main.somethingSelected) {
				Main.somethingSelected = true;
				sendPicks.setDisable(true);
				ScaleTransition scaleTransition1 = new ScaleTransition(Duration.millis(100), sendPicks);
				scaleTransition1.setByX(-0.1);
				scaleTransition1.setByY(-0.1);
				scaleTransition1.setOnFinished(play2 -> {
					ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(100), sendPicks);
					scaleTransition2.setByX(0.15);
					scaleTransition2.setByY(0.15);
					scaleTransition2.setOnFinished(play3 -> {
						ScaleTransition scaleTransition3 = new ScaleTransition(Duration.millis(100), sendPicks);
						scaleTransition3.setByX(-0.05);
						scaleTransition3.setByY(-0.05);
						scaleTransition3.setOnFinished(done -> {
							sendPicks.setDisable(false);
							Main.somethingSelected = false;
							Listener.sendWinner(pick);
						});
						scaleTransition3.play();
					});
					scaleTransition2.play();
				});
				scaleTransition1.play();
			}
		});
		this.getChildren().add(sendPicks);
	}

	public void setBlack(Card blackCard) {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				return null;
			}
		};
		task.setOnScheduled(add -> {
			for (int y = 0; y < this.getChildren().size(); y++) {
				if (this.getChildren().get(y).getId().equals("blackcard")
						|| this.getChildren().get(y).getId().equals("whitecard")) {
					this.getChildren().remove(y);
					y--;
				}
			}
			back.setVisible(false);
			forward.setVisible(false);
			sendPicks.setVisible(false);
			CardPane black = new CardPane(blackCard);
			black.setLayoutX(screenHeight * 0.05);
			black.setLayoutY(screenHeight * 0.05);
			this.getChildren().add(black);
		});
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	public void drawPicks(Card blackCard, boolean firstTime) {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				return null;
			}
		};
		task.setOnScheduled(add -> {
			for (int y = 0; y < this.getChildren().size(); y++) {
				if (this.getChildren().get(y).getId().equals("blackcard")
						|| this.getChildren().get(y).getId().equals("whitecard")) {
					this.getChildren().remove(y);
					y--;
				}
			}
			if (firstTime) {
				back.setVisible(false);
				forward.setVisible(false);
				sendPicks.setVisible(false);
			}
			double width = (screenWidth - screenHeight * 0.4) / 3;
			double height = (((screenWidth - screenHeight * 0.4) / 3) / cardWidth) * screenHeight;
			double radius = height / 22;
			double border = (width * 13) / 126;
			double text = (width * 9) / 100;
			CardPane black = new CardPane(blackCard);
			black.setPrefSize(width, height);
			black.setStyle("-fx-background-radius: " + Double.toString(radius) + " " + Double.toString(radius) + " "
					+ Double.toString(radius) + " " + Double.toString(radius) + ";");
			black.setLayoutX(screenHeight * 0.1);
			black.setLayoutY(screenHeight * 0.1);
			Label blackText = (Label) black.getChildren().get(0);
			blackText.setLayoutX(border);
			blackText.setLayoutY(border);
			blackText.setMaxSize(width - 2 * border, height - 2 * border);
			blackText.setStyle("-fx-font-size: " + Double.toString(text) + "px;");
			this.getChildren().add(black);
			CardPane card1 = new CardPane(picks.get(pick).get(0));
			card1.setPrefSize(width, height);
			card1.setStyle("-fx-background-radius: " + Double.toString(radius) + " " + Double.toString(radius) + " "
					+ Double.toString(radius) + " " + Double.toString(radius) + ";");
			card1.setLayoutX(screenHeight * 0.2 + (screenWidth - screenHeight * 0.4) / 3);
			card1.setLayoutY(screenHeight * 0.1);
			Label card1Text = (Label) card1.getChildren().get(0);
			card1Text.setLayoutX(border);
			card1Text.setLayoutY(border);
			card1Text.setMaxSize(width - 2 * border, height - 2 * border);
			card1Text.setStyle("-fx-font-size: " + Double.toString(text) + "px;");
			this.getChildren().add(card1);
			if (blackCard.getCardsRequired() == 2) {
				CardPane card2 = new CardPane(picks.get(pick).get(1));
				card2.setPrefSize(width, height);
				card2.setStyle("-fx-background-radius: " + Double.toString(radius) + " " + Double.toString(radius) + " "
						+ Double.toString(radius) + " " + Double.toString(radius) + ";");
				card2.setLayoutX(screenHeight * 0.3 + (screenWidth - screenHeight * 0.4) / 1.5);
				card2.setLayoutY(screenHeight * 0.1);
				Label card2Text = (Label) card2.getChildren().get(0);
				card2Text.setLayoutX(border);
				card2Text.setLayoutY(border);
				card2Text.setMaxSize(width - 2 * border, height - 2 * border);
				card2Text.setStyle("-fx-font-size: " + Double.toString(text) + "px;");
				this.getChildren().add(card2);
			}
			back.setOnMouseClicked(click1 -> {
				if (!Main.somethingSelected) {
					Main.somethingSelected = true;
					back.setDisable(true);
					ScaleTransition scaleTransition1 = new ScaleTransition(Duration.millis(100), back);
					scaleTransition1.setByX(-0.1);
					scaleTransition1.setByY(-0.1);
					scaleTransition1.setOnFinished(play2 -> {
						ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(100), back);
						scaleTransition2.setByX(0.15);
						scaleTransition2.setByY(0.15);
						scaleTransition2.setOnFinished(play3 -> {
							ScaleTransition scaleTransition3 = new ScaleTransition(Duration.millis(100), back);
							scaleTransition3.setByX(-0.05);
							scaleTransition3.setByY(-0.05);
							scaleTransition3.setOnFinished(done -> {
								back.setDisable(false);
								if (pick == 0) {
									pick = picks.size() - 1;
									drawPicks(blackCard, false);
								} else {
									pick--;
									drawPicks(blackCard, false);
								}
								Main.somethingSelected = false;
							});
							scaleTransition3.play();
						});
						scaleTransition2.play();
					});
					scaleTransition1.play();
				}
			});
			forward.setOnMouseClicked(click2 -> {
				if (!Main.somethingSelected) {
					Main.somethingSelected = true;
					forward.setDisable(true);
					ScaleTransition scaleTransition1 = new ScaleTransition(Duration.millis(100), forward);
					scaleTransition1.setByX(-0.1);
					scaleTransition1.setByY(-0.1);
					scaleTransition1.setOnFinished(play2 -> {
						ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(100), forward);
						scaleTransition2.setByX(0.15);
						scaleTransition2.setByY(0.15);
						scaleTransition2.setOnFinished(play3 -> {
							ScaleTransition scaleTransition3 = new ScaleTransition(Duration.millis(100), forward);
							scaleTransition3.setByX(-0.05);
							scaleTransition3.setByY(-0.05);
							scaleTransition3.setOnFinished(done -> {
								forward.setDisable(false);
								if (pick == picks.size() - 1) {
									pick = 0;
									drawPicks(blackCard, false);
								} else {
									pick++;
									drawPicks(blackCard, false);
								}
								Main.somethingSelected = false;
							});
							scaleTransition3.play();
						});
						scaleTransition2.play();
					});
					scaleTransition1.play();
				}
			});
			back.toFront();
			forward.toFront();
			back.setVisible(true);
			forward.setVisible(true);
		});

		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	public void setPicks(ArrayList<ArrayList<Card>> picks) {
		this.picks = picks;
	}

	public void askWinner() {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				return null;
			}
		};
		task.setOnScheduled(add -> {
			sendPicks.setVisible(true);
		});
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

}
