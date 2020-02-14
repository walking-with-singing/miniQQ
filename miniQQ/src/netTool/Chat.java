package netTool;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;

import com.alibaba.fastjson.JSONObject;

import Home.Main;
import bean.User;
import protocol.MessageType;

public class Chat {
	public static void main(String[] args) {
//		Chat server = null;
//		//服务器
//		try {
//			server = new Chat(2,InetAddress.getLocalHost(),8087,8086);
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//客户端
		Chat client = null;
		try {
			client = new Chat(2,InetAddress.getLocalHost(),8086,8087);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String path="C:\\Users\\雨\\Desktop\\胡波涛 - 别担心，一切正常.mp3";
		client.sendFile(new File(path));//客户端
		while(true);
		
	}
	int count=0;
	private Future<JSONObject> sendFuture;
	private Future<JSONObject> receiveFuture;
	private SendTask send;
	private ReceiveTask receive;
	public ReceiveTask getReceive() {
		return receive;
	}

	public void setReceive(ReceiveTask receive) {
		this.receive = receive;
	}
	private int receiver;
	private int n=8;
	private int seq=0;
	private Map<Integer,ScheduledFuture<?>> window=new ConcurrentHashMap<>();
	private Queue<JSONObject> sendQueue=new  ConcurrentLinkedQueue<>();
	private Queue<Integer> acknowledgeQueue=new  ConcurrentLinkedQueue<>();
	public Chat(int receiver,InetAddress targetAddress,int targetPort,int myPort) {
		this.receiver=receiver;
		send=new SendTask(receiver,targetAddress,targetPort,window,sendQueue,acknowledgeQueue);
		receive=new ReceiveTask(myPort,window,this,acknowledgeQueue);
		sendFuture=Main.pool.submit(send);
		receiveFuture=Main.pool.submit(receive);
	}

	public int getReceiver() {
		return receiver;
	}
	public void end()
	{
		sendFuture.cancel(true);
		receiveFuture.cancel(true);
	}
	public void sendData(String data)
	{
		sendQueue.offer(packing(MessageType.SENTENCE,data));
	}
	public void sendFile(File file)
	{
		try {
			sendQueue.offer(packing(MessageType.FILE_BEGIN,file.getName()));
			FileInputStream fin=new FileInputStream(file);
			while(fin.available()>0)
			{
				byte[] data=fin.readNBytes(10240);
				sendQueue.offer(packing(MessageType.FILE, data));
			}
			fin.close();
			sendQueue.offer(packing(MessageType.FILE_END,null));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(count);
	}
	private JSONObject packing(MessageType type,Object data)
	{
		count++;
		JSONObject message=new JSONObject();
		message.put("count", count);
		message.put("type", type);
		message.put("seq",seq);
		message.put("ack",-1);
		seq=(seq+1)%n;
		message.put("sender",User.id);
		message.put("receiver",receiver);
		message.put("data",data);
		return message;
	}
}
