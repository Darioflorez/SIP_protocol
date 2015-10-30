package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketException;
import java.util.regex.Pattern;
import java.util.Timer;
import java.util.TimerTask;

/*Authors: Dario Florez
 * Johan Ejdemark
 * */
public class StartIPTelephone {

	private static Timer timer;
	
	public static void main(String[] args) throws IOException, InterruptedException {
		
		if(args.length < 1){
			System.out.println("Missing Port nummer!");
			return;
		}
		
		System.out.println("\n----Welcome to IPTelephone----");
		
		int sip_port = Integer.parseInt(args[0]);
		IPTelephone ipTelephone = new IPTelephone();
		
		ConnectionListener listener = new ConnectionListener(ipTelephone, sip_port);
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
				String[] address_port = userInput.split("_");
				String hostaddress = address_port[1];
				int port = Integer.parseInt(address_port[2]);
				if(isValidIP(hostaddress)){
					try{
						ipTelephone.sendInvite(hostaddress, port);
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
			if(!ipTelephone.getStateName().equals("CONNECTED")) {
				System.out.println("No answer on the other end!");

				try {
					ipTelephone.timeout();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			timer.cancel();
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
