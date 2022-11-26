package shpp.com.services;

import shpp.com.model.Product;
import shpp.com.model.Shop;
import shpp.com.util.MyFileLoader;

import java.util.List;
import java.util.Random;

public class PojoGenerator {
    private static final int UPPER_GENERATE_BOUND = 1000;

    // name, location, City
    public Shop createShop(List<String> cities, List<String> streets) {
        String street = "Epicenter № " + new Random().nextInt(UPPER_GENERATE_BOUND);
        String city = cities.get(new Random().nextInt(cities.size() - 1));
        String location = "city " + city + ", str. " + streets.get(new Random().nextInt(cities.size() - 1)) +
                ", " + new Random().nextInt(UPPER_GENERATE_BOUND);
        return new Shop().setName(street).setCity(city).setLocation(location);
    }

    // category id, category name, product name and price
    public Product createProduct(MyFileLoader loader) {
        List<String> temp = loader.getProducts().get(new Random().nextInt(loader.getProducts().size() - 1));
        String name = temp.get(2) + ", art. # " + new Random().nextInt(UPPER_GENERATE_BOUND);
        return new Product().
                setCategoryID(Integer.parseInt(temp.get(0))).
                setName(name).
                setPrice(new Random().nextDouble() * UPPER_GENERATE_BOUND);
    }
}
