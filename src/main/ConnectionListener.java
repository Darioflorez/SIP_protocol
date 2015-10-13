package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionListener extends Thread {
	
	private IPTelephone ipTelephone;
	private int sipPort;
	private boolean done;
	
	public ConnectionListener(IPTelephone ipThelephone, int sipPort) {
		this.ipTelephone = ipThelephone;
		this.sipPort = sipPort;
		done = false;
	}
	
	public void run(){
//		Random rand = new Random();
//		int port = rand.nextInt((49151 - 1024) + 1) + 1024;
		
		//System.out.println("PORT: " + port);
		try(ServerSocket listeningSocket = new ServerSocket(sipPort))
		{
			while(!done){
				Socket peer = listeningSocket.accept();		
			//printMargin();
			//System.out.println("\n"+"--State: "+ ipTelephone.getStateName()+"\n");
			//System.out.println("--Client Port: " + peer.getPort()+ "--");
			//System.out.println("--Server Port : " + peer.getLocalPort()+ "--");
			//printMargin();
				EventListener phoneServer = new EventListener(peer, ipTelephone);
				phoneServer.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("ConnectionListening bye!");
		}
	}
	
//	private void printMargin(){
//		System.out.println("++++++++++++NEW CLIENT++++++++++++");
//	}

}
