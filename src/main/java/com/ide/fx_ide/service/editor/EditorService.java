package com.ide.fx_ide.service.editor;

import com.ide.fx_ide.MainApplication;
import javafx.geometry.Insets;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorService {
    private static final String DIRECTORY_CSS = "css/";
    private static final String[] FIRST_KEYWORDS = new String[] {
            "abstract", "boolean", "break", "switch", "case",
            "char", "clreplaced", "const", "continue", "do",
            "enum", "extends", "final", "for", "while", "goto",
            "if", "else", "extends", "implements", "import", "instanceof",
            "class", "interface", "native", "new", "package",
            "private", "protected", "public", "default",
            "return", "static", "strictfp", "super", "synchronized", "volatile",
            "this", "throw", "throws", "transient", "try", "catch", "finally", "void",
            "int", "double", "short", "byte", "long"
    };
    private static final String[] SECOND_KEYWORDS = new String[] {
            "String"
    };
    private static final String[] THIRD_KEYWORDS = new String[] {
            "in", "out"
    };
    private static final String FIRST_KEYWORD_PATTERN = "\\b(" + String.join("|", FIRST_KEYWORDS) + ")\\b";
    private static final String SECOND_KEYWORD_PATTERN = "\\b(" + String.join("|", SECOND_KEYWORDS) + ")\\b";
    private static final String THIRD_KEYWORD_PATTERN  = "\\b(" + String.join("|", THIRD_KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String TEXT_PAREN_PATTERN = "(.*?)[{]";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String TEXT_BRACE_PATTERN = "(.*?)[(]";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";
    private static final Pattern PATTERN = Pattern.compile(
            "(?<FIRST>" + FIRST_KEYWORD_PATTERN + ")"
                    + "|(?<SECOND>" + SECOND_KEYWORD_PATTERN + ")"
                    + "|(?<THIRD>" + THIRD_KEYWORD_PATTERN + ")"
                    + "|(?<PAREN>" + PAREN_PATTERN + ")"
                    + "|(?<BRACE>" + BRACE_PATTERN + ")"
                    + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
                    + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
                    + "|(?<STRING>" + STRING_PATTERN + ")"
                    + "|(?<COMMENT>" + COMMENT_PATTERN + ")"
    );
    private static final Pattern TEXT_PATTERN = Pattern.compile(
        "(?<BRACE>" + TEXT_BRACE_PATTERN + ")"
                + "|(?<PAREN>" + TEXT_PAREN_PATTERN + ")"
    );

    public void setCodeArea(CodeArea ca_code, String code) {
        setListener(ca_code);
        ca_code.setParagraphGraphicFactory(LineNumberFactory.get(ca_code));
        ca_code.setWrapText(true);
        ca_code.getStylesheets().add(Objects.requireNonNull(MainApplication.class.getResource(DIRECTORY_CSS + "codeArea.css")).toExternalForm());
        ca_code.replaceText(0, 0, code);
        ca_code.setBackground(new Background(new BackgroundFill(Paint.valueOf("#FFFFFF"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    private static void setListener(CodeArea ca_code) {
        ca_code.textProperty().addListener((obs, oldText, newText) -> {
            ca_code.setStyleSpans(0, computeHighlighting(newText));
            ca_code.setOnKeyPressed(event -> {
                if(event.getCode().equals(KeyCode.ENTER)) {
                    System.out.println("range : " + ca_code.getCaretSelectionBind().getRange());
                    System.out.println("index : " + ca_code.getCaretSelectionBind().getParagraphIndex());
                    System.out.println("-1 : " + ca_code.getText(ca_code.getCaretSelectionBind().getParagraphIndex() - 2));
                    ca_code.replaceText(ca_code.getCaretSelectionBind().getRange(), "\t\t");
                }
            });

            String text = ca_code.getText(ca_code.getCurrentParagraph());
            System.out.println("text : " + text);

            int position = ca_code.getAbsolutePosition(ca_code.getCaretSelectionBind().getParagraphIndex(), ca_code.getCaretColumn());
            System.out.println("position : " + position);

            Matcher matcher = TEXT_PATTERN.matcher(text.replaceAll(" ", ""));
            while (matcher.find()) {
                if(matcher.matches()) {
                    String group = matcher.group();
                    System.out.println("group : " + group);
                    if(group.contains("{}") || group.contains("()")) {
                        if(group.contains("(){")) {
                            ca_code.insertText(position, "\n\n\t}");
                        } else break;
                    }
                    if(group.contains("{") && group.contains("(")) break;
                    if(group.contains("{")) {
                        System.out.println("{{{{{");
                        ca_code.insertText(position, "\n\n}");
                        break;
                    }
                    if(group.contains("(")) {
                        System.out.println("{{{{{");
                        ca_code.insertText(position, ")");
                        break;
                    }
                }
            }
        });
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while(matcher.find()) {
            String replaced =
                    matcher.group("FIRST") != null ? "first" :
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
}
