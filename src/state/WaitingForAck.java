package state;

import java.io.IOException;

import main.IPTelephone;

public class WaitingForAck extends State {

	private IPTelephone ipTelephone;
	
	public WaitingForAck(IPTelephone ipTelephone) {
		this.ipTelephone = ipTelephone;
	}
	
	@Override
	public String getStateName() {
		return "WAITING_FOR_ACK";
	}
	
	public State receiveAck() throws IOException {
		ipTelephone.setTimeout(0);
		return new Connected(ipTelephone);
	}	
}
