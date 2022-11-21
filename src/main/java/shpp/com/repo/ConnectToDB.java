package shpp.com.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.util.MyException;
import shpp.com.util.PropertiesLoader;

import java.sql.*;
import java.util.Properties;

public class ConnectToDB {
    private static final String PROPERTIES_FILE = "app.properties";

    Logger logger = LoggerFactory.getLogger(ConnectToDB.class);
    private final Connection connection;

    public ConnectToDB() throws SQLException, MyException {
        this.connection = DriverManager.getConnection(
                getProperty("url"),
                getProperty("login"),
                getProperty("password")
        );
        logger.info("Connect is successful!");
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Метод загружає і повртає всі пропертіз із проперті файла
     * //     * @param file - назва файлу пропертіз
     *
     * @return - пропертіз
     */
    private static Properties loadProperties() {
        return new PropertiesLoader().loadProperties(PROPERTIES_FILE);
    }

    /**
     * Метод повертає обраний пропертіз у вигляді строки
     *
     * @param property - назва пропертіз
     * @return - значення пропертіз із файла пропертіз
     */
    private static String getProperty(String property) throws MyException {
        String url = loadProperties().getProperty(property);
        if (url != null) {
            return url;
        } else {
            throw new MyException("Sorry! Please enter " + property + " required for connection!");
        }
    }
}
