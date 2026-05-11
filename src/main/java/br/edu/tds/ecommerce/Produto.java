package br.edu.tds.ecommerce;
public class Produto {
   private int id;
   private String nome;
   private String descricao;
   private String categoria;
   private double preco;
   private int quantidade;
   private String imagem;
   private boolean ativo;
   public Produto() {
   }
   public int getId() {
       return id;
   }
   public void setId(int id) {
       this.id = id;
   }
   public String getNome() {
       return nome;
   }

   public void setNome(String nome) {
       this.nome = nome;
   }

   public String getDescricao() {
       return descricao;
   }

   public void setDescricao(String descricao) {
       this.descricao = descricao;
   }

   public String getCategoria() {
       return categoria;
   }

   public void setCategoria(String categoria) {
       this.categoria = categoria;
   }

   public double getPreco() {
       return preco;
   }

   public void setPreco(double preco) {
       this.preco = preco;
   }

   public int getQuantidade() {
       return quantidade;
   }

   public void setQuantidade(int quantidade) {
       this.quantidade = quantidade;
   }

   public String getImagem() {
       return imagem;
   }

   public void setImagem(String imagem) {
       this.imagem = imagem;
   }

   public boolean isAtivo() {
       return ativo;
   }

   public void setAtivo(boolean ativo) {
       this.ativo = ativo;
   }
}
