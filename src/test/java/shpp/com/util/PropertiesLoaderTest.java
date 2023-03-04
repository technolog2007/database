package shpp.com.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Properties;
import org.junit.jupiter.api.Test;

class PropertiesLoaderTest {

  private static final String PROPERTIES_TEST_FILE = "app.properties";
  private static final String PROPERTIES_TEST_FILE_PATH = "src/test/resources/";

  @Test
  void loadPropertiesFromIncorrectFile() {
    PropertiesLoader loader = new PropertiesLoader();
    int expected = 0;
    Properties actual = loader.loadProperties("ap.properties", PROPERTIES_TEST_FILE_PATH);
    assertEquals(expected, actual.size());
  }

  @Test
  void loadPropertiesLoadAllProperty() {
    PropertiesLoader loader = new PropertiesLoader();
    Properties properties = loader.loadProperties(PROPERTIES_TEST_FILE, PROPERTIES_TEST_FILE_PATH);
    int actual = properties.size();
    int expected = 12;
    assertEquals(expected, actual);
  }
}
