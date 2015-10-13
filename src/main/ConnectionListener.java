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
//		Random rand = new Random();
//		int port = rand.nextInt((49151 - 1024) + 1) + 1024;
		
		//System.out.println("PORT: " + port);
		try
		{
			listeningSocket = new ServerSocket(sipPort);
			while(!done){
				Socket peer = listeningSocket.accept();		
				EventListener phoneServer = new EventListener(peer, ipTelephone);
				phoneServer.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Error when listening for connections!");
		}
	}
	
	public void close(){
		done = true;
		if(listeningSocket != null){
			try {
				listeningSocket.close();
			} catch (IOException e) {
				System.out.println("Error when closing ServerSocket!");
			}
		}
	}
	
//	private void printMargin(){
//		System.out.println("++++++++++++NEW CLIENT++++++++++++");
//	}

}
