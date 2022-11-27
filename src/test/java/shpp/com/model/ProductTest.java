package shpp.com.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
    }

    @Test
    void setNameSetValueAndReturnIt() {
        String actual = "Ноутбук";
        Product product = new Product().setName(actual);
        String expected = product.getName();
        assertEquals(expected, actual);
    }

    @Test
    void productNameSetNullAndIsInvalid(){
        Product product = new Product().setCategoryID(1).setName(null).setPrice(100);
        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.NotNull.message}",
                constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void productNameSetInvalidName(){
        Product product = new Product().setCategoryID(1).
                setName("Notebook Asus Vivo 15 Pro Intel Core 5").setPrice(100);
        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.Size.message}",
                constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void setCategoryIDSetValueAndReturnIt() {
        int actual = 1;
        Product product = new Product().setCategoryID(actual);
        int expected = product.getCategoryID();
        assertEquals(expected, actual);
    }

    @Test
    void productCategoryIDSetMaxValueIsInvalid(){
        int expected = 3000001;
        Product product = new Product().setCategoryID(expected).setName("ноутбук").setPrice(100);
        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.Max.message}",
                constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void productCategoryIDSetMinValueIsInvalid(){
        int expected = -1;
        Product product = new Product().setCategoryID(expected).setName("ноутбук").setPrice(100);
        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.Min.message}",
                constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void setPriceSetValueAndReturnIt() {
        int actual = 100;
        Product product = new Product().setPrice(actual);
        double expected = product.getPrice();
        assertEquals(expected, actual);
    }

    @Test
    void productPriceSetNullAndIsInvalid(){
        double expected = -1.1;
        Product product = new Product().setCategoryID(1).setName("ноутбук").setPrice(expected);
        Set<ConstraintViolation<Product>> constraintViolations = validator.validate(product);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.Min.message}",
                constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void getNameSetValueAndReturnIt() {
        String expected = "Ноутбук";
        Product product = new Product().setName(expected);
        String actual = product.getName();
        assertEquals(expected, actual);
    }

    @Test
    void getCategoryIDSetValueAndReturnIt() {
        int expected = 1;
        Product product = new Product().setCategoryID(expected);
        int actual = product.getCategoryID();
        assertEquals(expected, actual);
    }

    @Test
    void getPriceSetValueAndReturnIt() {
        int expected = 100;
        Product product = new Product().setPrice(expected);
        double actual = product.getPrice();
        assertEquals(expected, actual);
    }

    @Test
    void testToStringSetValuesInObjectAndReturnStringIsEquals() {
        String expected = "Product{name='Ноутбук', categoryID=1, price=100.0}";
        Product product = new Product().setCategoryID(1).setName("Ноутбук").setPrice(100);
        String actual = product.toString();
        assertEquals(expected, actual);
    }
}