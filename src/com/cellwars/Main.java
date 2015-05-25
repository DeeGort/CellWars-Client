package com.cellwars;

import com.cellwars.login.LoginScreen;
import com.cellwars.client.ClientConnection;
import com.cellwars.scene.Client;
import com.cellwars.scene.Player;
import com.cellwars.scene.SceneScreen;
import com.cellwars.client.Scheduler;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    ClientConnection clientConnection;
    Scheduler scheduler;
    Player player;
    Client client;

    @Override
    public void start(Stage primaryStage){

        // Create client
        clientConnection = new ClientConnection();
        scheduler = new Scheduler(clientConnection);

        // Create login screen
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.start(primaryStage);
        primaryStage.show();

        // Login event -> run
        loginScreen.getLoginButton().setOnAction((ActionEvent event) -> {
            if (clientConnection.connect(loginScreen.getIp())) {

                createPlayer(loginScreen.getName(), loginScreen.getColor());
                createClient(primaryStage);

            }
        });
        primaryStage.setOnCloseRequest(event -> {
            System.exit(0);
        });
    }

    public void createPlayer(String name, Color color) {
        player = new Player(name, color);
    }

    public void createClient(Stage primaryStage) {
        SceneScreen sceneScreen = new SceneScreen(primaryStage);
        primaryStage.show();

        client = new Client(scheduler, sceneScreen, player);
        client.start();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
