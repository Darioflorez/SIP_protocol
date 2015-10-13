package state;

import java.io.IOException;
import java.io.PrintWriter;

import main.IPTelephone;

public class Connected extends State {

	private IPTelephone ipTelephone;
	
	public Connected(IPTelephone ipTelephone) {
		this.ipTelephone = ipTelephone;
		try {
			ipTelephone.startStreaming();
		} catch (IOException e) {
			System.out.println("Problem when starting audio streaming!");
			e.printStackTrace();
		}
		System.out.println("RemotePort: " + ipTelephone.getRemotePort());
	}
	
	@Override
	public String getStateName() {
		return "CONNECTED";
	}
	@Override
	public State receiveBye() {
		//SendOK automatic
		State sendingOk = new SendingOk(ipTelephone);
		return sendingOk.sendOk();
	}
	@Override
	public State sendBye() {
		//System.out.println("--sendBye--");
		PrintWriter out = ipTelephone.getWriter();
		out.println("BYE");
		return new WaitingForOk(ipTelephone);
	}
	
}
