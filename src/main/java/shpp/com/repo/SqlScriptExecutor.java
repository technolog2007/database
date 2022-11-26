package shpp.com.repo;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;

public class SqlScriptExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SqlScriptExecutor.class);

    /**
     * The method executes the dll-script from the *.sql file
     * @param fileName - *.sql file
     * @param connection - connection
     */
    public void executeSqlScript(String fileName, Connection connection) {
        FileReader fileReader = getFileReader(fileName);
        try (BufferedReader reader = new BufferedReader(fileReader)) {
            logger.info("Running script from file: {}", fileName);
            ScriptRunner runner = new ScriptRunner(connection);
            runner.setAutoCommit(false);
            runner.setStopOnError(true);
            runner.runScript(reader);
            logger.info("DLL-script download successful!");
        } catch (IOException ex) {
            logger.error(ex.getMessage());
        }
    }

    private FileReader getFileReader(String fileName) {
        try {
            return new FileReader("config/" + fileName);
        } catch (FileNotFoundException e) {
            try {
                return new FileReader("src/main/resources/" + fileName);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}


