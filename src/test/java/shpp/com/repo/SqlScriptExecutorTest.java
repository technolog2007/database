package shpp.com.repo;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SqlScriptExecutorTest {

  private static final String DDL_TEST_SCRIPT_FILE = "test.sql";
  private static final String DDL_TEST_SCRIPT_PATH = "src/test/resources/";
  private static final String NOT_FILE = " ";
  static Connection connection;
  static Statement statementMock;
  static SqlScriptExecutor executor;

  @BeforeAll
  static void init() throws SQLException {
    connection = mock(Connection.class);
    statementMock = mock(Statement.class);
    executor = mock(SqlScriptExecutor.class);
    when(connection.createStatement()).thenReturn(statementMock);
  }

  @Test
  void executeSqlScriptTest() {
    doCallRealMethod().when(executor)
        .executeSqlScript(DDL_TEST_SCRIPT_FILE, DDL_TEST_SCRIPT_PATH, connection);
    executor.executeSqlScript(DDL_TEST_SCRIPT_FILE, DDL_TEST_SCRIPT_PATH, connection);
    verify(executor).executeSqlScript(DDL_TEST_SCRIPT_FILE, DDL_TEST_SCRIPT_PATH, connection);
  }

  @Test
  void executeSqlScriptThrowRuntimeException() {
    doCallRealMethod().when(executor).executeSqlScript(NOT_FILE, DDL_TEST_SCRIPT_PATH, connection);
    assertThrows(RuntimeException.class,
        () -> executor.executeSqlScript(NOT_FILE, DDL_TEST_SCRIPT_PATH, connection));
  }
}