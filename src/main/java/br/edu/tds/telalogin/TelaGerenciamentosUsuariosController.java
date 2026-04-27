package br.edu.tds.telalogin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class TelaGerenciamentosUsuariosController implements Initializable {

    @FXML private TableView<Usuario> tabelaUsuarios;
    @FXML private TableColumn<Usuario, String> colNomeCompleto;
    @FXML private TableColumn<Usuario, String> colNomeUsuario;
    @FXML private TableColumn<Usuario, String> colEmail;
    @FXML private TableColumn<Usuario, String> colCpf;
    @FXML private TableColumn<Usuario, String> colSenha;

    @FXML private TextField txtNomeCompleto;
    @FXML private TextField txtNomeUsuario;
    @FXML private TextField txtEmail;
    @FXML private TextField txtCpf;
    @FXML private PasswordField txtSenha; // Alterado para PasswordField
    @FXML private Label lblMensagem;      // Adicionado conforme material

    private Usuario usuarioSelecionadoParaEdicao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNomeCompleto.setCellValueFactory(new PropertyValueFactory<>("NomeCompleto"));
        colNomeUsuario.setCellValueFactory(new PropertyValueFactory<>("NomeUsuario"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("CPF"));
        colSenha.setCellValueFactory(new PropertyValueFactory<>("Senha"));

        carregarUsuarios();
    }

    private void carregarUsuarios() {
        ObservableList<Usuario> lista = FXCollections.observableArrayList();
        String sql = "SELECT nomeCompleto, nomeUsuario, email, CPF, senha FROM usuarios"; 

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setNomeCompleto(rs.getString("nomeCompleto"));
                u.setNomeUsuario(rs.getString("nomeUsuario"));
                u.setEmail(rs.getString("email"));
                u.setCPF(rs.getString("CPF"));
                u.setSenha(rs.getString("senha"));
                lista.add(u);
            }
            tabelaUsuarios.setItems(lista);

        } catch (Exception e) {
            lblMensagem.setText("Erro ao carregar: " + e.getMessage());
        }
    }

    @FXML
    private void salvarAlteracoes() {
        // --- INÍCIO DAS VALIDAÇÕES DO MATERIAL DE ESTUDO ---
        lblMensagem.setText("");
        lblMensagem.setStyle("-fx-text-fill: red;");
        resetarEstilos();

        if (usuarioSelecionadoParaEdicao == null) {
            lblMensagem.setText("Selecione um usuário na tabela!");
            return;
        }

        if (txtNomeCompleto.getText().isEmpty()) {
            lblMensagem.setText("Informe o nome completo");
            txtNomeCompleto.setStyle("-fx-border-color: red;");
            txtNomeCompleto.requestFocus();
            return;
        }

        if (!txtEmail.getText().contains("@")) {
            lblMensagem.setText("E-mail inválido");
            txtEmail.setStyle("-fx-border-color: red;");
            txtEmail.requestFocus();
            return;
        }

        if (txtCpf.getText().length() != 11) {
            lblMensagem.setText("CPF deve ter 11 dígitos");
            txtCpf.setStyle("-fx-border-color: red;");
            txtCpf.requestFocus();
            return;
        }

        if (txtSenha.getText().length() < 6) {
            lblMensagem.setText("Senha muito curta (mín. 6)");
            txtSenha.setStyle("-fx-border-color: red;");
            txtSenha.requestFocus();
            return;
        }
        // --- FIM DAS VALIDAÇÕES ---

        String sql = "UPDATE usuarios SET nomeCompleto = ?, email = ?, CPF = ?, senha = ? WHERE nomeUsuario = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, txtNomeCompleto.getText());
            stmt.setString(2, txtEmail.getText());
            stmt.setString(3, txtCpf.getText());
            stmt.setString(4, txtSenha.getText());
            stmt.setString(5, usuarioSelecionadoParaEdicao.getNomeUsuario());

            stmt.executeUpdate();
            
            lblMensagem.setStyle("-fx-text-fill: green;");
            lblMensagem.setText("Usuário atualizado!");
            
            limparCampos();
            carregarUsuarios();

        } catch (Exception e) {
            lblMensagem.setText("Erro ao salvar: " + e.getMessage());
        }
    }

    private void resetarEstilos() {
        txtNomeCompleto.setStyle("");
        txtEmail.setStyle("");
        txtCpf.setStyle("");
        txtSenha.setStyle("");
    }

    @FXML
    private void editarUsuario() {
        usuarioSelecionadoParaEdicao = tabelaUsuarios.getSelectionModel().getSelectedItem();
        if (usuarioSelecionadoParaEdicao == null) {
            lblMensagem.setText("Selecione alguém na tabela.");
            return;
        }
        txtNomeCompleto.setText(usuarioSelecionadoParaEdicao.getNomeCompleto());
        txtNomeUsuario.setText(usuarioSelecionadoParaEdicao.getNomeUsuario());
        txtEmail.setText(usuarioSelecionadoParaEdicao.getEmail());
        txtCpf.setText(usuarioSelecionadoParaEdicao.getCPF());
        txtSenha.setText(usuarioSelecionadoParaEdicao.getSenha());
        txtNomeUsuario.setDisable(true);
        lblMensagem.setText("Editando: " + usuarioSelecionadoParaEdicao.getNomeUsuario());
    }

    private void limparCampos() {
        txtNomeCompleto.clear();
        txtNomeUsuario.clear();
        txtEmail.clear();
        txtCpf.clear();
        txtSenha.clear();
        txtNomeUsuario.setDisable(false);
        usuarioSelecionadoParaEdicao = null;
        resetarEstilos();
    }

    @FXML
    private void excluirUsuario() {
        Usuario selecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();
        if (selecionado == null) return;
        
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM usuarios WHERE nomeUsuario = ?")) {
            stmt.setString(1, selecionado.getNomeUsuario());
            stmt.executeUpdate();
            carregarUsuarios();
            lblMensagem.setText("Usuário removido.");
        } catch (Exception e) {
            lblMensagem.setText("Erro ao excluir.");
        }
    }

    @FXML private void abrirTelaCadastroUsuario() throws Exception { App.setRoot("TelaCadastroUsuario"); }
}