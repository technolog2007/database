package shpp.com.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.model.Product;
import shpp.com.model.Shop;
import shpp.com.repo.ConnectToDB;
import shpp.com.repo.SqlScriptExecutor;
import shpp.com.services.MyFileLoader;
import shpp.com.services.MyValidator;
import shpp.com.services.PojoGenerator;
import shpp.com.util.MyException;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Stream;

public class MyApp {

    //    private static final String PROPERTIES_FILE = "app.properties";
    private static final Logger logger = LoggerFactory.getLogger(MyApp.class);
    private static final String DDL_SCRIPT_FILE = "src/main/resources/" + "ddlScript.sql";
    private static final String STREETS_FILE = "src/main/resources/" + "streets.txt";
    private static final String CITIES_FILE = "src/main/resources/" + "cities.txt";
    private static final String PRODUCTS_FILE = "src/main/resources/" + "products.txt";
    private static final int NUMBER_OF_SHOPS = 20;
    private static final int NUMBER_OF_PRODUCTS = 10000;

    public static void main(String[] args) throws SQLException, MyException, NoSuchAlgorithmException {
        // створюю конект до DB
        ConnectToDB connect = new ConnectToDB();
        Connection connection = connect.getConnection();
        connection.setAutoCommit(false);
        // запускаю ddl-скрипт із файла ddlScript.sql
        // створюю схему і таблиці tb_shop, tb_category, tb_products, tb_result
        File file = new File(DDL_SCRIPT_FILE);
        SqlScriptExecutor.executeSqlScript(file, connect.getConnection());
        // генерую вхідні данні
        PojoGenerator pojoGenerator = new PojoGenerator();
        MyFileLoader loader = loadInputData();
        fillTBShop(connection, loader, pojoGenerator);
        fillTBCategory(connection, loader);
        fillTBProducts(connection, pojoGenerator, loader);

//        Stream.generate(() -> productGenerator(pojoGenerator, loader)).
//                limit(NUMBER_OF_PRODUCTS).
//                filter(s -> new MyValidator(s).complexValidator()).
//                map(Product::toString).
//                forEach(logger::info);

        connection.close();
    }

    private static void fillTBShop(Connection connection, MyFileLoader loader, PojoGenerator pojoGenerator) throws SQLException {
        String sql = "INSERT INTO schema_epicenter.tb_shop (shop_name, shop_city, shop_location) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            Stream.generate(() -> shopGenerator(pojoGenerator, loader)).
                    filter(s -> new MyValidator(s).complexValidator()).
                    limit(NUMBER_OF_SHOPS).
                    forEach(s -> setDataToTBShop(statement, s));
            statement.executeBatch();
        }
    }

    private static void fillTBCategory(Connection connection, MyFileLoader loader) throws SQLException {
        String sqlCategory = "INSERT INTO schema_epicenter.tb_category (category_name) VALUES (?)";
        try (PreparedStatement statement = connection.prepareStatement(sqlCategory)) {
            for (int i = 0; i < loader.getCategory().size(); i++) {
                statement.setString(1, loader.getCategory().get(i));
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private static void fillTBProducts(Connection connection, PojoGenerator pojoGenerator, MyFileLoader loader)
            throws SQLException {
        String sql = "INSERT INTO schema_epicenter.tb_products (category_id, products_name, products_price) VALUES (?, ?, ?)";
        try(PreparedStatement statement = connection.prepareStatement(sql)){
            Stream.generate(() -> productGenerator(pojoGenerator, loader)).
                    filter(s -> new MyValidator(s).complexValidator()).
                    limit(NUMBER_OF_PRODUCTS).
                    forEach(s -> setDataToTBProduct(statement, s));
            statement.executeBatch();
        }
    }

    private static void setDataToTBProduct(PreparedStatement statement, Product product) {
        try {
            statement.setInt(1, product.getCategoryID());
            statement.setString(2, product.getName());
            statement.setInt(3, product.getPrice());
            statement.addBatch();
        } catch (SQLException e) {
                throw new RuntimeException();
        }
    }

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

    private static Shop shopGenerator(PojoGenerator pojoGenerator, MyFileLoader loader) {
        try {
            return pojoGenerator.createShop(loader.getCities(), loader.getStreets());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static Product productGenerator(PojoGenerator pojoGenerator, MyFileLoader loader) {
        try {
            return pojoGenerator.createProduct(loader.getProducts());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static MyFileLoader loadInputData() throws MyException {
        MyFileLoader reader = new MyFileLoader();
        reader.createInputDataFromFile(STREETS_FILE);
        reader.createInputDataFromFile(CITIES_FILE);
        reader.createInputDataFromFile(PRODUCTS_FILE);
        return reader;
    }
}
