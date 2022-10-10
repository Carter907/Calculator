package com.example.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Calculator extends Application {

    private Stage window;

    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(Calculator.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(root, 600, 400);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

        window = stage;
        window.setTitle("Calculator Application");
        window.setResizable(false);
        window.setScene(scene);
        window.show();
    }

    public Stage getWindow() {

        return this.window;
    }

    public static void main(String[] args) {
        launch();
    }
}