package br.edu.tds.ecommerce;

public class Usuario {
    private String NomeCompleto;
    private String NomeUsuario;
    private String Email;
    private String Senha;
    private String CPF;

    public Usuario() {
    }

    public String getNomeCompleto() { return NomeCompleto; }
    public void setNomeCompleto(String NomeCompleto) { this.NomeCompleto = NomeCompleto; }

    public String getNomeUsuario() { return NomeUsuario; }
    public void setNomeUsuario(String NomeUsuario) { this.NomeUsuario = NomeUsuario; }

    public String getEmail() { return Email; }
    public void setEmail(String Email) { this.Email = Email; }

    public String getSenha() { return Senha; }
    public void setSenha(String Senha) { this.Senha = Senha; }

    public String getCPF() { return CPF; }
    public void setCPF(String CPF) { this.CPF = CPF; }
}