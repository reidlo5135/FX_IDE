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

    private static final String DEFAULT_CODE = "class Main {\n\tpublic static void main(String[] args) {\n\n\t}\n}";
    private static final String[] DEFAULT_TOP_MENU = {"File", "Edit", "View", "Navigate", "Code", "Refactor", "Build", "Run", "Tools", "Git", "Window", "Help"};

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDefaultTopMenu(mb_top);
        ta_code.setText(DEFAULT_CODE);
    }

    private static void setDefaultTopMenu(MenuBar menuBar) {
        for(String str : DEFAULT_TOP_MENU) {
            menuBar.getMenus().add(new Menu(str));
        }
    }
}