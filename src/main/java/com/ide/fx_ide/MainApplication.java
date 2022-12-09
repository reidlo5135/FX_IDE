package com.ide.fx_ide;

import com.ide.fx_ide.common.CommonService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("file.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 740);
        stage.setScene(scene);
        CommonService.setResources(stage, scene, "file.css", "favicon.png");
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}