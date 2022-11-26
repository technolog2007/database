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
        String expected = "ТЦ Епіцентр №10";
        Shop shop = new Shop().setName(expected);
        String actual = shop.getName();
        assertEquals(expected, actual);
    }

    @Test
    void shopNameSetNullAndIsInvalid(){
        Shop shop = new Shop().setName(null).setCity("Київ").setLocation("Івана Франка, 10");
        Set<ConstraintViolation<Shop>> constraintViolations = validator.validate(shop);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.NotNull.message}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void shopNameSetInvalidName(){
        Shop shop = new Shop().setName("name").setCity("Київ").setLocation("Івана Франка, 10");
        Set<ConstraintViolation<Shop>> constraintViolations = validator.validate(shop);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.Pattern.message}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void getLocationSetValueAndReturnIt() {
        String expected = "м.Львів, вул. Чорновола, б.10";
        Shop shop = new Shop().setLocation(expected);
        String actual = shop.getLocation();
        assertEquals(expected, actual);
    }

    @Test
    void shopLocationSetNullAndIsInvalid(){
        Shop shop = new Shop().setName("ТЦ Епіцентр № 333").setCity("Київ").setLocation(null);
        Set<ConstraintViolation<Shop>> constraintViolations = validator.validate(shop);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.NotNull.message}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void shopLocationSetInvalidName(){
        Shop shop = new Shop().setName("ТЦ Епіцентр № 333").setCity("Київ").setLocation("Івана Ф.");
        Set<ConstraintViolation<Shop>> constraintViolations = validator.validate(shop);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.Size.message}",
                constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void getCitySetValueAndReturnIt() {
        String expected = "Львів";
        Shop shop = new Shop().setCity(expected);
        String actual = shop.getCity();
        assertEquals(expected, actual);
    }

    @Test
    void shopCitySetNullAndIsInvalid(){
        Shop shop = new Shop().setName("ТЦ Епіцентр № 333").setCity(null).setLocation("Тараса Шевченко");
        Set<ConstraintViolation<Shop>> constraintViolations = validator.validate(shop);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.NotNull.message}", constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void shopCitySetInvalidName(){
        Shop shop = new Shop().setName("ТЦ Епіцентр № 333").setCity("Чоп").setLocation("Тараса Шевченко");
        Set<ConstraintViolation<Shop>> constraintViolations = validator.validate(shop);
        assertEquals(1, constraintViolations.size());
        assertEquals("{jakarta.validation.constraints.Size.message}",
                constraintViolations.iterator().next().getMessageTemplate());
    }

    @Test
    void setNameSetValueAndReturnIt() {
        String actual = "ТЦ Епіцентр №10";
        Shop shop = new Shop().setName(actual);
        String expected = shop.getName();
        assertEquals(expected, actual);
    }

    @Test
    void setLocationSetValueAndReturnIt() {
        String actual = "м.Львів, вул. Чорновола, б.10";
        Shop shop = new Shop().setLocation(actual);
        String expected = shop.getLocation();
        assertEquals(expected, actual);
    }

    @Test
    void setCitySetValueAndReturnIt() {
        String actual = "Львів";
        Shop shop = new Shop().setCity(actual);
        String expected = shop.getCity();
        assertEquals(expected, actual);
    }

    @Test
    void testToStringSetValuesInObjectAndReturnStringIsEquals() {
        Shop shop = new Shop().setCity("Львів").setName("ТЦ Епіцентр №10").setLocation("м.Львів, вул. Чорновола, б.10");
        String expected = "Shop{name='ТЦ Епіцентр №10', location='м.Львів, вул. Чорновола, б.10', city='Львів'}";
        String actual = shop.toString();
        assertEquals(expected, actual);
    }
}