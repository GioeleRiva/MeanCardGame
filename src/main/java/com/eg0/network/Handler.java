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
				player = new Player(game.getGameCode(), firstMessage.getUserName(), 0, objectOutputStream);
				sendRoomCode();
			}
			if (firstMessage.getMessageType().equals(MessageType.PLAYER_JOIN)) {
				for (int x = 0; x < Server.games.size(); x++) {
					if (Server.games.get(x).getGameCode().equals(firstMessage.getRoomCode())) {
						game = Server.games.get(x);
						player = new Player(game.getGameCode(), firstMessage.getUserName(), 0, objectOutputStream);
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
						game.shufflePlayers();
						String temp0 = "Players order: ";
						for (int x = 0; x < game.getPlayers().size(); x++) {
							temp0 = temp0 + game.getPlayers().get(x).getUserName() + " ";
						}
						System.out.println(temp0);
						startGame();
						Thread.sleep(1000);
						sendCards();
						Thread.sleep(3000);
						sendBlack();
						askPicks();
						break;
					case PLAYER_SENDPICKS:
						game.getPicks().add(message.getWhiteCards());
						game.getPickPlayers().add(player);
						for (int x = 0; x < message.getWhiteCards().size(); x++) {
							for (int y = 0; y < player.getCards().size(); y++) {
								if (player.getCards().get(y).getText()
										.equals(message.getWhiteCards().get(x).getText())) {
									player.getCards().remove(y);
								}
							}
						}
						if (game.getPicks().size() == game.getPlayers().size() - 1) {
							askWinner(game.getPicks());
							sendPicks(game.getPicks());
						}
						break;
					case PLAYER_SENDWINNER:
						game.getPickPlayers().get(message.getWinner()).addWin();
						System.out.println(game.getPickPlayers().get(message.getWinner()).getUserName() + " WINS!");
						sendWinner(game.getPicks().get(message.getWinner()),
								game.getPickPlayers().get(message.getWinner()).getUserName());
						Thread.sleep(5000);
						game.getPicks().clear();
						game.getPickPlayers().clear();
						for (int x = 0; x < game.getPlayers().size(); x++) {
							System.out.println("Player " + game.getPlayers().get(x).getUserName() + " wins: "
									+ game.getPlayers().get(x).getWins());
						}
						Thread.sleep(500);
						startGame();
						Thread.sleep(500);
						sendCards();
						Thread.sleep(3000);
						sendBlack();
						askPicks();
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
			// if (toSend != 0) {
			Message message = new Message();
			message.setMessageType(MessageType.SERVER_SENDCARDS);
			ArrayList<Card> cards = new ArrayList<>();
			String temp0 = "Sent cards ";
			for (int y = 0; y < toSend; y++) {
				cards.add(game.getWhiteCard());
			}
			for (int i = 0; i < cards.size(); i++) {
				temp0 = temp0 + cards.get(i).getText() + " ";
			}
			temp0 = temp0 + "to " + game.getPlayers().get(x).getUserName();
			game.getPlayers().get(x).getCards().addAll(cards);
			message.setWhiteCards(cards);
			try {
				game.getPlayers().get(x).getObjectOutputStream().writeObject(message);
				game.getPlayers().get(x).getObjectOutputStream().reset();
				game.getPlayers().get(x).getObjectOutputStream().flush();
			} catch (Exception e) {
				System.out.println(e);
			}
			System.out.println(temp0);
			// }
		}
	}

	private void sendBlack() {
		Message message = new Message();
		message.setMessageType(MessageType.SERVER_SENDBLACK);
		Card card = game.getNextBlackCard();
		message.setBlackCard(card);
		game.nextTurn();
		try {
			game.getPlayers().get(game.getTurn()).getObjectOutputStream().writeObject(message);
			game.getPlayers().get(game.getTurn()).getObjectOutputStream().reset();
			game.getPlayers().get(game.getTurn()).getObjectOutputStream().flush();
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("Sent " + card.getText() + " to " + game.getPlayers().get(game.getTurn()).getUserName());
	}

	private void askPicks() {
		for (int x = 0; x < game.getPlayers().size(); x++) {
			if (x != game.getTurn()) {
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

	private void askWinner(ArrayList<ArrayList<Card>> picks) {
		Message message = new Message();
		message.setMessageType(MessageType.SERVER_ASKWINNER);
		message.setPicks(picks);
		Card card = game.getCurrentBlack();
		message.setBlackCard(card);
		try {
			game.getPlayers().get(game.getTurn()).getObjectOutputStream().writeObject(message);
			game.getPlayers().get(game.getTurn()).getObjectOutputStream().reset();
			game.getPlayers().get(game.getTurn()).getObjectOutputStream().flush();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	private void sendPicks(ArrayList<ArrayList<Card>> picks) {
		for (int x = 0; x < game.getPlayers().size(); x++) {
			if (x != game.getTurn()) {
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

	private void sendWinner(ArrayList<Card> pick, String player) {
		for (int x = 0; x < game.getPlayers().size(); x++) {
			Message message = new Message();
			message.setMessageType(MessageType.SERVER_SENDWINNER);
			message.setWhiteCards(pick);
			message.setBlackCard(game.getCurrentBlack());
			message.setUserName(player);
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