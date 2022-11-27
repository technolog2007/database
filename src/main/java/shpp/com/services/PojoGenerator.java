package shpp.com.services;

import shpp.com.model.Product;
import shpp.com.model.Shop;
import shpp.com.util.MyException;
import shpp.com.util.MyFileLoader;

import java.util.List;
import java.util.Random;

public class PojoGenerator {
    private static final int UPPER_GENERATE_BOUND = 1000;
    List<String> listOfCities;
    List<String> listOfStreets;
    List<String> listOfCategories;
    List<List<String>> listOfProducts;
    private static final String STREETS_FILE = "streets.txt";
    private static final String CITIES_FILE = "cities.txt";
    private static final String PRODUCTS_FILE = "products.txt";

    public PojoGenerator() throws MyException {
        MyFileLoader loader = loadInputData();
        this.listOfCities = loader.getCities();
        this.listOfStreets = loader.getStreets();
        this.listOfCategories = loader.getCategory();
        this.listOfProducts = loader.getProducts();
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

    // name, location, City
    public Shop createShop() {
        String shopName = "Epicenter № " + new Random().nextInt(UPPER_GENERATE_BOUND);
        String city = listOfCities.get(new Random().nextInt(listOfCities.size() - 1));
        String location = "city " + city + ", str. " +
                listOfStreets.get(new Random().nextInt(listOfStreets.size() - 1)) +
                ", " + new Random().nextInt(UPPER_GENERATE_BOUND);
        return new Shop().setName(shopName).setCity(city).setLocation(location);
    }

    // category id, category name, product name and price
    public Product createProduct() {
        List<String> temp = listOfProducts.get(new Random().nextInt(listOfProducts.size() - 2));
        String name = temp.get(2) + ", art. # " + new Random().nextInt(UPPER_GENERATE_BOUND);
        return new Product().
                setCategoryID(Integer.parseInt(temp.get(0))).
                setName(name).
                setPrice(new Random().nextDouble() * UPPER_GENERATE_BOUND);
    }

    public List<String> getListOfCategories(){
        return listOfCategories;
    }
//
//    public int getAmountOfCategory(){
//        return
//    }
}
