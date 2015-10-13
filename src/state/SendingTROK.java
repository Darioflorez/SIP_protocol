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
		int localPort = ipTelephone.getLocalPort();
		out.println("TROK_"+Integer.toString(localPort));
		ipTelephone.setTimeout(0);
		
		displayProtocoll("--> " + "TROK_"+Integer.toString(localPort));
		return new WaitingForAck(ipTelephone);
	}
}
