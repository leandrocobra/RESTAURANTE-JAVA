package database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static final Properties properties = loadProperties();

    private ConnectionFactory() {
    }

    public static Connection getConnection() {
        try {
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar com o banco de dados.", e);
        }
    }

    private static Properties loadProperties() {
        try (InputStream input = ConnectionFactory.class
                .getClassLoader()
                .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new RuntimeException("Arquivo db.properties não encontrado em src/main/resources.");
            }

            Properties props = new Properties();
            props.load(input);
            return props;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar as configurações do banco.", e);
        }
    }
}