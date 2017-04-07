package com.eg0.network;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.eg0.gui.Main;

public class Listener implements Runnable {

	private String serverIP;
	private int port;
	private String username;
	private boolean create;
	private String roomCode;

	private Socket socket;
	private OutputStream outputStream;
	private static ObjectOutputStream objectOutputStream;
	private InputStream inputStream;
	private ObjectInputStream objectInputStream;

	public Listener(String serverIP, int port, String username, boolean create, String roomCode) {
		this.serverIP = serverIP;
		this.port = port;
		this.username = username;
		this.create = create;
		this.roomCode = roomCode;
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
						Main.gameScreen.drawCards(message.getWhiteCards());
						break;
					case SERVER_SENDBLACK:
						Main.changeScreen("pickScreen", true);
						break;
					case SERVER_ASKPICKS:
						Main.gameScreen.askPicks(message.getBlackCard().getCardsRequired());
						break;
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e + "2");
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
		try {
			objectOutputStream.writeObject(message);
			objectOutputStream.flush();
		} catch (Exception e) {
			System.out.println(e + "3");
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
			System.out.println(e + "4");
		}
	}

	public static void startGame() {
		Message message = new Message();
		message.setMessageType(MessageType.PLAYER_STARTGAME);
		message.setRoomCode(Main.roomCode);
		try {
			objectOutputStream.writeObject(message);
			objectOutputStream.flush();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}