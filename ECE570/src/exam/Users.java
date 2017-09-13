package exam;

import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Map;

public class Users {
	
	Map<SocketChannel, User> usernames;
	int count;
	int size;
	int answerCount;
	
	public Users() {
		usernames = new HashMap<SocketChannel, User>();
		count = 0;
		size = 3;
		answerCount = 0;
	}
	
	public boolean answer(SocketChannel sc, String response, Question q) {
		if(sc == null || response == null || q == null) {
			return false;
		}
		
		if(!exists(sc)) {
			return false;
		}
		
		if(response == "") {
			return false;
		}
		
		User u = usernames.get(sc);
		
		if(u.getAnswer() == "") {
			answerCount++;
		}
		
		u.setAnswer(response);
		
		if(answerCount == size) {
			lockIn(q);
			return true;
		}
		
		return false;
	}
	
	private void lockIn(Question q) {
		for(User u : usernames.values()) {
				u.updateTotal(q);
		}
		answerCount = 0;
	}
	
	public boolean exists(SocketChannel sc) {
		if (!usernames.containsKey(sc)) return false;
		else return true;
	}
	
	public boolean isFull() {
		if(count >= size)
			return true;
		else return false;
	}
	
	public void add(SocketChannel sc) {
		if(sc != null && usernames.get(sc) == null && count < size) {
			User u = new User();
			usernames.put(sc, u);
			count ++;
		}
	}
	
	@Override
	public String toString() {
		String s = "\n";
		
		for(User u : usernames.values()) {
			s += u.toString() + "\n";
		}
		s += "Enter anything to exit:";
		return s;
	}
}
