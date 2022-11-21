package shpp.com.repo;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shpp.com.util.MyException;

import java.io.*;
import java.sql.Connection;

public class SqlScriptExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SqlScriptExecutor.class);

    private SqlScriptExecutor() {
    }

    public static void executeSqlScript(File file, Connection conn) throws MyException {

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            logger.info("Running script from file: {}", file.getCanonicalPath());
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setAutoCommit(true);
            runner.setStopOnError(true);
            runner.runScript(reader);
            logger.info("Done.");
        } catch (FileNotFoundException er) {
            throw new MyException("File not found!");
        } catch (IOException e) {
            throw new MyException(e.toString());
        }
    }
}


