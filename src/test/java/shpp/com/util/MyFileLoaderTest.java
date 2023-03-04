package shpp.com.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MyFileLoaderTest {

  private static final String STREETS_FILE = "streets.txt";
  private static final String CITIES_FILE = "cities.txt";
  private static final String PRODUCTS_FILE = "products.txt";
  private static final String TEST_RESOURCES = "src/test/resources/";
  MyFileLoader loader;

  @BeforeEach
  void init() {
    this.loader = new MyFileLoader();
  }

  @Test
  void createInputDataFromFileNotEmptyTest() throws MyException {
    loader.createInputDataFromFile(STREETS_FILE, TEST_RESOURCES);
    List<String> listOfStreets = loader.getStreets();
    int expected = 12;
    int actual = listOfStreets.size();
    assertEquals(expected, actual);
  }

  @Test
  void createInputDataFromFileInvalidFileTest() {
    RuntimeException exception = assertThrows(
        RuntimeException.class,
        () -> loader.createInputDataFromFile("street.txt", TEST_RESOURCES),
        "ERROR! File not found! Please input correct file name!");
    assertEquals("ERROR! File not found! Please input correct file name!",
        exception.getMessage());
  }

  @Test
  void getProductsTestIsNotEmpty() throws MyException {
    loader.createInputDataFromFile(PRODUCTS_FILE, TEST_RESOURCES);
    List<List<String>> products = loader.getProducts();
    int actual = products.size();
    assertTrue(actual > 0);
  }

  @Test
  void getStreetsTestIsNotEmpty() throws MyException {
    loader.createInputDataFromFile(STREETS_FILE, TEST_RESOURCES);
    List<String> streets = loader.getStreets();
    int actual = streets.size();
    assertTrue(actual > 0);
  }

  @Test
  void getCitiesTestIsNotEmpty() throws MyException {
    loader.createInputDataFromFile(CITIES_FILE, TEST_RESOURCES);
    List<String> cities = loader.getCities();
    int actual = cities.size();
    assertTrue(actual > 0);
  }

  @Test
  void getCategoryTestIsNotEmpty() throws MyException {
    loader.createInputDataFromFile(PRODUCTS_FILE, TEST_RESOURCES);
    List<String> categories = loader.getCategory();
    int actual = categories.size();
    assertTrue(actual > 0);
  }

  @Test
  void changerExceptionTest() {
    MyException exception = assertThrows(
        MyException.class,
        () -> loader.createInputDataFromFile("task.txt", TEST_RESOURCES),
        "ERROR! Please, rename files with input data, which they are consist "
            + "\"cities\", \"street\", \"products\"");
    assertEquals("ERROR! Please, rename files with input data, which they are consist "
        + "\"cities\", \"street\", \"products\"", exception.getMessage());
  }
}