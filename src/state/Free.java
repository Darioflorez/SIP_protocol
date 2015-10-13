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
		//if(stream != null) stream.close();
	}
	
	@Override
	public State receiveInvite(Socket peer, int port) throws IOException{
		//System.out.println("--ReceiveInvite--");
		ipTelephone.init(peer);
		ipTelephone.setRemotePort(port);
		//Send TROK direkt
		return new SendingTROK(ipTelephone);
	}
	
	@Override
	public State sendInvite(String hostaddress, int port) throws IOException{
		//System.out.println("--SendInvite--");
		Socket peer = new Socket(hostaddress, port);
		int localPort = ipTelephone.getLocalPort();
		ipTelephone.init(peer);
		
		//System.out.println("--Client Port: "+peer.getLocalPort()+"--");
		PrintWriter out = ipTelephone.getWriter();
//		Scanner scanner = new Scanner(System.in);
//		System.out.print("#Sendinvite> ");
//		String invite = scanner.nextLine();
		out.println("INVITE_"+Integer.toString(localPort));
		
		//System.out.println("INVITE to: " + peer.getPort());
		
		ipTelephone.setTimeout(0);
		
		EventListener eventListener = new EventListener(peer, ipTelephone);
		eventListener.start();
		
		return new WaitingForTryRingingOk(ipTelephone);
	}
}
