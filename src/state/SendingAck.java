package state;

import java.io.IOException;
import java.io.PrintWriter;

import main.IPTelephone;

public class SendingAck extends State {

	private IPTelephone ipTelephone;
	
	public SendingAck(IPTelephone ipTelephone) {
		this.ipTelephone = ipTelephone;
	}
	
	@Override
	public String getStateName() {
		return "SENDING_ACK";
	}
	
	public State sendAck() throws IOException {
		//System.out.println("--sendAck--");
		PrintWriter out = ipTelephone.getWriter();
		out.println("ACK");
		ipTelephone.setTimeout(0);
		return new Connected(ipTelephone);
	}
}
