package DATA;

import dao.dao4;
import entity.Score;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// 主交互类：负责用户操作
public class DatabaseEampleV3 {
    private static Scanner scanner = new Scanner(System.in);
    private static dao4 scoreDAO = new dao4(); // 调用DAO层

    // 显示菜单
    public static void showMenu() {
        System.out.println("\n=====================================");
        System.out.println("          成绩表管理系统");
        System.out.println("=====================================");
        System.out.println("1 - 新增成绩");
        System.out.println("2 - 删除成绩");
        System.out.println("3 - 修改成绩");
        System.out.println("4 - 查询成绩");
        System.out.println("0 - 退出系统");
        System.out.println("=====================================");
        System.out.print("请输入操作指令（0-4）：");
    }

    // 新增成绩
    public static void addScore(Connection conn) {
        System.out.println("\n===== 新增成绩 =====");
        System.out.print("请输入选课ID：");
        int selectCourseId = scanner.nextInt();
        System.out.print("请输入平时成绩：");
        float usualScore = scanner.nextFloat();
        System.out.print("请输入期末成绩：");
        float finalScore = scanner.nextFloat();

        // 自动计算总评
        float totalScore = usualScore * 0.3f + finalScore * 0.7f;
        totalScore = (float) Math.round(totalScore * 10) / 10;

        // 封装为实体类，调用DAO
        Score score = new Score(selectCourseId, usualScore, finalScore, totalScore);
        scoreDAO.addScore(conn, score);
    }

    // 删除成绩
    public static void deleteScore(Connection conn) {
        System.out.println("\n===== 删除成绩 =====");
        System.out.print("请输入要删除的成绩ID：");
        int scoreId = scanner.nextInt();
        scoreDAO.deleteScore(conn, scoreId);
    }

    // 修改成绩
    public static void updateScore(Connection conn) {
        System.out.println("\n===== 修改成绩 =====");
        System.out.print("请输入要修改的成绩ID：");
        int scoreId = scanner.nextInt();
        System.out.print("请输入新的平时成绩：");
        float newUsualScore = scanner.nextFloat();
        System.out.print("请输入新的期末成绩：");
        float newFinalScore = scanner.nextFloat();

        // 自动计算总评
        float totalScore = newUsualScore * 0.3f + newFinalScore * 0.7f;
        totalScore = (float) Math.round(totalScore * 10) / 10;

        // 封装为实体类，调用DAO
        Score score = new Score(scoreId, newUsualScore, newFinalScore, totalScore,true);
        scoreDAO.updateScore(conn, score);
    }

    // 查询成绩
    public static void queryScore(Connection conn) {
        System.out.println("\n===== 查询成绩 =====");
        System.out.print("请选择查询方式（1-全部 2-按ID）：");
        int queryType = scanner.nextInt();
        int scoreId = 0;
        if (queryType == 2) {
            System.out.print("请输入成绩ID：");
            scoreId = scanner.nextInt();
        }
        scoreDAO.queryScore(conn, queryType, scoreId);
    }

    // 主方法
    public static void main(String[] args) {
        // 数据库连接参数
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbName = "stu";
        String user = "root";
        String pwd = "123456";
        String url = "jdbc:mysql://localhost:3306/" + dbName
                + "?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";

        Connection conn = null;
        try {
            // 加载驱动 + 连接数据库
            Class.forName(driver);
            conn = DriverManager.getConnection(url, user, pwd);
            System.out.println(" 数据库连接成功！");

            // 交互式循环
            int choice;
            do {
                showMenu();
                choice = scanner.nextInt();
                switch (choice) {
                    case 1: addScore(conn); break;
                    case 2: deleteScore(conn); break;
                    case 3: updateScore(conn); break;
                    case 4: queryScore(conn); break;
                    case 0: System.out.println(" 退出系统！"); break;
                    default: System.out.println(" 指令错误，请输入0-4！");
                }
            } while (choice != 0);

        } catch (ClassNotFoundException e) {
            System.out.println("驱动加载失败：" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("数据库异常：" + e.getMessage());
        } finally {
            // 关闭资源
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println(" 连接已关闭！");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            scanner.close();
        }
    }
}