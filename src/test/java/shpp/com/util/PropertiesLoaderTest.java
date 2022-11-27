package shpp.com.util;

import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PropertiesLoaderTest {
    @Test
    void loadPropertiesFromIncorrectFile() {
        PropertiesLoader loader = new PropertiesLoader();
        int expected = 0;
        Properties actual = loader.loadProperties("ap.properties");
        assertEquals(expected, actual.size());
    }

    @Test
    void loadPropertiesLoadAllProperty() {
        PropertiesLoader loader = new PropertiesLoader();
        Properties properties = loader.loadProperties("app.properties");
        int actual = properties.size();
        int expected = 12;
        assertEquals(expected, actual);
    }
}
