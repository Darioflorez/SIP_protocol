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
	public State receiveInvite(Socket peer) throws IOException{
		//System.out.println("--ReceiveInvite--");
		ipTelephone.setSocket(peer);
		return new SendingTROK(ipTelephone);
	}
	
	@Override
	public State sendInvite(String hostaddress, int port) throws IOException{
		//System.out.println("--SendInvite--");
		Socket peer = new Socket(hostaddress, port);
		ipTelephone.setSocket(peer);
		
		//System.out.println("--Client Port: "+peer.getLocalPort()+"--");
		PrintWriter out = ipTelephone.getWriter();
//		Scanner scanner = new Scanner(System.in);
//		System.out.print("#Sendinvite> ");
//		String invite = scanner.nextLine();
		out.println("INVITE");
		
		//System.out.println("INVITE to: " + peer.getPort());
		
		ipTelephone.setTimeout(0);
		
		EventListener eventListener = new EventListener(peer, ipTelephone);
		eventListener.start();
		
		return new WaitingForTryRingingOk(ipTelephone);
	}
}
