package hung.learn.crud.repositories;

import hung.learn.crud.models.User;
import hung.learn.crud.repositories.shared.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserRepository extends Repository<User, Integer> {
    public UserRepository(Connection connection) {
        super(connection);
    }

    @Override
    public void add(User entity) throws SQLException {
        String sql = String.format("INSERT INTO %s (fullname, username, password) VALUES (?, ?, ?)", getTableName());
        try (PreparedStatement statement = __connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getFullname());
            statement.setString(2, entity.getUsername());
            statement.setString(3, entity.getPassword());

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }

    @Override
    public void addRange(List<User> entities) throws SQLException {
        String sql = String.format("INSERT INTO %s (fullname, username, password) VALUES (?, ?, ?)", getTableName());
        try (PreparedStatement statement = __connection.prepareStatement(sql)) {
            for (User entity : entities) {
                statement.setString(1, entity.getFullname());
                statement.setString(2, entity.getUsername());
                statement.setString(3, entity.getPassword());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    @Override
    public void update(User entity) throws SQLException {
        String sql = String.format("UPDATE %s SET fullname = ?, username = ?, password = ? WHERE id = ?", getTableName());
        try (PreparedStatement statement = __connection.prepareStatement(sql)) {
            statement.setString(1, entity.getFullname());
            statement.setString(2, entity.getUsername());
            statement.setString(3, entity.getPassword());
            statement.setInt(4, entity.getId());

            statement.executeUpdate();
        }
    }

    public Optional<User> findByUsername(String username) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE username = ?", getTableName());
        try (PreparedStatement statement = __connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(mapResultSet(resultSet));
                } else {
                    return Optional.empty();
                }
            }
        }
    }

    @Override
    protected User mapResultSet(ResultSet resultSet) throws SQLException {
        User teacher = new User();
        teacher.setId(resultSet.getInt("id"));
        teacher.setFullname(resultSet.getString("fullname"));
        teacher.setUsername(resultSet.getString("username"));
        teacher.setPassword(resultSet.getString("password"));
        return teacher;
    }

    @Override
    protected String getTableName() {
        return "users";
    }
}
