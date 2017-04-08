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
	private Card blackCard;
	private ArrayList<ArrayList<Card>> picks;

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

	public Card getBlackCard() {
		return blackCard;
	}

	public void setBlackCard(Card blackCard) {
		this.blackCard = blackCard;
	}

	public ArrayList<ArrayList<Card>> getPicks() {
		return picks;
	}

	public void setPicks(ArrayList<ArrayList<Card>> picks) {
		this.picks = picks;
	}

}