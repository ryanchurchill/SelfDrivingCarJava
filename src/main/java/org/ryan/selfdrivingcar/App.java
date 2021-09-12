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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.ArrayList;

public class App extends Application {
    public void start(Stage stage) {
        stage.setTitle("Self driving car");

        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        stage.setScene(scene);



        Canvas canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);
        Car car = new Car(50, 50);
//        GraphicsContext graphicsContext2D = canvas.getGraphicsContext2D();
        car.draw(canvas);

//        Circle tiny = new Circle(50, 50, 2);
//        tiny.setFill(Color.RED);
//        root.getChildren().add(tiny);

        trackInput(scene, car, canvas);

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
                }
            }
        );
    }
}
