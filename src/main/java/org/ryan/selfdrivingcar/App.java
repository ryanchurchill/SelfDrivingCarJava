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

public class App extends Application {
    public void start(Stage stage) {
        stage.setTitle("Self driving car");

        Group root = new Group();
        Scene scene = new Scene(root, 800, 600, Color.BLACK);
        stage.setScene(scene);

        Car car = new Car();
        Rectangle carRectangle = car.getRectangle();
        root.getChildren().add(carRectangle);

//        Rectangle rect = new Rectangle(200, 200, 200, 200);
//        rect.setFill(Color.RED);
//        root.getChildren().add(rect);

        stage.show();
    }
}
