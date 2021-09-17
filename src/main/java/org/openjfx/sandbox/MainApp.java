package org.openjfx.sandbox;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;


public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        URL resoursePath = getClass().getResource("scene.fxml");
        System.out.println(resoursePath);
        Parent root = FXMLLoader.load(getClass().getResource("scene.fxml"));
        System.out.println(root);

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());

        stage.setTitle("JavaFX and Gradle");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}