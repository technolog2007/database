package shpp.com.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import shpp.com.model.Shop;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MyValidatorTest {

    @Test
    void complexValidatorReturnTrue() {
        Shop shop = new Shop().setName("Epicenter № 333").setCity("Khertson").setLocation("str, Tarasa Shevchenko, b.65");
        MyValidator myValidator = new MyValidator(shop);
        boolean actual = myValidator.complexValidator();
        assertTrue(actual);
    }

    @Test
    void validationMessagesGeneratorReturnListAndMessage() {
        Shop shop = new Shop().setName(null).setCity("Khertson").setLocation("str, Tarasa Shevchenko, b.65");
        MyValidator myValidator = new MyValidator(shop);
        List<String> list = myValidator.validationMessagesGenerator();
        int expected = 1;
        int actual = list.size();
        String expectedMessage = "{jakarta.validation.constraints.NotNull.message}";
        String actualMessage = list.get(0);
        assertEquals(expected, actual);
        assertEquals(expectedMessage, actualMessage);
    }
}