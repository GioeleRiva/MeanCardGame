package com.eg0.network;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import com.eg0.gui.Main;

public class Listener implements Runnable {

	private String serverIP;
	private int port;
	private String username;
	private boolean create;
	private String roomCode;
	private String wins;
	private String turns;

	private Socket socket;
	private OutputStream outputStream;
	private static ObjectOutputStream objectOutputStream;
	private InputStream inputStream;
	private ObjectInputStream objectInputStream;

	public Listener(String serverIP, int port, String username, boolean create, String roomCode, String wins,
			String turns) {
		this.serverIP = serverIP;
		this.port = port;
		this.username = username;
		this.create = create;
		this.roomCode = roomCode;
		this.wins = wins;
		this.turns = turns;
	}

	@SuppressWarnings("incomplete-switch")
	@Override
	public void run() {
		try {
			socket = new Socket(serverIP, port);
			outputStream = socket.getOutputStream();
			objectOutputStream = new ObjectOutputStream(outputStream);
			inputStream = socket.getInputStream();
			objectInputStream = new ObjectInputStream(inputStream);
		} catch (Exception e) {
			System.out.println(e + "1");
		}
		try {
			if (create) {
				create();
			} else {
				join(roomCode);
			}
			while (socket.isConnected()) {
				Message message = (Message) objectInputStream.readObject();
				if (message != null) {
					switch (message.getMessageType()) {
					case SERVER_SENDROOMCODE:
						Main.roomCode = message.getRoomCode();
						break;
					case SERVER_SENDPLAYERS:
						Main.roomScreen.drawPlayers(message.getPlayers());
						Main.roomScreen.playButton();
						break;
					case SERVER_STARTGAME:
						Main.changeScreen("gameScreen", false);
						break;
					case SERVER_SENDCARDS:
						Main.whiteCards.addAll(message.getWhiteCards());
						Main.gameScreen.drawCards(Main.whiteCards);
						break;
					case SERVER_SENDBLACK:
						Main.changeScreen("pickScreen", true);
						Main.pickScreen.setBlack(message.getBlackCard());
						break;
					case SERVER_ASKPICKS:
						Main.gameScreen.askPicks(message.getBlackCard().getCardsRequired());
						break;
					case SERVER_SENDPICKS:
						Main.pickScreen.pick = 0;
						Main.changeScreen("pickScreen", false);
						Main.pickScreen.setPicks(message.getPicks());
						Main.pickScreen.drawPicks(message.getBlackCard(), true);
						break;
					case SERVER_ASKWINNER:
						Main.pickScreen.pick = 0;
						Main.changeScreen("pickScreen", false);
						Thread.sleep(500);
						Main.pickScreen.setPicks(message.getPicks());
						Main.pickScreen.drawPicks(message.getBlackCard(), true);
						Thread.sleep(500);
						Main.pickScreen.askWinner();
						break;
					case SERVER_SENDWINNER:
						Main.winScreen.showWin(message.getBlackCard(), message.getWhiteCards(), message.getUserName());
						Main.changeScreen("winScreen", false);
						break;
					case SERVER_SENDENDGAME:
						Main.roomScreen.codeVisible = false;
						Main.roomCode = "";
						Main.endScreen.showWin(message.getPlayers());
						Main.changeScreen("endScreen", false);
						Thread.sleep(3000);
						Main.changeScreen("homeScreen", false);
						Message endMessage = new Message();
						endMessage.setMessageType(MessageType.PLAYER_DONE);
						try {
							objectOutputStream.writeObject(endMessage);
							objectOutputStream.flush();
						} catch (Exception e) {
							System.out.println(e + "2");
						}
						socket.close();
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e + "3");
			Main.roomCode = "";
			Main.changeScreen("homeScreen", false);
			try {
				socket.close();
			} catch (Exception x) {
				System.out.println(x);
			}
			return;
		}
	}

	private void create() {
		Message message = new Message();
		message.setUserName(username);
		message.setMessageType(MessageType.PLAYER_CREATE);
		message.setWins(wins);
		message.setTurns(turns);
		try {
			objectOutputStream.writeObject(message);
			objectOutputStream.flush();
		} catch (Exception e) {
			System.out.println(e + "4");
		}
	}

	private void join(String roomCode) {
		Message message = new Message();
		message.setUserName(username);
		message.setRoomCode(roomCode);
		message.setMessageType(MessageType.PLAYER_JOIN);
		try {
			objectOutputStream.writeObject(message);
			objectOutputStream.flush();
		} catch (Exception e) {
			System.out.println(e + "5");
		}
	}

	public static void startGame() {
		Message message = new Message();
		message.setMessageType(MessageType.PLAYER_STARTGAME);
		try {
			objectOutputStream.writeObject(message);
			objectOutputStream.flush();
		} catch (Exception e) {
			System.out.println(e + "6");
		}
	}

	public static void sendPicks(ArrayList<Card> cards) {
		Message message = new Message();
		message.setMessageType(MessageType.PLAYER_SENDPICKS);
		message.setWhiteCards(cards);
		try {
			objectOutputStream.writeObject(message);
			objectOutputStream.flush();
		} catch (Exception e) {
			System.out.println(e + "7");
		}
	}

	public static void sendWinner(int pick) {
		Message message = new Message();
		message.setMessageType(MessageType.PLAYER_SENDWINNER);
		message.setWinner(pick);
		try {
			objectOutputStream.writeObject(message);
			objectOutputStream.flush();
		} catch (Exception e) {
			System.out.println(e + "8");
		}
	}

}