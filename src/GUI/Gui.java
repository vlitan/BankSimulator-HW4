package GUI;

import java.awt.Dimension;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Date;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import javax.sound.midi.MidiDevice.Info;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Main.Account;
import Main.AccountType;
import Main.Person;

public class Gui implements Serializable{
	private static final int width = 800;
	private static final int height = 600;
	private JFrame frame;
	private JScrollPane personsPane;
	private JScrollPane accountsPane;
	private JTable personsTable;
	private JTable accountsTable;
	private JButton addAccount;
	private JButton addPerson;
	private JButton addMoney;
	private JButton withdrawMoney;
	private JButton deletePerson;
	private JButton deleteAccount;
	private UUID	selectedAccountUUID;
	private UUID	selectedPersonUUID;
	
	public Gui(WindowAdapter windowAdapter){
		frame = new JFrame();
		accountsPane = new JScrollPane();
		personsPane = new JScrollPane();
		frame.addWindowListener(windowAdapter);
		addAccount = new JButton("addAccount");
		addPerson = new JButton("addPerson");
		addMoney = new JButton("addMoney");
		withdrawMoney = new JButton("withdrawMoney");
		deletePerson = new JButton("deletePerson");
		deleteAccount = new JButton("deleteAccount");
	}
	
	public UUID getSelectedAccountUUID(){
		return selectedAccountUUID;
		
	}
	
	public UUID getSelectedPersonUUID(){
		return selectedPersonUUID;
	}

	public void loadPersons(List<Person> persons){	//TODO
		String[] columnNames = new String[persons.get(0).getClass().getDeclaredFields().length];
		String[][] rowsData = new String[persons.size()][persons.get(0).getClass().getDeclaredFields().length];
		int i = 0;

		for (Field field : persons.get(0).getClass().getDeclaredFields()) {
			columnNames[i++] = field.getName();
		}
		i = 0;
		for (Person o : persons){
			int j = 0;
			for (Field field : persons.get(0).getClass().getDeclaredFields()) {
				field.setAccessible(true);
				try {
					if (field.get(o) != null){
						rowsData[i][j++] = field.get(o).toString();
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			i++;
			System.out.println();
		}
		final JTable table = new JTable(rowsData, columnNames);
		table.setBounds(0, 0, personsPane.getWidth(), personsPane.getHeight());
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        //TODO add selected action listener
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedPersonUUID = persons.get(table.getSelectionModel().getMaxSelectionIndex()).getUuid();
			}
		});
        personsPane.setViewportView(table);
	}
	
	public void loadAccounts(List<Account> accounts){	//TODO
		String[] columnNames = new String[accounts.get(0).getClass().getSuperclass().getDeclaredFields().length];
		String[][] rowsData = new String[accounts.size()][accounts.get(0).getClass().getSuperclass().getDeclaredFields().length];
		int i = 0;

		for (Field field : accounts.get(0).getClass().getSuperclass().getDeclaredFields()) {
			columnNames[i++] = field.getName();
		}
		i = 0;
		for (Account o : accounts){
			int j = 0;
			for (Field field : accounts.get(0).getClass().getSuperclass().getDeclaredFields()) {
				field.setAccessible(true);
				try {
					rowsData[i][j++] = field.get(o).toString();
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			i++;
		}
		final JTable table = new JTable(rowsData, columnNames);
		table.setBounds(0, 0, accountsPane.getWidth(), accountsPane.getHeight());
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectedAccountUUID = accounts.get(table.getSelectionModel().getMaxSelectionIndex()).getUuid();
			}
		});
        accountsPane.setViewportView(table);
	}
	
	public Void notify(String s){
		JOptionPane.showMessageDialog(frame, s);
		return null;
	}
	
	public AccountType pollAccountType(){//TODO
		AccountType[] options = {AccountType.SAVING,
				AccountType.SPENDING};
		int n = JOptionPane.showOptionDialog(frame,
			    "What type of account whould you like to create",
			    "A Silly Question",
			    JOptionPane.YES_NO_CANCEL_OPTION,
			    JOptionPane.QUESTION_MESSAGE,
			    null,
			    options,
			    options[0]);
		return options[n];
	}
	
	
	public int pollAmount(){//TODO
		int ans =  Integer.parseInt(JOptionPane.showInputDialog(frame, "Add / withdraw from selected account", null, JOptionPane.INFORMATION_MESSAGE, null, null, "ammount").toString());
		return ans;
	}
	
	public Person pollPerson(){//TODO
		return new Person(JOptionPane.showInputDialog(frame, "The name of the new person", null, JOptionPane.INFORMATION_MESSAGE, null, null, "name").toString(), (Function<String, Void>)this::notify); //TODO
	}
	
	public void loadGui(){
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setBounds(0, 0, width, height);
		frame.setLayout(null);
		
		addAccount.setBounds(0, 10, width / 6 - 2, 40);
		addPerson.setBounds(width / 6, 10, width / 6 - 2, 40);
		addMoney.setBounds(width * 2 / 6, 10, width / 6 - 2, 40);
		withdrawMoney.setBounds(width * 3 / 6, 10, width / 6 - 2, 40);
		deletePerson.setBounds(width * 4 / 6, 10, width / 6 - 2, 40);
		deleteAccount.setBounds(width * 5 / 6, 10, width / 6 - 2, 40);
		
		personsPane.setBounds(0, 50, width / 2, height - 50);
		accountsPane.setBounds(width / 2, 50, width / 2, height - 50);
		
		frame.add(personsPane);
		frame.add(accountsPane);
		frame.add(addAccount);
		frame.add(addPerson);
		frame.add(addMoney);
		frame.add(withdrawMoney);
		frame.add(deletePerson);
		frame.add(deleteAccount);
		
		frame.setVisible(true);
	}
	
	public void addAddPersonActionListener(ActionListener al){
		addPerson.addActionListener(al);
	}
	public void addAddAcountActionListener(ActionListener al){
		addAccount.addActionListener(al);
	}
	public void addAddMoneyActionListener(ActionListener al){
		addMoney.addActionListener(al);
	}
	public void addWithdrawMoneytActionListener(ActionListener al){
		withdrawMoney.addActionListener(al);
	}
	public void addDeletePersonActionListener(ActionListener al){
		deletePerson.addActionListener(al);
	}
	public void addDeleteAccountActionListener(ActionListener al){
		deleteAccount.addActionListener(al);
	}

	public int pollInterest() {
		int ans =  Integer.parseInt(JOptionPane.showInputDialog(frame, "choose account interest", null, JOptionPane.INFORMATION_MESSAGE, null, null, "interest").toString());
		return ans;
	}
}
