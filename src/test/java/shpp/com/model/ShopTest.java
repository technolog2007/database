package shpp.com.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import shpp.com.model.Shop;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    private static Validator validator;

    @BeforeAll
    public static void setUpValidator() {
        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()){
            validator = factory.getValidator();
        }
    }

    @Test
    void getNameSetValueAndReturnIt() {
        String expected = "Epicenter № 333";
        Shop shop = new Shop().setName(expected);
        String actual = shop.getName();
        assertEquals(expected, actual);
    }

    @Test
    void shopNameSetNullAndIsInvalid(){
        Shop shop = new Shop().setName(null).setCity("Sumy").setLocation("str.Ivana Franka, 10");
        Set<ConstraintViolation<Shop>> constraintViolations = validator.validate(shop);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.NotNull.message}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void shopNameSetInvalidName(){
        Shop shop = new Shop().setName("name").setCity("Lviv").setLocation("str.Ivana Franka, 10");
        Set<ConstraintViolation<Shop>> constraintViolations = validator.validate(shop);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.Pattern.message}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void getLocationSetValueAndReturnIt() {
        String expected = "Lviv, st. Chornovola, b. 10";
        Shop shop = new Shop().setLocation(expected);
        String actual = shop.getLocation();
        assertEquals(expected, actual);
    }

    @Test
    void shopLocationSetNullAndIsInvalid(){
        Shop shop = new Shop().setName("Epicenter № 333").setCity("Sumy").setLocation(null);
        Set<ConstraintViolation<Shop>> constraintViolations = validator.validate(shop);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.NotNull.message}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void shopLocationSetInvalidName(){
        Shop shop = new Shop().setName("Epicenter № 333").setCity("Sumy").setLocation("Sumska");
        Set<ConstraintViolation<Shop>> constraintViolations = validator.validate(shop);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.Size.message}",
                constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void getCitySetValueAndReturnIt() {
        String expected = "Lviv";
        Shop shop = new Shop().setCity(expected);
        String actual = shop.getCity();
        assertEquals(expected, actual);
    }

    @Test
    void shopCitySetNullAndIsInvalid(){
        Shop shop = new Shop().setName("Epicenter № 333").setCity(null).setLocation("Tarasa Shevchenko");
        Set<ConstraintViolation<Shop>> constraintViolations = validator.validate(shop);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.NotNull.message}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void shopCitySetInvalidName(){
        Shop shop = new Shop().setName("Epicenter № 333").setCity("Chi").setLocation("Tarasa Shevchenko");
        Set<ConstraintViolation<Shop>> constraintViolations = validator.validate(shop);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.Size.message}",
                constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void setNameSetValueAndReturnIt() {
        String actual = "Epicenter № 333";
        Shop shop = new Shop().setName(actual);
        String expected = shop.getName();
        assertEquals(expected, actual);
    }

    @Test
    void setLocationSetValueAndReturnIt() {
        String actual = "Lviv, st. Chornovola, b. 10";
        Shop shop = new Shop().setLocation(actual);
        String expected = shop.getLocation();
        assertEquals(expected, actual);
    }

    @Test
    void setCitySetValueAndReturnIt() {
        String actual = "Lviv";
        Shop shop = new Shop().setCity(actual);
        String expected = shop.getCity();
        assertEquals(expected, actual);
    }

    @Test
    void testToStringSetValuesInObjectAndReturnStringIsEquals() {
        Shop shop = new Shop().setCity("Lviv").setName("Epicenter № 333").setLocation("Lviv, st. Chornovola, b. 10");
        String expected = "Shop{name='Epicenter № 333', location='Lviv, st. Chornovola, b. 10', city='Lviv'}";
        String actual = shop.toString();
        assertEquals(expected, actual);
    }
}