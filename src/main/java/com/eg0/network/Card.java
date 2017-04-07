package com.eg0.network;

import java.io.Serializable;

public class Card implements Serializable {

	private static final long serialVersionUID = -8195647694826848078L;

	private String text;
	private String color;
	private int cardsRequired;

	public Card(String text, String color, int cardsRequired) {
		this.text = text;
		this.color = color;
		this.cardsRequired = cardsRequired;
	}

	public String getText() {
		return text;
	}

	public String getColor() {
		return color;
	}

	public int getCardsRequired() {
		return cardsRequired;
	}

}