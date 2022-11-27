package shpp.com.repo;

import org.junit.jupiter.api.Test;
import shpp.com.util.MyException;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class ConnectToDBTest {

    @Test
    void getConnectionIsNotNull() throws MyException, SQLException {
        ConnectToDB connectToDB = new ConnectToDB();
        Connection connection = connectToDB.getConnection();
        assertNotNull(connection);
        connection.close();
    }
}