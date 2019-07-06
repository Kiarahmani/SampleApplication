import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Properties;

public class MySQLAccess {
	private Connection connect = null;
	private Statement stmt = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet rs = null;
	private int _ISOLATION = Connection.TRANSACTION_READ_UNCOMMITTED;
	private int insID;
	Properties p;

	public MySQLAccess(int insID) {
		this.insID = insID;
		p = new Properties();
		p.setProperty("ID", String.valueOf(insID));
		p.setProperty("dbName", "feedback");
	}

	public void testTransaction(int key) throws Exception {
		try {

			Object o = Class.forName("MyDriver").newInstance();
			DriverManager.registerDriver((Driver) o);
			Driver driver = DriverManager.getDriver("jdbc:mydriver://");
			System.out.println("driver: "+driver);
			connect = driver.connect("", p);
			System.out.println("connect: "+connect);
			connect.setAutoCommit(false);
			connect.setTransactionIsolation(_ISOLATION);
			preparedStatement = connect.prepareStatement("select * from feedback.kv where id=?");
			preparedStatement.setInt(1, key);
			// Thread.sleep(2500);
			rs = preparedStatement.executeQuery();
			rs.next();
			System.out.println("(" + rs.getInt("id") + "," + rs.getInt("value") + ")");
		} catch (Exception e) {
			throw e;
		} finally {
			// close();
		}

	}

	public void increment(int key) throws Exception {
		try {

			Object o = Class.forName("MyDriver").newInstance();
			DriverManager.registerDriver((Driver) o);

			Driver driver = DriverManager.getDriver("jdbc:mydriver://");
			Connection connect = DriverManager
					.getConnection("jdbc:mysql://localhost/feedback?" + "user=user1&password=pass1&useSSL=false");
			// DriverManager.getConnection("jdbc:mysql://localhost:3306/feedback", "user1",
			// "pass1");

			// connect.setAutoCommit(false);
			// connect.setTransactionIsolation(_ISOLATION);
			// stmt = connect.createStatement();
			// rs = stmt.executeQuery("select * from feedback.kv where id=1");
			// rs.next();

			// int old_val = rs.getInt(2);
			// System.out.println(old_val);
			// preparedStatement = connect.prepareStatement("update feedback.kv set value=?
			// where id=?");
			// preparedStatement.setInt(1, old_val + 100);
			// preparedStatement.setInt(2, key);
			// preparedStatement.executeUpdate();
			// connect.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			// close();
		}

	}

	public void doubleUpdate(int key) throws Exception {
		try {
			Object o = Class.forName("MyDriver").newInstance();
			DriverManager.registerDriver((Driver) o);
			Driver driver = DriverManager.getDriver("jdbc:mydriver://");
			connect = driver.connect(null, p);
			connect.setAutoCommit(false);
			connect.setTransactionIsolation(_ISOLATION);
			// update 1
			preparedStatement = connect.prepareStatement("update feedback.kv set value=1000 where id=?");
			preparedStatement.setInt(1, key);
			preparedStatement.executeUpdate();

			// update 2
			preparedStatement = connect.prepareStatement("update feedback.kv set value=2000 where id=?");
			preparedStatement.setInt(1, key);
			// Thread.sleep(5000);
			preparedStatement.executeUpdate();
			connect.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	public void select(int key) {
		try {

			Object o = Class.forName("MyDriver").newInstance();
			DriverManager.registerDriver((Driver) o);
			Driver driver = DriverManager.getDriver("jdbc:mydriver://");
			connect = driver.connect(null, p);
			connect.setAutoCommit(false);
			connect.setTransactionIsolation(_ISOLATION);
			preparedStatement = connect.prepareStatement("select * from feedback.kv where id=?");
			preparedStatement.setInt(1, key);
			// Thread.sleep(2500);
			rs = preparedStatement.executeQuery();
			rs.next();
			System.out.println("(" + rs.getInt("id") + "," + rs.getInt("value") + ")");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close();
		}
	}

	public void initialize(int key) throws Exception {
		try {
			Object o = Class.forName("MyDriver").newInstance();
			DriverManager.registerDriver((Driver) o);
			Driver driver = DriverManager.getDriver("jdbc:mydriver://");
			connect = driver.connect(null, p);
			preparedStatement = connect.prepareStatement("update feedback.kv set value=0 where id=?");
			preparedStatement.setInt(1, key);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			close();
		}

	}

	private void close() {
		try {
			if (rs != null)
				rs.close();
			if (rs != null)
				rs.close();
			if (connect != null)
				connect.close();
		} catch (Exception e) {

		}
	}

}
