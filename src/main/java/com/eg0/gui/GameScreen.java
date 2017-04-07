package com.eg0.gui;

import java.util.ArrayList;

import com.eg0.network.Card;

import javafx.animation.PathTransition;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

public class GameScreen extends Pane {

	double screenWidth = 640;
	double screenHeight = 360;
	double cardHeight = (screenHeight * 9) / 10;
	double cardWidth = (cardHeight * 63) / 88;
	double cardRadius = (cardHeight) / 22;
	double cardBorder = (cardWidth * 13) / 126;
	double cardTextSize = (cardWidth * 9) / 100;

	public GameScreen() {
		this.setPrefSize(screenWidth, screenHeight);
		this.setId("background");
		Pane backgroundImage = new Pane();
		backgroundImage.setPrefSize(screenHeight, screenHeight);
		backgroundImage.setLayoutX(screenWidth - screenHeight);
		backgroundImage.setId("backgroundimage");
		this.getChildren().add(backgroundImage);
	}

	public void drawCards(ArrayList<Card> whiteCards) {
		int old = Main.whiteCards.size();
		Main.whiteCards = whiteCards;
		Task<ArrayList<CardPane>> task = new Task<ArrayList<CardPane>>() {
			@Override
			protected ArrayList<CardPane> call() throws Exception {
				ArrayList<CardPane> cards = new ArrayList<>();
				for (int x = 0; x < whiteCards.size(); x++) {
					CardPane card = new CardPane(whiteCards.get(x));
					card.setCache(true);
					double temp = (screenWidth - cardWidth - screenHeight * 0.1) / 9;
					card.setLayoutX(screenHeight * 0.05 + temp * old + temp * x);
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
			for (int x = 0; x < task.getValue().size(); x++) {
				this.getChildren().add(task.getValue().get(x));
			}
		});
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

}