package com.eg0.network;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Handler extends Thread {

	private Socket socket;

	Game game;
	Player player;

	int turn = 0;

	public Handler(Socket socket) {
		this.socket = socket;
	}

	@SuppressWarnings("incomplete-switch")
	public void run() {
		try (InputStream inputStream = socket.getInputStream();
				ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
				OutputStream outputStream = socket.getOutputStream();
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);) {
			Message firstMessage = (Message) objectInputStream.readObject();
			if (firstMessage.getMessageType().equals(MessageType.PLAYER_CREATE)) {
				game = Server.createGame();
				player = new Player(game.getGameCode(), firstMessage.getUserName(), objectOutputStream);
				sendRoomCode();
			}
			if (firstMessage.getMessageType().equals(MessageType.PLAYER_JOIN)) {
				for (int x = 0; x < Server.games.size(); x++) {
					if (Server.games.get(x).getGameCode().equals(firstMessage.getRoomCode())) {
						game = Server.games.get(x);
						player = new Player(game.getGameCode(), firstMessage.getUserName(), objectOutputStream);
					}
				}
			}
			game.getPlayers().add(player);
			System.out.println("Player " + player.getUserName() + " - CONNECTED to game " + game.getGameCode());
			sendPlayers();
			while (socket.isConnected()) {
				Message message = (Message) objectInputStream.readObject();
				if (message != null) {
					switch (message.getMessageType()) {
					case PLAYER_STARTGAME:
						startGame();
						Thread.sleep(500);
						sendCards();
						Thread.sleep(3000);
						game.setTurn();
						sendBlack();
						askPicks();
						break;
					case PLAYER_SENDPICKS:
						game.getPicks().add(message.getWhiteCards());
						game.getPickPlayers().add(player);
						if (game.getPicks().size() == game.getPlayers().size() - 1) {
							sendPicks(game.getPicks());
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			try {
				System.out
						.println("Player " + player.getUserName() + " - DISCONNECTED from game " + game.getGameCode());
				game.getPlayers().remove(player);
				sendPlayers();
			} catch (Exception x) {
				System.out.println(x);
			}
			if (game.getPlayers().size() == 0) {
				Server.games.remove(game);
			}
			return;
		}
	}

	private synchronized void sendRoomCode() {
		Message message = new Message();
		message.setMessageType(MessageType.SERVER_SENDROOMCODE);
		message.setRoomCode(game.getGameCode());
		try {
			player.getObjectOutputStream().writeObject(message);
			player.getObjectOutputStream().reset();
			player.getObjectOutputStream().flush();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private synchronized void sendPlayers() {
		for (int x = 0; x < game.getPlayers().size(); x++) {
			Message message = new Message();
			message.setPlayers(game.getPlayers());
			message.setMessageType(MessageType.SERVER_SENDPLAYERS);
			try {
				game.getPlayers().get(x).getObjectOutputStream().writeObject(message);
				game.getPlayers().get(x).getObjectOutputStream().reset();
				game.getPlayers().get(x).getObjectOutputStream().flush();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private synchronized void startGame() {
		for (int x = 0; x < game.getPlayers().size(); x++) {
			Message message = new Message();
			message.setMessageType(MessageType.SERVER_STARTGAME);
			try {
				game.getPlayers().get(x).getObjectOutputStream().writeObject(message);
				game.getPlayers().get(x).getObjectOutputStream().reset();
				game.getPlayers().get(x).getObjectOutputStream().flush();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void sendCards() {
		for (int x = 0; x < game.getPlayers().size(); x++) {
			int toSend = 10 - game.getPlayers().get(x).getCards().size();
			Message message = new Message();
			message.setMessageType(MessageType.SERVER_SENDCARDS);
			ArrayList<Card> cards = new ArrayList<>();
			for (int y = 0; y < toSend; y++) {
				cards.add(game.getWhiteCard());
			}
			message.setWhiteCards(cards);
			try {
				game.getPlayers().get(x).getObjectOutputStream().writeObject(message);
				game.getPlayers().get(x).getObjectOutputStream().reset();
				game.getPlayers().get(x).getObjectOutputStream().flush();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	private void sendBlack() {
		Message message = new Message();
		message.setMessageType(MessageType.SERVER_SENDBLACK);
		Card card = game.getBlackCard();
		message.setBlackCard(card);
		turn = game.getNextTurn();
		try {
			game.getPlayers().get(turn).getObjectOutputStream().writeObject(message);
			game.getPlayers().get(turn).getObjectOutputStream().reset();
			game.getPlayers().get(turn).getObjectOutputStream().flush();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void askPicks() {
		for (int x = 0; x < game.getPlayers().size(); x++) {
			if (x != turn) {
				Message message = new Message();
				message.setMessageType(MessageType.SERVER_ASKPICKS);
				message.setBlackCard(game.getCurrentBlack());
				try {
					game.getPlayers().get(x).getObjectOutputStream().writeObject(message);
					game.getPlayers().get(x).getObjectOutputStream().reset();
					game.getPlayers().get(x).getObjectOutputStream().flush();
				} catch (Exception e) {
					System.out.println(e);
				}
			}
		}
	}

	private void sendPicks(ArrayList<ArrayList<Card>> picks) {
		for (int x = 0; x < game.getPlayers().size(); x++) {
			Message message = new Message();
			message.setMessageType(MessageType.SERVER_SENDPICKS);
			message.setPicks(picks);
			message.setBlackCard(game.getCurrentBlack());
			try {
				game.getPlayers().get(x).getObjectOutputStream().writeObject(message);
				game.getPlayers().get(x).getObjectOutputStream().reset();
				game.getPlayers().get(x).getObjectOutputStream().flush();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

}