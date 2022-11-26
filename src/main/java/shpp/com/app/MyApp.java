package shpp.com.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.model.Product;
import shpp.com.model.Shop;
import shpp.com.repo.ConnectToDB;
import shpp.com.repo.SqlScriptExecutor;
import shpp.com.util.MyFileLoader;
import shpp.com.services.MyValidator;
import shpp.com.services.PojoGenerator;
import shpp.com.util.MyException;
import shpp.com.util.PropertiesLoader;

import java.sql.*;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Stream;

public class MyApp {
    private static final Logger logger = LoggerFactory.getLogger(MyApp.class);
    private static final String DDL_SCRIPT_FILE = "ddlScript.sql";
    private static final String STREETS_FILE = "streets.txt";
    private static final String CITIES_FILE = "cities.txt";
    private static final String PRODUCTS_FILE = "products.txt";
    private static final String PROPERTIES_FILE = "app.properties";

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
        executor.executeSqlScript(DDL_SCRIPT_FILE, connect.getConnection());
        double finishTime = getFinishTime(startTime);
        logger.info("Dll script time is {} sec", finishTime);
        // generate input data
        MyFileLoader loader = loadInputData();
        PojoGenerator pojoGenerator = new PojoGenerator();
        // filling out the shop sign
        startTime = System.currentTimeMillis();
        fillTBShop(connection, loader, pojoGenerator);
        logger.info("Shop generator time is {} sec", getFinishTime(startTime));
        startTime = System.currentTimeMillis();
        // filling out the category table
        fillTBCategory(connection, loader);
        logger.info("Category generator time is {} sec", getFinishTime(startTime));
        startTime = System.currentTimeMillis();
        // filling in the products table
        fillTBProducts(connection, pojoGenerator, loader);
        logger.info("Product generator time is {} sec", getFinishTime(startTime));
        startTime = System.currentTimeMillis();
        // filling in the results table
        fillTBResult(connection);
        logger.info("Result generator time is {} sec", getFinishTime(startTime));
        startTime = System.currentTimeMillis();
        // making a request
        printRequest(connection);
        logger.info("request time is {} sec", getFinishTime(startTime));
        connection.commit();
        connection.close();
        logger.info("program time is {} sec", getFinishTime(startProgramTime));
    }

    /**
     * The method returns the operation execution time
     *
     * @param startTime - the initial time of the operation
     * @return - the actual time of the operation
     */
    private static double getFinishTime(long startTime) {
        return (System.currentTimeMillis() - startTime) * 0.001;
    }

    /**
     * The method fills the shop table with random values (shop_name, shop_city, shop_location)
     *
     * @param connection    - connection
     * @param loader        - data loader for generation from a file
     * @param pojoGenerator - shop object generator
     * @throws SQLException -
     * @throws MyException  -
     */
    private static void fillTBShop(Connection connection, MyFileLoader loader, PojoGenerator pojoGenerator)
            throws SQLException, MyException {
        String sql = "INSERT INTO tb_shops (shop_name, shop_city, shop_location) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            Stream.generate(() -> pojoGenerator.createShop(loader.getCities(), loader.getStreets())).
                    filter(s -> new MyValidator(s).complexValidator()).
                    limit(Integer.parseInt(getProperty("numberOfShops"))).
                    forEach(s -> setDataToTBShop(statement, s));
            statement.executeBatch();
        }
    }

    /**
     * The method fills table of product categories (category_name)
     *
     * @param connection - connection
     * @param loader     - data loader for generation from a file
     * @throws SQLException -
     */
    private static void fillTBCategory(Connection connection, MyFileLoader loader) throws SQLException {
        String sqlCategory = "INSERT INTO tb_categories (category_name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sqlCategory)) {
            for (int i = 0; i < loader.getCategory().size(); i++) {
                statement.setString(1, loader.getCategory().get(i));
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    /**
     * The method fills the products table with random values (category_id, product_name, product_price)
     *
     * @param connection    - connection
     * @param pojoGenerator - product object generator
     * @param loader        - data loader for generation from a file
     * @throws SQLException -
     * @throws MyException  -
     */
    private static void fillTBProducts(Connection connection, PojoGenerator pojoGenerator, MyFileLoader loader)
            throws SQLException, MyException {
        String sql = "INSERT INTO tb_products (category_id, product_name, product_price) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int numberOgProduct = Integer.parseInt(getProperty("numberOfProducts"));
            int count = 0;
            for(int i = 0; i < numberOgProduct; i++){
                Product prod = pojoGenerator.createProduct(loader);
                if(new MyValidator(prod).complexValidator()) {
//                    setDataToTBProduct(statement, prod, count);
                    try {
                        statement.setInt(1, prod.getCategoryID());
                        statement.setString(2, prod.getName());
                        statement.setDouble(3, prod.getPrice());
                        statement.addBatch();
                        count++;
                        if (count % 100 == 0) {
                            logger.info("Download product line # {} ", count);
                            statement.executeBatch();
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException();
                    }
                }
            }
//            Stream.generate(() -> pojoGenerator.createProduct(loader)).
//                    filter(prod -> new MyValidator(prod).complexValidator()).
//                    limit(numberOgProduct).
//                    forEach(valProd -> setDataToTBProduct(statement, valProd, count));
            statement.executeBatch();
        }
    }

    /**
     * The method fills the resulting table with random values (shop_id, products_id, amount_id)
     * that correspond to the values of the sho, categories, products tables
     *
     * @param connection - connection
     * @throws MyException  -
     * @throws SQLException -
     */
    private static void fillTBResult(Connection connection) throws MyException, SQLException {
        String sql = "INSERT INTO tb_result (shop_id, products_id, amount_id) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            int numberOfShops = Integer.parseInt(getProperty("numberOfShops"));
            int numberOfProducts = Integer.parseInt(getProperty("numberOfProducts"));
            int amount = Integer.parseInt(getProperty("amount"));
            int count = 0;
            for (int i = 0; i < numberOfProducts; i++) {
                setDataToTBResult(
                        statement,
                        new Random().nextInt(numberOfShops),
                        new Random().nextInt(numberOfProducts),
                        new Random().nextInt(amount)
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

    /**
     * The method sets the values for the corresponding columns of the current row and adds them to the batch
     *
     * @param statement - query instance in DB
     * @param shopID    - the ID value for the corresponding store
     * @param productID - the ID value for the corresponding product
     * @param amount    - the quantity value for the corresponding product
     */
    private static void setDataToTBResult(PreparedStatement statement, int shopID, int productID, int amount) {
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
     * The method sets the values for the corresponding columns of the current row and adds them to the batch
     *
     * @param statement - query instance in DB
     * @param product   - an instance of the product object
     * @param count     - request iteration counter
     */
    private static void setDataToTBProduct(PreparedStatement statement, Product product, int count) {
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
    }

    /**
     * The method sets the values for the corresponding columns of the current row and adds them to the package
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
     * The method loads input data from files containing streets, cities, products and their properties
     *
     * @return - MyFileLoader
     * @throws MyException -
     */
    private static MyFileLoader loadInputData() throws MyException {
        MyFileLoader loader = new MyFileLoader();
        loader.createInputDataFromFile(STREETS_FILE);
        loader.createInputDataFromFile(CITIES_FILE);
        loader.createInputDataFromFile(PRODUCTS_FILE);
        return loader;
    }

    /**
     * The method loads properties from the properties file and returns properties
     *
     * @return - properties
     */
    private static Properties loadProperties() {
        return new PropertiesLoader().loadProperties(PROPERTIES_FILE);
    }

    /**
     * The method returns the value for the corresponding key property
     *
     * @param property - open the key
     * @return - the value of the key property
     * @throws MyException -
     */
    private static String getProperty(String property) throws MyException {
        String url = loadProperties().getProperty(property);
        if (url != null) {
            return url;
        } else {
            throw new MyException("Sorry! Please enter " + property + " required for connection!");
        }
    }

//    private static void requestDllFormation() {
//        try (FileWriter writer = new FileWriter("src/main/resources/" + "mySearchScript.sql", false)) {
//            writer.write("SELECT p.product_id, p.product_name, c.category_name, s.shop_id, s.shop_name, " +
//                    "s.shop_location, max(r.amount_id) max\r");
//            writer.write("FROM tb_result r, tb_shops s, tb_products p, tb_categories c\r");
//            writer.write("WHERE r.shop_id = s.shop_id AND r.products_id = p.product_id AND p.category_id = " +
//                    "c.category_id AND c.category_name =" +
//                    "'" + getSystemProperty() + "'" + "\r");
//            writer.write("group by p.product_name, product_id, s.shop_id, s.shop_name, s.shop_location, " +
//                    "c.category_name\r");
//            writer.write("ORDER BY max desc\r");
//            writer.write("LIMIT 1\r");
//            writer.flush();
//        } catch (IOException ex) {
//            logger.error(ex.getMessage());
//        }
//    }

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
        return "SELECT p.product_id, p.product_name, c.category_name, s.shop_id, s.shop_name, s.shop_location, " +
                "max(r.amount_id) max FROM tb_result r, tb_shops s, tb_products p, tb_categories c " +
                "WHERE r.shop_id = s.shop_id AND r.products_id = p.product_id AND p.category_id = c.category_id " +
                "AND c.category_name =" + "'" + category + "'" +
                "group by p.product_name, product_id, s.shop_id, s.shop_name, s.shop_location, c.category_name " +
                "ORDER BY max desc " +
                "LIMIT 1";
    }

    /**
     * The method outputs the relevant data by REQUEST
     *
     * @param connection - connection
     * @throws SQLException -
     */
    private static void printRequest(Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(getRequest());
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
        statement.close();
    }
}
