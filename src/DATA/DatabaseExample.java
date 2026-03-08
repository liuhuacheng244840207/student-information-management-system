package DATA;

// Java JDBC（Java Database Connectivity）的核心 API 导入，没有这些类就无法进行数据库的连接和操作。
import java.sql.Connection;              // 数据库连接对象
import java.sql.DriverManager;           // 驱动管理类，获取数据库连接
import java.sql.PreparedStatement;       // 预编译SQL对象（防注入、提效）
import java.sql.ResultSet;               // 查询结果集对象
import java.sql.SQLException;            // SQL异常类

public class DatabaseExample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String driver = "com.mysql.cj.jdbc.Driver";//mySQL的驱动类名
		String dbName = "stu";//数据库名称
		String password = "123456";
		String userName = "root";
		String url = "jdbc:mysql://localhost:3306/" + dbName
		        + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC"
		        + "&characterEncoding=utf8&useUnicode=true"; // 必须包含这两个参数，不然无法在eclipse上操作
		String sql = "select * from `成绩表`";//定义要执行的SQL查询语句
		// 8. 声明数据库操作对象（初始化为null，后续在try块中赋值）
        PreparedStatement ps = null;  // 预编译SQL语句对象
        Connection conn = null;       // 数据库连接对象
        ResultSet rs = null;          // 查询结果集对象
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, userName, password);//连接对象
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();//执行查询 SQL，返回的rs对象包含查询到的所有数据（成绩表的行和列）；如果是增删改操作，用executeUpdate()，返回受影响的行数。
			while (rs.next()) {
				System.out.println(
				        "成绩id : " + rs.getInt("成绩ID")  // 对应表的“成绩ID”列
				        + "   选课id : " + rs.getInt("选课ID")  // 对应表的“选课ID”列
				        + "   平时成绩： " + rs.getFloat("平时成绩") // 对应表的“平时成绩”列
				    );
			}

		} catch (SQLException e) {
			e.printStackTrace();//捕获异常
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();//捕获驱动类找不到的异常，如没导入MySQL的驱动jar包
		}

		// 关闭记录集
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
// 关闭声明 
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

// 关闭链接对象 ，释放对应的数据库资源
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
