package Main;
import java.util.UUID;

public interface BankProc {
	
	/*count of persons always positive
	 * @post return > 0
	 * */
	public int getPersonsCount();
	
	/*
	 * @pre person != null
	 * */
	public void addPerson(Person person);
	public void removePerson(Person person);
	
	public Account createAccount(Person person, AccountType type);
	
	
	public void addAccount(Person owner, Account account);
	public void removeAccount(Person owner, Account account);
	
	public void generateReport();
	
	public void addToAccount(Person owner, Account account, int amount);
	public void withdrawFromAccount(Person owner, Account account, int amount);
	
	public void addToAccount(Person owner, UUID uuid, int amount);
	public int withdrawFromAccount(Person owner, UUID uuid, int amount);
}
