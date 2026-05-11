package br.edu.tds.ecommerce;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TelaCadastroProdutosController { // Nome ajustado para bater com o FXML

    @FXML private TextField txtNome;
    @FXML private TextArea txtDescricao;
    @FXML private ComboBox<String> cbCategoria;
    @FXML private TextField txtPreco;
    @FXML private TextField txtQuantidade;
    @FXML private TextField txtImagem;
    @FXML private CheckBox chkAtivo;

    @FXML
    public void initialize() {
        // Preenche o ComboBox ao abrir a tela
        if (cbCategoria != null) {
            cbCategoria.getItems().clear();
            cbCategoria.getItems().addAll(
                "Eletrônicos", "Informática", "Roupas", "Livros", "Jogos", "Celulares"
            );
        }
    }

    @FXML
    private void salvarProduto() {
        if (!validarCampos()) return;

        String sql = "INSERT INTO produto (nome, descricao, categoria, preco, quantidade, imagem, ativo) VALUES (?, ?, ?, ?, ?, ?, ?)";

        // O bloco try-with-resources garante que a conexão será fechada automaticamente
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, txtNome.getText().trim());
            stmt.setString(2, txtDescricao.getText().trim());
            stmt.setString(3, cbCategoria.getValue());
            
            // Tratamento para aceitar vírgula ou ponto no preço
            String precoTexto = txtPreco.getText().replace(",", ".");
            stmt.setDouble(4, Double.parseDouble(precoTexto));
            
            stmt.setInt(5, Integer.parseInt(txtQuantidade.getText().trim()));
            stmt.setString(6, txtImagem.getText().trim());
            stmt.setBoolean(7, chkAtivo.isSelected());

            stmt.executeUpdate();
            
            mostrarMensagem("Sucesso", "Produto cadastrado com sucesso!", Alert.AlertType.INFORMATION);
            limparCampos();

        } catch (NumberFormatException e) {
            mostrarMensagem("Erro de Formato", "Preço ou Quantidade inválidos. Use apenas números.", Alert.AlertType.ERROR);
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarMensagem("Erro de Banco", "Erro ao salvar no banco: " + e.getMessage(), Alert.AlertType.ERROR);
        } catch (Exception e) {
            e.printStackTrace();
            mostrarMensagem("Erro Crítico", "Ocorreu um erro inesperado.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void limparCampos() {
        txtNome.clear();
        txtDescricao.clear();
        txtPreco.clear();
        txtQuantidade.clear();
        txtImagem.clear();
        cbCategoria.setValue(null);
        chkAtivo.setSelected(true);
    }

    @FXML
    private void voltar() {
        // Exemplo: carregar a tela principal
        // App.setRoot("telaPrincipal"); 
        System.out.println("Botão voltar pressionado.");
    }

    private boolean validarCampos() {
        if (txtNome.getText().trim().isEmpty() || 
            txtPreco.getText().trim().isEmpty() || 
            txtQuantidade.getText().trim().isEmpty() ||
            cbCategoria.getValue() == null) {
            
            mostrarMensagem("Aviso", "Preencha Nome, Preço, Quantidade e Categoria!", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    // Método de mensagem melhorado para aceitar o tipo de alerta
    private void mostrarMensagem(String titulo, String msg, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}