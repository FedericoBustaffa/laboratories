import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.Set;

public class Handler implements Runnable {

	// data
	private String nickname;
	private Set<String> nicknames;
	private List<String> chat;

	// tcp
	private Socket socket;
	private DataInputStream is;
	private DataOutputStream os;

	public Handler(Set<String> nicknames, List<String> chat, Socket socket) {
		try {
			this.nicknames = nicknames;
			this.chat = chat;
			this.socket = socket;
			is = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
			os = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void shutdown() {
		try {
			nicknames.remove(nickname);
			is.close();
			os.close();
			socket.close();
			System.out.println(nickname + " left");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void msg(String[] parse) {

	}

	public void run() {
		try {
			// nickname
			boolean available;
			do {
				nickname = is.readUTF();
				available = !nicknames.contains(nickname);
				os.writeBoolean(available);
				os.flush();
			} while (!available);
			nicknames.add(nickname);
			chat.add(nickname + " joined");

			String[] parse;
			while (true) {
				parse = is.readUTF().split(" ");
				if (parse[0].equals("msg")) {
					msg(parse);
				} else if (parse[0].equals("exit") && parse.length == 1) {
					os.writeUTF("exit");
					os.flush();
					break;
				} else {
					os.writeUTF("error");
				}
				os.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		shutdown();
	}

}
