package state;

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
	
	public State sendAck(){
		//System.out.println("--sendAck--");
		PrintWriter out = ipTelephone.getWriter();
		out.println("ACK");
		return new Connected(ipTelephone);
	}
}
