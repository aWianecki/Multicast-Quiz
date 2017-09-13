package exam;

public class ClientExam {
	public static void main(String[] args) {
		MulticastThread mt = new MulticastThread();
		TCPthread tcpt = new TCPthread();
	
		Thread t1 = new Thread(mt);
		Thread t2 = new Thread(tcpt);
		t1.start();
		t2.start();
	}
}
