package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import bean.User;
import netTool.Chat;

public class UserList extends JFrame {
	JButton b0=new JButton("�����û���"+User.id);
	JButton b1=new JButton("�ͺ���1����");
	JButton b2=new JButton("�ͺ���2����");
	JButton b3=new JButton("�ͺ���3����");
	//��ȡ��Ļ�ֱ���
	private final Dimension dScreen=Toolkit.getDefaultToolkit().getScreenSize();
	private final int screenWidth=dScreen.width;
	private final int screenHeight=dScreen.height;
	public UserList() {
		//��ȫ��ʱ�ߴ��λ��
		this.setSize(screenWidth/2,screenHeight/2 );
		this.setLocationRelativeTo(null);
		//Ĭ�Ϲرմ�������
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//��ʼ��ȫ��
		this.setExtendedState(MAXIMIZED_BOTH);
		//��ʼ����
		this.setLayout(new BorderLayout());
		this.add(getCenter(),BorderLayout.CENTER);
		this.setVisible(true);
		addListener();
	}
	public JPanel getCenter() {
		JPanel center=new JPanel();
		center.add(b0);
		center.add(b1);
		center.add(b2);
		center.add(b3);
		return center;
	}
	private void addListener()
	{
		b1.addActionListener((e)->{
			Chat chat = null;
			try {
				chat = new Chat(1,InetAddress.getLocalHost(),8086,8087);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			new ChatWindow(chat);
			});
		b2.addActionListener((e)->{
			Chat chat = null;
			try {
				chat = new Chat(2,InetAddress.getLocalHost(),8087,8086);
			} catch (UnknownHostException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			new ChatWindow(chat);
			});
//		b3.addActionListener((e)->{
//			Chat chat=new Chat(3);
//			new ChatWindow(chat);
//			});
	}
}
