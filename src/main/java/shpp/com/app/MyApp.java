package shpp.com.app;

import main.java.shpp.com.connect.ConnectToMyDB;
import main.java.shpp.com.loader.PropertiesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.exception.MyException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class MyApp {

    private static final String PROPERTIES_FILE = "app.properties";
    private static final Logger logger = LoggerFactory.getLogger(MyApp.class);

    public static void main(String[] args) throws SQLException, MyException {
        ConnectToMyDB connect = new ConnectToMyDB(getProperty("url"), getProperty("login"), getProperty("password"));
        logger.info("Connection is successful!");

        ResultSet set = connect.getStatement().executeQuery("SELECT * FROM employees");
        while (set.next()) {
            String something = set.getString("id");
            logger.info("something is {}", something);
        }

        connect.closeConnection();
        logger.info("The connection is closed!");
    }

    /**
     * Метод загружає і повртає всі пропертіз із проперті файла
//     * @param file - назва файлу пропертіз
     * @return - пропертіз
     */
    private static Properties getProperties() {
        return new PropertiesLoader().loadProperties(PROPERTIES_FILE);
    }

    /**
     * Метод повертає обраний пропертіз у вигляді строки
     * @param property - назва пропертіз
     * @return - значення пропертіз із файла пропертіз
     */
    private static String getProperty(String property) throws MyException {
        String url = getProperties().getProperty(property);
        if (url != null) {
            return url;
        } else {
            throw new MyException("Sorry! Please enter " + property + " required for connection!");
        }
    }

}
