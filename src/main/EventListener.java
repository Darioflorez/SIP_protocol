package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class EventListener extends Thread{

	private Socket peer;
	private IPTelephone ipTelephone;
	private boolean done;
	
	public EventListener(Socket peer, IPTelephone ipTelephone) {
		this.peer = peer;
		this.ipTelephone = ipTelephone;
		done = false;
	}
	
	public void run(){
		try{
			BufferedReader in = new BufferedReader
					(new InputStreamReader(peer.getInputStream()));
			while(!done){
				//printMargin();
				//System.out.println("--State: " + ipTelephone.getStateName()+ "--");
				String peerInput = in.readLine();
				if(peerInput != null){
					//System.out.println("--EventListener Got--"+ peerInput + "--");
					if(peerInput.startsWith("INVITE_")){
						String[] port = peerInput.split("_");
						ipTelephone.receiveInvite(peer,Integer.parseInt(port[1]));
						System.out.println("\n"+"--State: " + ipTelephone.getStateName()+ "--");
						displayOptions();
					}else if(peerInput.startsWith("TROK_")){
						String[] port = peerInput.split("_");
						ipTelephone.receiveTROK(Integer.parseInt(port[1]));
						System.out.println("\n"+"--State: " + ipTelephone.getStateName()+ "--");
						displayOptions();
					}
					else{
						switch (peerInput) {
						case "ACK":
							//Change state to connected
							ipTelephone.receiveAck();
							System.out.println("\n"+"--State: " + ipTelephone.getStateName()+ "--");
							displayOptions();
							break;
						case "BYE":
							ipTelephone.receiveBye();
							done = true;
							System.out.println("\n"+"--State: " + ipTelephone.getStateName()+ "--");
							displayOptions();
							break;
						case "OK":
							ipTelephone.receiveOk();
							done = true;
							System.out.println("\n"+"--State: " + ipTelephone.getStateName()+ "--");
							displayOptions();
							break;
						case "BUSY":
							ipTelephone.receiveBusy();
							System.out.println("\n"+"--State: " + ipTelephone.getStateName()+ "--");
							done = true;
							displayOptions();
							break;
						default:
							break;
						}
					}
				} else {
					//Peer is died
					System.out.println("\n" + "Peer has died!");
					ipTelephone.loseConnection();
					System.out.println("\n"+"--State: " + ipTelephone.getStateName()+ "--");
					displayOptions();
					done = true;
				}
				//printMargin();
			}
		}catch(IOException e){
			try {
				ipTelephone.timeout();
				System.out.println("\n"+"--State: " + ipTelephone.getStateName()+ "--");
				if(peer.isClosed()){
					System.out.println("\n" + "Peer's socket is closed!");
				}
			} catch (IOException io) {
				System.out.println(io);
			}
			System.out.println(e.getMessage());
			done = true;
		}
//			catch (InterruptedException e) {
//			System.out.println(e.getMessage());
//			done = true;
//		}
		System.out.println("\n" + "EventListener Close!");
	}
	
	private void displayOptions(){
		System.out.println();
		System.out.print("Dial@: ");

	}
	
//	private void printMargin(){
//		System.out.println("******************************");
//	}
}
