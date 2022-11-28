module com.ide.fx_ide {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ide.fx_ide to javafx.fxml;
    exports com.ide.fx_ide;
    exports com.ide.fx_ide.home;
    opens com.ide.fx_ide.home to javafx.fxml;
}