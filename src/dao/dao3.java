package dao;

import entity.Score;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// 成绩表数据层：负责数据库操作
public class dao3 {
    // 新增成绩
    public void addScore(Connection conn, Score score) {
        String sql = "INSERT INTO `成绩表` (选课ID, 平时成绩, 期末成绩, 总评成绩) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, score.getSelectCourseId());
            ps.setFloat(2, score.getUsualScore());
            ps.setFloat(3, score.getFinalScore());
            ps.setFloat(4, score.getTotalScore());
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ? " 新增成绩成功！" : " 新增成绩失败！");
        } catch (SQLException e) {
            System.out.println("新增成绩异常：" + e.getMessage());
        }
    }

    // 删除成绩
    public void deleteScore(Connection conn, int scoreId) {
        String sql = "DELETE FROM `成绩表` WHERE 成绩ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, scoreId);
            int rows = ps.executeUpdate();
            System.out.println(rows > 0 ?
                    " 删除成绩（ID：" + scoreId + "）成功！" :
                    " 未找到成绩ID：" + scoreId + "，删除失败！");
        } catch (SQLException e) {
            System.out.println("删除成绩异常：" + e.getMessage());
        }
    }

    // 修改成绩
    public void updateScore(Connection conn, Score score) {
        String sql = "UPDATE `成绩表` SET 平时成绩 = ?, 期末成绩 = ?, 总评成绩 = ? WHERE 成绩ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setFloat(1, score.getUsualScore());
            ps.setFloat(2, score.getFinalScore());
            ps.setFloat(3, score.getTotalScore());
            ps.setInt(4, score.getScoreId());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println(" 修改成绩（ID：" + score.getScoreId() + "）成功！");
                System.out.println(" 自动计算总评成绩：平时成绩(" + score.getUsualScore() + ")×30% + 期末成绩(" + score.getFinalScore() + ")×70% = " + score.getTotalScore());
            } else {
                System.out.println(" 未找到成绩ID：" + score.getScoreId() + "，修改失败！");
            }
        } catch (SQLException e) {
            System.out.println("修改成绩异常：" + e.getMessage());
        }
    }

    // 查询成绩
    public void queryScore(Connection conn, int queryType, int scoreId) {
        String sql = queryType == 2 ? "select * from `成绩表` where 成绩ID = ?" : "select * from `成绩表`";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            if (queryType == 2) {
                ps.setInt(1, scoreId);
            }

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n 成绩表数据如下：");
                boolean hasData = false;
                while (rs.next()) {
                    hasData = true;
                    System.out.printf(
                            "成绩id : %d   选课id : %d   平时成绩：%.1f   期末成绩：%.1f   总评成绩：%.1f\n",
                            rs.getInt("成绩ID"),
                            rs.getInt("选课ID"),
                            rs.getFloat("平时成绩"),
                            rs.getFloat("期末成绩"),
                            rs.getFloat("总评成绩")
                    );
                }
                if (!hasData) {
                    System.out.println(" 暂无符合条件的成绩数据！");
                }
            }
        } catch (SQLException e) {
            System.out.println("查询成绩异常：" + e.getMessage());
        }
    }
}