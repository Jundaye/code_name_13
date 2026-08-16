package codename13;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

public class DBUtil {
	public static Connection getConnection() throws SQLException {
		BasicDataSource ds = new BasicDataSource();
		ds.setUrl("jdbc:mysql://localhost:3306/newhr");
		ds.setUsername("root");
		ds.setPassword("rootroot");
		ds.setDriverClassName("com.mysql.cj.jdbc.Driver");
		ds.setInitialSize(5);
		ds.setMaxTotal(20);
		Connection conn = ds.getConnection();
		return conn;
	}
}