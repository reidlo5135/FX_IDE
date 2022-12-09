package com.ide.fx_ide.file;

import com.ide.fx_ide.MainApplication;
import com.ide.fx_ide.common.CommonService;
import com.ide.fx_ide.editor.EditorController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class FileController implements Initializable {
    @FXML private Button btn_convert;

    private Stage stage;
    private FileService fileService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fileService = new FileService();
    }

    @FXML
    protected void onConvertButtonClick() {
        Map<String, String> map = fileService.setFileChooser(stage);
        Stage stage_file = (Stage) btn_convert.getScene().getWindow();
        String title = map.get("path") + " - " + map.get("name");

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApplication.class.getResource("editor.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            EditorController editorController = loader.getController();
            editorController.initData(map.get("code"));

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
