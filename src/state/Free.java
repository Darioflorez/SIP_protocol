package state;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import main.EventListener;
import main.IPTelephone;

public class Free extends State {
	
	private IPTelephone ipTelephone;
	
	public Free(IPTelephone ipTelephone) {
		this.ipTelephone = ipTelephone;
	}
	
	@Override
	public String getStateName() {
		return "FREE";
	}
	
	@Override
	public State receiveInvite(Socket peer, int port) throws IOException{
		displayAnswer();
		ipTelephone.init(peer);
		ipTelephone.setRemotePort(port);
		//Send TROK direkt
		displayProtocoll("<-- INVITE");
		return new SendingTROK(ipTelephone);
	}
	
	@Override
	public State sendInvite(String hostaddress, int port) throws IOException{
		Socket peer = new Socket(hostaddress, port);
		int localPort = ipTelephone.getLocalPort();
		ipTelephone.init(peer);
		
		PrintWriter out = ipTelephone.getWriter();
		String invite = "INVITE_"+Integer.toString(localPort);
		out.println(invite);
		
		ipTelephone.setTimeout(0);
		
		EventListener eventListener = new EventListener(peer, ipTelephone);
		eventListener.start();
		
		displayProtocoll("--> " + invite);
		displayHangUp();
		return new WaitingForTryRingingOk(ipTelephone);
	}
	
	public void displayAnswer(){
		System.out.println("\n" + "--Ringing.....");
		System.out.println("Press 1 to answer.");
		System.out.println("Press 0 to hang up");
		System.out.println("Dial@: ");
	}
	public void displayHangUp(){
		System.out.println("\n" + "Ringing.....");
		System.out.println("Press 0 to hang up");
		System.out.println("Dial@: ");
	}
}
