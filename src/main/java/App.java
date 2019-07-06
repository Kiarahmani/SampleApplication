import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class App {

	public static void main(String[] args) {
		try {
			Connection connect = null;
			PreparedStatement preparedStatement = null;
			ResultSet rs = null;
			Properties p;
			p = new Properties();
			p.setProperty("ID", "1");
			p.setProperty("dbName", "feedback");

			Driver driver = DriverManager.getDriver("jdbc:cassandra://localhost:9042/testks");
			System.out.println("driver: " + driver);
			connect = driver.connect("", p);
			System.out.println("connect: " + connect);

			preparedStatement = connect.prepareStatement("select * from feedback.kv where id=?");
			preparedStatement.setInt(1, 1);
			// Thread.sleep(2500);
			rs = preparedStatement.executeQuery();
			rs.next();
			System.out.println("(" + rs.getInt("id") + "," + rs.getInt("value") + ")");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close();
		}

	}

}
