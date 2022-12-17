package com.ide.fx_ide.service.common;

import com.ide.fx_ide.MainApplication;
import com.ide.fx_ide.controller.editor.CreatorSceneController;
import com.ide.fx_ide.controller.editor.EditorSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

public class CommonService {
    private static final String DIRECTORY_VIEW = "view/";
    private static final String DIRECTORY_CSS = "css/";
    private static final String DIRECTORY_IMAGE = "image/";

    public static String setDateTimeFormat() {
        Date nowTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(nowTime);
    }

    public static void setResources(Stage stage, Scene scene, String css, String favicon) {
        scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource(DIRECTORY_CSS + css)).toString());
        stage.getIcons().add(new Image(Objects.requireNonNull(MainApplication.class.getResource(DIRECTORY_IMAGE + favicon)).toString()));
    }

    public void moveToEditScene(Map<String, String> data, Stage stage_previous) {
        String title = data.get("path") + " - " + data.get("name");

        try {
            FXMLLoader loader = setLoaderLocation(DIRECTORY_VIEW + "editor.fxml");
            Parent root = loader.load();
            Scene scene = new Scene(root, 1680, 840);
            EditorSceneController editorSceneController = loader.getController();
            editorSceneController.initData(data);

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

    public void moveToCreateScene(String path, Stage stage_previous) {
        System.out.println("path : " + path);

        try {
            FXMLLoader loader = setLoaderLocation(DIRECTORY_VIEW + "creator.fxml");
            Parent root = loader.load();
            Scene scene = new Scene(root, 300, 150);
            CreatorSceneController creatorSceneController = loader.getController();
            creatorSceneController.setFilePath(path);

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
