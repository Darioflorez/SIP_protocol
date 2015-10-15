package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.regex.Pattern;
import java.util.Timer;
import java.util.TimerTask;

public class StartIPTelephone {

	public static final int SIP_PORT = 5060;
	private static Timer timer;

	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		IPTelephone ipTelephone = new IPTelephone();
		
		ConnectionListener listener = new ConnectionListener(ipTelephone, SIP_PORT);
		listener.start();
		
		Thread.sleep(1000/8);
		
		//CREATE SCANNER
		BufferedReader scanner = new BufferedReader
						(new InputStreamReader(System.in));
		
		//Client --->Ring : send invite
		//Server <--- Ringing: send TROK
		String userInput;
		boolean done = false;
		System.out.println("\n----Welcome to IPTelephone----");
		System.out.println("Invite a peer: invite_ipAddress");
		displayOptions();
		while(!done){
			userInput = scanner.readLine();
			
			if(userInput.startsWith("invite_")){
				String[] port = userInput.split("_");
				String hostaddress = port[1];
				if(isValidIP(hostaddress)){
					try{
						ipTelephone.sendInvite(hostaddress, SIP_PORT);
						timer = new Timer();
						timer.schedule(new StopCalling(ipTelephone), 8000);
					}catch(SocketException e){
						System.out.println("Peer unreachable!");
					}
				}
				else {
					System.out.println("IP address is not valid!");
				}
			}else {
				switch (userInput) {
				case "0":
					ipTelephone.sendBye();
					break;
				case "1":
					ipTelephone.sendTROK();
					break;
				case "x":
					ipTelephone.closeConnection();
					listener.close();
					done = true;
					break;
				default:
					System.out.println("Wrong input.");
					displayOptions();
					break;
				}
			}
			//System.out.println("----State: " + ipTelephone.getStateName()+"----");
			displayOptions();
		}
		System.out.println("IPTelephone close connection!");
	}

	static class StopCalling extends TimerTask {
		IPTelephone ipTelephone;
		StopCalling(IPTelephone ipTelephone) {
			this.ipTelephone=ipTelephone;
		}
		public void run() {
			System.out.println("No answer on the other end!");
			timer.cancel();
			if(!ipTelephone.getStateName().equals("CONNECTED")) {
				try {
					ipTelephone.timeout();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	public static boolean isValidIP(String ip){
		//System.out.println(ip.length());
		String[] ipaddress = ip.split(Pattern.quote("."));
		if(ipaddress.length != 4){
			return false;
		}
		for(String s: ipaddress){
			try{
				Integer.parseInt(s);
			}catch(NumberFormatException n){
				return false;
			}
		}
		return true;
	}
	
	public static void displayOptions(){
		System.out.println();
//		System.out.println("Select: ");
//		System.out.println("CloseProgram:0");
////		System.out.println("sendinvite:1");
////		System.out.println("receiveInvite:2");
//		System.out.println("sendTROK:3");
////		System.out.println("receiveTROK:4");
//		System.out.println("sendAck:5");
////		System.out.println("receiveAck:6");
//		System.out.println("sendBye:7");
////		System.out.println("receiveBye:8");
//		System.out.println("sendOk:9");
////		System.out.println("receiveOk:10");
//		System.out.println("timeout:11");
//		System.out.println("loseConnection:12");
		System.out.println("Press x to close app");
		System.out.print("Dial@: ");

	}

}
