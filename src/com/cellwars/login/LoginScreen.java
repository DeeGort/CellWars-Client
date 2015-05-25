package com.cellwars.login;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Created by Tamás on 2015-04-13.
 */
public class LoginScreen {

    private TextField nameField;
    private TextField ipField;
    private ColorPicker colorPicker;
    private Button loginButton;

    public void start(Stage primaryStage) {

        // Create background gridpane
        GridPane gridbg = new GridPane();
        gridbg.setAlignment(Pos.CENTER);
        gridbg.setHgap(10);
        gridbg.setVgap(10);
        gridbg.setPadding(new Insets(25, 25, 25, 25));
        gridbg.setStyle("-fx-background-color: #200010;");

        // Create design rectangle
        Rectangle panel = new Rectangle(500, 300, Color.web("#222222", 0.2));
        panel.setStrokeType(StrokeType.OUTSIDE);
        panel.setStroke(Color.web("#ffaa22", 0.90));
        panel.setStrokeWidth(10);
        panel.setArcWidth(45);
        panel.setArcHeight(45);
        panel.setEffect(new GaussianBlur(2));
        gridbg.add(panel, 0, 0);

        // Create dialog grid
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Bloom bloom = new Bloom();
        bloom.setThreshold(0.1);

        Text sceneTitle = new Text("   Cell Wars   ");
        sceneTitle.setEffect(bloom);
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.BOLD, 40));
        sceneTitle.setFill(Color.web("white", 0.8));
        sceneTitle.setStrokeWidth(3);
        grid.add(sceneTitle, 0, 0, 2, 1);

        Label userName = new Label("Player Name:");
        userName.setTextFill(Color.web("white"));
        grid.add(userName, 0, 1);


        nameField = new TextField();
        nameField.setText("Player");
        nameField.setStyle("-fx-background-color: rgb(80, 80, 80); -fx-text-fill: rgb(255, 255, 255);");
        grid.add(nameField, 1, 1);

        Label ipAddress = new Label("IP Address:");
        ipAddress.setTextFill(Color.web("white"));
        grid.add(ipAddress, 0, 2);

        ipField = new TextField();
        ipField.setText("127.0.0.1");
        ipField.setStyle("-fx-background-color: rgb(80, 80, 80); -fx-text-fill: rgb(255, 255, 255);");
        grid.add(ipField, 1, 2);

        Label color = new Label("Color:");
        color.setTextFill(Color.web("white"));
        grid.add(color, 0, 3);

        colorPicker = new ColorPicker(Color.web("#ffaa22"));
        colorPicker.setStyle("-fx-background-color: rgb(80, 80, 80); -fx-text-fill: rgb(255, 255, 255);");
        colorPicker.setPrefWidth(50);
        grid.add(colorPicker, 1, 3);

        loginButton = new Button("Connect");
        loginButton.setStyle("-fx-background-color: rgb(80, 80, 80); -fx-text-fill: rgb(255, 255, 255);");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginButton);
        grid.add(hbBtn, 1, 4);

        gridbg.add(grid, 0, 0);
        Scene scene = new Scene(gridbg, 1024, 768);
        primaryStage.setScene(scene);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO, // set start position at 0
                        new KeyValue(panel.scaleXProperty(), 0.1),
                        new KeyValue(panel.scaleYProperty(), 0.1)
                ),
                new KeyFrame(new Duration(500), // set end position at 40s
                        new KeyValue(panel.scaleXProperty(), 1),
                        new KeyValue(panel.scaleYProperty(), 1)
                )
        );
        timeline.play();

        colorPicker.setOnAction((ActionEvent t) -> {
            panel.setStroke(colorPicker.getValue());
        });
    }

    public String getName() {
        return nameField.getText();
    }

    public String getIp() {
        return ipField.getText();
    }

    public Color getColor() {
        return colorPicker.getValue();
    }


    public Button getLoginButton() {
        return loginButton;
    }
}
