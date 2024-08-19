package hung.learn.crud.repositories;

import hung.learn.crud.models.Student;
import hung.learn.crud.repositories.shared.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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

    public List<Student> findPaginated(int offset, int noOfRecords) throws SQLException {
        String query = String.format("SELECT * FROM %s LIMIT ?, ?", getTableName());
        try (PreparedStatement ps = __connection.prepareStatement(query)) {
            ps.setInt(1, offset);
            ps.setInt(2, noOfRecords);
            try (ResultSet rs = ps.executeQuery()) {
                List<Student> students = new ArrayList<>();
                while (rs.next()) {
                    Student student = mapResultSet(rs);
                    students.add(student);
                }
                return students;
            }
        }
    }

    public int countFiltered(String name, String phone, String address) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM ").append(getTableName()).append(" WHERE 1=1");
        List<String> filters = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            query.append(" AND fullname LIKE ?");
            filters.add("%" + name + "%");
        }
        if (phone != null && !phone.isEmpty()) {
            query.append(" AND phone LIKE ?");
            filters.add("%" + phone + "%");
        }
        if (address != null && !address.isEmpty()) {
            query.append(" AND address LIKE ?");
            filters.add("%" + address + "%");
        }

        try (PreparedStatement ps = __connection.prepareStatement(query.toString())) {
            for (int i = 0; i < filters.size(); i++) {
                ps.setString(i + 1, filters.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public List<Student> findFilteredPaginated(String name, String phone, String address, int offset, int noOfRecords) throws SQLException {
        StringBuilder query = new StringBuilder("SELECT * FROM ").append(getTableName()).append(" WHERE 1=1");
        List<String> filters = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            query.append(" AND fullname LIKE ?");
            filters.add("%" + name + "%");
        }
        if (phone != null && !phone.isEmpty()) {
            query.append(" AND phone LIKE ?");
            filters.add("%" + phone + "%");
        }
        if (address != null && !address.isEmpty()) {
            query.append(" AND address LIKE ?");
            filters.add("%" + address + "%");
        }
        query.append(" LIMIT ?, ?");

        try (PreparedStatement ps = __connection.prepareStatement(query.toString())) {
            int paramIndex = 1;
            for (String filter : filters) {
                ps.setString(paramIndex++, filter);
            }
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex, noOfRecords);

            try (ResultSet rs = ps.executeQuery()) {
                List<Student> students = new ArrayList<>();
                while (rs.next()) {
                    Student student = mapResultSet(rs);
                    students.add(student);
                }
                return students;
            }
        }
    }
}
