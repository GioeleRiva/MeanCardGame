package com.eg0.network;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {

	private static final long serialVersionUID = 3015565713726182942L;

	private String gameCode;
	private String userName;
	private transient ObjectOutputStream objectOutputStream;
	private ArrayList<Card> cards = new ArrayList<>();

	public Player(String gameCode, String userName, ObjectOutputStream objectOutputStream) {
		this.gameCode = gameCode;
		this.userName = userName;
		this.objectOutputStream = objectOutputStream;
	}

	public String getGameCode() {
		return gameCode;
	}

	public String getUserName() {
		return userName;
	}

	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

}