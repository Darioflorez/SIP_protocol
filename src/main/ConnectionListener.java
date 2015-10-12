package main;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class ConnectionListener extends Thread {
	
	private IPTelephone ipTelephone;
	
	public ConnectionListener(IPTelephone ipThelephone) {
		this.ipTelephone = ipThelephone;
	}
	
	public void run(){
		Random rand = new Random();
		int port = rand.nextInt((49151 - 1024) + 1) + 1024;
		
		try(ServerSocket listeningSocket = new ServerSocket(port))
		{
			System.out.println("PORT: " + port);
			while(true){
				Socket peer = listeningSocket.accept();
				//printMargin();
				//System.out.println("\n"+"--State: "+ ipTelephone.getStateName()+"\n");
				//System.out.println("--Client Port: " + peer.getPort()+ "--");
				//System.out.println("--Server Port : " + peer.getLocalPort()+ "--");
				//printMargin();
				EventListener phoneServer = new EventListener(peer, ipTelephone);
				phoneServer.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
//	private void printMargin(){
//		System.out.println("++++++++++++NEW CLIENT++++++++++++");
//	}

}
