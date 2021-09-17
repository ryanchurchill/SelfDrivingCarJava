package org.ryan.selfdrivingcar;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *                      Stage
 *                      Scene (black)
 *                      FlowPane
 *    gamePane (StackPane)      buttonPanel (HBox) (blue)
 *    carCanvas
 *    sandCanvas
 */
public class App extends Application {
    // "game" is the area where the car can drive
    private final int gameWidth = 800;
    private final int gameHeight = 600;

    // button panel goes below "game"
    private final int buttonPanelHeight = 100;

    public void start(Stage stage) {
        stage.setTitle("Self driving car");

        Group root = new Group();
        Scene scene = new Scene(root, this.gameWidth, this.gameHeight + this.buttonPanelHeight, Color.BLACK);
        stage.setScene(scene);

        // holds gamePane at the top and buttonPanel on the bottom
        FlowPane flow = new FlowPane();
        flow.setPrefWrapLength(gameWidth);
        root.getChildren().add(flow);

        // holds canvases
        StackPane gamePane = new StackPane();
        flow.getChildren().add(gamePane);

        // carCanvas and sandCanvas are separate so we can easily clear one at a time
        // for example, we clear the carCanvas and redraw every time the car moves

        Canvas carCanvas = new Canvas(this.gameWidth, this.gameHeight);
        gamePane.getChildren().add(carCanvas);
        Car car = new Car(50, 50);
        car.draw(carCanvas);

        Canvas sandCanvas = new Canvas(this.gameWidth, this.gameHeight);
        gamePane.getChildren().add(sandCanvas);
        Sand sand = new Sand(sandCanvas);
//        sand.addSandCircle(300, 300, 5, sandCanvas.getGraphicsContext2D());

//        Circle tiny = new Circle(500, 500, 10);
//        tiny.setFill(Color.RED);
//        root.getChildren().add(tiny);

        trackInput(scene, car, sand, carCanvas);

        HBox buttonPanel = new HBox();
        buttonPanel.setStyle("-fx-background-color: #336699;");
        buttonPanel.setPrefHeight(this.buttonPanelHeight);
        buttonPanel.setPrefWidth(this.gameWidth);
        Button btnPlay = new Button("Play");
        buttonPanel.getChildren().add(btnPlay);
        flow.getChildren().add(buttonPanel);

        stage.show();
    }

    private void trackInput(Scene scene, Car car, Sand sand, Canvas canvas)
    {
        scene.setOnKeyPressed(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
                    if (code == "RIGHT") {
                        car.rotateRight();
                        car.draw(canvas);
                    }
                    else if (code == "LEFT") {
                        car.rotateLeft();
                        car.draw(canvas);
                    }
                    else if (code == "UP") {
                        car.moveForward();
                        car.draw(canvas);
                    }
                }
            }
        );

        scene.setOnMouseDragged(
            new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    int x = (int) event.getX();
                    int y = (int) event.getY();
                    if (x >= 0 && x < gameWidth && y >= 0 && y < gameHeight) {
                        sand.addSandCircle(x, y, 10);
                    }
                }
            }
        );

    }
}
