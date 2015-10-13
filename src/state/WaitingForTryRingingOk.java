package state;

import main.IPTelephone;

public class WaitingForTryRingingOk extends State {
	
	private IPTelephone ipTelephone;
	
	public WaitingForTryRingingOk(IPTelephone ipTelephone) {
		this.ipTelephone = ipTelephone;
	}
	@Override
	public String getStateName() {
		return "WAITING_FOR_TRY_RINGING_OK";
	}
	@Override
	public State receiveTROK(int remotePort) {
		//System.out.println("--receiveTROK--");
		ipTelephone.setRemotePort(remotePort);
		State sendingAck = new SendingAck(ipTelephone);
		displayProtocoll("<-- TROK_"+Integer.toString(remotePort));
		return sendingAck.sendAck();
	}
}
