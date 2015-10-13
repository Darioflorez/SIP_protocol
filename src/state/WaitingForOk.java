package state;

import java.io.IOException;

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
//		try {
//			ipTelephone.closeConnection();
//		} catch (IOException e) {
//			System.out.println("Problem when closing connection: " + e.getMessage());
//		}
		return new Free(ipTelephone);
	}
}
