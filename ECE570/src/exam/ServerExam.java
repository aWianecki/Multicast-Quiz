package exam;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerExam {
	
	
	public static void multicast(String cast, DatagramSocket s, InetAddress group) throws IOException {
		DatagramPacket p = new DatagramPacket(cast.getBytes(), cast.length(), group, 5000);
		s.send(p);
	}
	
	ServerExam(int port) throws Exception {
		doEcho(port);
	}
	
	private static void doEcho(int port) throws Exception {
		Users users = new Users();
		Questions questions = new Questions();
		InetAddress group = InetAddress.getByName("224.0.0.10");
		DatagramSocket s = new DatagramSocket();
		Question curr = questions.getNext();
		
		// Selector
		Selector theSelector = Selector.open(); //SelectorProvider.provider().openSelector();
		
		// Server socket channel
		ServerSocketChannel ssc = ServerSocketChannel.open();
		ssc.configureBlocking(false); // make non-blocking
		
		// bind to local address
		InetSocketAddress isa = new InetSocketAddress(port);
		ssc.socket().bind(isa);
		
		System.out.println("Starting on "+port);
		
		// register the server socket with the selector
		SelectionKey acceptKey = ssc.register(theSelector, SelectionKey.OP_ACCEPT);
				boolean done = false;
		
		// while loop
		while ((theSelector.select())>0) {
			
			System.out.println("Select returned");
			
			// get the ready keys
			Set<SelectionKey> readyK = theSelector.selectedKeys();
			// get an iterator on the set of keys
			Iterator<SelectionKey> i = readyK.iterator();
			
			// process the ready keys
			while (i.hasNext()) {
				SelectionKey sk = (SelectionKey)i.next(); // get the next key
				i.remove(); // remove from the ready set
				
				// check if it is the serversocket
				if (sk.isAcceptable()) {
					System.out.println("Process accept");
					
					// Get the ServerSocketChannel
					ServerSocketChannel nssc = (ServerSocketChannel)sk.channel();
					
					// accept the connection
					SocketChannel sc = nssc.accept();
					
					// Configure the SocketChannel as non blocking
					sc.configureBlocking(false);
					
					// register the SocketChannel with the selector
					sc.register(theSelector, SelectionKey.OP_READ);

					System.out.println("acceptable sc " + sc.toString());
					
					if(!users.isFull()) {
						users.add(sc);
						if(users.isFull()) {
							multicast(curr.toString(), s, group);
						}
					}
					
				} else if (sk.isReadable()) { // if a readable key
					System.out.println("Process read");
					
					// get the SocketChannel
					SocketChannel sc = (SocketChannel)sk.channel();
					
					if(!users.exists(sc)) {
						
						String resp = "Server full";
						byte[] bytes = resp.getBytes("UTF-8");
						ByteBuffer dst = ByteBuffer.allocate(999);
						dst.put(bytes, 0, resp.length());
						dst.flip();
						sc.write(dst);
						sc.close();
						sk.cancel();
					}
					
					// Create a ByteBuffer for the data
					ByteBuffer dst = ByteBuffer.allocate(999);
					
					// read the data from the channel
					sc.read(dst);
					dst.flip();
					String response = new String(dst.array());
					String[] length = response.split(":");
					response = length[1].substring(0, Integer.parseInt(length[0]));
					System.out.println("response: " + response + "\nlength: " + response.length());
					
					boolean allAnswered = users.answer(sc, response, curr);
					if(allAnswered) {
						curr = questions.getNext();
						if(curr == null) {
							multicast(users.toString(), s, group);
							done = true;
						}
						else {
							multicast(curr.toString(), s, group);
						}
						
					}
					
					if(done) {
						sc.close();
						sk.cancel();
					}
				}
			}
		}
		
		System.out.println("Done");
		ssc.close();
		acceptKey.cancel();

	}

	public static void main(String[] args) {
		
		int port = 5000;
		
		try {
			new ServerExam(port);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}