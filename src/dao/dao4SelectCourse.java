package dao;

import entity.SelectCourse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class dao4SelectCourse {
    // 新增选课
    public void addSelectCourse(Connection conn, SelectCourse selectCourse) {
        String sql = "INSERT INTO 选课表(选课ID, 学生ID, 课程ID,选课时间,状态) VALUES (?, ?, ?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, selectCourse.getSelectCourseId());
            ps.setInt(2, selectCourse.getStudentId());
            ps.setInt(3, selectCourse.getCourseId());
            ps.setString(4, selectCourse.getSelectCourseTime());
            ps.setString(5, selectCourse.getState());

            ps.executeUpdate();
            System.out.println("✅ 新增选课成功！");
        } catch (SQLException e) {
            System.out.println("❌ 新增选课异常：" + e.getMessage());
        }
    }

    // 删除选课（按选课ID）
    public void deleteSelectCourse(Connection conn, int selectCourseId) {
        String sql = "DELETE FROM 选课表 WHERE 选课ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, selectCourseId);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? "✅ 删除选课成功！" : "❌ 未找到该选课ID！");
        } catch (SQLException e) {
            System.out.println("❌ 删除选课异常：" + e.getMessage());
        }
    }

    // 修改选课（按选课ID）
    public void updateSelectCourse(Connection conn, SelectCourse selectCourse) {
        // SQL 语句：根据选课ID更新选课表的其他字段
        String sql = "UPDATE 选课表 SET 学生ID=?, 课程ID=?, 选课时间=?, 状态=? WHERE 选课ID=?";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            // 注意：参数索引从1开始，先设置要更新的字段值，最后设置WHERE条件的选课ID
            ps.setInt(1, selectCourse.getStudentId());
            ps.setInt(2, selectCourse.getCourseId());
            ps.setString(3, selectCourse.getSelectCourseTime());
            ps.setString(4, selectCourse.getState());
            ps.setInt(5, selectCourse.getSelectCourseId());

            // 执行更新操作，获取受影响的行数
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("✅ 修改选课记录成功！");
            } else {
                System.out.println("⚠️ 未找到对应的选课记录，修改操作无生效！");
            }
        } catch (SQLException e) {
            System.out.println("❌ 修改选课记录异常：" + e.getMessage());
            }
        }

    // 查询选课（按选课ID）
    public void querySelectCourse(Connection conn, int selectCourseId) {
        String sql = "SELECT * FROM 选课表 WHERE 选课ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, selectCourseId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("===== 选课信息 =====");
                System.out.println("选课ID：" + rs.getInt("选课ID"));
                System.out.println("学生ID：" + rs.getInt("学生ID"));
                System.out.println("课程ID：" + rs.getInt("课程ID"));
                System.out.println("选课时间：" + rs.getString("选课时间")); // 新增
                System.out.println("状态：" + rs.getString("状态")); // 新增
            } else {
                System.out.println("❌ 未找到该选课ID！");
            }
        } catch (SQLException e) {
            System.out.println("❌ 查询选课异常：" + e.getMessage());
        }
    }
}