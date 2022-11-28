package shpp.com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.app.MyApp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
    Logger logger = LoggerFactory.getLogger(PropertiesLoader.class);

    /**
     * The method loads data from the properties file into a buffer. The method works when running the program
     * from IDEA and external properties from Jar.
     * @param fileName - properties filename
     * @return - link to properties buffer
     */
    public Properties loadProperties(String fileName){

        Properties properties = new Properties();

        try {
            InputStream rootPath = MyApp.class.getClassLoader().getResourceAsStream(fileName);
            properties.load(rootPath);
            return properties;
        } catch (Exception e) {
            try (FileInputStream inputStream = new FileInputStream("src/main/resources/"+ fileName)) {
                properties.load(inputStream);
            } catch (IOException exception) {
                logger.error("Something went wrong with reading the properties!");
            }
            return properties;
        }
    }

}
