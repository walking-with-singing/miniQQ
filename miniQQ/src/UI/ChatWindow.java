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
	JButton b0=new JButton("发送");
	JButton b1=new JButton("发送文件");
	JButton b2=new JButton("结束聊天");
	JTextField in=new JTextField(20);
	JTextArea display=new JTextArea(20,20);
	Chat chat;
	//获取屏幕分辨率
	private final Dimension dScreen=Toolkit.getDefaultToolkit().getScreenSize();
	private final int screenWidth=dScreen.width;
	private final int screenHeight=dScreen.height;
	public ChatWindow(Chat chat) {
		this.chat=chat;
		chat.getReceive().setDisplay(display);
		//非全屏时尺寸和位置
		this.setSize(screenWidth/2,screenHeight/2 );
		this.setLocationRelativeTo(null);
		//默认关闭窗口设置
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//初始即全屏
		this.setExtendedState(MAXIMIZED_BOTH);
		//开始布局
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
			String path="C:\\Users\\雨\\Desktop\\cart.rar";
			chat.sendFile(new File(path));
		});
		b2.addActionListener((e)->
		{
			chat.end();
		});
	}
	
}
