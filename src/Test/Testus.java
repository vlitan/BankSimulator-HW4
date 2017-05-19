package Test;

import org.junit.Test;

import Main.Account;
import Main.Bank;
import Main.Person;
import Main.SavingAccount;

public class Testus {
	Bank bank = new Bank();
	
	@Test
	public void first(){
		Person p = new Person("iulian", null);
		Account a = new SavingAccount(p);
		bank.addPerson(p);
		bank.addAccount(p, a);
		bank.removePerson(p);
		assert(bank.getAccounts().size() == 0);
	}
}
