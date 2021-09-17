package org.ryan.selfdrivingcar;

import javafx.application.Application;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.effect.BoxBlur;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class App extends Application {
    private final int width = 800;
    private final int height = 600;

    public void start(Stage stage) {
        stage.setTitle("Self driving car");

        Group root = new Group();
        Scene scene = new Scene(root, this.width, this.height, Color.BLACK);
        stage.setScene(scene);

        StackPane stackPane = new StackPane();
        root.getChildren().add(stackPane);

        Canvas carCanvas = new Canvas(this.width, this.height);
        stackPane.getChildren().add(carCanvas);
        Car car = new Car(50, 50);
        car.draw(carCanvas);

        Canvas sandCanvas = new Canvas(800, 600);
        stackPane.getChildren().add(sandCanvas);
        Sand sand = new Sand(this.width, this.height);
        sand.addSandCircle(300, 300, 5, sandCanvas.getGraphicsContext2D());

//        Circle tiny = new Circle(500, 500, 10);
//        tiny.setFill(Color.RED);
//        root.getChildren().add(tiny);

        trackInput(scene, car, carCanvas);

        stage.show();
    }

    private void trackInput(Scene scene, Car car, Canvas canvas)
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
    }
}
