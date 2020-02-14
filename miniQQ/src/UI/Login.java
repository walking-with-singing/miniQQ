package UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import bean.User;

public class Login extends JFrame{
	JPanel center=new JPanel();
	JTextField name=new JTextField(20);
	JPasswordField password=new JPasswordField(20);
	JButton submit=new JButton("登录");
	JButton signUp=new JButton("注册");
	//获取屏幕分辨率
	private final Dimension dScreen=Toolkit.getDefaultToolkit().getScreenSize();
	private final int screenWidth=dScreen.width;
	private final int screenHeight=dScreen.height;
	//Tool
	public Login() {
		//非全屏时尺寸和位置
		this.setSize(screenWidth/2,screenHeight/2 );
		this.setLocationRelativeTo(null);
		//默认关闭窗口设置
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//初始即全屏
		this.setExtendedState(MAXIMIZED_BOTH);
		//开始布局
		this.setLayout(new BorderLayout());
		this.add(getCenter(),BorderLayout.CENTER);
		this.setVisible(true);
		addListener();
	}
	public JPanel getCenter() {
		center.setLayout(null);
		JLabel tname =new JLabel("用户名：");
		JLabel tpassword =new JLabel("密码：");
		//设置绝对位置
		tname.setBounds(screenWidth/100*45, screenHeight/100*30,screenWidth/100*15,screenHeight/100*3);
		name.setBounds(screenWidth/100*50, screenHeight/100*30,screenWidth/100*15,screenHeight/100*3);
		tpassword.setBounds(screenWidth/100*45, screenHeight/100*35, screenWidth/100*15,screenHeight/100*3);
		password.setBounds(screenWidth/100*50, screenHeight/100*35, screenWidth/100*15,screenHeight/100*3);
		submit.setBounds(screenWidth/100*50, screenHeight/100*50, screenWidth/100*10,screenHeight/100*5);
		signUp.setBounds(screenWidth/100*50,screenHeight/100*60,screenWidth/100*10,screenHeight/100*5);
		center.add(tname);
		center.add(name);
		center.add(tpassword);
		center.add(password);
		center.add(submit);
		center.add(signUp);
		return center;
	}

	protected void addListener()
	{
		submit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				submitSignIn();
			}
		});
		signUp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				signUp();
			}
		});
	}
	
	private void submitSignIn()
	{
		
		String name=this.name.getText();
//		String password=new String(this.password.getPassword());
//		User user=dao.getUser(name);
//		if(user==null)
//		{
//			JOptionPane.showMessageDialog(center,"用户“"+name+"”不存在!");
//			return;
//		}
//		String truePassword=user.getPassword();
//		if(!password.equals(truePassword))
//		{
//			JOptionPane.showMessageDialog(center,"密码不正确，请重新输入。");
//			return;
//		}
//		else 
		{
			User.id=Integer.parseInt(name);
			new UserList();
			this.dispose();
		}		
	}

	private void signUp() {
//		logger.debug("注册");
//		new SignUp();
	}
}
