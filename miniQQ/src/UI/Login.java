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
	JButton submit=new JButton("��¼");
	JButton signUp=new JButton("ע��");
	//��ȡ��Ļ�ֱ���
	private final Dimension dScreen=Toolkit.getDefaultToolkit().getScreenSize();
	private final int screenWidth=dScreen.width;
	private final int screenHeight=dScreen.height;
	//Tool
	public Login() {
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
		center.setLayout(null);
		JLabel tname =new JLabel("�û�����");
		JLabel tpassword =new JLabel("���룺");
		//���þ���λ��
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
//			JOptionPane.showMessageDialog(center,"�û���"+name+"��������!");
//			return;
//		}
//		String truePassword=user.getPassword();
//		if(!password.equals(truePassword))
//		{
//			JOptionPane.showMessageDialog(center,"���벻��ȷ�����������롣");
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
//		logger.debug("ע��");
//		new SignUp();
	}
}
