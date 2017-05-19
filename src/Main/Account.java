package Main;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;

public abstract class Account extends Observable implements Serializable {
	private UUID uuid;
	private int balance;
	protected AccountType type;
	
	public AccountType getType() {
		return type;
	}
	
	public void setType(AccountType type) {
		this.type = type;
	}

	private Person owner;
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public int getBalance() {
		return balance;
	}
	
	public Account(Observer owner){
		this.addObserver(owner);
		this.owner = (Person) owner;
		uuid = UUID.randomUUID();
	}
	
	public void update(){
		this.addObserver(this.owner);
	}
	
	//returns the updated balance
	public int withdraw(int amount){
		if (amount > balance){
			System.out.println("[" + this.toString() + "]" + " not enough money in account");
			return (0);
		}
		setChanged();
		notifyObservers(-amount);
		balance -= amount;
		return (amount);
	}
	
	public int add(int amount){
		balance += amount;
		setChanged();
		notifyObservers(amount);
		return (balance);
	}
	
	@Override
	public String toString(){
		return ("Account " + uuid);
	}
	
	
}
