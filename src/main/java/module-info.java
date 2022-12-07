module com.ide.fx_ide {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.ide.fx_ide;
    exports com.ide.fx_ide.file;
    exports com.ide.fx_ide.editor;

    opens com.ide.fx_ide to javafx.fxml;
    opens com.ide.fx_ide.file to javafx.fxml;
    opens com.ide.fx_ide.editor to javafx.fxml;
}