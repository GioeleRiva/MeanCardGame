package com.eg0.gui;

import com.eg0.network.Card;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class CardPane extends Pane {

	static double screenWidth = 640;
	static double screenHeight = 360;
	static double cardHeight = (screenHeight * 9) / 10;
	static double cardWidth = (cardHeight * 63) / 88;
	static double cardRadius = (cardHeight) / 22;
	static double cardBorder = (cardWidth * 13) / 126;
	static double cardTextSize = (cardWidth * 9) / 100;

	public CardPane(Card card) {
		this.setPrefSize(cardWidth, cardHeight);
		this.setStyle("-fx-background-radius: " + Double.toString(cardRadius) + " " + Double.toString(cardRadius) + " "
				+ Double.toString(cardRadius) + " " + Double.toString(cardRadius) + ";");
		Label cardText = new Label(card.getText());
		cardText.setMaxSize(cardWidth - 2 * cardBorder, cardHeight - 2 * cardBorder);
		cardText.setLayoutX(cardBorder);
		cardText.setLayoutY(cardBorder);
		cardText.setWrapText(true);
		if (card.getColor().equals("white")) {
			this.setId("whitecard");
			cardText.setId("whitecardtext");
		} else {
			this.setId("blackcard");
			cardText.setId("blackcardtext");
		}
		cardText.setStyle("-fx-font-size: " + Double.toString(cardTextSize) + "px;");
		this.getChildren().add(cardText);
	}

	public double getCenterX() {
		return cardWidth / 2;
	}

	public double getCenterY() {
		return cardHeight / 2;
	}

	public double width() {
		return cardWidth;
	}

	public double height() {
		return cardHeight;
	}

}