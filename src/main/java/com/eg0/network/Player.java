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
	private int wins;

	public Player(String gameCode, String userName, int wins, ObjectOutputStream objectOutputStream) {
		this.gameCode = gameCode;
		this.userName = userName;
		this.wins = wins;
		this.objectOutputStream = objectOutputStream;
	}

	public String getGameCode() {
		return gameCode;
	}

	public String getUserName() {
		return userName;
	}

	public int getWins() {
		return wins;
	}

	public void addWin() {
		wins++;
	}

	public ObjectOutputStream getObjectOutputStream() {
		return objectOutputStream;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

}