package com.cellwars.scene;


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 * Created by Tamás on 2015-04-30.
 */
public class SceneScreen {

    private final int screenWidth = 1024;
    private final int screenHeight = 768;


    private Group root;
    private Rectangle map;

    public SceneScreen(Stage primaryStage) {
        root = new Group();
        javafx.scene.Scene scene = new javafx.scene.Scene(root, screenWidth, screenHeight, Color.web("#111111"));
        primaryStage.setScene(scene);
    }

    public void drawCell(Circle c, Color color, String playerName) {

        c.setFill(Color.web(color.toString(), 0.2));
        c.setStrokeType(StrokeType.OUTSIDE);
        c.setStroke(Color.web(color.toString(), 0.90));
        c.setEffect(new GaussianBlur(2));
        c.setStrokeWidth(5);


        Text text = new Text(playerName);
        text.setBoundsType(TextBoundsType.VISUAL);
        text.setFont(Font.font(null, FontWeight.BOLD, 10));
        text.setFill(Color.WHITE);

        double width = text.getLayoutBounds().getWidth();
        double height = text.getLayoutBounds().getHeight();
        text.xProperty().bind(c.centerXProperty().add(-width / 2));
        text.yProperty().bind(c.centerYProperty().add(height / 3));

        root.getChildren().add(c);
        root.getChildren().add(text);

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(c.scaleXProperty(), 6),
                        new KeyValue(c.scaleYProperty(), 6)
                ),
                new KeyFrame(new Duration(250),
                        new KeyValue(c.scaleXProperty(), 0.1),
                        new KeyValue(c.scaleYProperty(), 0.1)
                ),
                new KeyFrame(new Duration(500),
                        new KeyValue(c.scaleXProperty(), 1),
                        new KeyValue(c.scaleYProperty(), 1)
                )
        );
        timeline.play();
    }

    public void drawCooky(Circle c) {
        c.setFill(Color.web("#00ff55", 0.2));
        c.setStrokeType(StrokeType.OUTSIDE);
        c.setStroke(Color.web("#00ff55", 0.90));
        c.setEffect(new GaussianBlur(2));
        c.setStrokeWidth(5);
        c.centerYProperty().addListener(observable -> {
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(c.scaleXProperty(), 6),
                            new KeyValue(c.scaleYProperty(), 6)
                    ),
                    new KeyFrame(new Duration(150),
                            new KeyValue(c.scaleXProperty(), 0.1),
                            new KeyValue(c.scaleYProperty(), 0.1)
                    ),
                    new KeyFrame(new Duration(300),
                            new KeyValue(c.scaleXProperty(), 1),
                            new KeyValue(c.scaleYProperty(), 1)
                    )
            );
            timeline.play();
        });
        c.centerXProperty().addListener(observable -> {
            Timeline timeline = new Timeline();
            timeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.ZERO,
                            new KeyValue(c.scaleXProperty(), 8),
                            new KeyValue(c.scaleYProperty(), 8)
                    ),
                    new KeyFrame(new Duration(150),
                            new KeyValue(c.scaleXProperty(), 0.1),
                            new KeyValue(c.scaleYProperty(), 0.1)
                    ),
                    new KeyFrame(new Duration(300),
                            new KeyValue(c.scaleXProperty(), 1),
                            new KeyValue(c.scaleYProperty(), 1)
                    )
            );
            timeline.play();
        });

        root.getChildren().add(c);
    }

    public void drawMine(Circle c) {
        c.setFill(Color.web("#ff0011", 0.2));
        c.setStrokeType(StrokeType.OUTSIDE);
        c.setStroke(Color.web("#ff0011", 0.90));
        c.setEffect(new GaussianBlur(2));
        c.setStrokeWidth(5);
        c.centerYProperty().addListener(observable -> {
            spawnEffect(c);
        });
        c.centerXProperty().addListener(observable -> {
            spawnEffect(c);
        });

        root.getChildren().add(c);
    }

    public void drawMap() {
        map = Rules.MAP;
        map.setFill(Color.web("#200010"));

        root.getChildren().add(map);
    }

    public void spawnEffect(Circle c) {
        Timeline timeline = new Timeline();
        timeline.getKeyFrames().addAll(
                new KeyFrame(Duration.ZERO,
                        new KeyValue(c.scaleXProperty(), 8),
                        new KeyValue(c.scaleYProperty(), 8)
                ),
                new KeyFrame(new Duration(150),
                        new KeyValue(c.scaleXProperty(), 0.1),
                        new KeyValue(c.scaleYProperty(), 0.1)
                ),
                new KeyFrame(new Duration(300),
                        new KeyValue(c.scaleXProperty(), 1),
                        new KeyValue(c.scaleYProperty(), 1)
                )
        );
        timeline.play();
    }

    public Group getMap() {
        return root;
    }


}
