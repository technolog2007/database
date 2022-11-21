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
     * Конструктор инициализирует Set для обїекта pojo
     * @param pojo - объект
     */
    public MyValidator(Object pojo) {
        this.violations = validator.validate(pojo);
    }

    /**
     * Метод возвращает булевое true, если Set пустой
     * @return -
     */
    public boolean complexValidator() {
        return violations.isEmpty();
    }

    /**
     * Метод сохраняет в List<String> все выявленные невалидные сообщения и
     * возвращает его
     * @return - List<String>, содержащий error сообщения
     */
    public List<String> validationMessagesGenerator() {
        ArrayList<String> errorMessage = new ArrayList<>();
        violations.forEach(x -> errorMessage.add(x.getMessage()));
        return errorMessage;
    }
}
