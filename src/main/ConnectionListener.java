package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener extends Thread {
	
	private IPTelephone ipTelephone;
	private int sip_port;
	private boolean done;
	private ServerSocket listeningSocket;
	
	public ConnectionListener(IPTelephone ipThelephone, int sip_port) {
		this.ipTelephone = ipThelephone;
		this.sip_port = sip_port;
		done = false;
	}
	
	public void run(){
		try
		{
			listeningSocket = new ServerSocket(sip_port);
			
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
			System.out.println(e.getMessage());
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
