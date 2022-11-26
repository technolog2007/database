package shpp.com.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MyExceptionTest {

    @Test
    void myExceptionTest() throws MyException {
        MyException expected = new MyException("message");
        assertTrue(expected.getMessage().contains("message"));
    }

}