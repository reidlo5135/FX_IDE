package com.ide.fx_ide.controller.root;

import com.ide.fx_ide.service.common.CommonService;
import com.ide.fx_ide.service.file.FileService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {
    @FXML private Button btn_select;
    @FXML private Label label_select;
    @FXML private Button btn_new;
    @FXML private Label label_new;

    private Stage stage;
    private static final FileService fileService = new FileService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        label_select.setText("Select .java file");
        label_new.setText("Create new .java file");
    }

    @FXML
    protected void onSelectButtonClick() {
        CommonService.moveToEditScene(fileService.setFileChooser(stage), (Stage) btn_select.getScene().getWindow());
    }

    @FXML
    protected void onNewButtonClick() {
        fileService.setDirectoryChooser(stage);
    }
}
