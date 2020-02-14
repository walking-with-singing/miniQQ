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
			System.out.println("�ط�һ����Ϣ");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
