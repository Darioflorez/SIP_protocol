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
		
		System.out.println("\n----Welcome to IPTelephone----");
		
		IPTelephone ipTelephone = new IPTelephone();
		
		ConnectionListener listener = new ConnectionListener(ipTelephone, SIP_PORT);
		listener.start();
		
		Thread.sleep(1000/8);
		
		//CREATE SCANNER
		BufferedReader scanner = new BufferedReader
						(new InputStreamReader(System.in));
		
		String userInput;
		boolean done = false;
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
					break;
				}
			}
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
}
