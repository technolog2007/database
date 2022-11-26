package shpp.com.repo;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SqlScriptExecutorTest {
    private static final String DDL_SCRIPT_FILE = "test.sql";

    @Test
    void executeSqlScriptTest() {
        Connection connection = Mockito.mock(Connection.class);
        SqlScriptExecutor executor = Mockito.mock(SqlScriptExecutor.class);
        Mockito.doCallRealMethod().when(executor).executeSqlScript(DDL_SCRIPT_FILE, connection);
        executor.executeSqlScript(DDL_SCRIPT_FILE, connection);
        Mockito.verify(executor).executeSqlScript(DDL_SCRIPT_FILE, connection);
    }
}