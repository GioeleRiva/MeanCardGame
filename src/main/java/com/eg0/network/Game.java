package com.eg0.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collections;

public class Game {

	private ArrayList<Player> players = new ArrayList<>();
	private String gameCode;
	private int turn = 0;
	private static ArrayList<Card> whiteCards = new ArrayList<>();
	private static ArrayList<Card> blackCards = new ArrayList<>();
	private static Card currentBlack;
	private ArrayList<ArrayList<Card>> picks = new ArrayList<>();
	private ArrayList<Player> pickPlayers = new ArrayList<>();
	private int playersInGame = 0;
	private int wins;
	private int turns;
	private int turnsPassed = 0;

	public Game(String gameCode, String wins, String turns) {
		this.gameCode = gameCode;
		if (!wins.equals("-")) {
			this.wins = Integer.valueOf(wins);
		}
		if (!turns.equals("-")) {
			this.turns = Integer.valueOf(turns);
		}
		try {
			setWhiteCards();
			setBlackCards();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public String getGameCode() {
		return gameCode;
	}

	public void nextTurn() {
		if (getTurn() < players.size() - 1) {
			setTurn(getTurn() + 1);
		} else {
			setTurn(0);
		}
	}

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public void shufflePlayers() {
		Collections.shuffle(players);
		Collections.shuffle(players);
	}

	public synchronized Card getWhiteCard() {
		Card card = whiteCards.get(0);
		whiteCards.remove(0);
		return card;
	}

	public void setWhiteCards() throws Exception {
		LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(new File("WhiteCards.txt")));
		lineNumberReader.skip(Long.MAX_VALUE);
		long lines = lineNumberReader.getLineNumber();
		lineNumberReader.close();
		@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(new FileReader(new File("WhiteCards.txt")));
		for (int i = 0; i < lines + 1; i++) {
			String text = in.readLine();
			Card card = new Card(text, "white", 0);
			whiteCards.add(card);
		}
		Collections.shuffle(whiteCards);
		Collections.shuffle(whiteCards);
		Collections.shuffle(whiteCards);
	}

	public synchronized Card getNextBlackCard() {
		Card card = blackCards.get(0);
		currentBlack = card;
		blackCards.remove(0);
		return card;
	}

	public void setBlackCards() throws Exception {
		LineNumberReader lineNumberReader = new LineNumberReader(new FileReader(new File("BlackCards.txt")));
		lineNumberReader.skip(Long.MAX_VALUE);
		long lines = lineNumberReader.getLineNumber();
		lineNumberReader.close();
		@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(new FileReader(new File("BlackCards.txt")));
		for (int i = 0; i < lines; i = i + 2) {
			String text = in.readLine();
			int cardsRequired = Integer.valueOf(in.readLine());
			Card card = new Card(text, "black", cardsRequired);
			blackCards.add(card);
		}
		Collections.shuffle(blackCards);
		Collections.shuffle(blackCards);
		Collections.shuffle(blackCards);
	}

	public Card getCurrentBlack() {
		return currentBlack;
	}

	public ArrayList<ArrayList<Card>> getPicks() {
		return picks;
	}

	public ArrayList<Player> getPickPlayers() {
		return pickPlayers;
	}

	public int getPlayersInGame() {
		return playersInGame;
	}

	public void setPlayersInGame(int playersInGame) {
		this.playersInGame = playersInGame;
	}

	public int getWins() {
		return wins;
	}

	public int getTurns() {
		return turns;
	}

	public int getTurnsPassed() {
		return turnsPassed;
	}

	public void setTurnsPassed(int turnsPassed) {
		this.turnsPassed = turnsPassed;
	}

}