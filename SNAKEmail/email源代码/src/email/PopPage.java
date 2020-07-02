package email;
import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;;

public class PopPage extends JFrame {
    private User authUser;
	private JPanel contentPane;
	private String fileName;
	private boolean annex;
	private int emailNum;
	/**
	 * @wbp.nonvisual location=864,384
	 */

	/**
	 * Launch the application.
	 */
	public  void doPopPage(User auth) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PopPage frame = new PopPage(auth);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public PopPage(User auth) {
		 authUser = new User();
		 authUser = auth;
		setTitle("SNAKEmail");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PopPage.class.getResource("/imagine/mail.png")));
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 917, 593);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 248, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel userLabel = new JLabel("用  户："+auth.getUserName());
		userLabel.setIcon(new ImageIcon(PopPage.class.getResource("/imagine/user.png")));
		userLabel.setFont(new Font("等线", Font.BOLD | Font.ITALIC, 20));
		
		JButton backButton = new JButton("");
		backButton.setIcon(new ImageIcon(PopPage.class.getResource("/imagine/return.png")));
		backButton.setFont(new Font("等线", Font.PLAIN, 20));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 new MainPage(authUser).doMainPage(authUser);
				   dispose();
			}
		});
		JButton quitButton = new JButton("");
		quitButton.setIcon(new ImageIcon(PopPage.class.getResource("/imagine/登出.png")));
		quitButton.setFont(new Font("等线", Font.PLAIN, 21));
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
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("宋体", Font.PLAIN, 22));
		
		JLabel emailCountLabel = new JLabel("总数：");
		emailCountLabel.setFont(new Font("宋体", Font.PLAIN, 25));
		emailCountLabel.setForeground(new Color(0, 0, 0));
		emailCountLabel.setIcon(new ImageIcon(PopPage.class.getResource("/imagine/login2.png")));
		pop popMail = new pop();
		if(popMail.getEmailCount(auth)) {
			emailCountLabel.setText("总数："+popMail.getCount());
			for(int i=0; i < popMail.getCount(); i++)
				comboBox.addItem("第"+(i+1)+"封");
			}
		JLabel printLabel = new JLabel("等待选择邮件读取.........");
		printLabel.setFont(new Font("宋体", Font.BOLD, 20));
		
		JLabel mailLabel = new JLabel("邮件列表：");
		mailLabel.setIcon(new ImageIcon(PopPage.class.getResource("/imagine/read.png")));
		mailLabel.setFont(new Font("宋体", Font.PLAIN, 25));
		
		JLabel reverLabel = new JLabel("发件人：null");
		reverLabel.setIcon(new ImageIcon(PopPage.class.getResource("/imagine/revcer.png")));
		reverLabel.setFont(new Font("等线", Font.PLAIN, 20));
		
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setContentType("text/html\r\n");
		textPane.setBounds(15, 234, 890, 288);
		textPane.setFont(new Font("楷体", Font.PLAIN, 22));
		textPane.setText("这里将显示你选择的邮件内容");
		
		JScrollPane scroll = new JScrollPane(textPane);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scroll.setBounds(15, 234, 890, 288);
		contentPane.add(scroll);
		
		JLabel subjectLabel = new JLabel("主   题：null");
		subjectLabel.setIcon(new ImageIcon(PopPage.class.getResource("/imagine/subject.png")));
		subjectLabel.setFont(new Font("等线", Font.PLAIN, 20));
		
		JLabel connnetLabel = new JLabel("内  容：");
		connnetLabel.setIcon(new ImageIcon(PopPage.class.getResource("/imagine/内容.png")));
		connnetLabel.setFont(new Font("等线", Font.PLAIN, 20));
		
		JLabel annexLabel = new JLabel("");	
		annexLabel.setFont(new Font("宋体", Font.PLAIN, 21));
		
		JButton doButton = new JButton("显示");
		doButton.setIcon(new ImageIcon(PopPage.class.getResource("/imagine/显示 (2).png")));
		doButton.setFont(new Font("宋体", Font.PLAIN, 21));
		doButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				printLabel.setText("读取邮件中.......");
				pop popMail = new pop();	//
					if(popMail.getEmail(auth,comboBox.getItemCount()-comboBox.getSelectedIndex())) {
						textPane.setText(popMail.getBody());
						printLabel.setText("读取成功.......");
						annex = popMail.getAnnex();
						if(annex) {
							fileName = popMail.getFileName();
							annexLabel.setText(fileName);
							annexLabel.setIcon(new ImageIcon(PopPage.class.getResource("/imagine/添加附件.png")));
						}
						else {
							annexLabel.setText("");
							annexLabel.setIcon(new ImageIcon(PopPage.class.getResource("/imagine/空白.png")));
						}
					}
					else {
						textPane.setText("oh,oh,读取出现一一些问题，可能是多层嵌套邮件。");
						printLabel.setText("读取过程发生了一些未知错误.......");
						annexLabel.setText("");
						annexLabel.setIcon(new ImageIcon(PopPage.class.getResource("/imagine/空白.png")));
						JOptionPane.showMessageDialog(null,"<html><h1><font color='blue'>读取邮件过程发生了一些未知错误</font></h1></html>","错误",0);
					}
					reverLabel.setText("发件人："+popMail.getAddressee());
					subjectLabel.setText("主题："+popMail.getSubject());
			}
	    });
		
		JButton annexButton = new JButton("打开附件");
		annexButton.setBackground(new Color(240, 248, 255));
		annexButton.setIcon(new ImageIcon(PopPage.class.getResource("/imagine/添加附件.png")));
		annexButton.setFont(new Font("宋体", Font.PLAIN, 20));
		annexButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(annex) {
					try {	
						Runtime.getRuntime().exec("rundll32 url.dll FileProtocolHandler file://"+System.getProperty("user.dir")+"\\"+fileName);
					}
					catch(Exception s) {
						JOptionPane.showMessageDialog(null,"<html><h1><font color='blue'>打开附件发生了一些错误</font></h1></html>","消息",0);
					}
				}
			}
		});
		
		JLabel statusLabel = new JLabel("状态：");
		statusLabel.setIcon(new ImageIcon(PopPage.class.getResource("/javax/swing/plaf/metal/icons/Inform.gif")));
		statusLabel.setFont(new Font("等线", Font.BOLD, 20));
		statusLabel.setBackground(Color.YELLOW);
		
		JButton updateButton = new JButton("刷新");
		updateButton.setIcon(new ImageIcon(PopPage.class.getResource("/imagine/刷新 (1).png")));
		updateButton.setFont(new Font("宋体", Font.PLAIN, 21));
		updateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pop updateMail = new pop();
				if(updateMail.getEmailCount(auth)) {
					emailCountLabel.setText("总数："+updateMail.getCount());
					comboBox.removeAllItems();
					for(int i=0; i < updateMail.getCount(); i++)
						comboBox.addItem("第"+(i+1)+"封");
					}
				reverLabel.setText("发件人：");
				subjectLabel.setText("主题：");
				textPane.setText("这里将显示你选择的邮件内容");
				JLabel printLabel = new JLabel("等待选择邮件读取.........");
				annexLabel.setText("");
				annexLabel.setIcon(new ImageIcon(PopPage.class.getResource("/imagine/空白.png")));
			}
		});
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(connnetLabel, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(annexButton)
							.addGap(18)
							.addComponent(annexLabel, GroupLayout.PREFERRED_SIZE, 424, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(247, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(userLabel, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(mailLabel)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(comboBox, 100, 180, 180)
									.addGap(26)
									.addComponent(emailCountLabel)))
							.addGap(9)
							.addComponent(updateButton)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
									.addComponent(backButton)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(quitButton, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addGap(44))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(66)
									.addComponent(doButton)
									.addContainerGap())))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(subjectLabel, GroupLayout.PREFERRED_SIZE, 631, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(256, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(reverLabel, GroupLayout.DEFAULT_SIZE, 873, Short.MAX_VALUE)
							.addContainerGap())))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(statusLabel, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(printLabel, GroupLayout.DEFAULT_SIZE, 726, Short.MAX_VALUE)
					.addGap(71))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addContainerGap()
							.addComponent(userLabel, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(mailLabel)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(emailCountLabel)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(backButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
								.addComponent(quitButton, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(doButton)
								.addComponent(updateButton))))
					.addGap(18)
					.addComponent(reverLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(subjectLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(annexLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(connnetLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addComponent(annexButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED, 295, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(statusLabel, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
						.addComponent(printLabel, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)))
		);
		
		contentPane.setLayout(gl_contentPane);
	}
}
