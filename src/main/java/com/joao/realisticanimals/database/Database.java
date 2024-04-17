package com.joao.realisticanimals.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private final String HOST = "localhost";
    private final int PORT = 3306;
    private final String DATABASE = "realistic_animals";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    public Connection getConnection() { return connection; }

    private Connection connection;

    public void connect() throws SQLException {
        connection = DriverManager.getConnection(
                "jdbc:mysql://" + HOST + ":" + PORT + "/" + DATABASE + "?useSSL=FALSE",
                USERNAME,
                PASSWORD
        );
    }

    public boolean isConnected()  { return connection != null; }

    public void disconnect() {
        if (isConnected()){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}