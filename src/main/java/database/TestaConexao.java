package database;

import java.sql.Connection;

public class TestaConexao {
    public static void main(String[] args) {
        try (Connection conn = ConnectionFactory.getConnection()) {
            System.out.println("Conexão realizada com sucesso!");
        } catch (Exception exception) {
            System.out.println("Falha na conexão: " + exception.getMessage());
            exception.printStackTrace();
        }
    }
}