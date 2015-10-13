package state;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public abstract class State {
	
	public abstract String getStateName();
	
	//Define all the actions that can lead to another state
	
	//---------------------ReceiveActions------------------------------------
	public State receiveInvite(Socket peer, int remotePort) throws IOException{
		//System.out.println("Abstract class: " + peer.getPort());
		PrintWriter out = new PrintWriter(peer.getOutputStream(), true);
		out.println("BUSY");
		return this;
	}
	
	public State receiveBye() {
		return this;
	}
	
	public State receiveOk() {
		return this;
	}
	
	public State receiveTROK(int remotePort) {
		return this;
	}
	
	public State receiveAck() throws IOException {
		return this;
	}
	
	//-----------------------------------------------------------------------
	//---------------------SendActions---------------------------------------
	public State sendInvite(String hostaddress, int port)throws IOException {
		System.out.println("You can not start more than one session at once!");
		return this;
	}
	
	public State sendTROK() throws IOException {
		System.out.println("You have to start a session!");
		return this;
	}
	
	public State sendAck() {
		System.out.println("You have to start a session!");
		return this;
	}
	
	public State sendOk(){
		System.out.println("You have to start a session!");
		return this;
	}
	
	public State sendBye() {
		System.out.println("You have to start a session!");
		return this;
	}
	//-----------------------------------------------------------------------
	
//	public State timeout(){
//		return new Free();
//	}
//	
//	public State loseConnection(IPTelephone ipTelephone){
//		return new Free(ipTelephone);
//	}
}
