package exam;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastThread implements Runnable {
	
	int port;
	String ip;
	
	public MulticastThread() {
		port = 5000;
		ip = "224.0.0.10";
	}
	
	public MulticastThread(String ip, int port) {
		this.port = port;
		this.ip = ip;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
		try {
			MulticastSocket ms = new MulticastSocket(port);
			InetAddress group = InetAddress.getByName(ip);
			
			ms.joinGroup(group);
			
			boolean done = false;
			
			while (done==false) {
				byte[] buf = new byte[1000];
				DatagramPacket p = new DatagramPacket(buf, buf.length);
				ms.receive(p);
				String recv = new String(p.getData());
				if (recv.compareToIgnoreCase("done")==0) done=true;
				else {
					System.out.println(recv);
				}
			}
			ms.leaveGroup(group);
			ms.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}