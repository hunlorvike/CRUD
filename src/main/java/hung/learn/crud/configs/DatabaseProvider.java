package hung.learn.crud.configs;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseProvider {
    private static final String CONNECTION_STRING;
    private static final String USERNAME;
    private static final String PASSWORD;

    // Static block to initialize the database properties from the application.properties file
    static {
        Properties properties = new Properties();
        try (InputStream input = DatabaseProvider.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("application.properties file not found in classpath");
            }
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Error loading application.properties", e);
        }

        CONNECTION_STRING = properties.getProperty("db.url");
        USERNAME = properties.getProperty("db.username");
        PASSWORD = properties.getProperty("db.password");

        if (CONNECTION_STRING == null || USERNAME == null || PASSWORD == null) {
            throw new RuntimeException("Database properties not set in application.properties file");
        }
    }

    /**
     * Retrieves a database connection using the configured properties.
     *
     * @return a Connection object to the database
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
        try {
            return DriverManager.getConnection(CONNECTION_STRING, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException("Connection attempt failed: " + e.getMessage(), e);
        }
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
    public static void printDatabaseMetadata() {
        try (Connection connection = getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.printf("Database Product Name: %s%n", metaData.getDatabaseProductName());
            System.out.printf("Database Product Version: %s%n", metaData.getDatabaseProductVersion());
            System.out.printf("Database URL: %s%n", metaData.getURL());
            System.out.printf("Database Username: %s%n", metaData.getUserName());
        } catch (SQLException e) {
            System.err.println("Failed to retrieve database metadata: " + e.getMessage());
        }
    }

    /**
     * Checks if a database connection can be successfully established.
     *
     * @return true if the connection is successful, false otherwise
     */
    public static boolean isConnectionValid() {
        try (Connection connection = getConnection()) {
            return !connection.isClosed();
        } catch (SQLException e) {
            System.err.println("Failed to establish a database connection: " + e.getMessage());
            return false;
        }
    }
}
