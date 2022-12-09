package com.ide.fx_ide.editor;

import com.ide.fx_ide.file.FileService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditorController implements Initializable {
    @FXML AnchorPane subPage;
    @FXML private MenuBar mb_top;
    @FXML private TextArea ta_code;
    @FXML private Button btn_compile;
    @FXML private TextArea ta_result;

    private static final String DEFAULT_CODE = "class Main {\n\tpublic static void main(String[] args) {\n\n\t}\n}";
    private static final String[] DEFAULT_TOP_MENU = {"File", "Edit", "View", "Navigate", "Code", "Refactor", "Build", "Run", "Tools", "Git", "Window", "Help"};

    private FileService fileService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("EditorController initialized");
        fileService = new FileService();
        setDefaultTopMenu(mb_top);
    }

    @FXML
    protected void onCompileButtonClick() {
        List<String> compileResult = fileService.compileAfterConvertFile(ta_code.getText());
        System.out.println("CompileResult : " + compileResult);
        ta_result.setText(fileService.convertCompileResultToString(compileResult));
    }

    private static void setDefaultTopMenu(MenuBar menuBar) {
        for(String str : DEFAULT_TOP_MENU) {
            menuBar.getMenus().add(new Menu(str));
        }
    }

    public void initData(String data) {
        System.out.println("EditController initData: " + data);
        ta_code.setText(data);
    }
}