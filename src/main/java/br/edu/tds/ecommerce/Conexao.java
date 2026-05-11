package br.edu.tds.ecommerce;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável por gerenciar a conexão com o banco de dados MySQL.
 */
public class Conexao {

    // Configurações do Banco de Dados - Ajuste conforme seu ambiente
    private static final String DATABASE = "ecommerce"; // Nome do banco de dados
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE + "?useTimezone=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "ifsuldeminas";

    /**
     * Estabelece uma conexão com o MySQL.
     * @return Connection objeto pronto para uso.
     * @throws SQLException Caso a conexão falhe.
     */
    public static Connection conectar() throws SQLException {
        try {
            // 1. Carrega o driver explicitamente (evita ClassNotFoundException em ambientes antigos/Web)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Tenta estabelecer a conexão
            return DriverManager.getConnection(URL, USER, PASSWORD);
            
        } catch (ClassNotFoundException e) {
            // Erro caso o conector MySQL não esteja no pom.xml
            throw new SQLException("Erro: Driver JDBC não encontrado. Verifique seu arquivo pom.xml. " + e.getMessage());
        } catch (SQLException e) {
            // Erro de senha, URL ou banco não criado
            throw new SQLException("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
    }
}