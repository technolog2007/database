package shpp.com.services;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import shpp.com.util.MyException;
import shpp.com.util.MyFileLoader;

class LoadInputDataTest {

  private static final String TEST_RESOURCES = "src/test/resources/";

  @Test
  void loadInputDataIsSuccess() throws MyException {
    MyFileLoader expected = LoadInputData.loadInputData(TEST_RESOURCES);
    assertNotNull(expected);
  }

  @Test
  void loadInputDataThrowRuntimeException() {
    assertThrows(RuntimeException.class,
        () -> LoadInputData.loadInputData(TEST_RESOURCES + "broke"));
  }
}