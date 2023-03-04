package shpp.com.repo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.util.MyException;
import shpp.com.util.PropertiesLoader;

public class ConnectToDB {

    private static final String PROPERTIES_FILE = "app.properties";
    private static final String PROPERTIES_FILE_PATH = "src/main/resources/";
    private static final Logger logger = LoggerFactory.getLogger(ConnectToDB.class);
    private final Connection connection;

    /**
     * The constructor initializes the connection to the database
     *
     * @throws SQLException - SQLException
     * @throws MyException  - MyException
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
     * @return - Property
     */
    private static Properties loadProperties() {
        return new PropertiesLoader().loadProperties(PROPERTIES_FILE, PROPERTIES_FILE_PATH);
    }

    /**
     * The method returns the selected property as a string
     *
     * @param property - property name
     * @return - Property values from the properties file
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
