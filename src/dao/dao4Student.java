package dao;

import entity.Student;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dao4Student {
    // 新增学生
    public void addStudent(Connection conn, Student student) {
        String sql = "INSERT INTO 学生表(学生ID, 姓名, 性别, 学号, 班级, 联系方式) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, student.getStudentId());
            ps.setString(2, student.getStudentName());
            ps.setString(3, student.getGender());
            ps.setString(4, student.getStudentNo());
            ps.setString(5, student.getClassName());
            ps.setString(6, student.getContact());
            ps.executeUpdate();
            System.out.println("✅ 新增学生成功！");
        } catch (SQLException e) {
            System.out.println("❌ 新增学生异常：" + e.getMessage());
        }
    }

    // 删除学生（按学生ID）
    public void deleteStudent(Connection conn, int studentId) {
        String sql = "DELETE FROM 学生表 WHERE 学生ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "✅ 删除学生成功！" : "❌ 未找到该学生ID！");
        } catch (SQLException e) {
            System.out.println("❌ 删除学生异常：" + e.getMessage());
        }
    }

    // 修改学生（按学生ID）
    public void updateStudent(Connection conn, Student student) {
        String sql = "UPDATE 学生表 SET 姓名=?, 性别=?, 学号=?, 班级=?, 联系方式=? WHERE 学生ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getStudentName());
            ps.setString(2, student.getGender());
            ps.setString(3, student.getStudentNo());
            ps.setString(4, student.getClassName());
            ps.setString(5, student.getContact());
            ps.setInt(6, student.getStudentId());
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "✅ 修改学生成功！" : "❌ 未找到该学生ID！");
        } catch (SQLException e) {
            System.out.println("❌ 修改学生异常：" + e.getMessage());
        }
    }

    // 查询学生（按学生ID）
    public void queryStudent(Connection conn, int studentId) {
        String sql = "SELECT * FROM 学生表 WHERE 学生ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("===== 学生信息 =====");
                System.out.println("学生ID：" + rs.getInt("学生ID"));
                System.out.println("姓名：" + rs.getString("姓名"));
                System.out.println("性别：" + rs.getString("性别"));
                System.out.println("学号：" + rs.getString("学号"));
                System.out.println("班级：" + rs.getString("班级"));
                System.out.println("联系方式：" + rs.getString("联系方式"));
            } else {
                System.out.println("❌ 未找到该学生ID！");
            }
        } catch (SQLException e) {
            System.out.println("❌ 查询学生异常：" + e.getMessage());
        }
    }
}