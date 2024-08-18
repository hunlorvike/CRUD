package hung.learn.crud.repositories;

import hung.learn.crud.models.Student;
import hung.learn.crud.repositories.shared.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class StudentRepository extends Repository<Student, Integer> {
    public StudentRepository(Connection connection) {
        super(connection);
    }

    @Override
    protected Student mapResultSet(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setId(resultSet.getInt("id"));
        student.setFullname(resultSet.getString("fullname"));
        student.setPhone(resultSet.getString("phone"));
        student.setAddress(resultSet.getString("address"));
        student.setPoint(resultSet.getFloat("point"));
        return student;
    }

    @Override
    protected String getTableName() {
        return "student";
    }

    @Override
    public void add(Student entity) throws SQLException {
        String sql = String.format("INSERT INTO %s (fullname, phone, address, point) VALUES (?, ?, ?, ?)", getTableName());
        try (PreparedStatement statement = __connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, entity.getFullname());
            statement.setString(2, entity.getPhone());
            statement.setString(3, entity.getAddress());
            statement.setFloat(4, entity.getPoint());

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
    public void addRange(List<Student> entities) throws SQLException {
        String sql = String.format("INSERT INTO %s (fullname, phone, address, point) VALUES (?, ?, ?, ?)", getTableName());
        try (PreparedStatement statement = __connection.prepareStatement(sql)) {
            for (Student entity : entities) {
                statement.setString(1, entity.getFullname());
                statement.setString(2, entity.getPhone());
                statement.setString(3, entity.getAddress());
                statement.setFloat(4, entity.getPoint());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    @Override
    public void update(Student entity) throws SQLException {
        String sql = String.format("UPDATE %s SET fullname = ?, phone = ?, address = ?, point = ? WHERE id = ?", getTableName());
        try (PreparedStatement statement = __connection.prepareStatement(sql)) {
            statement.setString(1, entity.getFullname());
            statement.setString(2, entity.getPhone());
            statement.setString(3, entity.getAddress());
            statement.setFloat(4, entity.getPoint());
            statement.setInt(5, entity.getId());

            statement.executeUpdate();
        }
    }
}
