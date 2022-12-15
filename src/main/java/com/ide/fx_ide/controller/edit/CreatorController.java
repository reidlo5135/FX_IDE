package com.ide.fx_ide.controller.edit;

import com.ide.fx_ide.service.common.CommonService;
import com.ide.fx_ide.service.file.FileService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CreatorController implements Initializable {
    @FXML private Label label_path;
    @FXML private TextField tf_name;
    @FXML private Button btn_create;

    private Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

    @FXML
    protected void onCreateButtonClick() {
        String path = label_path.getText();
        String name = tf_name.getText();
        String code = "public class " + name + " {\n\tpublic static void main(String[] args) {\n\n\t}\n}";

        Map<String, String> data = new HashMap<>();
        data.put("path", path);
        data.put("name", name);
        data.put("code", code);

        FileService.createNewFile(data);
        CommonService.moveToEditScene(data, (Stage) btn_create.getScene().getWindow());
    }

    public void setFilePath(String path) {
        label_path.setText(path);
    }

    private boolean isCapStart(String name) {
        char[] chars = name.toCharArray();
        return Character.isUpperCase(chars[0]);
    }
}
