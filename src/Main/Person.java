package Main;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;
import java.util.UUID;
import java.util.function.Function;
public class Person implements Serializable, Observer {

	private String 	name;
	private UUID	uuid;
	private transient Function<String, Void> notify;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNotify(Function<String, Void> notify) {
		this.notify = notify;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		notify.apply("[Person" + " " + this.name + "] i got notified by " + o.toString() + " " +(((int)arg < 0)  ? " withdraw " : "added ") + Math.abs((int)arg));
	}

	public Person(String name, Function<String, Void> notify) {
		super();
		this.notify = notify;
		this.name = name;
		this.uuid = uuid.randomUUID();
	}
	
	@Override
	public String toString(){
		return (name);
	}
	
}
