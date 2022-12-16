package com.ide.fx_ide.service.common;

import com.ide.fx_ide.MainApplication;
import com.ide.fx_ide.controller.edit.CreatorSceneController;
import com.ide.fx_ide.controller.edit.EditorSceneController;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonService {
    private static final String DIRECTORY_VIEW = "view/";
    private static final String DIRECTORY_CSS = "css/";
    private static final String DIRECTORY_IMAGE = "image/";
    private static final String[] KEYWORDS = new String[] {
            "abstract", "replacedert", "boolean", "break",
            "switch", "case", "char", "clreplaced", "const",
            "continue", "do", "enum", "extends", "final", "for", "while",
            "goto", "if", "else", "extends", "implements", "import",
            "instanceof", "class", "interface", "native", "new", "package",
            "private", "protected", "public", "default", "return", "static",
            "strictfp", "super", "synchronized", "this", "throw", "throws",
            "transient", "try", "catch", "finally", "void", "volatile",
            "int", "double", "short", "byte", "long"
    };
    private static final String[] SECOND_KEYWORDS = new String[] {
            "String"
    };
    private static final String[] THIRD_KEYWORDS = new String[] {
            "in", "out"
    };
    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String SECOND_KEYWORD_PATTERN = "\\b(" + String.join("|", SECOND_KEYWORDS) + ")\\b";
    private static final String THIRD_KEYWORD_PATTERN  = "\\b(" + String.join("|", THIRD_KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";
    private static final Pattern PATTERN = Pattern.compile(
            "(?<KEYWORD>" + KEYWORD_PATTERN + ")"
                    + "|(?<SECOND>" + SECOND_KEYWORD_PATTERN + ")"
                    + "|(?<THIRD>" + THIRD_KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );

    public static String setDateTimeFormat() {
        Date nowTime = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        return simpleDateFormat.format(nowTime);
    }

    public static void setResources(Stage stage, Scene scene, String css, String favicon) {
        scene.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource(DIRECTORY_CSS + css)).toString());
        stage.getIcons().add(new Image(Objects.requireNonNull(MainApplication.class.getResource(DIRECTORY_IMAGE + favicon)).toString()));
    }

    public void moveToEditScene(Map<String, String> data, Stage stage_previous) {
        String title = data.get("path") + " - " + data.get("name");

        try {
            FXMLLoader loader = setLoaderLocation(DIRECTORY_VIEW + "editor.fxml");
            Parent root = loader.load();
            Scene scene = new Scene(root, 1680, 840);
            EditorSceneController editorSceneController = loader.getController();
            editorSceneController.initData(data);

            Stage stage_editor = new Stage();
            stage_editor.setScene(scene);
            stage_editor.setTitle(title);
            CommonService.setResources(stage_editor, scene, "editor.css", "favicon.png");
            stage_editor.show();

            stage_previous.close();
            stage_previous.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void moveToCreateScene(String path, Stage stage_previous) {
        System.out.println("path : " + path);

        try {
            FXMLLoader loader = setLoaderLocation(DIRECTORY_VIEW + "creator.fxml");
            Parent root = loader.load();
            Scene scene = new Scene(root, 300, 150);
            CreatorSceneController creatorSceneController = loader.getController();
            creatorSceneController.setFilePath(path);

            Stage stage_creator = new Stage();
            stage_creator.setScene(scene);
            stage_creator.setTitle("Create New Java File");
            CommonService.setResources(stage_creator, scene, "creator.css", "favicon.png");
            stage_creator.show();

            stage_previous.close();
            stage_previous.hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static FXMLLoader setLoaderLocation(String fxml) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApplication.class.getResource(fxml));
        return loader;
    }

    public void setCodeArea(CodeArea ca_code, String code) {
        ca_code.setParagraphGraphicFactory(LineNumberFactory.get(ca_code));
        ca_code.textProperty().addListener((obs, oldText, newText) -> {
            ca_code.setStyleSpans(0, computeHighlighting(newText));
            ca_code.setOnKeyPressed(event -> {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    ca_code.replaceText(ca_code.getCaretSelectionBind().getRange(), "\t\t");
                }
            });
        });
        ca_code.setWrapText(true);
        String styleSheet = Objects.requireNonNull(MainApplication.class.getResource(DIRECTORY_CSS + "codeArea.css")).toExternalForm();
        ca_code.getStylesheets().add(styleSheet);
        ca_code.replaceText(0, 0, code);
        ca_code.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));;
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String replaced =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("SECOND") != null ? "second" :
                                    matcher.group("THIRD") != null ? "third" :
                                                matcher.group("PAREN") != null ? "paren" :
                                                    matcher.group("BRACE") != null ? "brace" :
                                                            matcher.group("BRACKET") != null ? "bracket" :
                                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                                            matcher.group("STRING") != null ? "string" :
                                                                                    matcher.group("COMMENT") != null ? "comment" :
                                                                                            null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(replaced), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private boolean isCapStart(String name) {
        char[] chars = name.toCharArray();
        return Character.isUpperCase(chars[0]);
    }
}
