package Main;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.Set;
import java.util.UUID;

public class Bank implements BankProc, Serializable {

	private Hashtable<Person, Set<Account>> table = new Hashtable<Person, Set<Account>>(0);
	
	@Override
	public int getPersonsCount() {
		assert (table.size() > 0);
		return table.size();
	}

	@Override
	public void addPerson(Person person) {
		assert(isWellformed());
		assert(person != null);
		table.put(person, new HashSet<Account>());
		assert(isWellformed());
	}

	@Override
	public void removePerson(Person person) {
		assert(isWellformed());
		table.remove(person);
		assert(isWellformed());
	}

	@Override
	public void addAccount(Person owner, Account account) {
		assert(isWellformed());
		table.get(owner).add(account);
		assert(isWellformed());
	}

	@Override
	public void removeAccount(Person owner, Account account) {
		assert(isWellformed());
		table.get(owner).remove(account);
		assert(isWellformed());
	}

	
	public void removeAccount(UUID accountUuid) {
		assert(isWellformed());
		Account account = null;
		for (Entry<Person, Set<Account>> e : table.entrySet()){
			for (Account a : e.getValue()){
				if (a.getUuid() == accountUuid){
					account = a;
				}
			}
			removeAccount(e.getKey(), account);
		}
		assert(isWellformed());
	}
	
	@Override
	public void generateReport() {
		assert(isWellformed());
		System.out.println("[Bank] generated report");
		assert(isWellformed());
	}

	@Override
	public void addToAccount(Person owner, Account account, int amount) {
		assert(isWellformed());
		if (table.get(owner).contains(account)){
			account.add(amount);
		}
		assert(isWellformed());
	}

	@Override
	public void withdrawFromAccount(Person owner, Account account, int amount) {
		assert(isWellformed());
		if (table.get(owner).contains(account)){
			account.withdraw(amount);
		}
		assert(isWellformed());
	}

	@Override
	public Account createAccount(Person person, AccountType type) {
		Account account;
		if (type == AccountType.SAVING){
			account = new SavingAccount(person);
		}
		else{
			account = new SpendingAccount(person);
		}
		addAccount(person, account);
		return account;
	}

	public List<Entry<Person, Set<Account>>> getEntries(){
		assert(isWellformed());
		return (List<Entry<Person, Set<Account>>>) (table.entrySet());
	}
	
	public void printTable(){
		for (Entry<Person, Set<Account>> e : table.entrySet()){
			assert(isWellformed());
			System.out.println("[Bank] person " + e.getKey().toString() + " has accounts:");
			for (Account a : e.getValue()){
				System.out.println("[Bank]       " + a.toString());
			}
			assert(isWellformed());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void addToAccount(Person owner, UUID uuid, int amount) {
		assert(isWellformed());
		for (Account account : table.get(owner)){
			if (account.getUuid() == uuid){
				account.add(amount);
			}
		}
		assert(isWellformed());
	}

	@SuppressWarnings("unchecked")
	@Override
	public int withdrawFromAccount(Person owner, UUID uuid, int amount) {
		assert(isWellformed());
		for (Account account :  table.get(owner)){
			if (account.getUuid() == uuid){
				return account.withdraw(amount);
			}
		}
		assert(isWellformed());
		return (0);
	}
	
	@SuppressWarnings("unchecked")
	public void addToAccount(UUID uuid, int amount) {
		assert(isWellformed());
		for (Account account : getAccounts()){
			if (account.getUuid() == uuid){
				account.add(amount);
			}
		}
		assert(isWellformed());
	}

	@SuppressWarnings("unchecked")
	public int withdrawFromAccount(UUID uuid, int amount) {
		assert(isWellformed());
		for (Account account :  getAccounts()){
			if (account.getUuid() == uuid){
				return account.withdraw(amount);
			}
		}
		assert(isWellformed());
		return (0);
	}
	
	public Person getPerson(UUID uuid){
		assert(isWellformed());
		for (Entry<Person, Set<Account>> entry :  table.entrySet()){
			if (entry.getKey().getUuid() == uuid){
				return (entry.getKey());
			}
		}
		assert(isWellformed());
		return null;
	}
	
	public List<Person> getPersons(){
		assert(isWellformed());
		List<Person> persons = new ArrayList<Person>();
		for (Entry<Person, Set<Account>> e : table.entrySet()){
			persons.add(e.getKey());
		}
		assert(isWellformed());
		return (persons);
	}
	
	
	public List<Account> getAccounts(){
		assert(isWellformed());
		List<Account> accounts = new ArrayList<Account>();
		for (Entry<Person, Set<Account>> e : table.entrySet()){
			for (Account a : e.getValue()){
				accounts.add(a);
			}
		}
		assert(isWellformed());
		return (accounts);
	}
	
	private boolean isWellformed(){
		return (this != null);
	}
	
}
