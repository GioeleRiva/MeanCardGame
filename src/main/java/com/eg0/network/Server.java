package com.eg0.network;

import java.net.ServerSocket;
import java.security.SecureRandom;
import java.util.ArrayList;

public class Server {

	private static int port = 15000;

	static String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	static SecureRandom rnd = new SecureRandom();

	static ArrayList<Game> games = new ArrayList<>();

	public static void main(String[] args) throws Exception {
		ServerSocket serverSocket = new ServerSocket(port);
		try {
			while (true) {
				new Handler(serverSocket.accept()).start();
			}
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			serverSocket.close();
		}
	}

	static String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		}
		for (int y = 0; y < games.size(); y++) {
			if (games.get(y).getGameCode().equals(sb)) {
				return randomString(4);
			}
		}
		return sb.toString();
	}

	public synchronized static Game createGame(String wins, String turns) {
		String code = randomString(4);
		System.out.println(code);
		Game game = new Game(code, wins, turns);
		games.add(game);
		return game;
	}

}