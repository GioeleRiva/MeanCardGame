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
				sendRoomCode(game);
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
			sendPlayers(game);
			while (socket.isConnected()) {
				Message message = (Message) objectInputStream.readObject();
				if (message != null) {
					switch (message.getMessageType()) {
					case PLAYER_STARTGAME:
						startGame(findGame(message.getRoomCode()));
						sendCards(findGame(message.getRoomCode()));
						break;
					}

				}
			}
		} catch (Exception e) {
			try {
				System.out
						.println("Player " + player.getUserName() + " - DISCONNECTED from game " + game.getGameCode());
				game.getPlayers().remove(player);
				sendPlayers(game);
			} catch (Exception x) {
				System.out.println(x);
			}
			if (game.getPlayers().size() == 0) {
				Server.games.remove(game);
			}
			return;
		}
	}

	private Game findGame(String gameCode) {
		Game game = null;
		for (int x = 0; x < Server.games.size(); x++) {
			if (Server.games.get(x).getGameCode().equals(gameCode)) {
				game = Server.games.get(x);
			}
		}
		return game;
	}

	private synchronized void sendRoomCode(Game game) {
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

	private synchronized void sendPlayers(Game game) {
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

	private synchronized void startGame(Game game) {
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

	private void sendCards(Game game) {
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

}