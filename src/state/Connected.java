package state;

import java.io.PrintWriter;

import main.IPTelephone;

public class Connected extends State {

	private IPTelephone ipTelephone;
	
	public Connected(IPTelephone ipTelephone) {
		this.ipTelephone = ipTelephone;
		//ipTelephone.startStreaming();
		System.out.println("RemotePort: " + ipTelephone.getRemotePort());
	}
	
	@Override
	public String getStateName() {
		return "CONNECTED";
	}
	
	public State receiveBye() {
		return new SendingOk(ipTelephone);
	}
	
	public State sendBye() {
		//System.out.println("--sendBye--");
		PrintWriter out = ipTelephone.getWriter();
		out.println("BYE");
		return new WaitingForOk(ipTelephone);
	}
	
}
