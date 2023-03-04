package shpp.com.repo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SqlScriptExecutor {

  private static final Logger logger = LoggerFactory.getLogger(SqlScriptExecutor.class);
  private static final String CONFIG = "config/";

  /**
   * The method executes the dll-script from the *.sql file.
   *
   * @param fileName   - *.sql file
   * @param connection - connection
   */
  public void executeSqlScript(String fileName, String filePath, Connection connection) {
    FileReader fileReader = getFileReader(fileName, filePath);
    try (BufferedReader reader = new BufferedReader(fileReader)) {
      logger.info("Running script from file: {}", fileName);
      ScriptRunner runner = new ScriptRunner(connection);
      runner.setStopOnError(true);
      runner.runScript(reader);
      logger.info("DLL-script download successful!");
    } catch (IOException ex) {
      logger.error(ex.getMessage());
    }
  }

  /**
   * The method creates and returns FileReader for the specified file.
   *
   * @param fileName - *.sql file
   * @return - FileReader
   */
  private FileReader getFileReader(String fileName, String filepath) {
    try {
      return new FileReader(CONFIG + fileName);
    } catch (FileNotFoundException e) {
      try {
        return new FileReader(filepath + fileName);
      } catch (FileNotFoundException ex) {
        throw new RuntimeException(ex);
      }
    }
  }
}


