package shpp.com.app;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class MyAppTest {

    @Test
    void mainTest() {
        MyApp app = Mockito.mock(MyApp.class);
        Mockito.verify(app);
    }
}