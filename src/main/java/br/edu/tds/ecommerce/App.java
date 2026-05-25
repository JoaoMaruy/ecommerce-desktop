package br.edu.tds.ecommerce;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    
    public static Usuario usuarioSelecionado; 

    @Override
    public void start(Stage stage) throws IOException {
        // Garanta que o nome do seu arquivo FXML na pasta de resources seja TelaGerenciamentoProdutos.fxml
        scene = new Scene(loadFXML("TelaGerenciamentoProdutos"), 1000, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
            App.class.getResource("/br/edu/tds/ecommerce/" + fxml + ".fxml")
        );
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
}