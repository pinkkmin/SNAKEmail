package email;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
	private String userName;
	private String password;
	private String serverAddr;
	public User() {}
	public User(String name,String pass) {
		setUser(name,pass);
	}
	public boolean setServerAddr() {
		String check = "^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$";  
	     Pattern regex = Pattern.compile(check);  
	     Matcher matcher = regex.matcher(userName);   
		String[]  temp = userName.split("@");
		if(temp.length==1) return false;
		else serverAddr = temp[1];
		return matcher.matches();
	}
	public String getUserName() {
		return this.userName;
	}
	public String getPassword() {
		return this.password;
	}
	public String getServerAddr() {
		return serverAddr;
	}
	public boolean setUser(String name,String pass) {
		userName = name;
		password = pass;
		if(setServerAddr()) return true;
		else return false;
	}
	public boolean login() {
		String userBase64;
		String passwordBase64;
		try {
			 userBase64 = Base64.getEncoder().encodeToString(userName.getBytes("UTF-8"));
			 passwordBase64 = Base64.getEncoder().encodeToString(password.getBytes("UTF-8"));
				try {
					Socket socketLogin = new Socket("SMTP." + serverAddr,25);
					//捕获异常--socketLogin 
					OutputStream outToServer = socketLogin.getOutputStream();
					PrintWriter toServer = new PrintWriter(outToServer,true);
					InputStream inFromServer = socketLogin.getInputStream();
					BufferedReader fromServer = new BufferedReader(new InputStreamReader(inFromServer));
					
					String ReMessage = fromServer.readLine().toString();
					int status = Integer.parseInt(ReMessage.substring(0,3));
					//System.out.println("login: "+status);
					if(status!=220) { 
						socketLogin.close();
						return false;
					}
					toServer.println("HELO CMF");
					ReMessage = fromServer.readLine().toString();
					status = Integer.parseInt(ReMessage.substring(0,3));
					if(status!=250) {
						socketLogin.close();
						return false;
					} 
					if(serverAddr.indexOf("qq")!=-1||serverAddr.indexOf("QQ")!=-1)
					{fromServer.readLine();
					  fromServer.readLine();	
					}
					toServer.println("AUTH LOGIN");
					fromServer.readLine();
					toServer.println(userBase64);
					fromServer.readLine();
					toServer.println(passwordBase64);
					ReMessage = fromServer.readLine().toString();
					status = Integer.parseInt(ReMessage.substring(0,3));
					if(status!=235) {
						socketLogin.close();
						return false;
					}
					System.out.println(ReMessage);
					toServer.println("QUIT");
					socketLogin.close();
					return true;
					
			}catch (UnknownHostException e) 
				{
					return false;
			}catch (ConnectException e) 
				{
				    return false;
			}catch (SocketTimeoutException e)
				{
				    return false;
			}catch(IOException e) 
				{
		        	e.printStackTrace();
		     }
		}catch(UnsupportedEncodingException e)
		 	{
	         	System.out.println("Error :" + e.getMessage());
	     }
		return false;
	}
}
