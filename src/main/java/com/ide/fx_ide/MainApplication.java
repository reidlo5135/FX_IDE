package com.ide.fx_ide;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private static final String DIRECTORY_CSS = "static/css/";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("root.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1680, 840);
        scene.getStylesheets().add(getClass().getResource(DIRECTORY_CSS + "root.css").toString());
        stage.setTitle("RDE");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}