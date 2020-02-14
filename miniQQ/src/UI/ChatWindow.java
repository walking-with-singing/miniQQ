package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import bean.User;
import netTool.Chat;

public class ChatWindow extends JFrame {
	JButton b0=new JButton("����");
	JButton b1=new JButton("�����ļ�");
	JButton b2=new JButton("��������");
	JTextField in=new JTextField(20);
	JTextArea display=new JTextArea(20,20);
	Chat chat;
	//��ȡ��Ļ�ֱ���
	private final Dimension dScreen=Toolkit.getDefaultToolkit().getScreenSize();
	private final int screenWidth=dScreen.width;
	private final int screenHeight=dScreen.height;
	public ChatWindow(Chat chat) {
		this.chat=chat;
		chat.getReceive().setDisplay(display);
		//��ȫ��ʱ�ߴ��λ��
		this.setSize(screenWidth/2,screenHeight/2 );
		this.setLocationRelativeTo(null);
		//Ĭ�Ϲرմ�������
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//��ʼ��ȫ��
		this.setExtendedState(MAXIMIZED_BOTH);
		//��ʼ����
		this.setLayout(new BorderLayout());
		this.add(getNorth(),BorderLayout.NORTH);
		this.add(getCenter(),BorderLayout.CENTER);
		addListener();
		this.setVisible(true);
	}
	public JPanel getNorth()
	{
		JPanel north=new JPanel();
		north.add(display);
		north.add(in);
		return north;
	}
	public JPanel getCenter() {
		JPanel center=new JPanel();
		center.add(b0);
		center.add(b1);
		return center;
	}
	private void addListener()
	{
		b0.addActionListener((e)->{
			String str=in.getText();
			in.setText("");
			chat.sendData(str);
		});
		b1.addActionListener((e)->{
			String path="C:\\Users\\��\\Desktop\\cart.rar";
			chat.sendFile(new File(path));
		});
		b2.addActionListener((e)->
		{
			chat.end();
		});
	}
	
}
