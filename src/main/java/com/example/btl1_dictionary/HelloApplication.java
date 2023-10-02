package com.example.btl1_dictionary;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent fxmlLoader = FXMLLoader.load(getClass().getResource("sample.fxml"));
            Scene scene = new Scene(fxmlLoader, 900, 600);
            stage.setTitle("Dictionary");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}