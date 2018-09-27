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
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	private int _ISOLATION = Connection.TRANSACTION_READ_UNCOMMITTED;
	private int insID;
	Properties p;

	public MySQLAccess(int insID) {
		this.insID = insID;
		p = new Properties();
		p.setProperty("ID", String.valueOf(insID));
	}

	public void increment(int key) throws Exception {
		try {

			Object o = Class.forName("MyDriver").newInstance();
			DriverManager.registerDriver((Driver) o);
			Driver driver = DriverManager.getDriver("jdbc:mydriver://");
			connect = driver.connect(null, p);
			connect.setAutoCommit(false);
			connect.setTransactionIsolation(_ISOLATION);
			statement = connect.createStatement();
			resultSet = statement.executeQuery("select * from feedback.kv where id=1");
			resultSet.next();
			int old_val = resultSet.getInt(2);
			preparedStatement = connect.prepareStatement("update feedback.kv set value=? where id=?");
			preparedStatement.setInt(1, old_val + 100);
			preparedStatement.setInt(2, key);
			preparedStatement.executeUpdate();
			connect.commit();
		} catch (Exception e) {
			throw e;
		} finally {
			close();
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
			Thread.sleep(100);
			// connect.commit();
			// update 2
			preparedStatement = connect.prepareStatement("update feedback.kv set value=2000 where id=?");
			preparedStatement.setInt(1, key);
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
			Thread.sleep(50);
			Object o = Class.forName("MyDriver").newInstance();
			DriverManager.registerDriver((Driver) o);
			Driver driver = DriverManager.getDriver("jdbc:mydriver://");
			connect = driver.connect(null, p);
			connect.setAutoCommit(false);
			connect.setTransactionIsolation(_ISOLATION);
			preparedStatement = connect.prepareStatement("select * from feedback.kv where id=?");
			preparedStatement.setInt(1, key);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			System.out.println("(" + resultSet.getInt("id") + "," + resultSet.getInt("value") + ")");
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
			statement = connect.createStatement();
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
			if (resultSet != null)
				resultSet.close();
			if (statement != null)
				statement.close();
			if (connect != null)
				connect.close();
		} catch (Exception e) {

		}
	}

}
