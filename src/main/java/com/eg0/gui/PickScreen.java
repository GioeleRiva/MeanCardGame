package com.eg0.gui;

import java.util.ArrayList;

import com.eg0.network.Card;

import javafx.concurrent.Task;
import javafx.scene.layout.Pane;

public class PickScreen extends Pane {

	double screenWidth = 640;
	double screenHeight = 360;
	double cardHeight = (screenHeight * 9) / 10;
	double cardWidth = (cardHeight * 63) / 88;
	double cardRadius = (cardHeight) / 22;
	double cardBorder = (cardWidth * 13) / 126;
	double cardTextSize = (cardWidth * 9) / 100;

	public PickScreen() {
		this.setPrefSize(screenWidth, screenHeight);
		this.setId("background");
		Pane backgroundImage = new Pane();
		backgroundImage.setPrefSize(screenHeight, screenHeight);
		backgroundImage.setLayoutX(screenWidth - screenHeight);
		backgroundImage.setId("backgroundimage");
		this.getChildren().add(backgroundImage);
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
			CardPane black = new CardPane(blackCard);
			black.setLayoutX(screenHeight * 0.05);
			black.setLayoutY(screenHeight * 0.05);
			this.getChildren().add(black);
		});
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

	public void setPicks(ArrayList<ArrayList<Card>> picks) {
		// TODO
	}

}
