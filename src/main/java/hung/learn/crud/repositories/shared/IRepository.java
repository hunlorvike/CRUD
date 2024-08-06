package hung.learn.crud.repositories.shared;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IRepository<T> {
    void add(T entity) throws SQLException;

    void addRange(List<T> entities) throws SQLException;

    Optional<T> findById(int id) throws SQLException;

    List<T> findAll() throws SQLException;

    void update(T entity) throws SQLException;

    void delete(int id) throws SQLException;

    int count() throws SQLException;

    boolean exists(int id) throws SQLException;
}
