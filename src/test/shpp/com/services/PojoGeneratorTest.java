package shpp.com.services;

import org.junit.jupiter.api.Test;
import shpp.com.model.Product;
import shpp.com.model.Shop;
import shpp.com.util.MyException;
import shpp.com.util.MyFileLoader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PojoGeneratorTest {
    private static final String PRODUCTS_FILE = "src/main/resources/" + "products.txt";
    @Test
    void createShopInitialObjectIsNotNullAndValueIsInsideIt() {
        String expectedData = "Київ";
        Shop shop = new PojoGenerator().createShop(List.of(expectedData, expectedData), List.of("Київська"));
        assertNotNull(shop);
        String actualData = shop.getCity();
        assertTrue(actualData.contains(expectedData));
    }

    @Test
    void createProductInitialObjectIsNotNull() throws MyException {
        MyFileLoader loader = new MyFileLoader();
        loader.createInputDataFromFile(PRODUCTS_FILE);
        PojoGenerator pojoGenerator = new PojoGenerator();
        Product product = pojoGenerator.createProduct(loader);
        assertNotNull(product);
    }
}