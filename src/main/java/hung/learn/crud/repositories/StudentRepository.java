package hung.learn.crud.repositories;

import hung.learn.crud.models.Student;
import hung.learn.crud.repositories.shared.IRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentRepository implements IRepository<Student> {

    private Connection connection;

    public StudentRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void add(Student entity) throws SQLException {
        String sql = "INSERT INTO Students (Name, Email, Address) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getEmail());
            pstmt.setString(3, entity.getAddress());
            pstmt.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void addRange(List<Student> entities) throws SQLException {
        String sql = "INSERT INTO Students (Name, Email, Address) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            for (Student student : entities) {
                pstmt.setString(1, student.getName());
                pstmt.setString(2, student.getEmail());
                pstmt.setString(3, student.getAddress());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
        } finally {
            closeConnection();
        }
    }

    @Override
    public Optional<Student> findById(int id) throws SQLException {
        String sql = "SELECT * FROM Students WHERE Id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Student(
                            rs.getInt("Id"),
                            rs.getString("Name"),
                            rs.getString("Email"),
                            rs.getString("Address")
                    ));
                }
            }
        } finally {
            closeConnection();
        }
        return Optional.empty();
    }

    @Override
    public List<Student> findAll() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                students.add(new Student(
                        rs.getInt("Id"),
                        rs.getString("Name"),
                        rs.getString("Email"),
                        rs.getString("Address")
                ));
            }
        } finally {
            closeConnection();
        }
        return students;
    }

    @Override
    public void update(Student entity) throws SQLException {
        String sql = "UPDATE Students SET Name = ?, Email = ?, Address = ? WHERE Id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, entity.getName());
            pstmt.setString(2, entity.getEmail());
            pstmt.setString(3, entity.getAddress());
            pstmt.setInt(4, entity.getId());
            pstmt.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM Students WHERE Id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } finally {
            closeConnection();
        }
    }

    @Override
    public int count() throws SQLException {
        String sql = "SELECT COUNT(*) FROM Students";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } finally {
            closeConnection();
        }
        return 0;
    }

    @Override
    public boolean exists(int id) throws SQLException {
        String sql = "SELECT 1 FROM Students WHERE Id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next();
            }
        } finally {
            closeConnection();
        }
    }

    private void closeConnection() {
        if (this.connection != null) {
            try {
                if (!this.connection.isClosed()) {
                    this.connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
