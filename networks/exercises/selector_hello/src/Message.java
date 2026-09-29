import java.io.Serializable;

public class Message implements Serializable {

	private String username;
	private String text;

	public Message(String username, String text) {
		this.username = username;
		this.text = text;
	}

	public Message(String text) {
		this(null, text);
	}

	public Message() {
		this(null, null);
	}

	public String getUsername() {
		return username;
	}

	public String getText() {
		return text;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setText(String text) {
		this.text = text;
	}

}
