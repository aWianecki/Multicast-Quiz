package exam;

public class Question {
	int num;
	String question;
	String answer;
	String choices[];
	int choiceNum;
	int correctScore;
	int incorrectScore;
	
	public Question() {
		num = -1;
		question = "";
		answer = "";
		choices = null;
		choiceNum = 0;
		correctScore = 0;
		incorrectScore = 0;
	}
	
	public Question(int num) {
		correctScore = 10;
		incorrectScore = -5;
		this.num = num;
		choiceNum = 4;
		choices = new String[choiceNum];
		
		switch(num) {
		case 0 : question = "Please enter a username";
				 correctScore = 0;
				 incorrectScore = 0;
				 choiceNum = 0;
				 choices = null;
				 answer = "";
				 break;
				 
		case 1 : question = "You have approached The Keeper of the Bridge of Death.\nYou must answer his questions 5 in order to pass.\n\nWhat... is your name?";
				 choices[0] = "A: Arthur, King of the Britons";
				 choices[1] = "B: The Knights Who Say Ni";
				 choices[2] = "C: Brian";
				 choices[3] = "D: Biggus Dickus";
				 answer = "A";
				 break;
				  
		case 2 : question = "What... is your quest?";
		  		 choices[0] = "A: Snap snap, grin grin, wink wink, nudge nudge, say no more";
		  		 choices[1] = "B: To cut down trees and put on women's clothing";
		  		 choices[2] = "C: To seek the Holy Grail";
		  		 choices[3] = "D: To have an argument";
		  		 answer = "C";
		  		 break;
		  		  
		case 3 : question = "What... is your favorite color?";
		  		 choices[0] = "A: Blue, no Green";
		  		 choices[1] = "B: Blue";
		  		 choices[2] = "C: Green";
		  		 choices[3] = "D: I don't know";
		  		 answer = "B";
		  		 break;
		  		  
		case 4 : question = "What... is the capital of Assyria?";
		  		 choices[0] = "A: I don't know";
		  		 choices[1] = "B: Tudiya";
		  		 choices[2] = "C: Mitanni";
		  		 choices[3] = "D: Nineveh";
		  		 answer = "D";
		  		 break;
		  		  
		default : question = "What... is the airspeed velocity of an unladen swallow?";
		  		  choices[0] = "A: 20mph";
		  		  choices[1] = "B: 15mph";
		  		  choices[2] = "C: 13mph";
		  		  choices[3] = "D: African or European?";
		  		  answer = "D";
		  		  break;
		}	
	}
	
	public int getNum() {
		return num;
	}
	
	public int score(String answer) {
		if(this.answer.equalsIgnoreCase(answer)) {
			return correctScore;
		}
		else return incorrectScore;
	}
	
	@Override
	public String toString() {
		String q = "\n" + question + "\n";
		for(int i = 0; i < choiceNum; i++) {
			q+= choices[i];
			q+="\n";
		}
		q+="Enter your response:";
		return q;
	}
}
