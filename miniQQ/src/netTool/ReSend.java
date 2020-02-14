package netTool;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;


public class ReSend implements Runnable {
	private DatagramPacket  packet;
	private DatagramSocket socket;
	public ReSend(DatagramPacket  packet) {
		try {
			this.packet=packet;
			socket=new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public void run() {
		try {
			socket.send(packet);
			System.out.println("重发一条信息");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
