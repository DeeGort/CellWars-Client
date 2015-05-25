package com.cellwars.scene;

import com.cellwars.client.Scheduler;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Created by Tamás on 2015-04-30.
 */
public class Client {

    private SceneScreen sceneScreen;
    private Scheduler scheduler;
    private Player player;
    private Scene scene;


    public Client(Scheduler scheduler, SceneScreen sceneScreen, Player player) {
        this.sceneScreen = sceneScreen;
        this.scheduler = scheduler;
        this.player = player;
        scene = new Scene(scheduler, sceneScreen, player);
    }

    public void start() {
        scheduler.start();
        scheduler.request("PLAYERINFO", player.getName(), player.getColor().toString());
        waitForAnswer();
        scheduler.request("NULL");
        gameLoop();
    }

    private void gameLoop() {
        AnimationTimer getstates = new AnimationTimer() {
            @Override
            public synchronized void handle(long now) {
                scheduler.request("GETPLAYERS", player.getName());
                waitForAnswer();
                scheduler.request("GETCOOKIES");
                waitForAnswer();
                scheduler.request("GETMINES");
                waitForAnswer();

                // If an event occurred process it
                interpret(scheduler.getAnswer());
            }
        };
        getstates.start();
    }

    private void waitForAnswer() {
        String answer;
        do {
            answer = scheduler.getAnswer();
        } while (answer == null);
        interpret(answer);
    }

    private void interpret(String serverMessage) {
        String[] request = serverMessage.split(":");
        if (request.length > 0) {
            switch (request[0]) {
                case "RULES":
                    try {
                        String[] map = request[1].split(" ");
                        scene.initRules(
                                new Rectangle(
                                        Double.parseDouble(map[0]),
                                        Double.parseDouble(map[1]),
                                        Double.parseDouble(map[2]),
                                        Double.parseDouble(map[3])
                                ),
                                Float.parseFloat(request[2]),
                                Float.parseFloat(request[3])
                        );
                        scene.initMap();
                    } catch (Exception e) {
                        System.out.println("FATAL ERROR!");
                    }
                    break;
                case "CELL":
                    scene.initPlayer(request[1], Double.parseDouble(request[2]), Double.parseDouble(request[3]));
                    break;
                case "PLAYERS":
                    for (int i = 1; i < request.length; i += 6) {
                        scene.updatePlayers(request[i],
                                Double.parseDouble(request[i + 1]),
                                Double.parseDouble(request[i + 2]),
                                Color.web(request[i + 3]),
                                Double.parseDouble(request[i + 4]),
                                Boolean.parseBoolean(request[i + 5])
                        );
                    }
                    break;
                case "COOKIES":
                    for (int i = 1; i < request.length; i += 3)
                        scene.updateCookies(Integer.parseInt(request[i]), Double.parseDouble(request[i + 1]), Double.parseDouble(request[i + 2]));
                    break;
                case "MINES":
                    for (int i = 1; i < request.length; i += 3)
                        scene.updateMines(Integer.parseInt(request[i]), Double.parseDouble(request[i + 1]), Double.parseDouble(request[i + 2]));
                    break;
            }
        }
    }
}
