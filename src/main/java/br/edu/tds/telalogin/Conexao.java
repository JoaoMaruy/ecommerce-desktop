/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.tds.telalogin;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

   private static final String URL = "jdbc:mysql://localhost:3306/tela_login";
   private static final String USER = "root";
   private static final String PASSWORD = "ifsuldeminas";

   public static Connection conectar() throws Exception {
       return DriverManager.getConnection(URL, USER, PASSWORD);
   }
}
