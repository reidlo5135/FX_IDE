package com.ide.fx_ide.common;

import com.ide.fx_ide.MainApplication;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class CommonService {
    private static final String DIRECTORY_CSS = "static/css/";
    private static final String DIRECTORY_IMAGE = "static/image/";

    public static void setResources(Stage stage, Scene scene, String css, String favicon) {
        scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource(DIRECTORY_CSS + css)).toString());
        stage.getIcons().add(new Image(Objects.requireNonNull(MainApplication.class.getResource(DIRECTORY_IMAGE + favicon)).toString()));
    }
}
