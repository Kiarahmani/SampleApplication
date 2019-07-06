import com.mysql.jdbc.exceptions.jdbc4.MySQLTransactionRollbackException;

public class Transaction {
	private MySQLAccess dao;
	private int insID;

	// constructor
	public Transaction(int insID) {
		this.insID = insID;
		dao = new MySQLAccess(insID);
	}

	// run the transaction wrapper
	public void run(String opType) {
		try {
			if (opType.equals("select"))
				dao.select(1);
			else if (opType.equals("increment"))
				dao.increment(1);
			else if (opType.equals("init"))
				dao.initialize(1);
			else if (opType.equals("double"))
				dao.doubleUpdate(1);
			else if (opType.equals("testTransaction"))
				dao.testTransaction(1);
			else
				System.err.println("Unknown Operaition Type");
		} catch (MySQLTransactionRollbackException e) {
			// retry
			try {
				Thread.sleep(10);
				// System.out.println(".");
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			run(opType);
		} catch (Exception e) {
			System.out.println(e);
			// TODO Auto-generated catch block

		}
	}
}
