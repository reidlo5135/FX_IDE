package com.ide.fx_ide.service.common;

import com.ide.fx_ide.MainApplication;
import com.ide.fx_ide.controller.edit.CreatorController;
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
            FXMLLoader loader = setLoaderLocation("editor.fxml");
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

    public static void moveToCreateScene(String path, Stage stage_previous) {
        System.out.println("path : " + path);

        try {
            FXMLLoader loader = setLoaderLocation("creator.fxml");
            Parent root = loader.load();
            Scene scene = new Scene(root, 300, 150);
            CreatorController creatorController = loader.getController();
            creatorController.setFilePath(path);

            Stage stage_creator = new Stage();
            stage_creator.setScene(scene);
            stage_creator.setTitle("Create New Java File");
            CommonService.setResources(stage_creator, scene, "creator.css", "favicon.png");
            stage_creator.show();

            stage_previous.close();
            stage_previous.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static FXMLLoader setLoaderLocation(String fxml) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource(fxml));
        return loader;
    }
}
