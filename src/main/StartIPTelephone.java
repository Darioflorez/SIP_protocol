package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;

public class StartIPTelephone {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		IPTelephone ipTelephone = new IPTelephone();
		
		ConnectionListener listener = new ConnectionListener(ipTelephone);
		listener.start();
		
		Thread.sleep(1000/4);
		
		//CREATE SCANNER
		BufferedReader scanner = new BufferedReader
						(new InputStreamReader(System.in));
		
		String userInput;
		boolean done = false;
		displayOptions();
		while(!done){
			if(scanner.ready()){
				userInput = scanner.readLine();
				//System.out.println("--Your input: "+ userInput + "--");
				//Invite request: invite_hostaddress_port | invite_130.182.0.19_37897
				if(userInput.startsWith("invite_")){
					String[] port = userInput.split("_");
					try{
						ipTelephone.sendInvite(port[1], Integer.parseInt(port[2]));
					}catch(ConnectException e){
						System.out.println("Peer unreachable!");
					}
				}
				
				switch (userInput) {
				case "0":
					done = true;
					break;
//				case "1":
//					 ipTelephone.sendInvite();
//					break;
//				case "2":	
//					ipTelephone.receiveInvite();
//					break;
				case "3":
					ipTelephone.sendTROK();
					break;
//				case "4":
//					ipTelephone.receiveTROK();
//					break;
				case "5":
					ipTelephone.sendAck();
					break;
//				case "6":
//					ipTelephone.receiveAck();
//					break;
				case "7":
					ipTelephone.sendBye();
					break;
//				case "8":
//					ipTelephone.receiveBye();
//					break;
				case "9":
					ipTelephone.sendOk();
					break;
//				case "10":
//					ipTelephone.receiveOk();
//					break;
				case "11":
					ipTelephone.timeout();
					break;
				case "12":
					ipTelephone.loseConnection();
					break;
				default:
					break;
				}
				System.out.println("----State: " + ipTelephone.getStateName()+"----");
				displayOptions();
			}else{
				Thread.sleep(1000/2);
			}
		}
		System.out.println("IPTelephone close connection!");
	}
	
	public static void displayOptions(){
		System.out.println();
		System.out.println("Select: ");
		System.out.println("CloseProgram:0");
//		System.out.println("sendinvite:1");
//		System.out.println("receiveInvite:2");
		System.out.println("sendTROK:3");
//		System.out.println("receiveTROK:4");
		System.out.println("sendAck:5");
//		System.out.println("receiveAck:6");
		System.out.println("sendBye:7");
//		System.out.println("receiveBye:8");
		System.out.println("sendOk:9");
//		System.out.println("receiveOk:10");
		System.out.println("timeout:11");
		System.out.println("loseConnection:12");
		System.out.print("Dial@: ");

	}

}
