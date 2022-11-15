package main.java.shpp.com.connect;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectToMyDB {

//    public static final String URL = "jdbc:postgresql://localhost:5432/test";
//    public static final String LOGIN = "postgres";
//    public static final String PASSWORD = "admin";


    //    Logger logger = LoggerFactory.getLogger(ConnectToMyDB.class);
    private final Connection connection;
    private final Statement statement;

    //    Connection connection;
    public ConnectToMyDB(String url, String login, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, login, password);
        this.statement = connection.createStatement();
    }

    public void closeConnection() throws SQLException {
        statement.close();
        connection.close();
    }

    public Statement getStatement() {
        return statement;
    }
}
