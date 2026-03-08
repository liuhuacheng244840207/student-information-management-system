package DATA;

import java.util.Scanner;
import java.sql.Connection;              // 数据库连接对象
import java.sql.DriverManager;           // 驱动管理类，获取数据库连接
import java.sql.PreparedStatement;       // 预编译SQL对象（防注入、提效）
import java.sql.ResultSet;               // 查询结果集对象
import java.sql.SQLException;            // SQL异常类

public class DatabaseExampleV2 {

    private static Scanner scanner = new Scanner(System.in);

    // 1. 显示操作菜单
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

    // 2. 新增成绩
    public static void addScore(Connection conn) {
        System.out.println("\n===== 新增成绩 =====");
        System.out.print("请输入选课ID（必须存在于选课表）：");
        int selectCourseId = scanner.nextInt();
        System.out.print("请输入平时成绩：");
        float usualScore = scanner.nextFloat();
        System.out.print("请输入期末成绩：");
        float finalScore = scanner.nextFloat();
        System.out.print("请输入总评成绩：");
        float totalScore = scanner.nextFloat();

        String sql = "INSERT INTO `成绩表` (选课ID, 平时成绩, 期末成绩, 总评成绩) VALUES (?, ?, ?, ?)";
        //资源自动关闭语法括号内声明的 PreparedStatement 对象会在 try 代码块执行完毕后自动关闭，无需手动调用 close()，避免数据库连接 / 语句资源泄漏。
        /*
         	捕获异常：拦截程序运行中出现的 “不可预知的错误”（比如数据库连接断开、用户输入非数字、文件找不到），避免程序直接崩溃；
			容错处理：捕获异常后可以做针对性处理（比如提示用户、记录日志），让程序继续运行；
			异常隔离：把可能出错的代码包裹在 try 块中，出错时只进入 catch 块，不影响其他代码执行。
         */
        
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {//conn就是数据库连接对象
            //给SQL语句的第1个？占位符赋值
        	ps.setInt(1, selectCourseId);
            ps.setFloat(2, usualScore);
            ps.setFloat(3, finalScore);
            ps.setFloat(4, totalScore);
            int rows = ps.executeUpdate();//返回受影响的行数
            System.out.println(rows > 0 ? " 新增成绩成功！" : " 新增成绩失败！");
        } catch (SQLException e) {
            System.out.println("新增成绩异常：" + e.getMessage());//e.getMessage()会返回异常的具体原因
        }
    }

    // 3. 删除成绩
    public static void deleteScore(Connection conn) {
        System.out.println("\n===== 删除成绩 =====");
        System.out.print("请输入要删除的成绩ID：");
        int scoreId = scanner.nextInt();

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

    // 4. 修改成绩
    public static void updateScore(Connection conn) {
        System.out.println("\n===== 修改成绩 =====");
        // 1. 输入要修改的成绩ID
        System.out.print("请输入要修改的成绩ID：");
        int scoreId = scanner.nextInt();
        
        // 2. 输入新的平时成绩和期末（考试）成绩
        System.out.print("请输入新的平时成绩：");
        float newUsualScore = scanner.nextFloat();
        System.out.print("请输入新的期末（考试）成绩：");
        float newFinalScore = scanner.nextFloat();

        // 3. 定义总评成绩计算规则（可根据你的需求调整权重，比如平时30% + 期末70%）
        float totalScore = newUsualScore * 0.3f + newFinalScore * 0.7f;
        totalScore = (float) Math.round(totalScore * 10) / 10;

        // 4. 执行更新SQL：同时更新平时成绩、期末成绩、总评成绩
        String sql = "UPDATE `成绩表` SET 平时成绩 = ?, 期末成绩 = ?, 总评成绩 = ? WHERE 成绩ID = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setFloat(1, newUsualScore);   // 赋值平时成绩
            ps.setFloat(2, newFinalScore);   // 赋值期末成绩
            ps.setFloat(3, totalScore);      // 赋值自动计算的总评成绩
            ps.setInt(4, scoreId);           // 赋值成绩ID
            
            int rows = ps.executeUpdate();
            // 提示更新结果，包含自动计算的总评成绩
            if (rows > 0) {
                System.out.println(" 修改成绩（ID：" + scoreId + "）成功！");
                System.out.println(" 自动计算总评成绩：平时成绩(" + newUsualScore + ")×30% + 期末成绩(" + newFinalScore + ")×70% = " + totalScore);
            } else {
                System.out.println(" 未找到成绩ID：" + scoreId + "，修改失败！");
            }
        } catch (SQLException e) {
            System.out.println("修改成绩异常：" + e.getMessage());
        }
    }

    // 5. 查询成绩
    public static void queryScore(Connection conn) {
        System.out.println("\n===== 查询成绩 =====");
        System.out.print("请选择查询方式（1-查询全部 2-按ID查询）：");
        int queryType = scanner.nextInt();

        String sql = queryType == 2 ? "select * from `成绩表` where 成绩ID = ?" : "select * from `成绩表`";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {//创造预编译SQL语句对象，把SQL传入并绑定在PS中
            if (queryType == 2) {
                System.out.print("请输入要查询的成绩ID：");
                int scoreId = scanner.nextInt();
                ps.setInt(1, scoreId);
            }
            try (ResultSet rs = ps.executeQuery()) {//依照之前绑定的SQL语句，进行查询
                System.out.println("\n 成绩表数据如下：");
                boolean hasData = false;
                while (rs.next()) {//移动结果集的游标，遍历每一行数据（如果有数据返回 true，无数据返回 false）
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

    public static void main(String[] args) {
        // 数据库连接参数
        String driver = "com.mysql.cj.jdbc.Driver";
        String dbName = "stu";
        String password = "123456";
        String userName = "root";
        String url = "jdbc:mysql://localhost:3306/" + dbName
                + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
                + "&characterEncoding=utf8&useUnicode=true";

        Connection conn = null;

        // 步骤1：初始化数据库连接
        try {
            Class.forName(driver);//加载数据库驱动类
            conn = DriverManager.getConnection(url, userName, password);//通过驱动管理器获取数据库连接对象
            System.out.println(" 数据库连接成功！");

            // 步骤2：交互式循环（用户操作）
            int choice;
            do {
                showMenu();
                choice = scanner.nextInt();
                switch (choice) {
                    case 1: addScore(conn); break;
                    case 2: deleteScore(conn); break;
                    case 3: updateScore(conn); break;
                    case 4: queryScore(conn); break;
                    case 0: System.out.println(" 退出系统，感谢使用！"); break;
                    default: System.out.println(" 指令错误！请输入0-4的数字");
                }
            } while (choice != 0);

        } catch (ClassNotFoundException e) {//仅捕获Class.forName(driver);的错误
            System.out.println("驱动加载失败：" + e.getMessage());
        } catch (SQLException e) {//捕获所有数据库相关异常
            System.out.println(" 数据库操作异常：" + e.getMessage());
        } finally {//无论 try 块是否抛出异常，finally 块必执行，保证资源一定会被释放
            // 步骤3：关闭所有资源（必执行）
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println(" 数据库连接已关闭！");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}