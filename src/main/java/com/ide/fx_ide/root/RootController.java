package com.ide.fx_ide.root;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {
    @FXML private TextArea ta_code;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ta_code.setText("class Main {\n\tpublic static void main(String[] args) {\n\n\t}\n}");
    }
}