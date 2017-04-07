package com.eg0.gui;

import java.util.ArrayList;

import com.eg0.network.Card;
import com.eg0.network.Player;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

	static double screenWidth = 640;
	static double screenHeight = 360;
	static double cardHeight = (screenHeight * 9) / 10;
	static double cardWidth = (cardHeight * 63) / 88;
	static double cardRadius = (cardHeight) / 22;
	static double cardBorder = (cardWidth * 13) / 126;
	static double cardTextSize = (cardWidth * 9) / 100;

	public static StackPane stackPane;
	public static HomeScreen homeScreen;
	public static NameScreen nameScreen;
	public static PlayScreen playScreen;
	public static JoinScreen joinScreen;
	public static RoomScreen roomScreen;
	public static GameScreen gameScreen;
	public static PickScreen pickScreen;

	public static boolean somethingSelected = false;

	public static String playerName = "";
	public static String roomCode = "";

	public static ArrayList<Player> players = new ArrayList<>();
	public static ArrayList<Card> whiteCards = new ArrayList<>();

	@Override
	public void start(Stage stage) {
		stackPane = new StackPane();
		stackPane.getStylesheets().add("/Style.css");
		pickScreen = new PickScreen();
		stackPane.getChildren().add(pickScreen);
		gameScreen = new GameScreen();
		stackPane.getChildren().add(gameScreen);
		roomScreen = new RoomScreen();
		stackPane.getChildren().add(roomScreen);
		joinScreen = new JoinScreen();
		stackPane.getChildren().add(joinScreen);
		playScreen = new PlayScreen();
		stackPane.getChildren().add(playScreen);
		nameScreen = new NameScreen();
		stackPane.getChildren().add(nameScreen);
		homeScreen = new HomeScreen();
		stackPane.getChildren().add(homeScreen);
		Scene scene = new Scene(stackPane);
		stage.setScene(scene);
		stage.show();
	}

	public static void changeScreen(String screen, boolean czar) {
		Task<Void> task = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				return null;
			}
		};
		task.setOnSucceeded(change -> {
			Pane black = new Pane();
			black.setPrefSize(screenWidth, screenHeight);
			FadeTransition fade1 = new FadeTransition();
			if (!czar) {
				black.setId("black");
				fade1.setDuration(Duration.millis(500));
			} else {
				black.setId("blackczar");
				fade1.setDuration(Duration.millis(1500));
			}
			black.setMouseTransparent(true);
			stackPane.getChildren().add(stackPane.getChildren().size(), black);

			fade1.setFromValue(0);
			fade1.setToValue(1);
			fade1.setNode(black);
			fade1.setOnFinished(done1 -> {
				Main.somethingSelected = false;
				if (screen.equals("homeScreen")) {
					homeScreen.toFront();
				}
				if (screen.equals("nameScreen")) {
					nameScreen.toFront();
				}
				if (screen.equals("playScreen")) {
					playScreen.toFront();
				}
				if (screen.equals("joinScreen")) {
					joinScreen.toFront();
				}
				if (screen.equals("roomScreen")) {
					roomScreen.toFront();
				}
				if (screen.equals("gameScreen")) {
					gameScreen.toFront();
				}
				black.toFront();
				FadeTransition fade2 = new FadeTransition();
				if (!czar) {
					fade2.setDuration(Duration.millis(500));
				} else {
					fade2.setDuration(Duration.millis(1500));
				}
				fade2.setFromValue(1);
				fade2.setToValue(0);
				fade2.setNode(black);
				fade2.setOnFinished(done2 -> {
					stackPane.getChildren().remove(black);
				});
				fade2.play();
			});
			fade1.play();
		});
		Thread thread = new Thread(task);
		thread.setDaemon(true);
		thread.start();
	}
}