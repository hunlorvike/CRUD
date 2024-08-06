package hung.learn.crud.configs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConfig {
    private static final String connectionString;
    private static final String username;
    private static final String password;

    // Static block to initialize the database properties from the application.properties file
    static {
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();

            // Check if the properties file is available
            if (input == null) {
                System.err.println("Sorry, unable to find application.properties");
                throw new RuntimeException("application.properties file not found in classpath");
            }

            // Load the properties from the file
            prop.load(input);
            connectionString = prop.getProperty("db.url");
            username = prop.getProperty("db.username");
            password = prop.getProperty("db.password");

            // Ensure that all required properties are set
            if (connectionString == null || username == null || password == null) {
                System.err.println("Database properties not set in application.properties file");
                throw new RuntimeException("Database properties not set in application.properties file");
            }
        } catch (IOException e) {
            System.err.println("Error loading application.properties" + e);
            throw new RuntimeException("Error loading application.properties", e);
        }
    }

    /**
     * Retrieves a database connection using the configured properties.
     *
     * @return a Connection object to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionString, username, password);
    }

    /**
     * Closes the given database connection, if it is not null.
     *
     * @param connection the Connection object to be closed
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.err.println("Failed to close the connection: " + e.getMessage());
            }
        }
    }

    /**
     * Retrieves and prints metadata about the database, including product name, version, URL, and username.
     */
    public static void getMetadata() {
        try (Connection connection = getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println("Database Product Name: " + metaData.getDatabaseProductName());
            System.out.println("Database Product Version: " + metaData.getDatabaseProductVersion());
            System.out.println("Database URL: " + metaData.getURL());
            System.out.println("Database Username: " + metaData.getUserName());
        } catch (SQLException e) {
            System.err.println("Failed to retrieve database metadata: " + e.getMessage());
        }
    }

    /**
     * Checks if a database connection can be successfully established.
     *
     * @return true if the connection is successful, false otherwise
     */
    public static boolean checkConnection() {
        try (Connection connection = getConnection()) {
            return true;
        } catch (SQLException e) {
            System.err.println("Failed to establish a database connection: " + e.getMessage());
            return false;
        }
    }
}
