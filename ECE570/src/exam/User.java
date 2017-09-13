package exam;

public class User {
	
	private String username;
	private String answer;
	private int total;
	
	public User() {
		username = "";
		answer = "";
		total = 0;
	}
	
	public int getTotal() {
		return total;
	}
	
	private void setName() {
		username = answer;
	}
	
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public void updateTotal(Question q) {
		if(q != null) {
			if (q.getNum() == 0) {
				setName();
			}
			else {
				total += q.score(answer);
			}
		}
		answer = "";
	}
	
	@Override
	public String toString() {
		String s = username + ": " + total;
		System.out.println(s);
		return s;
	}
}
