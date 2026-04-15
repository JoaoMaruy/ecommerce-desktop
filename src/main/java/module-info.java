module br.edu.tds.telalogin {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens br.edu.tds.telalogin to javafx.fxml;
    exports br.edu.tds.telalogin;
}
