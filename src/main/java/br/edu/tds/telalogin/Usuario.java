/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.tds.telalogin;

/**
 *
 * @author aluno
 */
public class Usuario {
    private String NomeCompleto;
    private String NomeUsuario;
    private String Email;
    private String Senha;
    private String CPF;

    // Construtor vazio (Necessário para o 'new Usuario()' no Controller)
    public Usuario() {
    }

    // Getters e Setters seguindo o padrão exato que você pediu
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

    void set(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}