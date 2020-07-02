package email;
import java.net.*;
import java.util.*;

import sun.misc.BASE64Encoder;

import java.io.*;
public class Send {
	private User sendUser;
	static String userBase64;
	static String passwordBase64;
	static String ReMessage; // SMTP服务器返回的信息
	int statusCode;   // SMTP服务器返回的状态码
	public Send(User auth) {
		setUserInfo(auth);
	}
	public void setUserInfo(User auth){
	 //设置用户信息
		try {
			sendUser = auth;
		userBase64 = Base64.getEncoder().encodeToString(auth.getUserName().getBytes("UTF-8"));
		 
		passwordBase64 = Base64.getEncoder().encodeToString(auth.getPassword().getBytes("UTF-8"));
	}catch(UnsupportedEncodingException e){
        System.out.println("Error :" + e.getMessage());
     }
	}
	//String Addressee,String cc,String subject,String body
	public int connect(Mail Mail) {
			try {
				Socket socketSend = new Socket("SMTP." + sendUser.getServerAddr(), 25);
			//Output: message to server  发送给server的信息
			OutputStream outToServer = socketSend.getOutputStream();
			PrintWriter toServer = new PrintWriter(outToServer,true);
			//Input：message from server 接受来自server的信息
			InputStream inFromServer = socketSend.getInputStream();
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(inFromServer));
			ReMessage = fromServer.readLine().toString();
			statusCode = Integer.parseInt(ReMessage.substring(0,3));
			if(statusCode!=220)
			//System.out.println("Server return Info:"+ReMessage);
			 {  socketSend.close();
				   return statusCode;
			       }
			toServer.println("HELO CMF");
			ReMessage = fromServer.readLine().toString();
			statusCode = Integer.parseInt(ReMessage.substring(0,3));
			if(statusCode!=250) {
				socketSend.close();
				return statusCode;
			} 
			if(sendUser.getServerAddr().indexOf("qq")!=-1||sendUser.getServerAddr().indexOf("QQ")!=-1)
			{fromServer.readLine();
			  fromServer.readLine();	
			}
			//System.out.println("helo: " + ReMessage+"\n"+fromServer.readLine()+"\n"+fromServer.readLine());
			toServer.println("AUTH LOGIN");
			fromServer.readLine();
			//System.out.println("auth: " + fromServer.readLine());
			toServer.println(userBase64);
			fromServer.readLine();
			//System.out.println("user: " + fromServer.readLine());
			toServer.println(passwordBase64);
			ReMessage = fromServer.readLine().toString();
			statusCode = Integer.parseInt(ReMessage.substring(0,3));
			if(statusCode!=235) {
				socketSend.close();
				return statusCode;
			}
			System.out.println("password: " + ReMessage);
			
			toServer.println("MAIL FROM: " + "<" + sendUser.getUserName() + ">");
			System.out.println("mail from: " + fromServer.readLine());
			toServer.println("RCPT TO: " + "<" + Mail.getAddressee() + ">");
			System.out.println("RCPT TO : " + fromServer.readLine());
			
			toServer.println("DATA");
			fromServer.readLine();
			//邮件头部
			toServer.println("From: "+ "<"+sendUser.getUserName()+">");
			toServer.println("To:" + "<"+Mail.getAddressee()+">");
            toServer.println("Subject: =?UTF-8?B?"+ Base64.getEncoder().encodeToString(Mail.getSubject().getBytes("UTF-8"))+"?=");
            if(Mail.getAnnex()) {
            	toServer.println("Content-Type: multipart/mixed;boundary=\"minfu\"");
            	toServer.println("MIME-Version: 1.0");
            	toServer.println("\n--minfu");
            	toServer.println("Content-Type: multipart/alternative; boundary=\"part_minfu\"");
            	toServer.println("\n--part_minfu");
            	toServer.println("Content-Type: text/plain; charset=\"UTF-8\"");
                toServer.println("Content-Transfer-Encoding: base64\n");             
            }else
            {
            	toServer.println("Content-Type: multipart/alternative; boundary=\"minfu\"");
            	toServer.println("MIME-Version: 1.0");
            	toServer.println("\n--minfu");
            	toServer.println("Content-Type: text/plain; charset=\"UTF-8\"");
                toServer.println("Content-Transfer-Encoding: base64");
                toServer.println();
            } 
            //正文
            toServer.println(Base64.getEncoder().encodeToString(Mail.getBody().getBytes("UTF-8")));
            //附件
            if(Mail.getAnnex()) {
            	toServer.println("\n--part_minfu--");
            	toServer.println("\n--minfu");
            	toServer.println("Content-Type:multipart/mixed");
                String fileName = new String(Mail.getFilePath().substring(Mail.getFilePath().lastIndexOf('\\')+1));
            	toServer.println("	"+"\"=?UTF-8?B?"+Base64.getEncoder().encodeToString(fileName.getBytes("UTF-8"))+"?=\"");
            	toServer.println("Content-Transfer-Encoding: base64\n");
            	File file = new File(Mail.getFilePath().replaceAll("/","\\"));

            	FileInputStream is = new FileInputStream(file); 
            	byte[] data =new byte[(int)file.length()];
            	is.read(data);
            	toServer.println(new BASE64Encoder().encode(data));
            	is.close();
            }
            // --minfu--表multipart结束
            // <CR><LF>.<CR><LF>  为特殊标记，表示邮件结束
            toServer.println("\n--minfu--\n");
            toServer.println(".");
            fromServer.readLine();
            //System.out.println("成功: " + );
			socketSend.close();
			return 777;
			} catch (UnknownHostException e) {
				return 111; //无效的主机地址
				//System.out.println("无效的主机");
			} catch (ConnectException e) {
				return 222; 
				//System.out.println("连接被拒绝");
			} catch (SocketTimeoutException e) {
				return 333;
				//System.out.println("连接超时");
			}
	catch(IOException e)
	 {
        e.printStackTrace();
     }
		return 0;
	}
}
