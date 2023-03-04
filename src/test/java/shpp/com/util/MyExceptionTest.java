package shpp.com.util;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class MyExceptionTest {

    @Test
    void myExceptionTest() {
        MyException expected = new MyException("message");
        assertTrue(expected.getMessage().contains("message"));
    }

}