package com.eg0.network;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {

	private static final long serialVersionUID = -7551775022560609490L;

	private MessageType messageType;
	private String userName;
	private String roomCode;
	private ArrayList<Player> players;
	private ArrayList<Card> whiteCards;

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoomCode() {
		return roomCode;
	}

	public void setRoomCode(String roomCode) {
		this.roomCode = roomCode;
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}

	public ArrayList<Card> getWhiteCards() {
		return whiteCards;
	}

	public void setWhiteCards(ArrayList<Card> whiteCards) {
		this.whiteCards = whiteCards;
	}

}