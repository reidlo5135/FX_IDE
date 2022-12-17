package com.ide.fx_ide.controller.editor;

import com.ide.fx_ide.service.common.CommonService;
import com.ide.fx_ide.service.compiler.CompilerService;
import com.ide.fx_ide.service.editor.EditorService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.fxmisc.richtext.CodeArea;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EditorSceneController implements Initializable {
    @FXML private MenuBar mb_top;
    @FXML private Label label_name;
    @FXML private Button btn_compile;
    @FXML private TextArea ta_result;
    @FXML private CodeArea ca_code;

    private static final String DEFAULT_EXTENSION = ".java";
    private static final String[] DEFAULT_TOP_MENU = {"File", "Edit", "View", "Navigate", "Code", "Refactor", "Build", "Run", "Tools", "Git", "Window", "Help"};

    private static CommonService commonService;
    private static EditorService editorService;
    private static CompilerService compilerService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        commonService = new CommonService();
        editorService = new EditorService();
        compilerService = new CompilerService();
        setDefaultTopMenu(mb_top);
    }

    @FXML
    protected void onCompileButtonClick() {
        List<String> compileResult = compilerService.compileAfterConvertFile(label_name.getText() + DEFAULT_EXTENSION, ca_code.getText());
        ta_result.setText(compilerService.convertCompileResultToString(compileResult));
    }

    public void initData(Map<String, String> data) {
        label_name.setText(data.get("name"));
        editorService.setCodeArea(ca_code, data.get("code"));
    }

    private static void setDefaultTopMenu(MenuBar menuBar) {
        for (String str : DEFAULT_TOP_MENU) {
            menuBar.getMenus().add(new Menu(str));
        }
    }
}