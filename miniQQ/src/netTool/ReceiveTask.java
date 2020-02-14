package netTool;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import javax.swing.JTextArea;

import com.alibaba.fastjson.JSONObject;

import protocol.MessageType;

public class ReceiveTask implements Callable<JSONObject> {
	private Map<Integer,ScheduledFuture<?>> sendWindow;
	private Map<Integer,JSONObject> receiveWindow;
	private DatagramSocket socket;
	private JTextArea display;
	private Chat chat;
	private Queue<Integer> acknowledgeQueue;
	private int n=8;
	private int nextReq=0;
	private int sendWindowHead=0;
	private FileOutputStream fout = null;
	public ReceiveTask(int port,Map<Integer,ScheduledFuture<?>> window,Chat chat,Queue<Integer> acknowledgeQueue) {
		try {
			socket=new DatagramSocket(port);
			this.acknowledgeQueue=acknowledgeQueue;
			this.sendWindow=window;
			receiveWindow=new HashMap<>();
			for(int i=0;i<n/2;i++)
			{
				receiveWindow.put(i, null);
			}
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public JSONObject call() throws Exception {
		while(true)
		{
			DatagramPacket  packet=new DatagramPacket(new byte[20480],20480);
			socket.receive(packet);
			JSONObject message=JSONObject.parseObject(new String(packet.getData()));
			if(message.getString("type").equals("ACK"))
			{
				handle(message);
				continue;
			}
			acknowledgeQueue.offer((int)message.get("seq"));
			int seq=message.getIntValue("seq");
			if(seq==nextReq)
			{
				handle(message);
				while(receiveWindow.get(nextReq)!=null)
				{
					handle(receiveWindow.get(nextReq));
				}
			}
			else
			{
				if(receiveWindow.containsKey(seq))
				{
					receiveWindow.put(seq, message);
					System.out.println("缓存一条信息，序列号："+message.get("seq"));
				}
			}
		}
	}
	public void handle(JSONObject message)
	{
		int ack=message.getIntValue("ack");
		if(ack!=-1)
		{
			ScheduledFuture<?> sf=sendWindow.getOrDefault(ack, null);
			if(sf!=null&&sf.getClass()!=MyFuture.class)
			{	
				sf.cancel(true);
				System.out.println("消息"+ack+"被确认收到确认");
				if(ack==sendWindowHead)
				{
					while(sendWindow.get(sendWindowHead).getClass()!=MyFuture.class&&sendWindow.get(sendWindowHead).isCancelled())
					{
						sendWindow.remove(sendWindowHead);
						sendWindow.put((sendWindowHead+n/2)%n,new MyFuture());
						sendWindowHead=(sendWindowHead+1)%n;
					}
				}
				System.out.println("发送窗口序号范围"+sendWindow.keySet());
			}
		}
		if(message.getString("type").equals("ACK"))
			return;
		System.out.println("处理总序号："+message.getIntValue("count")+"\t类型："+message.get("type"));
		receiveWindow.remove(nextReq);
		receiveWindow.put((nextReq+n/2)%n, null);
		nextReq=(nextReq+1)%n;
		switch(message.getString("type"))
		{
		case "FILE_BEGIN":
			String name=message.getString("data");
			try {
				fout=new FileOutputStream(name);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "FILE":
			try {
				fout.write(message.getBytes("data"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case "FILE_END":
			if(fout!=null)
				try {
					fout.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			break;
		case "SENTENCE":
			if(display!=null)
				display.setText(display.getText()+(String)message.get("data"));
			else
				System.out.println(message.getString("data"));
			break;
		case "CLOSE":
			break;
		case "REQUESTCONNECTION":
			break;
		case "IP_AND_PORT":
			break;
		default:
			System.out.println("未匹配成功");
			break;
		}
	}
	public JTextArea getDisplay() {
		return display;
	}
	public void setDisplay(JTextArea display) {
		this.display = display;
	}
	
}
