package Main;
import java.util.Observer;

public class SavingAccount extends Account {

	private float interest = 1;
	
	public SavingAccount(Observer owner) {
		super(owner);
		type = AccountType.SAVING;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int withdraw(int amount){
		
		return (int) (super.withdraw((amount)) * interest);
	}
	
	public float getInterest() {
		return interest;
	}

	public void setInterest(float interest) {
		this.interest = interest;
	}

}
