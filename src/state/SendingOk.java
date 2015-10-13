package state;

import java.io.IOException;
import java.io.PrintWriter;

import main.IPTelephone;

public class SendingOk extends State {

	private IPTelephone ipTelephone;
	
	public SendingOk(IPTelephone ipTelephone) {
		this.ipTelephone = ipTelephone;
	}
	
	@Override
	public String getStateName() {
		return "SENDING_OK";
	}
	
	@Override
	public State sendOk(){
		PrintWriter out = ipTelephone.getWriter();
		out.println("OK");
		return new Free(ipTelephone);
	}
}
