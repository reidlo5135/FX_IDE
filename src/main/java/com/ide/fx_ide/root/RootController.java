package com.ide.fx_ide.root;

import com.ide.fx_ide.MainApplication;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class RootController implements Initializable {
    @FXML
    private Button btn_convert;

    private static final String DIRECTORY_CSS = "static/css/";
    private static final String DIRECTORY_IMAGE = "static/image/";

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    protected void onConvertButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("java", ".java"));

        File file = fileChooser.showOpenDialog(stage);

        Stage stage_editor = new Stage();
        Stage stage_root = (Stage) btn_convert.getScene().getWindow();

        try {
            Parent fxmlEditor = FXMLLoader.load(Objects.requireNonNull(MainApplication.class.getResource("editor.fxml")));
            Scene scene_editor = new Scene(fxmlEditor, 1680, 840);
            stage_editor.setScene(scene_editor);
            setResources(stage_editor, scene_editor);
            stage_editor.show();

            stage_root.close();
            stage_root.hide();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void setResources(Stage stage, Scene scene) {
        scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource(DIRECTORY_CSS + "editor.css")).toString());
        stage.getIcons().add(new Image(Objects.requireNonNull(MainApplication.class.getResource(DIRECTORY_IMAGE + "favicon.png")).toString()));
    }
}
