package shpp.com.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.model.Product;
import shpp.com.model.Shop;
import shpp.com.repo.ConnectToDB;
import shpp.com.repo.SqlScriptExecutor;
import shpp.com.services.LoadInputData;
import shpp.com.services.MyValidator;
import shpp.com.services.PojoGenerator;
import shpp.com.util.MyException;
import shpp.com.util.PropertiesLoader;

public class MyApp {

  private static final Logger logger = LoggerFactory.getLogger(MyApp.class);
  private static final String DDL_SCRIPT_FILE = "ddlScript.sql";
  private static final String DDL_SCRIPT_FILE_PATH = "src/main/resources/";
  private static final String PROPERTIES_FILE = "app.properties";
  private static final String PROPERTIES_FILE_PATH = "src/main/resources/";
  private static final String IDEA_RESOURCES = "src/main/resources/";

  public static void main(String[] args) throws SQLException, MyException {
    // creating a connection to the DB
    long startProgramTime = System.currentTimeMillis();
    ConnectToDB connect = new ConnectToDB();
    Connection connection = connect.getConnection();
    connection.setAutoCommit(false);
    // running the ddl script from the ddlScript.sql file
    // creating a scheme and tables tb_shop, tb_category, tb_products, tb_result
    long startTime = System.currentTimeMillis();
    SqlScriptExecutor executor = new SqlScriptExecutor();
    executor.executeSqlScript(DDL_SCRIPT_FILE, DDL_SCRIPT_FILE_PATH, connect.getConnection());
    double finishTime = getFinishTime(startTime);
    logger.info("Dll script time is {} mls", finishTime);
    // generate input data
    PojoGenerator pojoGenerator = new PojoGenerator(LoadInputData.loadInputData(IDEA_RESOURCES));
    // filling out the shop sign
    startTime = System.currentTimeMillis();
    fillTBShop(connection, pojoGenerator);
    logger.info("Shop generator time is {} mls", getFinishTime(startTime));
    startTime = System.currentTimeMillis();
    // filling out the category table
    fillTBCategory(connection, pojoGenerator);
    logger.info("Category generator time is {} mls", getFinishTime(startTime));
    startTime = System.currentTimeMillis();
    // filling in the products table
    fillTBProducts(connection, pojoGenerator);
    logger.info("Product generator time is {} mls", getFinishTime(startTime));
    startTime = System.currentTimeMillis();
    // filling in the results table
    fillTBResult(connection);
    logger.info("Result generator time is {} mls", getFinishTime(startTime));
    // set index to products table to categories_id
    setIndex(connection);
    startTime = System.currentTimeMillis();
    // making a request
    printRequest(connection);
    logger.info("request time is {} mls", getFinishTime(startTime));
    connection.commit();
    connection.close();
    logger.info("program time is {} mls", getFinishTime(startProgramTime));
  }

  /**
   * The method returns the operation execution time.
   *
   * @param startTime - the initial time of the operation
   * @return - the actual time of the operation
   */
  private static double getFinishTime(long startTime) {
    return System.currentTimeMillis() - startTime;
  }

//  /**
//   * The method loads input data from files containing streets, cities, products and their
//   * properties.
//   *
//   * @return - MyFileLoader
//   * @throws MyException - MyException
//   */
//  private static MyFileLoader loadInputData() throws MyException {
//    MyFileLoader loader = new MyFileLoader();
//    loader.createInputDataFromFile(STREETS_FILE, IDEA_RESOURCES);
//    loader.createInputDataFromFile(CITIES_FILE, IDEA_RESOURCES);
//    loader.createInputDataFromFile(PRODUCTS_FILE, IDEA_RESOURCES);
//    return loader;
//  }

  /**
   * The method fills the shop table with random values (shop_name, shop_city, shop_location).
   *
   * @param connection    - connection.
   * @param pojoGenerator - shop object generator
   * @throws SQLException - SQLException
   * @throws MyException  - MyException
   */
  private static void fillTBShop(Connection connection, PojoGenerator pojoGenerator)
      throws SQLException, MyException {
    String sql = "INSERT INTO tb_shops (shop_name, shop_city, shop_location) VALUES (?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      Stream.generate(pojoGenerator::createShop).
          filter(s -> new MyValidator(s).complexValidator()).
          limit(Integer.parseInt(getProperty("numberOfShops"))).
          forEach(s -> setDataToTBShop(statement, s));
      statement.executeBatch();
    }
  }

  /**
   * The method fills table of product categories (category_name).
   *
   * @param connection - connection
   * @throws SQLException - SQLException
   */
  private static void fillTBCategory(Connection connection, PojoGenerator generator)
      throws SQLException {
    String sqlCategory = "INSERT INTO tb_categories (category_name) VALUES (?)";
    try (PreparedStatement statement = connection.prepareStatement(sqlCategory)) {
      List<String> list = generator.getListOfCategories();
      for (String s : list) {
        statement.setString(1, s);
        statement.addBatch();
      }
      statement.executeBatch();
    }
  }

  /**
   * The method fills the products table with random values (category_id, product_name,
   * product_price).
   *
   * @param connection    - connection
   * @param pojoGenerator - product object generator
   * @throws SQLException - SQLException
   * @throws MyException  - MyException
   */
  private static void fillTBProducts(Connection connection, PojoGenerator pojoGenerator)
      throws SQLException, MyException {
    String sql = "INSERT INTO tb_products (category_id, product_name, product_price) VALUES (?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      int numberOgProduct = Integer.parseInt(getProperty("numberOfProducts"));
      int count = 0;
      for (int i = 0; i < numberOgProduct; i++) {
        Product product = pojoGenerator.createProduct();
        if (new MyValidator(product).complexValidator()) {
          try {
            statement.setInt(1, product.getCategoryID());
            statement.setString(2, product.getName());
            statement.setDouble(3, product.getPrice());
            statement.addBatch();
            count++;
            if (count % 100 == 0) {
              logger.info("Download product line # {} ", count);
              statement.executeBatch();
            }
          } catch (SQLException e) {
            throw new RuntimeException();
          }
        } else {
          numberOgProduct++;
        }
      }
    }
  }

  /**
   * The method fills  the resulting table with random values (shop_id, products_id, amount_id) that
   * correspond to the values of the sho, categories, products tables.
   *
   * @param connection - connection
   * @throws MyException  - MyException
   * @throws SQLException - SQLException
   */
  private static void fillTBResult(Connection connection) throws MyException, SQLException {
    String sql = "INSERT INTO tb_result (shop_id, products_id, amount_id) VALUES (?, ?, ?)";
    try (PreparedStatement statement = connection.prepareStatement(sql)) {
      int numberOfLeftovers = Integer.parseInt(getProperty("numberOfLeftovers"));
      int numberOfShops = Integer.parseInt(getProperty("numberOfShops"));
      int numberOfProducts = Integer.parseInt(getProperty("numberOfProducts"));
      int amount = Integer.parseInt(getProperty("amount"));
      int count = 0;
      for (int i = 0; i < numberOfLeftovers; i++) {
        setDataToTBResult(
            statement,
            getRandom(numberOfShops),
            getRandom(numberOfProducts),
            getRandom(amount)
        );
        count++;
        if (count % 100 == 0) {
          statement.executeBatch();
          logger.info("Download result line # {} ", count);
        }
      }
      statement.executeBatch();
    }
  }

  private static int getRandom(int upperBound) {
    return 1 + new Random().nextInt(upperBound);
  }

  /**
   * The method sets the values for the corresponding columns of the current row and adds them to
   * the batch
   *
   * @param statement - query instance in DB
   * @param shopID    - the ID value for the corresponding store
   * @param productID - the ID value for the corresponding product
   * @param amount    - the quantity value for the corresponding product
   */
  private static void setDataToTBResult(PreparedStatement statement, int shopID, int productID,
      int amount) {
    try {
      statement.setInt(1, shopID);
      statement.setInt(2, productID);
      statement.setInt(3, amount);
      statement.addBatch();
    } catch (SQLException e) {
      throw new RuntimeException();
    }
  }

  /**
   * The method sets the values for the corresponding columns of the current row and adds them to
   * the package
   *
   * @param statement - query instance in DB
   * @param shop      - an instance of the shop object
   */
  private static void setDataToTBShop(PreparedStatement statement, Shop shop) {
    try {
      statement.setString(1, shop.getName());
      statement.setString(2, shop.getCity());
      statement.setString(3, shop.getLocation());
      statement.addBatch();
    } catch (SQLException e) {
      throw new RuntimeException();
    }
  }

  /**
   * The method loads properties from the properties file and returns properties
   *
   * @return - properties
   */
  private static Properties loadProperties() {
    return new PropertiesLoader().loadProperties(PROPERTIES_FILE, PROPERTIES_FILE_PATH);
  }

  /**
   * The method returns the value for the corresponding key property
   *
   * @param property - open the key
   * @return - the value of the key property
   * @throws MyException - MyException
   */
  private static String getProperty(String property) throws MyException {
    String url = loadProperties().getProperty(property);
    if (url != null) {
      return url;
    } else {
      throw new MyException("Sorry! Please enter " + property + " required for connection!");
    }
  }

  /**
   * The method returns the value for the "category" system parameter, or the default value
   *
   * @return - value for the "category" parameter
   */
  private static String getSystemProperty() {
    if (System.getProperty("category") != null) {
      return System.getProperty("category");
    } else {
      return "Electronics";
    }
  }

  /**
   * The method forms a request to the database
   *
   * @return - request to the database
   */
  private static String getRequest() {
    String category = getSystemProperty();
    return "SELECT p.product_id, p.product_name, c.category_name, s.shop_id, s.shop_name, "
        + "s.shop_location, max(r.amount_id) max "
        + "FROM tb_result r, tb_shops s, tb_products p, tb_categories c "
        + "WHERE r.shop_id = s.shop_id AND r.products_id = p.product_id AND "
        + "p.category_id = c.category_id AND c.category_name =" + "'" + category + "'"
        + "group by p.product_name, product_id, s.shop_id, s.shop_name, s.shop_location, "
        + "c.category_name ORDER BY max desc LIMIT 1";
  }

  /**
   * The method creates an index for the category_id column, tb_products table
   *
   * @param connection - connection
   */
  private static void setIndex(Connection connection) {
    String sqlIndex = "CREATE INDEX index_categories ON tb_products(category_id)";
    try (PreparedStatement statement = connection.prepareStatement(sqlIndex)) {
      statement.execute();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * The method outputs the relevant data by REQUEST
   *
   * @param connection - connection
   */
  private static void printRequest(Connection connection) {
    try (PreparedStatement statement = connection.prepareStatement(getRequest())) {
      ResultSet resultSet = statement.executeQuery();
      logger.info("------------------------------------------------------------------------");
      if (resultSet.next()) {
        logger.info("product name is: {}", resultSet.getString("product_name"));
        logger.info("category name is: {}", resultSet.getString("category_name"));
        logger.info("shop name is: {}", resultSet.getString("shop_name"));
        logger.info("shop location is: {}", resultSet.getString("shop_location"));
        logger.info("max amount is: {}", resultSet.getInt("max"));
      } else {
        logger.error("ResultSet is empty!");
      }
      resultSet.close();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
