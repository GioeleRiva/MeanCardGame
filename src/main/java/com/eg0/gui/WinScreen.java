package com.eg0.gui;

import java.util.ArrayList;

import com.eg0.network.Card;

import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class WinScreen extends Pane {

	static double screenWidth = Util.width;
	static double screenHeight = Util.height;
	double cardHeight = (screenHeight * 9) / 10;
	double cardWidth = (cardHeight * 63) / 88;
	double cardRadius = (cardHeight) / 22;
	double cardBorder = (cardWidth * 13) / 126;
	double cardTextSize = (cardWidth * 9) / 100;

	public WinScreen() {
		this.setPrefSize(screenWidth, screenHeight);
		this.setId("background");
		Pane backgroundImage = new Pane();
		backgroundImage.setPrefSize(screenHeight, screenHeight);
		backgroundImage.setLayoutX(screenWidth - screenHeight);
		backgroundImage.setId("backgroundimage");
		this.getChildren().add(backgroundImage);
	}

	public void showWin(Card blackCard, ArrayList<Card> pick, String playerName) {
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
			double width = (screenWidth - screenHeight * 0.2) / 3;
			double height = (((screenWidth - screenHeight * 0.2) / 3) / cardWidth) * cardHeight;
			if (blackCard.getCardsRequired() == 3) {
				width = (screenWidth - screenHeight * 0.25) / 4;
				height = (((screenWidth - screenHeight * 0.25) / 4) / cardWidth) * cardHeight;
			}
			double radius = height / 22;
			double border = (width * 13) / 126;
			double text = (width * 9) / 100;
			CardPane black = new CardPane(blackCard);
			black.setPrefSize(width, height);
			black.setStyle("-fx-background-radius: " + Double.toString(radius) + " " + Double.toString(radius) + " "
					+ Double.toString(radius) + " " + Double.toString(radius) + ";");
			black.setLayoutX(screenHeight * 0.05);
			black.setLayoutY(screenHeight * 0.05);
			Label blackText = (Label) black.getChildren().get(0);
			blackText.setLayoutX(border);
			blackText.setLayoutY(border);
			blackText.setMaxSize(width - 2 * border, height - 2 * border);
			blackText.setStyle("-fx-font-size: " + Double.toString(text) + "px;");
			this.getChildren().add(black);
			CardPane card1 = new CardPane(pick.get(0));
			card1.setPrefSize(width, height);
			card1.setStyle("-fx-background-radius: " + Double.toString(radius) + " " + Double.toString(radius) + " "
					+ Double.toString(radius) + " " + Double.toString(radius) + ";");
			card1.setLayoutX(screenHeight * 0.1 + width);
			card1.setLayoutY(screenHeight * 0.05);
			Label card1Text = (Label) card1.getChildren().get(0);
			card1Text.setLayoutX(border);
			card1Text.setLayoutY(border);
			card1Text.setMaxSize(width - 2 * border, height - 2 * border);
			card1Text.setStyle("-fx-font-size: " + Double.toString(text) + "px;");
			this.getChildren().add(card1);
			if (blackCard.getCardsRequired() > 1) {
				CardPane card2 = new CardPane(pick.get(1));
				card2.setPrefSize(width, height);
				card2.setStyle("-fx-background-radius: " + Double.toString(radius) + " " + Double.toString(radius) + " "
						+ Double.toString(radius) + " " + Double.toString(radius) + ";");
				card2.setLayoutX(screenHeight * 0.15 + 2 * width);
				card2.setLayoutY(screenHeight * 0.05);
				Label card2Text = (Label) card2.getChildren().get(0);
				card2Text.setLayoutX(border);
				card2Text.setLayoutY(border);
				card2Text.setMaxSize(width - 2 * border, height - 2 * border);
				card2Text.setStyle("-fx-font-size: " + Double.toString(text) + "px;");
				this.getChildren().add(card2);
			}
			if (blackCard.getCardsRequired() == 3) {
				CardPane card3 = new CardPane(pick.get(1));
				card3.setPrefSize(width, height);
				card3.setStyle("-fx-background-radius: " + Double.toString(radius) + " " + Double.toString(radius) + " "
						+ Double.toString(radius) + " " + Double.toString(radius) + ";");
				card3.setLayoutX(screenHeight * 0.2 + 3 * width);
				card3.setLayoutY(screenHeight * 0.05);
				Label card3Text = (Label) card3.getChildren().get(0);
				card3Text.setLayoutX(border);
				card3Text.setLayoutY(border);
				card3Text.setMaxSize(width - 2 * border, height - 2 * border);
				card3Text.setStyle("-fx-font-size: " + Double.toString(text) + "px;");
				this.getChildren().add(card3);
			}
			Label label = new Label(playerName + " WINS!");
			label.setPrefSize(screenWidth / 2, cardBorder * 2);
			label.setAlignment(Pos.CENTER);
			label.setLayoutX(screenWidth / 4);
			label.setLayoutY(screenHeight - screenHeight * 0.05 - 2 * cardBorder);
			label.setId("buttontext");
			label.setStyle("-fx-background-radius: " + Double.toString(radius) + " " + Double.toString(radius) + " "
					+ Double.toString(radius) + " " + Double.toString(radius)
					+ "; -fx-background-color: black; -fx-font-size: " + Double.toString(cardTextSize) + "px;");
			this.getChildren().add(label);
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