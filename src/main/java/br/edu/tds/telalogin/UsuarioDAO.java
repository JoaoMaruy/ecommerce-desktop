/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.tds.telalogin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    /**
     * Método para cadastrar um novo usuário no banco de dados.
     * Utiliza Try-with-resources para garantir que a conexão e o statement sejam fechados.
     * @param usuario
     */
public void cadastrar(Usuario usuario) {
    String sql = "INSERT INTO usuarios(nomeCompleto, nomeUsuario, email, senha, CPF) VALUES (?,?,?,?,?)";
    try (Connection conn = Conexao.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, usuario.getNomeCompleto());
        stmt.setString(2, usuario.getNomeUsuario());
        stmt.setString(3, usuario.getEmail());
        stmt.setString(4, usuario.getSenha());
        stmt.setString(5, usuario.getCPF());
        
        stmt.executeUpdate();
        System.out.println("Usuário cadastrado com sucesso!");
    } catch (Exception e) {
        System.out.println("Erro ao cadastrar: " + e.getMessage());
    }
}
    /**
     * Método para validar o login.
     * @param email
     * @param senha
     * @return true se as credenciais estiverem corretas.
     */
    public boolean login(String email, String senha) {
        String sql = "SELECT * FROM usuarios WHERE email = ? AND senha = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Retorna true se encontrar um registro
            }

        } catch (SQLException e) {
            System.err.println("Erro ao validar login no banco: " + e.getMessage());
            return false;
        } catch (Exception e) {
            System.err.println("Erro inesperado no login: " + e.getMessage());
            return false;
        }
    }

    void atualizar(Usuario u) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}