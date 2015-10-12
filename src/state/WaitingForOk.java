package state;

import main.IPTelephone;

public class WaitingForOk extends State {

	private IPTelephone ipTelephone;
	
	public WaitingForOk(IPTelephone ipTelephone) {
		this.ipTelephone = ipTelephone;
	}
	
	@Override
	public String getStateName() {
		return "WAITING_FOR_OK";
	}
	
	public State receiveOk() {
		return new Free(ipTelephone);
	}
}
