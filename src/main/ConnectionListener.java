package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener extends Thread {
	
	private IPTelephone ipTelephone;
	private int sipPort;
	private boolean done;
	private ServerSocket listeningSocket;
	
	public ConnectionListener(IPTelephone ipThelephone, int sipPort) {
		this.ipTelephone = ipThelephone;
		this.sipPort = sipPort;
		done = false;
	}
	
	public void run(){
<<<<<<< HEAD

=======
>>>>>>> 22e04e69f26ab08686c9752201a17c94ec49de89
		try
		{
			listeningSocket = new ServerSocket(sipPort);
			
			while(!done){
<<<<<<< HEAD
				Socket peer = listeningSocket.accept();

				if(ipTelephone.getStateName().equals("FREE")) {
=======
				Socket peer = listeningSocket.accept();	
				
				if(ipTelephone.getStateName().equals("FREE")){	
>>>>>>> 22e04e69f26ab08686c9752201a17c94ec49de89
					EventListener phoneServer = new EventListener(peer, ipTelephone);
					phoneServer.start();
				}
				else {
					int port = peer.getPort();
<<<<<<< HEAD
					ipTelephone.receiveInvite(peer,port);
				}


=======
					ipTelephone.receiveInvite(peer, port);
				}
>>>>>>> 22e04e69f26ab08686c9752201a17c94ec49de89
			}
		} catch (IOException e) {
			System.out.println("Connection listener closed!");
		}
	}
	
	public void close(){
		done = true;
		if(listeningSocket != null){
			try {
				listeningSocket.close();
			} catch (IOException e) {
				System.out.println("\nConnection listener close connection!");
			}
		}
	}

}
