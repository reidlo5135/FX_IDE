package com.ide.fx_ide;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {
    private static final String DIRECTORY_CSS = "static/css/";
    private static final String DIRECTORY_IMAGE = "static/image/";

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("root.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1680, 840);
        stage.setScene(scene);
        stage.setResizable(false);
        setResources(stage, scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    private static void setResources(Stage stage, Scene scene) {
        scene.getStylesheets().add(MainApplication.class.getResource(DIRECTORY_CSS + "root.css").toString());
        stage.getIcons().add(new Image(MainApplication.class.getResource(DIRECTORY_IMAGE + "favicon.png").toString()));
    }
}