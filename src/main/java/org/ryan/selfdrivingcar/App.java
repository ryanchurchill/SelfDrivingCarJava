package org.ryan.selfdrivingcar;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.ryan.selfdrivingcar.ai.CarAI;
import org.ryan.selfdrivingcar.ai.CarAILoop;
import org.ryan.selfdrivingcar.ai.RandomCarAI;
import org.ryan.selfdrivingcar.ai.brain.Network;

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
        Network n = new Network(3, 3);

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
        Car car = new Car(50, 50, carCanvas);
        car.draw();

        Canvas sandCanvas = new Canvas(this.gameWidth, this.gameHeight);
        gamePane.getChildren().add(sandCanvas);
        Sand sand = new Sand(sandCanvas);

//        Circle tiny = new Circle(500, 500, 10);
//        tiny.setFill(Color.RED);
//        root.getChildren().add(tiny);

        /*
        INPUT AND AI
         */

        establishInput(scene, car, sand, carCanvas);

        CarAI carAI = new RandomCarAI();
        CarAILoop carAILoop = new CarAILoop(carAI, car);


        /*
        BUTTON PANEL
         */
        HBox buttonPanel = new HBox();
        buttonPanel.setStyle("-fx-background-color: #336699;");
        buttonPanel.setPrefHeight(this.buttonPanelHeight);
        buttonPanel.setPrefWidth(this.gameWidth);
        flow.getChildren().add(buttonPanel);

        Button btnPlay = new Button("Play");
        btnPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                carAILoop.start();
            }
        });
        btnPlay.setFocusTraversable(false);
        buttonPanel.getChildren().add(btnPlay);

        Button btnPause = new Button("Pause");
        btnPause.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                carAILoop.stop();
            }
        });
        btnPause.setFocusTraversable(false);
        buttonPanel.getChildren().add(btnPause);

        stage.show();
    }

    private void establishInput(Scene scene, Car car, Sand sand, Canvas canvas)
    {
        scene.setOnKeyPressed(
            new EventHandler<KeyEvent>()
            {
                public void handle(KeyEvent e)
                {
                    String code = e.getCode().toString();
                    if (code == "RIGHT") {
                        car.rotateRight();
                        car.draw();
                    }
                    else if (code == "LEFT") {
                        car.rotateLeft();
                        car.draw();
                    }
                    else if (code == "UP") {
                        car.moveForward();
                        car.draw();
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
