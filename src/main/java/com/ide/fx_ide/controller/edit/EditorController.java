package com.ide.fx_ide.controller.edit;

import com.ide.fx_ide.service.compiler.CompilerService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EditorController implements Initializable {
    @FXML private MenuBar mb_top;
    @FXML private Label label_name;
    @FXML private TextArea ta_code;
    @FXML private Button btn_compile;
    @FXML private TextArea ta_result;

    private static final CompilerService compilerService = new CompilerService();
    private static final String[] DEFAULT_TOP_MENU = {"File", "Edit", "View", "Navigate", "Code", "Refactor", "Build", "Run", "Tools", "Git", "Window", "Help"};
    private static final String DEFAULT_EXTENSION = ".java";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setDefaultTopMenu(mb_top);
    }

    @FXML
    protected void onCompileButtonClick() {
        List<String> compileResult = compilerService.compileAfterConvertFile(label_name.getText() + DEFAULT_EXTENSION, ta_code.getText());
        ta_result.setText(compilerService.convertCompileResultToString(compileResult));
    }

    public void initData(Map<String, String> data) {
        label_name.setText(data.get("name"));
        ta_code.setText(data.get("code"));
    }

    private static void setDefaultTopMenu(MenuBar menuBar) {
        for (String str : DEFAULT_TOP_MENU) {
            menuBar.getMenus().add(new Menu(str));
        }
    }
}