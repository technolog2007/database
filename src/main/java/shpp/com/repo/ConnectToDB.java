package shpp.com.repo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.util.MyException;
import shpp.com.util.PropertiesLoader;

import java.sql.*;
import java.util.Properties;

public class ConnectToDB {
    private static final String PROPERTIES_FILE = "app.properties";
    private static final Logger logger = LoggerFactory.getLogger(ConnectToDB.class);
    private final Connection connection;

    /**
     * The constructor initializes the connection to the database
     * @throws SQLException -
     * @throws MyException -
     */
    public ConnectToDB() throws SQLException, MyException {
        this.connection = DriverManager.getConnection(
                getProperty("url"),
                getProperty("login"),
                getProperty("password")
        );
        logger.info("Connect is successful!");
    }

    /**
     * The method returns a connection to the database
     * @return - connection
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Method to load and restore all properties from a property file
     *
     * @return - property
     */
    private static Properties loadProperties() {
        return new PropertiesLoader().loadProperties(PROPERTIES_FILE);
    }

    /**
     * The method returns the selected property as a string
     *
     * @param property - property name
     * @return - property values from the properties file
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
