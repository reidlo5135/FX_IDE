module com.ide.fx_ide {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.ide.fx_ide;
    exports com.ide.fx_ide.service.file;
    exports com.ide.fx_ide.service.common;
    exports com.ide.fx_ide.service.compiler;
    exports com.ide.fx_ide.controller.edit;
    exports com.ide.fx_ide.controller.root;

    opens com.ide.fx_ide to javafx.fxml;
    opens com.ide.fx_ide.service.file to javafx.fxml;
    opens com.ide.fx_ide.service.common to javafx.fxml;
    opens com.ide.fx_ide.service.compiler to javafx.fxml;
    opens com.ide.fx_ide.controller.edit to javafx.fxml;
    opens com.ide.fx_ide.controller.root to javafx.fxml;
}