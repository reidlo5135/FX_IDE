package com.ide.fx_ide.service.common;

import com.ide.fx_ide.MainApplication;
import com.ide.fx_ide.controller.edit.EditorController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Map;
import java.util.Objects;

public class CommonService {
    private static final String DIRECTORY_CSS = "static/css/";
    private static final String DIRECTORY_IMAGE = "static/image/";

    public static void setResources(Stage stage, Scene scene, String css, String favicon) {
        scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource(DIRECTORY_CSS + css)).toString());
        stage.getIcons().add(new Image(Objects.requireNonNull(MainApplication.class.getResource(DIRECTORY_IMAGE + favicon)).toString()));
    }

    public static void moveToEditScene(Map<String, String> data, Stage stage_previous) {
        String title = data.get("path") + " - " + data.get("name");

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("editor.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 1680, 840);
            EditorController editorController = loader.getController();
            editorController.setTextArea(data.get("code"));

            Stage stage_editor = new Stage();
            stage_editor.setScene(scene);
            stage_editor.setTitle(title);
            CommonService.setResources(stage_editor, scene, "editor.css", "favicon.png");
            stage_editor.show();

            stage_previous.close();
            stage_previous.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
