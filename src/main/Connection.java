package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

import audio.AudioStreamUDP;

public class Connection {

	private Socket peer;
	private BufferedReader in;
	private PrintWriter out;
	private AudioStreamUDP stream;
	private int localPort;
	private int remotePort;
	private InetAddress address;
	private EventListener eventListener;
	
	public Connection(Socket peer) throws IOException {
		stream = new AudioStreamUDP();
		localPort = stream.getLocalPort();
		//System.out.println("LOCAL_PORT: " + localPort);
		this.peer = peer;
		this.createInput();
		this.createOutput();
		address = InetAddress.getByName(peer.getInetAddress().getHostAddress());
		//Create an EventListener
		
	}
	
	public void Open(){
		
	}
	
	public void Close(){
		
	}
	
	public int getLocalPort(){
		return this.localPort;
	}
	
	public void setRemotePort(int remotePort){
		this.remotePort = remotePort;
	}
	
	public void Write(String msg){
		out.println(msg);
	}
	
	public String Read() throws IOException{
		return in.readLine();
	}
	
	private void createOutput() throws IOException{
		out = new PrintWriter(peer.getOutputStream(),true);
	}
	
	private void createInput() throws IOException{
		in = new BufferedReader
				(new InputStreamReader(peer.getInputStream()));
	}
	
	
}
