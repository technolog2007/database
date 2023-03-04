package shpp.com.services;

import shpp.com.util.MyException;
import shpp.com.util.MyFileLoader;

public class LoadInputData {

  private static final String STREETS_FILE = "streets.txt";
  private static final String CITIES_FILE = "cities.txt";
  private static final String PRODUCTS_FILE = "products.txt";

  /**
   * The method loads input data from files containing streets, cities, products and their
   * properties.
   *
   * @return - MyFileLoader
   * @throws MyException - MyException
   */
  public static MyFileLoader loadInputData(String filePath) throws MyException {
    MyFileLoader loader = new MyFileLoader();
    loader.createInputDataFromFile(STREETS_FILE, filePath);
    loader.createInputDataFromFile(CITIES_FILE, filePath);
    loader.createInputDataFromFile(PRODUCTS_FILE, filePath);
    return loader;
  }
}
