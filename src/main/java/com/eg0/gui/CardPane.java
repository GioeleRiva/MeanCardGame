package com.eg0.gui;

import com.eg0.network.Card;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class CardPane extends Pane {

	static double screenWidth = Screen.width;
	static double screenHeight = Screen.height;
	double cardHeight = (screenHeight * 9) / 10;
	double cardWidth = (cardHeight * 63) / 88;
	double cardRadius = (cardHeight) / 22;
	double cardBorder = (cardWidth * 13) / 126;
	double cardTextSize = (cardWidth * 9) / 100;

	public CardPane(Card card) {
		this.setPrefSize(cardWidth, cardHeight);
		this.setStyle("-fx-background-radius: " + Double.toString(cardRadius) + " " + Double.toString(cardRadius) + " "
				+ Double.toString(cardRadius) + " " + Double.toString(cardRadius) + ";");
		Label cardText = new Label(card.getText());
		cardText.setMaxSize(cardWidth - 2 * cardBorder, cardHeight - 2 * cardBorder);
		cardText.setLayoutX(cardBorder);
		cardText.setLayoutY(cardBorder);
		cardText.setWrapText(true);
		cardText.setAlignment(Pos.TOP_LEFT);
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

}