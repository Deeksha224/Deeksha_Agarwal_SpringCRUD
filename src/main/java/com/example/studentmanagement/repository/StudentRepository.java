package com.example.studentmanagement.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.example.studentmanagement.model.Student;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Repository
public class StudentRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Student> rowMapper = (rs, rowNum) -> {
        Student student = new Student();
        student.setId(rs.getInt("id"));
        student.setName(rs.getString("name"));
        student.setEmail(rs.getString("email"));
        student.setCourse(rs.getString("course"));
        return student;
    };

    public Student save(Student student) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO student (name, email, course) VALUES (?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setString(3, student.getCourse());
            return ps;
        }, keyHolder);
        student.setId(keyHolder.getKey().intValue());
        return student;
    }

    public List<Student> findAll() {
        return jdbcTemplate.query("SELECT * FROM student", rowMapper);
    }

    public Student findById(Integer id) {
        return jdbcTemplate.queryForObject("SELECT * FROM student WHERE id = ?", rowMapper, id);
    }

    public int update(Student student) {
        return jdbcTemplate.update(
            "UPDATE student SET name = ?, email = ?, course = ? WHERE id = ?",
            student.getName(), student.getEmail(), student.getCourse(), student.getId());
    }

    public int deleteById(Integer id) {
        return jdbcTemplate.update("DELETE FROM student WHERE id = ?", id);
    }
}