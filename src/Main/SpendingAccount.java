package Main;
import java.util.Observer;

public class SpendingAccount extends Account {

	public SpendingAccount(Observer owner) {
		super(owner);
		type = AccountType.SPENDING;
		// TODO Auto-generated constructor stub
	}

}
