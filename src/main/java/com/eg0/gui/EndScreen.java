package com.eg0.gui;

import java.util.ArrayList;

import com.eg0.network.Card;
import com.eg0.network.Player;

import javafx.concurrent.Task;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class EndScreen extends Pane {

	double screenWidth = 640;
	double screenHeight = 360;
	double cardHeight = (screenHeight * 9) / 10;
	double cardWidth = (cardHeight * 63) / 88;
	double cardRadius = (cardHeight) / 22;
	double cardBorder = (cardWidth * 13) / 126;
	double cardTextSize = (cardWidth * 9) / 100;

	public EndScreen() {
		this.setPrefSize(screenWidth, screenHeight);
		this.setId("background");
		Pane backgroundImage = new Pane();
		backgroundImage.setPrefSize(screenHeight, screenHeight);
		backgroundImage.setLayoutX(screenWidth - screenHeight);
		backgroundImage.setId("backgroundimage");
		this.getChildren().add(backgroundImage);
	}

	public void showWin(ArrayList<Player> players) {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				return null;
			}
		};
		task.setOnScheduled(add -> {
			for (int x = 0; x < this.getChildren().size(); x++) {
				if (!this.getChildren().get(x).getId().equals("backgroundimage")) {
					this.getChildren().remove(x);
					x--;
				}
			}
			String temp = "";
			if (players.size() > 1) {
				temp = "WINNERS: ";
			} else {
				temp = "WINNER: ";
			}
			for (int x = 0; x < players.size(); x++) {
				temp = temp + players.get(x).getUserName();
				if (x != players.size() - 1) {
					temp = temp + ", ";
				}
			}
			Card card = new Card(temp, "black", 0);
			CardPane cardPane = new CardPane(card);
			cardPane.setLayoutX(screenWidth / 2 - cardWidth / 2);
			cardPane.setLayoutY(screenHeight * 0.05);
			this.getChildren().add(cardPane);
			Image image = new Image("/Tada.gif");
			ImageView imageView = new ImageView(image);
			imageView.setId("imageview");
			imageView.setFitWidth(screenWidth);
			imageView.setFitHeight(screenHeight);
			this.getChildren().add(imageView);
		});
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}

}
