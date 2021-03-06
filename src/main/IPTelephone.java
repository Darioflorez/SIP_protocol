package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;

import audio.AudioStreamUDP;
import state.Free;
import state.State;

public class IPTelephone {
	
	private State currentState;
	private Socket peer;
	private BufferedReader in;
	private PrintWriter out;
	private AudioStreamUDP stream;
	private int localPort;
	private int remotePort;
	private InetAddress address;
	private EventListener eventListener;
	
	public IPTelephone() throws IOException {
		currentState = new Free(this);
	}
	
	//Allocate all the resources
	public void init(Socket peer) throws IOException{
		stream = new AudioStreamUDP();
		localPort = stream.getLocalPort();
		//System.out.println("LOCAL_PORT: " + localPort);
		this.peer = peer;
		in = new BufferedReader
				(new InputStreamReader(peer.getInputStream()));
		out = new PrintWriter(peer.getOutputStream(),true);
		address = InetAddress.getByName(peer.getInetAddress().getHostAddress());
	}
	
	//Get and set resources
	public void setRemotePort(int remotePort){
		this.remotePort = remotePort;
	}
	public int getRemotePort(){
		return remotePort;
	}
	public int getLocalPort(){
		return localPort;
	}
	public BufferedReader getReader(){
		return in;
	}
	public PrintWriter getWriter(){
		return out;
	}
	public void initEventListener(){
		eventListener = new EventListener(peer, this);
		eventListener.start();
	}
	
	//Define all the actions that can lead to another state
	public String getStateName(){
		return currentState.getStateName();
	}
	//---------------------ReceiveActions--------------------------
	public synchronized void receiveInvite(Socket peer, int port) throws IOException{
		currentState = currentState.receiveInvite(peer, port);
	}
	public synchronized void receiveBye() {
		currentState = currentState.receiveBye();
	}
	public synchronized void receiveOk() {
		currentState = currentState.receiveOk();
	}
	public synchronized void receiveTROK(int remotePort) {
		currentState = currentState.receiveTROK(remotePort);
	}
	public synchronized void receiveAck() throws IOException {
		currentState = currentState.receiveAck();
	}
	public synchronized void receiveBusy(){
		System.out.println("--receiveBusy--");
		currentState = new Free(this);
	}
	
	//---------------------SendActions----------------------------
	public synchronized void sendInvite(String hostaddress, int port) throws IOException{
		currentState = currentState.sendInvite(hostaddress, port);
	}
	public synchronized void sendTROK() throws IOException{
		currentState = currentState.sendTROK();
	}
	public synchronized void sendAck() {
		currentState = currentState.sendAck();
	}
	public synchronized void sendOk(){
		currentState = currentState.sendOk();
	}
	public synchronized void sendBye() {
		currentState = currentState.sendBye();
	}
	
	//-------------------Connection related functions-------------
	
	public synchronized void timeout() throws IOException{
		currentState = new Free(this);
		closeConnection();
	}
	
	public synchronized void loseConnection() throws IOException{
		currentState = new Free(this);
		closeConnection();
	}
	
	public void closeConnection() throws IOException{
		if(eventListener != null){
			eventListener.close();
		}
		if(peer != null){
			peer.close();
			out.close();
			in.close();
			//Close stream if it is open
			if(stream != null){
				stream.close();
			}
		}
	}
	
	public void setTimeout(int timeout) throws SocketException{
		peer.setSoTimeout(timeout);
	}

	public void startStreaming() throws IOException{
		stream.connectTo(address, remotePort);
		stream.startStreaming();
	}
}
