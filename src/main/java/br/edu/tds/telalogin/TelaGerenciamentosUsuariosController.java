package br.edu.tds.telalogin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class TelaGerenciamentosUsuariosController implements Initializable {

    @FXML
    private TableView<Usuario> tabelaUsuarios;

    @FXML
    private TableColumn<Usuario, String> colNomeCompleto;

    @FXML
    private TableColumn<Usuario, String> colNomeUsuario;

    @FXML
    private TableColumn<Usuario, String> colEmail;

    @FXML
    private TableColumn<Usuario, String> colCpf;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // AJUSTADO: Os nomes devem ser iguais aos atributos da sua classe Usuario (NomeCompleto, NomeUsuario, etc)
        colNomeCompleto.setCellValueFactory(new PropertyValueFactory<>("NomeCompleto"));
        colNomeUsuario.setCellValueFactory(new PropertyValueFactory<>("NomeUsuario"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("Email"));
        colCpf.setCellValueFactory(new PropertyValueFactory<>("CPF"));

        carregarUsuarios();
    }

    private void carregarUsuarios() {
        ObservableList<Usuario> lista = FXCollections.observableArrayList();
        // AJUSTADO: Verifique se no banco é 'usuarios' (plural) conforme seu DAO sugeria
        String sql = "SELECT * FROM usuarios"; 

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                // AJUSTADO: getString deve usar o nome exato da COLUNA no banco de dados
                u.setNomeCompleto(rs.getString("nomeCompleto"));
                u.setNomeUsuario(rs.getString("nomeUsuario"));
                u.setEmail(rs.getString("email"));
                u.setCPF(rs.getString("CPF"));

                lista.add(u);
            }

            tabelaUsuarios.setItems(lista);

        } catch (Exception e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    @FXML
    private void abrirTelaCadastroUsuario() throws Exception {
        // AJUSTADO: O nome deve ser IDÊNTICO ao arquivo em resources (ex: TelaCadastroUsuario)
        // Se o arquivo for "TelaCadastroUsuario.fxml", use "TelaCadastroUsuario"
        App.setRoot("TelaCadastroUsuario"); 
    }

    @FXML
    private void excluirUsuario() {
        Usuario selecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            System.out.println("Selecione um usuário na tabela para excluir.");
            return;
        }

        // AJUSTADO: SQL para deletar usando a coluna correta do banco
        String sql = "DELETE FROM usuarios WHERE nomeUsuario = ?"; 

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, selecionado.getNomeUsuario());
            stmt.executeUpdate();

            carregarUsuarios();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
private void editarUsuario() throws Exception {
    Usuario selecionado = tabelaUsuarios.getSelectionModel().getSelectedItem();

    if (selecionado == null) {
        System.out.println("Selecione um usuário para editar.");
        return;
    }

    // 1. Salva o usuário na sessão para a outra tela ler
    Sessao.usuarioSelecionado = selecionado;

    // 2. Abre a tela de cadastro (que agora vai servir para editar também)
    App.setRoot("TelaCadastroUsuario"); 
}
}