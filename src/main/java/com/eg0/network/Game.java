package com.eg0.network;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collections;

import com.eg0.gui.Main;

public class Game {

	private ArrayList<Player> players = new ArrayList<>();
	private String gameCode;
	private int turn;
	private static ArrayList<Card> whiteCards = new ArrayList<>();
	private static ArrayList<Card> blackCards = new ArrayList<>();

	public Game(String gameCode) {
		this.gameCode = gameCode;
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

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public synchronized Card getWhiteCard() {
		Card card = whiteCards.get(0);
		whiteCards.remove(0);
		return card;
	}

	public void setWhiteCards() throws Exception {
		LineNumberReader lineNumberReader = new LineNumberReader(
				new FileReader(new File(Main.class.getClassLoader().getResource("WhiteCards.txt").getFile())));
		lineNumberReader.skip(Long.MAX_VALUE);
		long lines = lineNumberReader.getLineNumber();
		lineNumberReader.close();
		@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(
				new FileReader(new File(Main.class.getClassLoader().getResource("WhiteCards.txt").getFile())));
		for (int i = 0; i < lines + 1; i++) {
			String text = in.readLine();
			Card card = new Card(text, "white", 0);
			whiteCards.add(card);
		}
		Collections.shuffle(whiteCards);
		Collections.shuffle(whiteCards);
		Collections.shuffle(whiteCards);
	}

	public synchronized ArrayList<Card> getBlackCards() {
		return blackCards;
	}

	public void setBlackCards() throws Exception {
		LineNumberReader lineNumberReader = new LineNumberReader(
				new FileReader(new File(Main.class.getClassLoader().getResource("BlackCards.txt").getFile())));
		lineNumberReader.skip(Long.MAX_VALUE);
		long lines = lineNumberReader.getLineNumber();
		lineNumberReader.close();
		@SuppressWarnings("resource")
		BufferedReader in = new BufferedReader(
				new FileReader(new File(Main.class.getClassLoader().getResource("BlackCards.txt").getFile())));
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

}