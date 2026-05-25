package br.edu.tds.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TelaGerenciamentoProdutosController {

    @FXML private Label lblMensagem;
    @FXML private TextField txtNome;
    @FXML private TextField txtDescricao;
    @FXML private TextField txtPreco;
    @FXML private TextField txtEstoque;
    @FXML private TextField txtCategoria;

    @FXML private TableView<Produto> tabelaProdutos; 
    @FXML private TableColumn<Produto, String> colNome;
    @FXML private TableColumn<Produto, String> colDescricao;
    @FXML private TableColumn<Produto, Double> colPreco;
    @FXML private TableColumn<Produto, Integer> colEstoque;
    @FXML private TableColumn<Produto, String> colCategoria;

    private ObservableList<Produto> listaDeProdutos = FXCollections.observableArrayList();
    private Produto produtoSelecionado;

    // CONFIGURAÇÃO DO BANCO DE DADOS 
    private final String URL = "jdbc:mysql://localhost:3306/ecommerce?useTimezone=true&serverTimezone=UTC";
    private final String USUARIO = "root"; 
    private final String SENHA = "ifsuldeminas";       

    private Connection obterConexao() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    @FXML
    public void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao")); 
        colPreco.setCellValueFactory(new PropertyValueFactory<>("preco"));
        colEstoque.setCellValueFactory(new PropertyValueFactory<>("quantidade")); 
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));

        carregarDadosDoBanco();

        tabelaProdutos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                produtoSelecionado = newValue;
                txtNome.setText(produtoSelecionado.getNome());
                txtDescricao.setText(produtoSelecionado.getDescricao()); 
                txtPreco.setText(String.valueOf(produtoSelecionado.getPreco()));
                txtEstoque.setText(String.valueOf(produtoSelecionado.getQuantidade()));
                txtCategoria.setText(produtoSelecionado.getCategoria());
                lblMensagem.setText("Editando: " + produtoSelecionado.getNome());
            }
        });
    }

    private void carregarDadosDoBanco() {
        listaDeProdutos.clear();
        String sql = "SELECT * FROM ecommerce.produtos"; 

        try (Connection conn = obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto(
                    rs.getInt("id"), 
                    rs.getString("nome"),
                    rs.getString("descricao"),  // 🌟 CORRIGIDO: O Java agora busca 'descricao' do banco
                    rs.getString("categoria"),
                    rs.getDouble("preco"),
                    rs.getInt("quantidade")     
                );
                listaDeProdutos.add(p);
            }
            tabelaProdutos.setItems(listaDeProdutos);

        } catch (SQLException e) {
            e.printStackTrace();
            lblMensagem.setText("Erro ao carregar dados do MySQL.");
        }
    }

    @FXML
    public void salvarAlteracoes(ActionEvent event) {
        if (produtoSelecionado == null) {
            lblMensagem.setText("Selecione um produto na tabela primeiro!");
            return;
        }

        // 🌟 CORRIGIDO: Mudado de descricac para descricao na query do UPDATE
        String sql = "UPDATE produtos SET nome = ?, descricao = ?, categoria = ?, preco = ?, quantidade = ? WHERE id = ?";

        try (Connection conn = obterConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, txtNome.getText());
            stmt.setString(2, txtDescricao.getText()); 
            stmt.setString(3, txtCategoria.getText());
            stmt.setDouble(4, Double.parseDouble(txtPreco.getText()));
            stmt.setInt(5, Integer.parseInt(txtEstoque.getText()));
            stmt.setInt(6, produtoSelecionado.getId()); 

            stmt.executeUpdate();
            lblMensagem.setText("Produto atualizado com sucesso!");
            
            carregarDadosDoBanco(); 
            produtoSelecionado = null; 

        } catch (SQLException e) {
            e.printStackTrace();
            lblMensagem.setText("Erro ao salvar no banco de dados.");
        } catch (NumberFormatException e) {
            lblMensagem.setText("Erro: Preço e Quantidade precisam ser números.");
        }
    }

    @FXML public void abrirTelaCadastroProduto(ActionEvent event) {}
    @FXML public void editarProduto(ActionEvent event) {}
    @FXML public void excluirProduto(ActionEvent event) {}

    // =========================================================================
    // CLASSE INTERNA PRODUTO
    // =========================================================================
    public static class Produto {
        private int id;
        private String nome;
        private String descricao; 
        private String categoria;
        private double preco;
        private int quantidade;   

        public Produto(int id, String nome, String descricao, String categoria, double preco, int quantidade) {
            this.id = id;
            this.nome = nome;
            this.descricao = descricao;
            this.categoria = categoria;
            this.preco = preco;
            this.quantidade = quantidade;
        }

        public int getId() { return id; }
        public String getNome() { return nome; }
        public String getDescricao() { return descricao; } 
        public String getCategoria() { return categoria; }
        public double getPreco() { return preco; }
        public int getQuantidade() { return quantidade; }
    }
}