package io.oilfox.backend.api.shared.test.helpers;

import com.google.inject.Inject;
import io.oilfox.backend.api.shared.properties.DatabaseProperties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.postgresql.util.PSQLException;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

/**
 * Created by ipusic on 1/21/16.
 */
public class DatabaseHelper {

    @Inject
    public DatabaseProperties databaseProperties;

    public void bootstrapDatabase() throws Exception {

        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");

            Properties props = new Properties();
            props.setProperty("user", databaseProperties.getUsername());
            props.setProperty("password", databaseProperties.getPassword());

            String connectionString = String.format("jdbc:postgresql://%s:%d/%s", databaseProperties.getHostname(), databaseProperties.getPort(), databaseProperties.getDatabase());

            connection = DriverManager.getConnection(connectionString, props);

            Statement statement = connection.createStatement();

            // there is a difference between Debug / Run
            // the working directory can be different
            // so we need to find the sql directory from two different starting points
            File directory = Paths.get("sql").toFile();

            if (!directory.exists()) {
                directory = Paths.get("..", "..", "sql").toFile();
                if (!directory.exists()) {
                    throw new Exception("cannot find SQL directory");
                }
            }

            // search for all .sql files in that directory
            Collection<File> sqls = (Collection<File>)FileUtils.listFiles(directory, new String[]{"sql"}, false);

            // find the highest version
            Optional<Integer> result = sqls.stream()
                    .filter((item) -> item.getName().startsWith("V"))
                    .map((item) -> item.getName().substring(1))

                    .map((item) -> item.substring(0, item.lastIndexOf(".")))
                    .map((item) -> Integer.parseInt(item))
                    .max(Integer::compare);

            // get a reference to that file
            File file = directory.toPath().resolve("V" + result.get() + ".sql").toFile();

            // and load it
            try (FileInputStream fisTargetFile = new FileInputStream(file)) {

                String targetFileStr = IOUtils.toString(fisTargetFile, "UTF-8");
                List<String> statements = Arrays.asList(targetFileStr.split(";"));

                for(int x = 0; x<statements.size(); x++) {
                    String sql = statements.get(x);
                    statement.executeUpdate(sql);
                }
            }
        }
        finally {
            if (connection != null) {
                connection.close();
            }
        }
    }

    public void recreateDatabase() throws Exception {

        Connection connection = null;

        try {
            Class.forName("org.postgresql.Driver");

            Properties props = new Properties();
            props.setProperty("user", databaseProperties.getUsername());
            props.setProperty("password", databaseProperties.getPassword());

            String connectionString = String.format("jdbc:postgresql://%s:%d/", databaseProperties.getHostname(), databaseProperties.getPort());

            connection = DriverManager.getConnection(connectionString, props);

            Statement statement = connection.createStatement();

            try {
                statement.executeUpdate("DROP DATABASE " + databaseProperties.getDatabase());
            }
            catch(PSQLException e) {
                e.printStackTrace();
                System.out.println(e.toString());
                // intentionally left blank
            }

            try {
                statement.executeUpdate("CREATE DATABASE "+ databaseProperties.getDatabase());
            }
            catch(PSQLException e) {
                e.printStackTrace();
                throw new Exception("Cannot (re)create the database " + databaseProperties.getDatabase()+". Are there still other open connections?");
            }

        }
        finally {
            if (connection != null) {
                connection.close();
            }
        }
    }
}
