package state;

import java.io.IOException;
import java.io.PrintWriter;

import main.IPTelephone;

public class SendingTROK extends State {

	private IPTelephone ipTelephone;
	
	public SendingTROK(IPTelephone ipTelephone) {
		this.ipTelephone = ipTelephone;
	}
	
	@Override
	public String getStateName() {
		return "SENDING_TRY_RINGING_OK";
	}
	
	public State sendTROK() throws IOException{
		PrintWriter out = ipTelephone.getWriter();
		out.println("TROK");
		ipTelephone.setTimeout(4000);
		return new WaitingForAck(ipTelephone);
	}

}
