package shpp.com.services;

import java.util.List;
import java.util.Random;
import shpp.com.model.Product;
import shpp.com.model.Shop;
import shpp.com.util.MyFileLoader;

public class PojoGenerator {

    private static final int UPPER_GENERATE_BOUND = 1000;
    List<String> listOfCities;
    List<String> listOfStreets;
    List<String> listOfCategories;
    List<List<String>> listOfProducts;

    public PojoGenerator(MyFileLoader loader) {
        this.listOfCities = loader.getCities();
        this.listOfStreets = loader.getStreets();
        this.listOfCategories = loader.getCategory();
        this.listOfProducts = loader.getProducts();
    }

    /**
     * The method creates a store object from the fields name, city and location.
     *
     * @return - Shop object
     */
    public Shop createShop() {
        String shopName = "Epicenter № " + getRandomInt(UPPER_GENERATE_BOUND);
        String city = listOfCities.get(getRandomInt(listOfCities.size()));
        String location = "city " + city + ", str. " +
            listOfStreets.get(getRandomInt(listOfStreets.size())) +
            ", " + getRandomInt(UPPER_GENERATE_BOUND);
        return new Shop().setName(shopName).setCity(city).setLocation(location);
    }

    /**
     * The method creates a product object from the fields name, categoryID, price.
     *
     * @return - Product
     */
    public Product createProduct() {
        List<String> temp = listOfProducts
            .get(1 + getRandomInt(listOfProducts.size() - 1));
        String name = temp.get(2) + ", art. # " + getRandomInt(UPPER_GENERATE_BOUND);
        return new Product().
            setCategoryID(Integer.parseInt(temp.get(0))).
            setName(name).
            setPrice(new Random().nextDouble() * UPPER_GENERATE_BOUND);
    }

    /**
     * The method generates a random integer from 1 to upperBound.
     *
     * @param upperBound - upper limit of integer generation
     * @return - number generated within specified limits
     */
    private int getRandomInt(int upperBound) {
        return 1 + new Random().nextInt(upperBound - 1);
    }

    /**
     * Method for converting a list of product category names.
     *
     * @return - list of product category names
     */
    public List<String> getListOfCategories() {
        return listOfCategories;
    }
}
