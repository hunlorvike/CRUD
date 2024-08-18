package hung.learn.crud.repositories.shared;

import hung.learn.crud.models.BaseModel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Repository<T extends BaseModel, ID> implements IRepository<T, ID> {
    protected final Connection __connection;

    public Repository(Connection connection) {
        this.__connection = connection;
    }

    protected abstract T mapResultSet(ResultSet resultSet) throws SQLException;

    protected abstract String getTableName();

    @Override
    public Optional<T> findById(ID id) throws SQLException {
        String sql = String.format("SELECT * FROM %s WHERE id = ?", getTableName());
        try (PreparedStatement statement = __connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    T entity = mapResultSet(resultSet);
                    return Optional.of(entity);
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<T> findAll() throws SQLException {
        List<T> entities = new ArrayList<>();
        String sql = String.format("SELECT * FROM %s", getTableName());
        try (Statement statement = __connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                T entity = mapResultSet(resultSet);
                entities.add(entity);
            }
        }
        return entities;
    }

    @Override
    public void delete(ID id) throws SQLException {
        String sql = String.format("DELETE FROM %s WHERE id = ?", getTableName());
        try (PreparedStatement statement = __connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            statement.executeUpdate();
        }
    }

    @Override
    public int count() throws SQLException {
        String sql = String.format("SELECT COUNT(*) FROM %s", getTableName());
        try (Statement statement = __connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        }
        return 0;
    }

    @Override
    public boolean exists(ID id) throws SQLException {
        String sql = String.format("SELECT 1 FROM %s WHERE id = ? LIMIT 1", getTableName());
        try (PreparedStatement statement = __connection.prepareStatement(sql)) {
            statement.setObject(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
            }
        }
    }
}
