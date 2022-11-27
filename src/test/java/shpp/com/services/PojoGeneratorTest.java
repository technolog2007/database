package shpp.com.services;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import shpp.com.model.Product;
import shpp.com.model.Shop;
import shpp.com.util.MyException;
import shpp.com.util.MyFileLoader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PojoGeneratorTest {
    private static final String PRODUCTS_FILE = "products.txt";
    @Test
    void createShopInitialObjectIsNotNullAndValueIsInsideIt() {
//        String expectedData = "Sumy";
//        MyFileLoader loader = Mockito.mock(MyFileLoader.class);
//        PojoGenerator generator = Mockito.mock(PojoGenerator.class);
//        Shop shop = generator.createShop(/*List.of(expectedData, expectedData), List.of("Sumskaya")*/);
//        assertNotNull(shop);
//        String actualData = shop.getCity();
//        assertTrue(actualData.contains(expectedData));
    }

    @Test
    void createProductInitialObjectIsNotNull() throws MyException {
        MyFileLoader loader = new MyFileLoader();
        loader.createInputDataFromFile(PRODUCTS_FILE);
        PojoGenerator pojoGenerator = new PojoGenerator();
        Product product = pojoGenerator.createProduct(/*loader*/);
        assertNotNull(product);
    }
}