package shpp.com.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shpp.com.model.Product;
import shpp.com.model.Shop;
import shpp.com.util.MyException;
import shpp.com.util.MyFileLoader;

class PojoGeneratorTest {

  private static final String TEST_RESOURCES = "src/test/resources/";
  MyFileLoader loader;
  PojoGenerator pojoGenerator;

  @BeforeEach
  void init() throws MyException {
    this.loader = LoadInputData.loadInputData(TEST_RESOURCES);
    this.pojoGenerator = new PojoGenerator(loader);
  }

  @Test
  void createShopInitialObjectIsNotNull() {
    Shop shop = pojoGenerator.createShop();
    assertNotNull(shop);
  }

  @Test
  void createProductInitialObjectIsNotNull() {
    Product product = pojoGenerator.createProduct();
    assertNotNull(product);
  }
}