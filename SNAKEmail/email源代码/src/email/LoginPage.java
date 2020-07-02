package email;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Toolkit;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPasswordField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JToolBar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingConstants;
public class LoginPage extends JFrame {
    private User authLog;
	private JPanel loginPane;
	private boolean signLogin;
	private JTextField userTextField;
	private JPasswordField passwordField;
	/**
	 * Launch the application.
	 */
	public  void doLogin(){
		signLogin = false;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public User getAuth() {
		return this.authLog;
	}
	public void setSignLogin(boolean sign) {
		signLogin = sign;
	}
	public boolean isLogin() {
		return signLogin;
	}
	/**
	 * Create the frame.
	 */
	public LoginPage() {
		setBackground(Color.YELLOW);
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(LoginPage.class.getResource("/imagine/mail.png")));
		setTitle("SNAKEmail");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 916, 595);
		loginPane = new JPanel();
		loginPane.setBackground(new Color(240, 255, 240));
		loginPane.setForeground(Color.BLACK);
		loginPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(loginPane);
		
		JLabel logText = new JLabel("SNAKE 简易邮件");
		logText.setFont(new Font("等线", Font.BOLD, 59));
		
		JLabel userLabel = new JLabel("用户名：");
		userLabel.setIcon(new ImageIcon(LoginPage.class.getResource("/imagine/user.png")));
		userLabel.setFont(new Font("等线", Font.BOLD, 25));
		userTextField = new JTextField();
		userTextField.setFont(new Font("Consolas", Font.PLAIN, 17));
		userTextField.setColumns(10);
		
		JLabel passLabel = new JLabel("密   码：");
		passLabel.setIcon(new ImageIcon(LoginPage.class.getResource("/imagine/passw.png")));
		passLabel.setFont(new Font("等线", Font.BOLD, 25));
		passwordField = new JPasswordField();
		
		JToolBar statusBar = new JToolBar();
		statusBar.setBackground(Color.LIGHT_GRAY);
		statusBar.setToolTipText("");
		JLabel statusLabel = new JLabel("状态：");
		statusLabel.setIcon(new ImageIcon(LoginPage.class.getResource("/javax/swing/plaf/metal/icons/Question.gif")));
		statusLabel.setBackground(Color.YELLOW);
		statusLabel.setFont(new Font("等线", Font.BOLD, 20));
		statusBar.add(statusLabel);
		JLabel printStatusLabel = new JLabel("等待登录中...........");
		printStatusLabel.setFont(new Font("等线", Font.BOLD, 20));
		statusBar.add(printStatusLabel);
		
		JButton loginButton = new JButton("登录");
		loginButton.setBackground(Color.CYAN);
		loginButton.setIcon(new ImageIcon(LoginPage.class.getResource("/imagine/login2.png")));
		loginButton.setFont(new Font("等线 Light", Font.BOLD, 22));
		//  登录按钮-监听事件
		loginButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				authLog  = new User();
				String Message = new String("");
				if(authLog.setUser(userTextField.getText().toString(),String.valueOf(passwordField.getPassword())))
					{ 
					if(authLog.login()) {
						setSignLogin(true);
						Message  = "登录成功......";
					}
					else  Message  = "密码或用户名错误,请重试......";
					}
				else Message  = "用户名错误,请检查重试.......";
				printStatusLabel.setText(Message);
				if(signLogin) { 
					   new MainPage(authLog).doMainPage(authLog);
					   dispose();
				}
			}
		});
		
		JButton reButton = new JButton("重置");
		reButton.setForeground(Color.BLACK);
		reButton.setBackground(Color.WHITE);
		reButton.setIcon(new ImageIcon(LoginPage.class.getResource("/imagine/reName.png")));
		reButton.setFont(new Font("等线 Light", Font.BOLD, 22));
		// 重置按钮-监听事件
		reButton.addActionListener(new ActionListener() {
			String reMessage = new String("");
			public void actionPerformed(ActionEvent e) {
				//显示的文字
				userTextField.setText("");
				passwordField.setText("");
				reMessage  = "重置用户名与密码.......";
				printStatusLabel.setText(reMessage);
			}
		});
		
		JButton aboutButton = new JButton("关于");
		aboutButton.setVerticalAlignment(SwingConstants.BOTTOM);
		aboutButton.setBackground(Color.WHITE);
		aboutButton.setForeground(new Color(0, 0, 0));
		aboutButton.setIcon(new ImageIcon(LoginPage.class.getResource("/imagine/About.png")));
		aboutButton.setFont(new Font("等线", Font.ITALIC, 20));
		aboutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String Message = new String("<html><h1><font color='blue'> -------------------关于---------------------- </font></h1>"
				  + "<h2><font color='black'>161710228--计网工程型实验成果,支持大多<br/>"
				  + "数类型邮箱登录。<br/>"
				  + "主要功能:图形界面+stmp发送邮件+带附<br/>件发送+pop获取文件并显示"
				  + "</font></h2></html>");
				JLabel showMessage =  new JLabel(Message);
			 JOptionPane.showMessageDialog(null,showMessage,"About",1);
			}
		});
		
		GroupLayout gl_loginPane = new GroupLayout(loginPane);
		gl_loginPane.setHorizontalGroup(
			gl_loginPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_loginPane.createSequentialGroup()
					.addComponent(reButton)
					.addGap(232)
					.addComponent(loginButton)
					.addGap(225))
				.addGroup(gl_loginPane.createSequentialGroup()
					.addComponent(statusBar, GroupLayout.PREFERRED_SIZE, 915, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(gl_loginPane.createSequentialGroup()
					.addContainerGap(270, Short.MAX_VALUE)
					.addGroup(gl_loginPane.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_loginPane.createSequentialGroup()
							.addComponent(userLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(userTextField, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_loginPane.createSequentialGroup()
							.addComponent(passLabel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(passwordField)))
					.addGap(329))
				.addGroup(gl_loginPane.createSequentialGroup()
					.addContainerGap(226, Short.MAX_VALUE)
					.addComponent(logText)
					.addGap(128)
					.addComponent(aboutButton)
					.addGap(42))
		);
		gl_loginPane.setVerticalGroup(
			gl_loginPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_loginPane.createSequentialGroup()
					.addGroup(gl_loginPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_loginPane.createSequentialGroup()
							.addGap(10)
							.addComponent(aboutButton))
						.addGroup(gl_loginPane.createSequentialGroup()
							.addGap(34)
							.addComponent(logText)))
					.addGap(108)
					.addGroup(gl_loginPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(userLabel)
						.addComponent(userTextField, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addGap(29)
					.addGroup(gl_loginPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(passLabel)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addGap(76)
					.addGroup(gl_loginPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(reButton)
						.addComponent(loginButton))
					.addPreferredGap(ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
					.addComponent(statusBar, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
		);
		loginPane.setLayout(gl_loginPane);
	}
}
