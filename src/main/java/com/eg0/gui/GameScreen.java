package com.eg0.gui;

import java.util.ArrayList;
import java.util.Collections;

import com.eg0.network.Card;
import com.eg0.network.Listener;

import javafx.animation.PathTransition;
import javafx.animation.ScaleTransition;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class GameScreen extends Pane {

	static double screenWidth = Util.width;
	static double screenHeight = Util.height;
	double cardHeight = (screenHeight * 9) / 10;
	double cardWidth = (cardHeight * 63) / 88;
	double cardRadius = (cardHeight) / 22;
	double cardBorder = (cardWidth * 13) / 126;
	double cardTextSize = (cardWidth * 9) / 100;

	Button send;

	private ArrayList<Integer> selectedCards = new ArrayList<>();

	public GameScreen() {
		this.setPrefSize(screenWidth, screenHeight);
		this.setId("background");
		Pane backgroundImage = new Pane();
		backgroundImage.setPrefSize(screenHeight, screenHeight);
		backgroundImage.setLayoutX(screenWidth - screenHeight);
		backgroundImage.setId("backgroundimage");
		this.getChildren().add(backgroundImage);
		send = new Button("Send picks.", null);
		send.setLayoutX(screenWidth - screenHeight * 0.05 - cardWidth);
		send.setLayoutY(screenHeight * 0.05);
		this.getChildren().add(send);
		send.setVisible(false);
	}

	public void drawCards(ArrayList<Card> whiteCards) {
		Main.whiteCards = whiteCards;
		Task<ArrayList<CardPane>> task = new Task<ArrayList<CardPane>>() {
			@Override
			protected ArrayList<CardPane> call() throws Exception {
				ArrayList<CardPane> cards = new ArrayList<>();
				for (int x = 0; x < whiteCards.size(); x++) {
					CardPane card = new CardPane(whiteCards.get(x));
					card.setCache(true);
					double temp = (screenWidth - cardWidth - screenHeight * 0.1) / 9;
					card.setLayoutX(screenHeight * 0.05 + temp * x);
					card.setLayoutY(screenHeight * 0.7);
					Delta dragDelta = new Delta();
					double originx = card.getLayoutX();
					double originy = card.getLayoutY();
					card.setOnMousePressed(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent mouseEvent) {
							dragDelta.x = card.getLayoutX() - mouseEvent.getSceneX();
							dragDelta.y = card.getLayoutY() - mouseEvent.getSceneY();
						}
					});
					card.setOnMouseReleased(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent mouseEvent) {
							card.setLayoutX(originx);
							card.setLayoutY(originy);
						}
					});
					card.setOnMouseDragged(new EventHandler<MouseEvent>() {
						@Override
						public void handle(MouseEvent mouseEvent) {
							card.setLayoutX(mouseEvent.getSceneX() + dragDelta.x);
							card.setLayoutY(mouseEvent.getSceneY() + dragDelta.y);
						}
					});
					PathTransition util = new PathTransition();
					util.setDuration(Duration.millis(200 + 200 * x));
					Path utilPath = new Path();
					utilPath.getElements().add(new MoveTo(cardWidth / 2, cardHeight / 2 + screenHeight * 0.9));
					utilPath.getElements().add(new LineTo(cardWidth / 2, cardHeight / 2 + screenHeight * 0.9));
					util.setPath(utilPath);
					util.setNode(card);
					util.setOnFinished(utilDone -> {
						PathTransition cardPathTransition = new PathTransition();
						cardPathTransition.setDuration(Duration.millis(250));
						Path cardPath = new Path();
						cardPath.getElements().add(new MoveTo(cardWidth / 2, cardHeight / 2 + screenHeight * 0.9));
						cardPath.getElements().add(new LineTo(cardWidth / 2, cardHeight / 2));
						cardPathTransition.setPath(cardPath);
						cardPathTransition.setNode(card);
						cardPathTransition.play();
					});
					util.play();
					cards.add(card);
				}
				return cards;
			}
		};
		task.setOnSucceeded(draw -> {
			for (int y = 0; y < this.getChildren().size(); y++) {
				if (this.getChildren().get(y).getId().equals("whitecard")
						|| this.getChildren().get(y).getId().equals("whitecardselected")) {
					this.getChildren().remove(y);
					y--;
				}
			}
			for (int x = 0; x < task.getValue().size(); x++) {
				this.getChildren().add(task.getValue().get(x));
			}
		});
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	public void askPicks(int picksNum) {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				return null;
			}
		};
		task.setOnSucceeded(ask -> {
			send.setVisible(true);
			send.setOnMouseClicked(clicked1 -> {
				if (!Main.somethingSelected) {
					Main.somethingSelected = true;
					send.setDisable(true);
					ScaleTransition scaleTransition1 = new ScaleTransition(Duration.millis(100), send);
					scaleTransition1.setByX(-0.1);
					scaleTransition1.setByY(-0.1);
					scaleTransition1.setOnFinished(play2 -> {
						ScaleTransition scaleTransition2 = new ScaleTransition(Duration.millis(100), send);
						scaleTransition2.setByX(0.15);
						scaleTransition2.setByY(0.15);
						scaleTransition2.setOnFinished(play3 -> {
							ScaleTransition scaleTransition3 = new ScaleTransition(Duration.millis(100), send);
							scaleTransition3.setByX(-0.05);
							scaleTransition3.setByY(-0.05);
							scaleTransition3.setOnFinished(done -> {
								send.setDisable(false);
								if (selectedCards.size() < picksNum) {
									for (int i = 0; i < this.getChildren().size(); i++) {
										if (this.getChildren().get(i).getId().equals("whitecard")) {
											CardPane card = (CardPane) this.getChildren().get(i);
											card.setOnMouseClicked(clicked2 -> {
												int temp = 0;
												for (int x = 0; x < Main.whiteCards.size(); x++) {
													if (Main.whiteCards.get(x).getText()
															.equals(((Label) card.getChildren().get(0)).getText())) {
														temp = x;
													}
												}
												if (selectedCards.contains(temp)) {
													for (int x = 0; x < selectedCards.size(); x++) {
														if (selectedCards.get(x).equals(temp)) {
															selectedCards.remove(x);
														}
													}
													card.setId("whitecard");
												} else {
													if (selectedCards.size() < picksNum) {
														selectedCards.add(temp);
														card.setId("whitecardselected");
													}
												}
											});
										}
									}
								}
								if (selectedCards.size() == picksNum) {
									ArrayList<Card> cards = new ArrayList<>();
									if (picksNum == 1) {
										Card card = Main.whiteCards.get(selectedCards.get(0));
										cards.add(card);
										int temp = selectedCards.get(0);
										Main.whiteCards.remove(temp);
									}
									if (picksNum == 2) {
										Card card1 = Main.whiteCards.get(selectedCards.get(0));
										cards.add(card1);
										Card card2 = Main.whiteCards.get(selectedCards.get(1));
										cards.add(card2);
										Collections.sort(selectedCards);
										for (int x = 0; x < selectedCards.size(); x++) {
											int temp1 = selectedCards.get(x);
											Main.whiteCards.remove(temp1);
											for (int y = x + 1; y < selectedCards.size(); y++) {
												int temp2 = selectedCards.get(y) - 1;
												selectedCards.remove(y);
												selectedCards.add(y, temp2);
											}
										}
									}
									if (picksNum == 3) {
										Card card1 = Main.whiteCards.get(selectedCards.get(0));
										cards.add(card1);
										Card card2 = Main.whiteCards.get(selectedCards.get(1));
										cards.add(card2);
										Card card3 = Main.whiteCards.get(selectedCards.get(2));
										cards.add(card3);
										Collections.sort(selectedCards);
										for (int x = 0; x < selectedCards.size(); x++) {
											int temp1 = selectedCards.get(x);
											Main.whiteCards.remove(temp1);
											for (int y = x + 1; y < selectedCards.size(); y++) {
												int temp2 = selectedCards.get(y) - 1;
												selectedCards.remove(y);
												selectedCards.add(y, temp2);
											}
										}
									}
									Listener.sendPicks(cards);
									selectedCards.clear();
									send.setVisible(false);
									drawCards(Main.whiteCards);
								}
							});
							scaleTransition3.play();
						});
						scaleTransition2.play();
					});
					scaleTransition1.play();
					Main.somethingSelected = false;
				}
			});

		});
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

}