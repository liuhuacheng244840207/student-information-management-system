package DATA;

import dao.dao3;
import dao.dao4;
import dao.dao4SelectCourse;
import dao.dao4Student;
import entity.Score;
import entity.Student;
import entity.SelectCourse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// 主交互类：负责用户操作
public class DatabaseExampleV4 {
    private static Scanner scanner = new Scanner(System.in);
    private static dao3 scoreDAO = new dao3(); // 调用DAO层
    // 数据库连接参数（抽成常量，便于维护）
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DB_NAME = "stu";
    private static final String USER = "root";
    private static final String PWD = "123456";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME
            + "?useSSL=false&serverTimezone=UTC&characterEncoding=utf8";

    // 显示主菜单
    private static void showMainMenu() {
        System.out.println("\n===== 学生成绩管理系统 =====");
        System.out.println("1 - 新增成绩");
        System.out.println("2 - 删除成绩");
        System.out.println("3 - 修改成绩");
        System.out.println("4 - 查询成绩");
        System.out.println("5 - 学生表的增删改查");
        System.out.println("6 - 选课表的增删改查");
        System.out.println("7 - 查询某学生的所有成绩"); // 补充分支
        System.out.println("0 - 退出系统");
        System.out.print("请输入操作编号：");
    }
    
    // 学生表子菜单
    private static void showStudentSubMenu() {
        System.out.println("\n===== 学生表操作 =====");
        System.out.println("1 - 新增学生信息");
        System.out.println("2 - 删除学生信息");
        System.out.println("3 - 修改学生信息");
        System.out.println("4 - 查询学生信息");
        System.out.print("请输入操作编号：");
    }
    
    // 选课表子菜单
    private static void showSelectCourseSubMenu() {
        System.out.println("\n===== 选课表操作 =====");
        System.out.println("1 - 新增选课信息");
        System.out.println("2 - 删除选课信息");
        System.out.println("3 - 修改选课信息");
        System.out.println("4 - 查询选课信息");
        System.out.print("请输入操作编号：");
    }

    // 新增成绩（需Connection参数）
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

    // 删除成绩（需Connection参数）
    public static void deleteScore(Connection conn) {
        System.out.println("\n===== 删除成绩 =====");
        System.out.print("请输入要删除的成绩ID：");
        int scoreId = scanner.nextInt();
        scoreDAO.deleteScore(conn, scoreId);
    }

    // 修改成绩（需Connection参数）
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
        Score score = new Score(scoreId, newUsualScore, newFinalScore, totalScore, true);
        scoreDAO.updateScore(conn, score);
    }

    // 查询成绩（需Connection参数）
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

    // 查询某学生的所有成绩
    public static void queryScoreByStudentId(Connection conn) {
        System.out.print("请输入学生ID：");
        int studentId = scanner.nextInt();
        // 多表联查SQL：成绩表+选课表+学生表
        String sql = "SELECT s.成绩ID, sc.选课ID, st.姓名, s.平时成绩, s.期末成绩, s.总评成绩 " +
                     "FROM 成绩表 s JOIN 选课表 sc ON s.选课ID=sc.选课ID " +
                     "JOIN 学生表 st ON sc.学生ID=st.学生ID WHERE st.学生ID=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, studentId);
            ResultSet rs = ps.executeQuery();
            boolean hasData = false; // 标记是否有数据
            while (rs.next()) {
                hasData = true;
                System.out.printf("学生：%s 成绩ID：%d 平时成绩：%.1f 总评：%.1f\n",
                        rs.getString("姓名"), rs.getInt("成绩ID"),
                        rs.getFloat("平时成绩"), rs.getFloat("总评成绩"));
            }
            if (!hasData) {
                System.out.println("该学生暂无成绩记录！");
            }
        } catch (SQLException e) {
            System.out.println("查询异常：" + e.getMessage());
        }
    }

    // 学生表操作 - 新增
    private static void addStudent() {
        System.out.println("\n===== 新增学生 =====");
        System.out.print("请输入学生ID：");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // 吸收换行符
        System.out.print("请输入学生姓名：");
        String studentName = scanner.nextLine();
        System.out.print("请输入学生性别（男/女）：");
        String gender = scanner.nextLine();
        System.out.print("请输入学生学号：");
        String studentNo = scanner.nextLine();
        System.out.print("请输入学生班级：");
        String className = scanner.nextLine();
        System.out.print("请输入学生联系方式：");
        String contact = scanner.nextLine();

        Student student = new Student();
        student.setStudentId(studentId);
        student.setStudentName(studentName);
        student.setGender(gender);
        student.setStudentNo(studentNo);
        student.setClassName(className);
        student.setContact(contact);

        dao4Student studentDAO = new dao4Student();
        studentDAO.addStudent(getConnection(), student);
    }


    private static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PWD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("数据库连接失败：" + e.getMessage());
        }
        return conn;
    }

    // 学生表操作 - 删除
    private static void deleteStudent() {
        System.out.println("\n===== 删除学生 =====");
        System.out.print("请输入要删除的学生ID：");
        int studentId = scanner.nextInt();
        dao4Student studentDAO = new dao4Student();
        studentDAO.deleteStudent(getConnection(), studentId);
    }

    // 学生表操作 - 修改
    private static void updateStudent() {
        System.out.println("\n===== 修改学生 =====");
        System.out.print("请输入要修改的学生ID：");
        int studentId = scanner.nextInt();
        scanner.nextLine();
        System.out.print("请输入新的姓名：");
        String studentName = scanner.nextLine();
        System.out.print("请输入新的性别（男/女）：");
        String gender = scanner.nextLine();
        System.out.print("请输入新的学号：");
        String studentNo = scanner.nextLine();
        System.out.print("请输入新的班级：");
        String className = scanner.nextLine();
        System.out.print("请输入新的联系方式：");
        String contact = scanner.nextLine();

        Student student = new Student();
        student.setStudentId(studentId);
        student.setStudentName(studentName);
        student.setGender(gender);
        student.setStudentNo(studentNo);
        student.setClassName(className);
        student.setContact(contact);

        dao4Student studentDAO = new dao4Student();
        studentDAO.updateStudent(getConnection(), student);
    }

    // 学生表操作 - 查询
    private static void queryStudent() {
        System.out.println("\n===== 查询学生 =====");
        System.out.print("请输入要查询的学生ID：");
        int studentId = scanner.nextInt();
        dao4Student studentDAO = new dao4Student();
        studentDAO.queryStudent(getConnection(), studentId);
    }

    // 选课表操作 - 新增
    private static void addSelectCourse() {
        System.out.println("\n===== 新增选课 =====");
        System.out.print("请输入选课ID：");
        int selectCourseId = scanner.nextInt();
        System.out.print("请输入学生ID：");
        int studentId = scanner.nextInt();
        System.out.print("请输入课程ID：");

        int courseId = scanner.nextInt();
        scanner.nextLine();
     // 2. 补充新增字段的输入（选课时间、状态）
        System.out.print("请输入选课时间（格式：yyyy-MM-dd，如2025-09-01）：");
        String selectCourseTime = scanner.nextLine().trim();
        if (selectCourseTime.isEmpty()) { // 新增空值兜底
            selectCourseTime = "2025-09-01";
        }
        System.out.print("请输入选课状态（可选：已选课/退课/待审核，默认：已选课）：");
        String state = scanner.nextLine().trim();
        
        

        SelectCourse selectCourse = new SelectCourse();
        selectCourse.setSelectCourseId(selectCourseId);
        selectCourse.setStudentId(studentId);
        selectCourse.setCourseId(courseId);
        selectCourse.setSelectCourseTime(selectCourseTime); // 新增
        selectCourse.setState(state); // 新增

        dao4SelectCourse selectCourseDAO = new dao4SelectCourse();
        selectCourseDAO.addSelectCourse(getConnection(), selectCourse);
    }

    // 选课表操作 - 删除
    private static void deleteSelectCourse() {
        System.out.println("\n===== 删除选课 =====");
        System.out.print("请输入要删除的选课ID：");
        int selectCourseId = scanner.nextInt();
        dao4SelectCourse selectCourseDAO = new dao4SelectCourse();
        selectCourseDAO.deleteSelectCourse(getConnection(), selectCourseId);
    }

    // 选课表操作 - 修改
    private static void updateSelectCourse() {
    	System.out.print("请输入选课ID：");
        int selectCourseId = scanner.nextInt();
        System.out.print("请输入学生ID：");
        int studentId = scanner.nextInt();
        System.out.print("请输入课程ID：");

        int courseId = scanner.nextInt();
        scanner.nextLine();
     // 2. 补充新增字段的输入（选课时间、状态）
        System.out.print("请输入选课时间（格式：yyyy-MM-dd，如2025-09-01）：");
        String selectCourseTime = scanner.nextLine().trim();
        if (selectCourseTime.isEmpty()) { // 新增空值兜底
            selectCourseTime = "2025-09-01";
        }
        System.out.print("请输入选课状态（可选：已选课/退课/待审核，默认：已选课）：");
        String state = scanner.nextLine().trim();
        
        SelectCourse selectCourse = new SelectCourse();
        selectCourse.setSelectCourseId(selectCourseId);
        selectCourse.setStudentId(studentId);
        selectCourse.setCourseId(courseId);
        selectCourse.setSelectCourseTime(selectCourseTime); // 新增
        selectCourse.setState(state); // 新增

        dao4SelectCourse selectCourseDAO = new dao4SelectCourse();
        selectCourseDAO.updateSelectCourse(getConnection(), selectCourse);
    }

    // 选课表操作 - 查询
    private static void querySelectCourse() {
        System.out.println("\n===== 查询选课 =====");
        System.out.print("请输入要查询的选课ID：");
        int selectCourseId = scanner.nextInt();
        dao4SelectCourse selectCourseDAO = new dao4SelectCourse();
        selectCourseDAO.querySelectCourse(getConnection(), selectCourseId);
    }

    // 主方法（核心修复：循环+传参）
    public static void main(String[] args) {
        Connection conn = null;
        try {
            // 加载驱动 + 建立数据库连接（只连接一次，避免重复创建）
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("✅ 数据库连接成功！");

            // 核心修复：添加循环，实现交互式菜单
            while (true) {
                showMainMenu();
                int mainChoice = scanner.nextInt();
                switch (mainChoice) {
                    case 1: addScore(conn); break; // 修复：传入conn参数
                    case 2: deleteScore(conn); break; // 修复：传入conn参数
                    case 3: updateScore(conn); break; // 修复：传入conn参数
                    case 4: queryScore(conn); break; // 修复：传入conn参数
                    case 5:  // 学生表子菜单
                        while (true) {
                            showStudentSubMenu();
                            int studentChoice = scanner.nextInt();
                            switch (studentChoice) {
                                case 1: addStudent(); break;
                                case 2: deleteStudent(); break;
                                case 3: updateStudent(); break;
                                case 4: queryStudent(); break;                                
                                default: System.out.println("❌ 输入无效，请重新输入！");
                            }
                        }
                    case 6:  // 选课表子菜单
                        while (true) {
                            showSelectCourseSubMenu();
                            int scChoice = scanner.nextInt();
                            switch (scChoice) {
                                case 1: addSelectCourse(); break;
                                case 2: deleteSelectCourse(); break;
                                case 3: updateSelectCourse(); break;
                                case 4: querySelectCourse(); break;                               
                                default: System.out.println("❌ 输入无效，请重新输入！");
                            }
                        }
                    case 7: queryScoreByStudentId(conn); break; // 补充：查询学生所有成绩
                    case 0:
                        System.out.println("✅ 退出系统，感谢使用！");
                        scanner.close();
                        System.exit(0);
                    default: System.out.println("❌ 输入无效，请重新输入！");
                }
            }

        } catch (ClassNotFoundException e) {
            System.out.println("❌ 驱动加载失败：" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ 数据库异常：" + e.getMessage());
        } finally {
            // 关闭资源
            if (conn != null) {
                try {
                    conn.close();
                    System.out.println("🔌 数据库连接已关闭！");
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