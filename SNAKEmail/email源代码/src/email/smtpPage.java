package email;
import java.util.*;
import java.util.regex.Matcher;  
import java.util.regex.Pattern; 
import java.awt.EventQueue;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.awt.event.ActionEvent;

public class smtpPage extends JFrame {
    private User authUser;
	private JPanel contentPane;
	private JTextField revtextField;
	private JTextField subjectTextField;
    private Mail Mail;
	/**
	 * Launch the application.
	 */
	public  void doSmtpPage(User auth) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					smtpPage frame = new smtpPage(auth);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public Mail getMail() {
		return Mail;
	}
	public boolean isRight(String address) {
		String check = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";  
	     Pattern regex = Pattern.compile(check);  
	     Matcher matcher = regex.matcher(address);  
	    return matcher.matches(); 
	}
    public String returnConnet(int sign) {
     String  connetMessage = new String("<html><h1><font color='blue'>"); 
     if(sign==111) connetMessage += "无效的服务器地址, 请检查用户名是否正确...";
     else if(sign==222) {
    	 connetMessage += "连接服务器被拒绝, 请稍后重试....";
     }
     else if(sign==333) {
    	 connetMessage += "连接服务器被拒绝, 请稍后重试....";
     }
     else if(sign==222) {
    	 connetMessage += "连接服务器超时, 请稍后重试....";
     }
     else if(sign==777) {
    	 connetMessage += "发送成功！！！！";
     }
     connetMessage += "</font></h1></html>";
     return  connetMessage;
    }
	/**
	 * Create the frame.
	 */
	public smtpPage(User auth) {
		 authUser = new User();
		 authUser = auth;
		 Mail = new Mail(false);
		 
		setResizable(false);
		setTitle("SNAKEmail");
		setIconImage(Toolkit.getDefaultToolkit().getImage(smtpPage.class.getResource("/imagine/mail.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 917, 593);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 255, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel reverLabel = new JLabel("收件人：");
		reverLabel.setIcon(new ImageIcon(smtpPage.class.getResource("/imagine/revcer.png")));
		reverLabel.setFont(new Font("等线", Font.PLAIN, 20));
		
		JLabel subjectLabel = new JLabel("主   题：");
		subjectLabel.setIcon(new ImageIcon(smtpPage.class.getResource("/imagine/subject.png")));
		subjectLabel.setFont(new Font("等线", Font.PLAIN, 20));
		
		JLabel connnetLabel = new JLabel("内  容：");
		connnetLabel.setIcon(new ImageIcon(smtpPage.class.getResource("/imagine/内容.png")));
		connnetLabel.setFont(new Font("等线", Font.PLAIN, 20));
		
		revtextField = new JTextField();
		revtextField.setFont(new Font("等线", Font.PLAIN, 20));
		revtextField.setColumns(10);
		
		subjectTextField = new JTextField();
		subjectTextField.setFont(new Font("等线 Light", Font.PLAIN, 20));
		subjectTextField.setBackground(SystemColor.inactiveCaptionBorder);
		subjectTextField.setColumns(10);
		
		JTextArea contentArea = new JTextArea();
		contentArea.setFont(new Font("楷体", Font.PLAIN, 21));
		
		JLabel userLabel = new JLabel("用  户："+authUser.getUserName());
		userLabel.setIcon(new ImageIcon(smtpPage.class.getResource("/imagine/user.png")));
		userLabel.setFont(new Font("等线", Font.BOLD | Font.ITALIC, 20));
		userLabel.setForeground(new Color(0, 0, 0));
		
		JButton backButton = new JButton("");
		backButton.setIcon(new ImageIcon(smtpPage.class.getResource("/imagine/return.png")));
		backButton.setFont(new Font("等线", Font.PLAIN, 20));
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			int option= JOptionPane.showConfirmDialog(null,"当前编辑将不保存,确定要返回吗？","返回提示",JOptionPane.YES_NO_OPTION);
			// 0--JOptionPane.YES_OPTION 1--JOptionPane.NO_OPTION
			if(option==0) {
				 new MainPage(authUser).doMainPage(authUser);
				   dispose();
			}
			}
		});
		JButton quitButton = new JButton("");
		quitButton.setIcon(new ImageIcon(smtpPage.class.getResource("/imagine/登出.png")));
		quitButton.setFont(new Font("等线", Font.PLAIN, 21));
		quitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			int option= JOptionPane.showConfirmDialog(null,"<html><h1><font color='blue'>确定要登出吗？</font></h1></html>","登出提示",JOptionPane.YES_NO_OPTION);
			// 0--JOptionPane.YES_OPTION 1--JOptionPane.NO_OPTION
			if(option==0)   {
				new LoginPage().doLogin();
				dispose();
			}
			}
		});
		JLabel annexLabel = new JLabel(" ");
		annexLabel.setFont(new Font("等线", Font.ITALIC, 22));
		JButton annexButton = new JButton("附件");
		annexButton.setIcon(new ImageIcon(smtpPage.class.getResource("/imagine/添加附件.png")));
		annexButton.setFont(new Font("楷体", Font.PLAIN, 20));
		annexButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser FileChooser = new JFileChooser();
	            int valJFile = FileChooser.showOpenDialog(null);  
	            if(valJFile == FileChooser.APPROVE_OPTION) {
	            	String filePath = new String(FileChooser.getSelectedFile().toString());
	                 Mail.setFilePath(true,filePath);
	            	annexLabel.setIcon(new ImageIcon(smtpPage.class.getResource("/imagine/添加附件.png")));
	            	annexLabel.setText(filePath.substring(filePath.lastIndexOf('\\')+1));
	            }
			}
		});
		JButton cannelButton = new JButton("取消");
		cannelButton.setIcon(new ImageIcon(smtpPage.class.getResource("/imagine/取消附件.png")));
		cannelButton.setFont(new Font("等线", Font.PLAIN, 20));
		cannelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int  Option = JOptionPane.showConfirmDialog(null,"<html><h1><font color='blue'>确定取消附件吗？？</font></h1></html>","提示",0);   
				if(Option==0) {  // 选择空的内容为Yes
					Mail.setFilePath(false,"");
					annexLabel.setText("");
					annexLabel.setIcon(new ImageIcon(smtpPage.class.getResource("/imagine/空白.png")));
				  }
			}
		});
		JButton sendButton = new JButton("发送");
		sendButton.setIcon(new ImageIcon(smtpPage.class.getResource("/imagine/发送.png")));
		sendButton.setFont(new Font("等线", Font.PLAIN, 20));
		sendButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isRight(revtextField.getText())) {
					String subject = subjectTextField.getText();   // 抄送 和 主题 
					String body = contentArea.getText(),Message = new String("<html><h1><font color='black'>");
					
					boolean signal = false;            
					int Option = 0;
					if(subjectTextField.getText().length()==0) {
						Message += "主题为空, ";
						signal = true;
					}
				    if(contentArea.getText().length()==0) {
				    	Message += "邮件内容为空, ";
				    	signal = true;
				    }
				    if(signal)  Option = JOptionPane.showConfirmDialog(null,Message+"确定不填写吗？？</font></h1></html>","提示",0);
				  if(Option==0) {  // 选择空的内容为Yes
					  String showMessage = new  String();
					  Mail.setMailContent(revtextField.getText(),subject, body);
					  showMessage = returnConnet(new Send(auth).connect(getMail()));
					  JOptionPane.showMessageDialog(null,showMessage,"消息",1);
				  }
				} else 
				{   // 用户名不含@
					 JOptionPane.showMessageDialog(null,"用户名不正确，请检查重试.","错误",0);
				}
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(10)
							.addComponent(contentArea, GroupLayout.PREFERRED_SIZE, 863, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addComponent(userLabel, GroupLayout.PREFERRED_SIZE, 467, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 228, Short.MAX_VALUE)
							.addComponent(backButton)
							.addGap(18)
							.addComponent(quitButton)
							.addGap(68))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(subjectLabel)
								.addComponent(reverLabel)
								.addComponent(connnetLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(revtextField, GroupLayout.PREFERRED_SIZE, 295, GroupLayout.PREFERRED_SIZE)
									.addGap(115)
									.addComponent(sendButton))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(annexButton)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cannelButton)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(annexLabel, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE))
								.addComponent(subjectTextField, GroupLayout.PREFERRED_SIZE, 735, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED, 40, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(10)
									.addComponent(userLabel))
								.addComponent(backButton))
							.addGap(42)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(revtextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(reverLabel)
								.addComponent(sendButton)))
						.addComponent(quitButton))
					.addGap(38)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(subjectLabel)
						.addComponent(subjectTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(connnetLabel)
						.addComponent(annexButton)
						.addComponent(cannelButton)
						.addComponent(annexLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(contentArea, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
