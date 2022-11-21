package shpp.com.services;

import shpp.com.model.Product;
import shpp.com.model.Shop;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public class PojoGenerator {
    private static final int UPPER_BOUND = 1000;

    public Random createRandom() throws NoSuchAlgorithmException {
        return SecureRandom.getInstanceStrong();
    }

    // name, location, City
    public Shop createShop(List<String> cities, List<String> streets) throws NoSuchAlgorithmException {
        String street = "ТЦ Епіцентр № " + createRandom().nextInt(UPPER_BOUND);
        String city = cities.get(createRandom().nextInt(cities.size() - 1));
        String location = "м. " + city + ", вул. " + streets.get(createRandom().nextInt(cities.size() - 1)) +
                ", " + createRandom().nextInt(UPPER_BOUND);
        return new Shop().setName(street).setCity(city).setLocation(location);
    }
    // category id, category name, product name and price
    public Product createProduct(List<String[]> products) throws NoSuchAlgorithmException {
        String[] temp = products.get(createRandom().nextInt(products.size() - 1));
        Integer categoryID = Integer.parseInt(temp[0]);
        String categoryName = temp[1];
        String name = temp[2] + ", арт. # " + createRandom().nextInt(UPPER_BOUND);
        int price = createRandom().nextInt(UPPER_BOUND);
        return new Product().setCategoryID(categoryID).setName(name).setPrice(price).setCategory(categoryName);
    }
}
