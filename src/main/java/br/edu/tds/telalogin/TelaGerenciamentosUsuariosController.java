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

    // Campos de entrada na mesma tela (Certifique-se de que os fx:id no FXML sejam estes)
    @FXML private TextField txtNomeCompleto;
    @FXML private TextField txtNomeUsuario;
    @FXML private TextField txtEmail;
    @FXML private TextField txtCpf;

    private Usuario usuarioSelecionadoParaEdicao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colNomeCompleto.setCellValueFactory(new PropertyValueFactory<>("NomeCompleto"));
        colNomeUsuario.setCellValueFactory(new PropertyValueFactory<>("NomeUsuario"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("CPF"));

        carregarUsuarios();
    }

    private void carregarUsuarios() {
        ObservableList<Usuario> lista = FXCollections.observableArrayList();
        String sql = "SELECT * FROM usuarios"; 

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setNomeCompleto(rs.getString("nomeCompleto"));
                u.setNomeUsuario(rs.getString("nomeUsuario"));
                u.setEmail(rs.getString("email"));
                u.setCPF(rs.getString("CPF"));
                lista.add(u);
            }
            tabelaUsuarios.setItems(lista);

        } catch (Exception e) {
            mostrarAlerta("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    // MÉTODO EDITAR: Agora preenche os campos locais em vez de trocar de tela
    @FXML
    private void editarUsuario() {
        usuarioSelecionadoParaEdicao = tabelaUsuarios.getSelectionModel().getSelectedItem();

        if (usuarioSelecionadoParaEdicao == null) {
            mostrarAlerta("Selecione um usuário na tabela para editar.");
            return;
        }

        // Preenche os campos para edição
        txtNomeCompleto.setText(usuarioSelecionadoParaEdicao.getNomeCompleto());
        txtNomeUsuario.setText(usuarioSelecionadoParaEdicao.getNomeUsuario());
        txtEmail.setText(usuarioSelecionadoParaEdicao.getEmail());
        txtCpf.setText(usuarioSelecionadoParaEdicao.getCPF());

        // Desabilita o ID (nomeUsuario) para evitar alteração da chave primária
        txtNomeUsuario.setDisable(true);
        
        mostrarAlerta("Dados carregados! Altere as informações e clique em Salvar.");
    }

    // NOVO MÉTODO: Para confirmar a alteração no banco
    @FXML
    private void salvarAlteracoes() {
        if (usuarioSelecionadoParaEdicao == null) {
            mostrarAlerta("Nenhum usuário está sendo editado no momento.");
            return;
        }

        String sql = "UPDATE usuarios SET nomeCompleto = ?, email = ?, CPF = ? WHERE nomeUsuario = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, txtNomeCompleto.getText());
            stmt.setString(2, txtEmail.getText());
            stmt.setString(3, txtCpf.getText());
            stmt.setString(4, usuarioSelecionadoParaEdicao.getNomeUsuario());

            stmt.executeUpdate();
            
            mostrarAlerta("Usuário atualizado com sucesso!");
            
            limparCampos();
            carregarUsuarios();

        } catch (Exception e) {
            mostrarAlerta("Erro ao salvar: " + e.getMessage());
        }
    }

    @FXML
    private void excluirUsuario() {
        Usuario selecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            mostrarAlerta("Selecione um usuário para excluir.");
            return;
        }

        String sql = "DELETE FROM usuarios WHERE nomeUsuario = ?"; 

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, selecionado.getNomeUsuario());
            stmt.executeUpdate();

            mostrarAlerta("Usuário removido com sucesso!");
            carregarUsuarios();

        } catch (Exception e) {
            mostrarAlerta("Erro ao excluir: " + e.getMessage());
        }
    }

    private void limparCampos() {
        txtNomeCompleto.clear();
        txtNomeUsuario.clear();
        txtEmail.clear();
        txtCpf.clear();
        txtNomeUsuario.setDisable(false);
        usuarioSelecionadoParaEdicao = null;
    }

    // Mantenha este se ainda quiser ir para a tela de novo cadastro
    @FXML
    private void abrirTelaCadastroUsuario() throws Exception {
        App.setRoot("TelaCadastroUsuario"); 
    }

    // O ALERTA FUNCIONAL SOLICITADO
    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sistema");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}