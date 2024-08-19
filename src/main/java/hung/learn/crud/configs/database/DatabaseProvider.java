package hung.learn.crud.configs.database;

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
    private static SimpleConnectionPool pool;

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

        try {
            pool = new SimpleConnectionPool(CONNECTION_STRING, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Lấy kết nối cơ sở dữ liệu từ pool kết nối.
     *
     * @return một đối tượng Connection đến cơ sở dữ liệu
     * @throws SQLException nếu xảy ra lỗi truy cập cơ sở dữ liệu
     */
    public static Connection getConnection() throws SQLException {
        return pool.getConnection();
    }

    /**
     * Trả kết nối về pool.
     *
     * @param connection kết nối cần trả về pool
     */
    public static void releaseConnection(Connection connection) {
        pool.releaseConnection(connection);
    }

    /**
     * Đóng pool và giải phóng tất cả tài nguyên.
     */
    public static void close() {
        pool.close();
    }

    /**
     * Lấy và in metadata về cơ sở dữ liệu, bao gồm tên sản phẩm, phiên bản, URL và tên người dùng.
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
     * Kiểm tra xem kết nối cơ sở dữ liệu có thể được thiết lập thành công hay không.
     *
     * @return true nếu kết nối thành công, false nếu không
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
