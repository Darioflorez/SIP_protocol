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
		try
		{
			listeningSocket = new ServerSocket(sipPort);
			
			while(!done){
				Socket peer = listeningSocket.accept();			
				if(ipTelephone.getStateName().equals("FREE")){	
					
					EventListener phoneServer = new EventListener(peer, ipTelephone);
					phoneServer.start();
				}
				else {
					int port = peer.getPort();
					ipTelephone.receiveInvite(peer,port);
				}
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
