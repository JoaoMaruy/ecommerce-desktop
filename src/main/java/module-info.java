module br.edu.tds.ecommerce {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.fontawesome5;

    // Esta linha é CRUCIAL. Ela abre o pacote para o FXMLLoader encontrar sua classe.
    opens br.edu.tds.ecommerce to javafx.fxml;
    
    exports br.edu.tds.ecommerce;
}