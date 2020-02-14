package netTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableScheduledFuture;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.alibaba.fastjson.JSONObject;

import bean.User;
import protocol.MessageType;

public class SendTask implements Callable<JSONObject> {
	private InetAddress adderss;
	private int port=8089;
	private DatagramSocket socket;
	private int receiver;
	private int n=8;
	private Map<Integer,ScheduledFuture<?>> window;
	private Queue<JSONObject> sendQueue;
	private Queue<Integer> acknowledgeQueue;
	ScheduledExecutorService scheduledPool=Executors.newScheduledThreadPool(n-1);
	

	public SendTask(int receiver,InetAddress adderss,int port,Map<Integer,ScheduledFuture<?>> window,Queue<JSONObject> sendQueue,Queue<Integer> acknowledgeQueue) {
		this.sendQueue=sendQueue;
		this.acknowledgeQueue=acknowledgeQueue;
		this.window=window;
		for(int i=0;i<n/2;i++)
		{
			window.put(i, new MyFuture());
		}
		this.receiver=receiver;
		try {
			this.adderss=adderss;
			this.port=port;
			socket=new DatagramSocket();
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public JSONObject call() throws Exception {
		while(true)
		{
			while(acknowledgeQueue.size()>0)
			{
				System.out.println("发送一个带确定号的信息");
				int ack=acknowledgeQueue.poll();
				JSONObject message=null;
				if(sendQueue.size()>0)
				{
					message=sendQueue.poll();
					message.put("ack",ack);
				}
				else
				{
					message=Packing(MessageType.ACK);
					message.put("ack",ack);
				}
				sendMessage(message);
			}
			if(sendQueue.size()>0)
			{
				sendMessage(sendQueue.poll());
			}
		}
	}

	private JSONObject Packing(MessageType type)
	{
		JSONObject message=new JSONObject();
		message.put("type",type);
		message.put("sender",User.id);
		message.put("receiver",receiver);
		return message;
	}
	private void sendMessage(JSONObject message)
	{
		int seq=message.getIntValue("seq");
		while(!window.containsKey(seq));
		byte[] data=message.toString().getBytes();
		DatagramPacket  packet=new DatagramPacket(data,data.length,adderss,port);
		try {
			socket.send(packet);
			
			System.out.println("-------------------------------------发送总序号："+message.getIntValue("count")+"\t序列号"+message.getIntValue("seq")+"确认号"+message.getIntValue("ack"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(message.getString("type").equals("ACK"))
			return;
		ScheduledFuture<?> sf= scheduledPool.scheduleAtFixedRate(new ReSend(packet),1000,1000,TimeUnit.MILLISECONDS);
		window.put((int)message.get("seq"), sf);
		
	}
}
