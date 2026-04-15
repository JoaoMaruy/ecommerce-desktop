package br.edu.tds.telalogin;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class TelaCadastroUsuarioController implements Initializable {

    @FXML
    private TextField txtNomeCompleto;
    @FXML
    private TextField txtNovoUsuario;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtCPF;
    @FXML
    private PasswordField txtNovaSenha;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Verifica se estamos em modo de edição ao abrir a tela
        if (App.usuarioSelecionado != null) {
            Usuario u = App.usuarioSelecionado;
            txtNomeCompleto.setText(u.getNomeCompleto());
            txtNovoUsuario.setText(u.getNomeUsuario());
            txtEmail.setText(u.getEmail());
            txtCPF.setText(u.getCPF());
            txtNovaSenha.setText(u.getSenha());

            // O nome de usuário (ID) não deve ser alterado na edição
            txtNovoUsuario.setDisable(true); 
        }
    }

    @FXML
    private void cadastrar() {
        String nomeCompleto = txtNomeCompleto.getText().trim();
        String nomeUsuario = txtNovoUsuario.getText().trim();
        String email = txtEmail.getText().trim();
        String CPF = txtCPF.getText().trim();
        String senha = txtNovaSenha.getText().trim();

        

        if (nomeCompleto.isEmpty() || nomeUsuario.isEmpty() || email.isEmpty() || CPF.isEmpty() || senha.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos!");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        Usuario u = new Usuario();
        u.setNomeCompleto(nomeCompleto);
        u.setNomeUsuario(nomeUsuario);
        u.setEmail(email);
        u.setSenha(senha);
        u.setCPF(CPF);

        try {
            if (App.usuarioSelecionado != null) {
                // MODO EDIÇÃO
                dao.atualizar(u);
                mostrarAlerta("Sucesso", "Dados atualizados com sucesso!");
                App.usuarioSelecionado = null; // Limpa a sessão
                voltarGerenciamento(); // Volta para a tabela
            } else {
                // MODO NOVO CADASTRO
                dao.cadastrar(u);
                mostrarAlerta("Sucesso", "Cadastro realizado com sucesso!");
                limparCampos();
            }
        } catch (Exception e) {
            mostrarAlerta("Erro", "Falha na operação: " + e.getMessage());
        }
    }

    @FXML
    private void abrirTelaLogin() throws IOException {
        App.usuarioSelecionado = null; // Limpa a sessão ao sair
        App.setRoot("TelaLogin");
    }
    
    // Método para voltar à tela de gerenciamento após editar
    private void voltarGerenciamento() throws IOException {
        App.setRoot("TelaGerenciamentosUsuarios"); // Verifique se o nome do FXML está correto
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void limparCampos() {
        txtNomeCompleto.clear();
        txtNovoUsuario.clear();
        txtEmail.clear();
        txtCPF.clear();
        txtNovaSenha.clear();
    }
}