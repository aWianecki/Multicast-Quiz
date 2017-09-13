package exam;

public class Questions {
	int size;
	Question questions[];
	int currNum;
	
	public Questions() {
		size = 5;
		currNum = -1;
		questions = new Question[size+1];
		for(int i = 0; i <= size; i++) {
			questions[i] = new Question(i);
		}
	}
	
	public Question getNext() {
		if(currNum >= size) {
			return null;
		}
		currNum ++;
		return questions[currNum];
	}
}
