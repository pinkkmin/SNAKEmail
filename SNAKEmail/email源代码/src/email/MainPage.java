package email;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.ScrollPane;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.Color;

public class MainPage extends JFrame {
    private User authUser;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public  void doMainPage(User auth) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainPage frame = new MainPage(auth);				
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public void setUser(User auth) {
		authUser = auth;
	}

	/**
	 * Create the frame.
	 */
	public MainPage(User auth) {
		 authUser = new User();
		 authUser = auth;
		setResizable(false);
		setTitle("SNAKEmail");
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainPage.class.getResource("/imagine/mail.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 917, 593);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel userLabel = new JLabel("用  户："+auth.getUserName());
		userLabel.setIcon(new ImageIcon(MainPage.class.getResource("/imagine/user.png")));
		userLabel.setFont(new Font("等线", Font.BOLD | Font.ITALIC, 22));
		JButton quitButton = new JButton("登出");
		quitButton.setIcon(new ImageIcon(MainPage.class.getResource("/imagine/登出.png")));
		quitButton.setFont(new Font("等线", Font.PLAIN, 20));
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int option= JOptionPane.showConfirmDialog(null,"<html><h1><font color='blue'>确定要登出吗？</font></h1></html>","登出提示",JOptionPane.YES_NO_OPTION);
			// 0--JOptionPane.YES_OPTION 1--JOptionPane.NO_OPTION
			if(option==0)  {
				new LoginPage().doLogin();
				dispose();
			}
			}
		});
		
		
		JLabel lblNewLabel = new JLabel("欢迎使用SNAKEMmail简易邮件");
		
		lblNewLabel.setVerticalAlignment(SwingConstants.TOP);
		lblNewLabel.setFont(new Font("宋体", Font.BOLD | Font.ITALIC, 29));
		
		JButton smtpButton = new JButton("smtp写 信");
		smtpButton.setIcon(new ImageIcon(MainPage.class.getResource("/imagine/write.png")));
		smtpButton.setFont(new Font("等线", Font.BOLD, 25));
		smtpButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 new smtpPage(authUser).doSmtpPage(authUser);
				   dispose();
			}
		});
		JButton popButton = new JButton(" pop 读 信");
		popButton.setIcon(new ImageIcon(MainPage.class.getResource("/imagine/read.png")));
		popButton.setFont(new Font("等线", Font.BOLD, 25));
		popButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 new PopPage(authUser).doPopPage(authUser);
				   dispose();
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("关于读取邮件，支持读取base64和Quoted-Printable编码邮件");
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 20));
		
		JLabel lblNewLabel_2 = new JLabel("显示源HTML，比较丑陋。有关附件亦可将自动下载,由于java");
		lblNewLabel_2.setFont(new Font("宋体", Font.PLAIN, 20));
		
		JLabel lblNewLabel_1_1 = new JLabel("尚存未知bug,至念道臻，寄感真诚，使用愉快。");
		lblNewLabel_1_1.setFont(new Font("宋体", Font.PLAIN, 20));
		
		JLabel lblNewLabel_1_1_1 = new JLabel("                                          by 161710228");
		lblNewLabel_1_1_1.setFont(new Font("宋体", Font.PLAIN, 20));
		
		JLabel lblNewLabel_1_2 = new JLabel("以下是一些说明 : ");
		lblNewLabel_1_2.setFont(new Font("宋体", Font.PLAIN, 20));
		
		JLabel lblNewLabel_1_3 = new JLabel("关于写信发送邮件，支持发送附件(可以是图片、PDF、word、");
		lblNewLabel_1_3.setFont(new Font("宋体", Font.PLAIN, 20));
		
		JLabel lblNewLabel_1_4 = new JLabel("PPT等诸多类型)，文本部分正常邮件内容。");
		lblNewLabel_1_4.setFont(new Font("宋体", Font.PLAIN, 20));
		
		JLabel lblNewLabel_1_5 = new JLabel("虽java的Text域支持显示HTML，但由于邮件中HTML不完整，只");
		lblNewLabel_1_5.setFont(new Font("宋体", Font.PLAIN, 20));
		
		JLabel lblNewLabel_1_5_1 = new JLabel("的解码过慢所以带附件需要会一些时间，有可能陷入死循环。");
		lblNewLabel_1_5_1.setFont(new Font("宋体", Font.PLAIN, 20));
		
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(userLabel, GroupLayout.PREFERRED_SIZE, 498, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 257, Short.MAX_VALUE)
							.addComponent(quitButton))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(smtpButton)
							.addPreferredGap(ComponentPlacement.RELATED, 182, Short.MAX_VALUE)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 440, GroupLayout.PREFERRED_SIZE)
							.addGap(51))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(popButton)
							.addGap(104)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_1_2, GroupLayout.PREFERRED_SIZE, 553, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1_1_1, GroupLayout.PREFERRED_SIZE, 553, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1_3, GroupLayout.PREFERRED_SIZE, 553, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1_4, GroupLayout.PREFERRED_SIZE, 553, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 539, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1_5, GroupLayout.PREFERRED_SIZE, 553, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1_5_1, GroupLayout.PREFERRED_SIZE, 553, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 553, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(10)
									.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 553, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(35))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(userLabel, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
						.addComponent(quitButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addGap(48)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(smtpButton)
						.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_1_2, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(2)
					.addComponent(lblNewLabel_1_3, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(lblNewLabel_1_4, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(2)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(15)
							.addComponent(popButton)
							.addGap(32))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(7)
							.addComponent(lblNewLabel_1_5, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblNewLabel_1_5_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_1_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(lblNewLabel_1_1_1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(35))
		);
		contentPane.setLayout(gl_contentPane);
	}
}
