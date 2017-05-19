package Main;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.Function;

import GUI.Gui;

public class Main {

	private static final String STORAGE_FILE = "bank1.txt";
	static Bank bank = new Bank();
	public static void main(String[] args) {
		Gui gui = new Gui(new WindowAdapter()
		{
		    public void windowClosing(WindowEvent e)
		    {
		    	storeBank(bank, STORAGE_FILE);
		    }
		});

		gui.addAddAcountActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				Account a = bank.createAccount(bank.getPerson(gui.getSelectedPersonUUID()), gui.pollAccountType());
				if (a.getType() == AccountType.SAVING){
					((SavingAccount)a).setInterest(gui.pollInterest());
				}
				gui.loadAccounts(bank.getAccounts());
			}
			
		});
		gui.addAddMoneyActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				bank.addToAccount(gui.getSelectedAccountUUID(), gui.pollAmount());
				gui.loadAccounts(bank.getAccounts());
			}
			
		});
		gui.addAddPersonActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				bank.addPerson(gui.pollPerson());
				gui.loadPersons(bank.getPersons());
			}
			
		});
		gui.addDeleteAccountActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				bank.removeAccount(gui.getSelectedAccountUUID());
				gui.loadAccounts(bank.getAccounts());
			}
			
		});
		gui.addDeletePersonActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				bank.removePerson(bank.getPerson(gui.getSelectedPersonUUID()));
				gui.loadPersons(bank.getPersons());
				gui.loadAccounts(bank.getAccounts());
			}
			
		});
		gui.addWithdrawMoneytActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				int a = bank.withdrawFromAccount(gui.getSelectedAccountUUID(), gui.pollAmount());
				System.out.println("the owner got " + a + " money from the bank");
				gui.loadAccounts(bank.getAccounts());
			}
			
		});
		try{
			bank = (Bank) loadBank(STORAGE_FILE);
	
			for (Person p : bank.getPersons()){
				p.setNotify((Function<String, Void>)gui::notify);
			}
			for (Account a: bank.getAccounts()){
				a.update();
			}
		}
		catch(Exception e){
			Person p1 = new Person("geta", (Function<String, Void>)gui::notify);
			Person p2 = new Person("menumorut", (Function<String, Void>)gui::notify);
			Person p3 = new Person("nica", (Function<String, Void>)gui::notify);
			bank.addPerson(p1);
			bank.addPerson(p2);
			bank.addPerson(p3);
			bank.createAccount(p1, AccountType.SAVING);
			bank.createAccount(p1, AccountType.SAVING);
			bank.createAccount(p2, AccountType.SPENDING);
			bank.createAccount(p2, AccountType.SAVING);
			bank.createAccount(p2, AccountType.SPENDING);
			bank.createAccount(p3, AccountType.SPENDING);
			System.out.println("[Main] Loaded default values");
		}
		
		gui.loadGui();
		gui.loadAccounts(bank.getAccounts());
		gui.loadPersons(bank.getPersons());
	}
	

	public static BankProc loadBank(String file){
		BankProc bank = null;
	      try {
	         FileInputStream fileIn = new FileInputStream(file);
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         bank = (Bank) in.readObject();
	         in.close();
	         fileIn.close();
	         System.out.println("[Main] Loading bank");
	         return (bank);
	      }catch(IOException i) {
	         i.printStackTrace();
	         return null;
	      }catch(ClassNotFoundException c) {
	         System.out.println("Bank class not found");
	         c.printStackTrace();
	         return null;
	      }
	}
	
	public static void storeBank(BankProc bank, String file){
	      try {
	         FileOutputStream fileOut = new FileOutputStream(file);
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(bank);
	         out.close();
	         fileOut.close();
	         System.out.printf("Serialized data is saved in " + file);
	      }catch(IOException i) {
	         i.printStackTrace();
	      }
	}

}

//Person p1 = new Person("geta", (Function<String, Void>)gui::notify);
//Person p2 = new Person("menumorut", (Function<String, Void>)gui::notify);
//Person p3 = new Person("nica", (Function<String, Void>)gui::notify);
//bank.addPerson(p1);
//bank.addPerson(p2);
//bank.addPerson(p3);
//bank.createAccount(p1, AccountType.SAVING);
//bank.createAccount(p1, AccountType.SAVING);
//bank.createAccount(p2, AccountType.SPENDING);
//bank.createAccount(p2, AccountType.SAVING);
//bank.createAccount(p2, AccountType.SPENDING);
//bank.createAccount(p3, AccountType.SPENDING);
