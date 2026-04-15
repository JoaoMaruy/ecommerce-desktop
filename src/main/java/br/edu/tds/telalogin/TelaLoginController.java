package br.edu.tds.telalogin;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

public class TelaLoginController {

   @FXML
   private TextField txtUsuario;

   @FXML
   private PasswordField txtSenha;
   
   @FXML
   private Label lblUsuario;
   
   @FXML
   private Label lblSenha;


 @FXML
private void abrirTelaCadastroUsuario() throws Exception {
    // Verifique se o nome aqui é IDENTICO ao nome do arquivo na pasta resources
    App.setRoot("TelaCadastroUsuario"); 
}

      @FXML
   private void realizarLogin() throws IOException {

       String usuario = txtUsuario.getText();
       String senha = txtSenha.getText();
       

       if(usuario.isEmpty() && senha.isEmpty()){
           lblUsuario.setText("*Campo usuário é obrigatório");
           lblSenha.setText("*Campo senha é obrigatório");
           
           System.out.println("Campo usuário e senha são obrigatórios");
           return;
       }
       
       
       if(usuario.isEmpty()){
       lblUsuario.setText("*Campo usuário é obrigatório");

           
           System.out.println("Campo Usuário é obrigatório");
           return;
       }
       
       if(senha.isEmpty()){
        lblSenha.setText("*Campo senha é obrigatório");
           
           System.out.println("Campo senha é obrigatório");
           return;
       }
       
       
       lblUsuario.setText("");
       lblSenha.setText("");
       
       UsuarioDAO dao = new UsuarioDAO();
       Boolean login = dao.login(usuario, senha);
     if(login){
    // login com sucesso
    txtUsuario.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
    txtSenha.setStyle("-fx-border-color: green; -fx-border-width: 2px;");
    System.out.println("Login feito");
    App.setRoot("telaGerenciamentoUsuarios");
    } else {
    // falha no login
    lblUsuario.setText("Usuário/Senha incorretos");
    lblSenha.setText("Usuário/Senha incorretos");
    }   
   
   }}
