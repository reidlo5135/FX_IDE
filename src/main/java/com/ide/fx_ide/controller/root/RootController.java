package com.ide.fx_ide.controller.root;

import com.ide.fx_ide.MainApplication;
import com.ide.fx_ide.service.common.CommonService;
import com.ide.fx_ide.service.file.FileService;
import com.ide.fx_ide.controller.edit.EditorController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class RootController implements Initializable {
    @FXML private Button btn_file;
    @FXML private Label label_file;

    private Stage stage;
    private FileService fileService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileService = new FileService();
        label_file.setText("Select .java file");
    }

    @FXML
    protected void onConvertButtonClick() {
        Map<String, String> map = fileService.setFileChooser(stage);
        Stage stage_file = (Stage) btn_file.getScene().getWindow();
        String title = map.get("path") + " - " + map.get("name");

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("editor.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            EditorController editorController = loader.getController();
            editorController.setTextArea(map.get("code"));

            Stage stage_editor = new Stage();
            stage_editor.setScene(scene);
            stage_editor.setTitle(title);
            CommonService.setResources(stage_editor, scene, "editor.css", "favicon.png");
            stage_editor.show();

            stage_file.close();
            stage_file.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
