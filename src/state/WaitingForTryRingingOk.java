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
	
	public State receiveTROK(int remotePort) {
		//System.out.println("--receiveTROK--");
		ipTelephone.setRemotePort(remotePort);
		return new SendingAck(ipTelephone);
	}

}
