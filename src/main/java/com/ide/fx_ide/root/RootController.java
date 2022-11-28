package com.ide.fx_ide.root;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class RootController implements Initializable {
    @FXML private MenuBar mb_top;
    @FXML private TextArea ta_code;

    private static final String[] mb_top_arr = {"File", "Edit", "View", "Navigate", "Code", "Refactor", "Build", "Run", "Tools", "Git", "Window", "Help"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setMb_top_arr(mb_top);
        ta_code.setText("class Main {\n\tpublic static void main(String[] args) {\n\n\t}\n}");
    }

    private static void setMb_top_arr(MenuBar menuBar) {
        for(String str : mb_top_arr) {
            menuBar.getMenus().add(new Menu(str));
        }
    }
}