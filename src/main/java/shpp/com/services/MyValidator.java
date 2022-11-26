package shpp.com.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyValidator {

    // Initialize the ValidatorFactory, calling the default build
    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    //Create a validator object
    private static final Validator validator = factory.getValidator();
    private final Set<ConstraintViolation<Object>> violations;

    /**
     * The constructor initializes the Set for the pojo object
     * @param pojo - объект
     */
    public MyValidator(Object pojo) {
        this.violations = validator.validate(pojo);
    }

    /**
     * Method returns boolean true if Set is empty
     * @return -
     */
    public boolean complexValidator() {
        return violations.isEmpty();
    }

    /**
     * The method stores all detected invalid messages in a List<String> and returns it
     * @return - List<String>, containing error messages
     */
    public List<String> validationMessagesGenerator() {
        ArrayList<String> errorMessage = new ArrayList<>();
        violations.forEach(x -> errorMessage.add(x.getMessageTemplate()));
        return errorMessage;
    }
}
