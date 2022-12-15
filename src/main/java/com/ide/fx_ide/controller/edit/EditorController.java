package com.ide.fx_ide.controller.edit;

import com.ide.fx_ide.MainApplication;
import com.ide.fx_ide.service.compiler.CompilerService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorController implements Initializable {
    @FXML private MenuBar mb_top;
    @FXML private Label label_name;
    @FXML private TextArea ta_code;
    @FXML private Button btn_compile;
    @FXML private TextArea ta_result;
    @FXML private CodeArea ca_code;

    private static final CompilerService compilerService = new CompilerService();
    private static final String[] DEFAULT_TOP_MENU = {"File", "Edit", "View", "Navigate", "Code", "Refactor", "Build", "Run", "Tools", "Git", "Window", "Help"};
    private static final String DEFAULT_EXTENSION = ".java";

    private static final String[] KEYWORDS = new String[] {
            "abstract", "replacedert", "boolean", "break", "byte",
            "case", "catch", "char", "clreplaced", "const",
            "continue", "default", "do", "double", "else",
            "enum", "extends", "final", "finally", "float",
            "for", "goto", "if", "implements", "import",
            "instanceof", "int", "interface", "long", "native",
            "new", "package", "private", "protected", "public",
            "return", "short", "static", "strictfp", "super",
            "switch", "synchronized", "this", "throw", "throws",
            "transient", "try", "void", "volatile", "while"
    };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    private static final String sampleCode = String.join("\n", new String[] {
            "package com.example;",
            "",
            "import java.util.*;",
            "",
            "public clreplaced Foo extends Bar implements Baz {",
            "",
            "    /*",
            "     * multi-line comment",
            "     */",
            "    public static void main(String[] args) {",
            "        // single-line comment",
            "        for(String arg: args) {",
            "            if(arg.length() != 0)",
            "                System.out.println(arg);",
            "            else",
            "                System.err.println(\"Warning: empty string as argument\");",
            "        }",
            "    }",
            "",
            "}"
    });

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

        ca_code.setParagraphGraphicFactory(LineNumberFactory.get(ca_code));
        ca_code.textProperty().addListener((obs, oldText, newText) -> {
            System.out.println("oldText : " + oldText);
            System.out.println("newText : " + newText);
            ca_code.setStyleSpans(0, computeHighlighting(newText));
        });
        ca_code.setWrapText(true);
        String styleSheet = Objects.requireNonNull(MainApplication.class.getResource("static/css/test.css")).toExternalForm();
        ca_code.getStylesheets().add(styleSheet);
        ca_code.replaceText(0, 0, sampleCode);
        ca_code.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        System.out.println("matcher : " + matcher);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder
                = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String styleClreplaced =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACE") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("STRING") != null ? "string" :
                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                            null; /* never happens */
//            replacedert styleClreplaced != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClreplaced), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private static void setDefaultTopMenu(MenuBar menuBar) {
        for (String str : DEFAULT_TOP_MENU) {
            menuBar.getMenus().add(new Menu(str));
        }
    }
}