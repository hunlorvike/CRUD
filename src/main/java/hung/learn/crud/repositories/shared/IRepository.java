package hung.learn.crud.repositories.shared;

import hung.learn.crud.models.BaseModel;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IRepository<T extends BaseModel, ID> {
    void add(T entity) throws SQLException;

    void addRange(List<T> entities) throws SQLException;

    Optional<T> findById(ID id) throws SQLException;

    List<T> findAll() throws SQLException;

    void update(T entity) throws SQLException;

    void delete(ID id) throws SQLException;

    int count() throws SQLException;

    boolean exists(ID id) throws SQLException;
}
