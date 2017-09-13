package exam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class TCPthread implements Runnable {
	
	String ip;
	int port;
	
	public TCPthread() {
		ip = "localhost";
		port = 5000;
	}
	
	public TCPthread(String ip, int port) {
		this.ip = ip;
		this.port = port;
	}
	
	@Override
	public void run() {
		try {
			
			Socket soc = new Socket(ip,port);
			
			BufferedReader sin = new BufferedReader(new InputStreamReader(soc.getInputStream()));
			PrintWriter sout = new PrintWriter(soc.getOutputStream());
			
			BufferedReader bin = new BufferedReader(new InputStreamReader(System.in));
			
			while (true) {
				String request = bin.readLine();
				if (request==null) break;
				
				sout.println(request.length() + ":" + request);
				sout.flush();
				
//				String response = sin.readLine();
//				System.out.println("Received:"+response);
				
			}
			
			bin.close();
			sin.close();
			sout.close();
			soc.close();
			
			
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
