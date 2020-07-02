package email;
import java.net.*;
import java.util.Base64;
import sun.misc.BASE64Decoder;
import java.io.*;
public class pop {
	 private String Addressee;
	 private String cc;
	 private String subject;
	 private String body;
	 private String fileName;
	 boolean annex;
	 private int emailCount;
	 private boolean isEndLine;
	 private int multipart;
	 String Mainboundary = new String();
	// private ByteArrayOutputStream buffer = new ByteArrayOutputStream();	 
	public pop() {
		annex = false;
		body = "";
	}
	 public  void qpDecoding(ByteArrayOutputStream buffer,String str)
	    {
	        try
	        {
	            StringBuffer sb = new StringBuffer(str);
	            for (int i = 0; i < sb.length(); i++)
	            {
	                if (sb.charAt(i) == '\n' && sb.charAt(i - 1) == '=')
	                {
	                    sb.deleteCharAt(i - 1);
	                }
	            }
	            str = sb.toString();
	            byte[] bytes = str.getBytes("US-ASCII");
	            for (int i = 0; i < bytes.length; i++)
	            {
	                byte b = bytes[i];
	                if (b != 95)
	                {
	                    bytes[i] = b;
	                }
	                else
	                {
	                    bytes[i] = 32;
	                }
	            }   
	            int length = bytes.length;
	            if(length==76) length = 75;
	            for (int i = 0; i < length; i++)
	            {
	                int b = bytes[i];
	                if (b == '='){
	                    try {   
	                    	i++;
	                    	if(i==length) break;
	                        int u = Character.digit((char) bytes[i], 16);
	                        i++;
	                        int l = Character.digit((char) bytes[i], 16);
	                        if (u == -1 || l == -1)  continue;
	                        buffer.write((char) ((u << 4) + l));
	                    }
	                    catch (ArrayIndexOutOfBoundsException e)
	                    {
	                        
	                    }
	                }
	                else
	                    buffer.write(b);
	            }
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }
	    }
	public String getAddressee() {
    	return Addressee;
    }
    public String getCc() {
    	return cc;
    }
    public String getSubject() {
    	return subject;
    }
    public String getBody() {
    	return body;
    }
    public int getCount() {
    	return emailCount;
    }
    public String getFileName() {
    	return fileName;
    } 
    /* is Base64 ? true of false
     * */
    public boolean isBase64(String reply) {
    	 if(reply.equals("base64")||reply.equals("Base64")||reply.equals("BASE64"))
    		 return true;
    	return false;
    }
    
    /* is UTF-8 ? true of false
     * */
    public boolean isGBK(String reply) {
    	if(reply.equals("gbk")||reply.equals("GBK"))
    		return true;
    	return false;
    }
    public boolean isUTF8(String reply) {
    	if(reply.equals("UTF-8")||reply.equals("utf-8"))
    		return true;
    	return false;
    }
    public String doMialFrom(String reply) throws Exception{    
    	String Addressee = new String("");
    	try{
    	if(reply.indexOf("=?")!=-1) {
    		/* from: =?fromType?codeType?coding?= <xxxx@nuaa.edu.cn>
        	 * fromType: UTF-8 GBK gb2312
        	 * codeType:  B/Q  Base64 
        	*/
        	String fromType = reply.substring(reply.indexOf("=?")+2);
        	char codeType  = fromType.charAt(fromType.indexOf("?")+1);
        	fromType = fromType.substring(0,fromType.indexOf("?"));
        	reply = reply.substring(reply.indexOf(codeType+"?")+2);
        	String coding = reply.substring(0,reply.indexOf("?="));
        	/* 多余
        	 * if(isUTF8(fromType))  
            	reply = reply.substring(reply.indexOf(fromCoding+"?")+2); // UTF-8
        	else if(isGBK(fromType)) reply = reply.substring(16); // GBK
            else reply = reply.substring(17);// GB2312 */             
        	if(codeType=='b'||codeType=='B')  // BASE64
        	Addressee = new String(Base64.getMimeDecoder().decode(coding), fromType);
        	else { 
        		ByteArrayOutputStream buffer = new ByteArrayOutputStream();     	    
                qpDecoding(buffer,reply);
                Addressee = new String(buffer.toByteArray(), fromType);
        	}
        	Addressee += " ";
      	}
        else if(reply.indexOf('<')>6){
        	Addressee = reply.substring(6,reply.indexOf('<'));
        	// from: mingfu <xxxx@nuaa.edu.cn>
        }  
        else Addressee = ""; // from: <xxxx@nuaa.edu.cn>
    	if(reply.indexOf('<')!=-1)
        Addressee += reply.substring(reply.indexOf('<')+1, reply.indexOf('>'));
    	}catch(Exception e) {
    		return new String("");
    	}
        return Addressee;
    }
    public String doMialSubject(String reply)throws Exception {
    	String subject = new String("");
    	try {
    		if(reply.indexOf("=?")!=-1) {
            	/* Subject: =?subjectType?coding?coding=?=
            	 * subjectType: UTF-8 GBK gb2312
            	 * codeType:  B/Q  Base64 
            	*/ 
            	String subjectType = reply.substring(reply.indexOf("=?")+2);
            	char subjectCoding  = subjectType.charAt(subjectType.indexOf("?")+1);
            	subject = subjectType;
            	subjectType = subjectType.substring(0,subjectType.indexOf("?"));
            	subject = subject.substring(subject.indexOf(subjectCoding+"?")+2);
            	subject = subject.substring(0,subject.indexOf("?="));
            	if(subjectCoding=='b'||subjectCoding=='B') // BASE64编码
            		subject = new String(Base64.getMimeDecoder().decode(subject), subjectType);
            	else {
            		ByteArrayOutputStream buffer = new ByteArrayOutputStream();     	    
                    qpDecoding(buffer,reply);
                    subject = new String(buffer.toByteArray(), subjectType);
            	}
    		}
            else {//Subject: 未编码纯文本格式主题
            	subject = new String(reply.substring(9).getBytes(),"UTF-8");
            }
    	}catch(Exception e) {	
    	}
    	return subject;
    }
    
    public String doBoundary(BufferedReader fromServer,String reply) throws Exception {
    	while(true) {
    		if(reply.indexOf("boundary=")!=-1)  break;
    		// break in boundary="part-boundary"
				reply = fromServer.readLine();
			}   	
    	// return cut (boundary=).length + '"' = 9+1=10 
    	reply = reply.substring(reply.indexOf("boundary=")+10,reply.length());
    	return reply.substring(0,reply.indexOf("\""));
    }
    
    /* get multipart
     * true--mixed false--alternative
     * */
    public int getMultipart(String reply) {
    	if(reply.indexOf("multipart")==-1)  return -1;
    	String type = reply.substring(reply.indexOf("/")+1,reply.indexOf(";"));
    	if(type.equals("mixed")) return 1;
    	else if(type.equals("related")) return 2;
    	
    	return 0;
    }
    
    /* get a boundary of multipart
     * */
    public String doMialBoundary(BufferedReader fromServer,String reply) throws Exception {
    	// to get Mainmultipart   ps:true--mixed false--alternative
    	multipart = getMultipart(reply);
    	// to get the Mainboundary
    	return doBoundary(fromServer,reply);
    }
    
    /* is break in From/subject/Content-type
     * 因为上述三者出现的顺序无固定规律
     * */
    public int isBreak(String reply) {
    	if((reply.indexOf("FROM:")!=-1 || reply.indexOf("From:")!=-1)&&(reply.indexOf("Subject:")!=-1 || reply.indexOf("SUBJECT:")!=-1))
    	return -1;
    	if(reply.indexOf("FROM:")!=-1 || reply.indexOf("From:")!=-1) return 1;
		if(reply.indexOf("Subject:")!=-1 || reply.indexOf("SUBJECT:")!=-1)return 2;
    	if(reply.indexOf("Content-Type:")!=-1) return 3;
    	return -1;
    }
    
    /* doFBS 
     * get From or Subject or boundary   
     * */
    public void doFBS(BufferedReader fromServer,String reply ) {
    	try { 
    	while(true) {
         	if(isBreak(reply)!=-1)  break;
				reply = fromServer.readLine();
			}    	
         if(isBreak(reply)==1)  Addressee = doMialFrom(reply);
         else if(isBreak(reply)==2) subject = doMialSubject(reply);
         else Mainboundary = doMialBoundary(fromServer,reply);
    	}
    	catch(Exception e) {
    		
    	}
    }
    /* 获取一个part的文本内容 encodingType： = 0--不编码   1--BASE64 2--quoted-printable
     * --boundary
     * Content-Type: text/**; charset="**"
     * Content-Transfer-Encoding: *****
     * 换行
     * 文本内容
     * */
    public String doContent(BufferedReader fromServer,String boundary,String charset,int encodingType) {
    	String reply = new String(" ");   
    	String codingText = new String("");
    	 try {
    		 // 未编码 encodingType=0
    		 if(encodingType==0) {
    			while (true) { 
    				 if(reply.indexOf("--"+boundary)!=-1||reply.equals("")) break;     
    				 if(reply.equals("--"+Mainboundary+"--")) break;
    				 codingText += reply;
    				reply = fromServer.readLine();
    			}
    		 }
    		 else { //get the encoding body
    			 if(encodingType==1)  {	 
    				 // Base64  encodingType=1
    				 while (true) { 
    					 if(reply.indexOf("--"+boundary)!=-1||reply.equals("")) break;
    					 if(reply.equals("--"+Mainboundary+"--")) break;
    					 codingText += reply;
    					 reply = fromServer.readLine(); 
    				 }        
    				 codingText =  new String(Base64.getMimeDecoder().decode(codingText),charset);
    			 }
    			 else {   
    				// quoted-printable encodingType=2
    				 ByteArrayOutputStream buffer = new ByteArrayOutputStream();
    				 while (true) { 
    					 if(reply.indexOf("--"+boundary)!=-1||reply.equals("")) break; 
    					 if(reply.equals("--"+Mainboundary+"--")) break;
    					 qpDecoding(buffer,reply);
    					 reply = fromServer.readLine();
    				 }
    				 codingText = new String(buffer.toByteArray(), charset);
    			 }     
    		 }
    		 if(reply.equals("--"+Mainboundary+"--"))  isEndLine = true;	
    		 //if(reply.indexOf("--"+boundary+"--")!=-1) isEndLine = true;
    		 return codingText;
	}catch(Exception e) {
		 isEndLine = true;
		return new String("");
	} 	 
} 
    /* get content-type 
     * */
    public String getCharset(BufferedReader fromServer,String reply) throws Exception{
    	while(true) {
			 if(reply.indexOf("Content-Type: text")!=-1||reply.indexOf("Content-Type:text")!=-1)  break;
			 if(reply.equals("--"+Mainboundary+"--")||reply.equals(""))  {
				 isEndLine = true;
			 break;}
			 reply = fromServer.readLine();
		 }
    	 if(isEndLine||reply.equals("")) return new  String();
		 if(reply.indexOf("\"")!=-1)
			return reply.substring(reply.indexOf("=")+2,reply.length()-1); 
		 
		 return   reply.substring(reply.indexOf("=")+1,reply.length()); 
    }
    public int  getEncodingType(BufferedReader fromServer,String reply) throws Exception{
    	while(true) {
			 if(reply.indexOf("Content-Transfer-Encoding:")!=-1||reply.equals(""))  break;
			 if(reply.equals("--"+Mainboundary+"--"))  {
				 isEndLine = true;
			     break;}	
			 reply = fromServer.readLine();
		 } 
    	 if(isEndLine) return -1;
		 if(reply.indexOf("Content-Transfer-Encoding:")!=-1) 
		  {  reply = reply.substring(reply.indexOf(": ")+2);  			  			
			 if(isBase64(reply) ) {
				 while(true) {
					 reply = fromServer.readLine();//消除换行
					 if(reply.equals("")) break;
					 if(reply.equals("--"+Mainboundary+"--"))  {
						 isEndLine = true;
					     break;}	
					 }
				 return   1;
			 }
			 else  {  // quoted-printable
				 while(true) {
				 reply = fromServer.readLine();//消除换行
				 if(reply.equals("")) break;
				 if(reply.equals("--"+Mainboundary+"--"))  {
					 isEndLine = true;
				     break;}	
				 }
				 return   2;   
			 }
		 }
		 // 如果是换行 那么就是不编码格式 encodingType=0
		 return 0;
    	}
    /* doMialBody
     * --boundary
     * Content-Type: text/plain; charset="xx"
     * Content-Transfer-Encoding: xx
     * 换行
     * encoding body
     * */
    public boolean doMialBody(BufferedReader fromServer,String reply,String boundary) throws Exception{ 
    	String charset = new String("");
    	int encodingType = 0;
    	 try { 
    		 // get charset
    		 //reply = fromServer.readLine();
    		 while(!reply.equals("--"+boundary+"--")&&!isEndLine) {	
    			 charset = getCharset(fromServer,reply);	
    			 if(isEndLine)  break;
    			 // get encoding-type
    			 reply = fromServer.readLine();
    			 encodingType = getEncodingType(fromServer,reply);
    			 if(isEndLine)  break;
    			
    			 body +=  doContent(fromServer, boundary, charset, encodingType); 
    			 reply = fromServer.readLine();
    		 }
    		if(reply.indexOf("--"+boundary+"--")!=-1||isEndLine) return true;
	}catch(Exception e) {
	}
    	 return  false;
	}
    /*  do a part of mixed mail
     * */
    public boolean doMailPart(BufferedReader fromServer,String boundary) {	
    	 //Content-Type: multipart/mixed;
    	// reply in ---mian-boundary
    	try {
    		String partboundary = new String();
    		String reply  = fromServer.readLine(),textCharset = new String("");
    		// reply in Content-Type: multipart/alternative;  	
    		while(true) {
    			if(reply.indexOf("Content-Type:")!=-1||reply.equals(""))  break;
    			reply = fromServer.readLine();
    		}
    		// reply in Content-Type: multipart/
    		int partMultipart = getMultipart(reply);
    		if(partMultipart==-1) doMialBody(fromServer,reply,boundary);
    	
    		else {
    		partboundary = doBoundary(fromServer,reply);
    		while(true) {
    			if(reply.indexOf("--"+partboundary)!=-1)  break;
    			reply = fromServer.readLine();
    		}
    			doMialBody(fromServer,reply,partboundary);
    			if(multipart==1) {
    			 if(!doMailAnnex(fromServer,reply)) isEndLine = true;
    			}
    		}
    	}catch(Exception e) {
    		
    	}
        return false;
    }
    
    public boolean doMailAnnex(BufferedReader fromServer,String reply ) throws Exception{
    	 try {
    		 	String annaxEncoding= new String();
         		while (true) { 
         			if(reply.indexOf("filename=")!=-1||reply.equals("--"+Mainboundary+"--")) break;
         			if(reply.indexOf("Content-Transfer-Encoding:")!=-1) break;
         			reply = fromServer.readLine();
         		}
         	 if(reply.equals("--"+Mainboundary+"--")) return false;
         	 if(reply.indexOf("filename=")!=-1) {
         		 annex  = true;
         		 doAnnexFileName(reply); 
         	 }
         	 else {
         		annaxEncoding = reply.substring(reply.indexOf(":")+2);
         	 }
         	reply = fromServer.readLine();
        	while (true) { 
                if(reply.indexOf("filename=")!=-1||reply.equals("--"+Mainboundary+"--")) break;
                if(reply.indexOf("Content-Transfer-Encoding:")!=-1) break;
                reply = fromServer.readLine();
            }
        	if(reply.equals("--"+Mainboundary+"--")) return false;
        	 if(reply.indexOf("filename=")!=-1) {
        		 annex  = true;
        		 doAnnexFileName(reply); 
        	 }
        	 else {
        		 annaxEncoding = reply.substring(reply.indexOf(":")+2);
        	 }
        	  
         	FileOutputStream annexfileInput = new FileOutputStream(new File(fileName));     		
         	while (true) { 
                if(reply.equals("")) break;
                reply = fromServer.readLine();
            }
         	reply = fromServer.readLine();
         	 
         	if(isBase64(annaxEncoding))
         	{ // BASE64编码
         		while (true) { 
                  if(reply.indexOf("--"+Mainboundary)!=-1) break;
                  annexfileInput.write(Base64.getMimeDecoder().decode(reply));
                  
                  reply = fromServer.readLine();
              }           	
         	}
             else { // q编码
             	ByteArrayOutputStream buffer = new ByteArrayOutputStream();     	    
             	while (true) { 
                     if(reply.indexOf("--"+Mainboundary)!=-1) break;
                     qpDecoding(buffer,reply);               
                     reply = fromServer.readLine();
                 }  
             	annexfileInput.write(buffer.toByteArray());
             }
         	annexfileInput.close();
         	return true;
    	 }catch(Exception e) {
    		 return false;
    	 }
    }
    public void  doAnnexFileName(String reply)throws Exception {
    	try {
    		if(reply.indexOf("=?")!=-1) {     
            	String Type = reply.substring(reply.indexOf("=?")+2);
            	char Coding  =Type.charAt(Type.indexOf("?")+1);
            	fileName = Type;
            	Type = Type.substring(0,Type.indexOf("?"));
            	String lastFileName = new String("");  	
            	if(fileName.charAt(fileName.indexOf("?=")+2)!='\"') 
            		lastFileName =fileName.substring(fileName.indexOf("?=")+2,fileName.indexOf("\""));
            	fileName = fileName.substring(fileName.indexOf(Coding)+2,fileName.indexOf("?="));
            	if(Coding=='b'||Coding=='B') // BASE64编码
            		fileName = new String(Base64.getMimeDecoder().decode(fileName), Type);
            	else {
            		ByteArrayOutputStream buffer = new ByteArrayOutputStream();     	    
                    qpDecoding(buffer,reply);
                    fileName = new String(buffer.toByteArray(), Type);   
            	}
            	fileName += lastFileName;
    		}
            else {
            	fileName = new String(reply.substring(9).getBytes(),"UTF-8");
            }
    	}catch(Exception e) {	
    	}
    }
    public boolean getEmailCount(User popAuth){
    	try {  
			Socket popClient = new Socket("pop."+popAuth.getServerAddr(),110);
			
			OutputStream outToServer = popClient.getOutputStream();
			PrintWriter toServer = new PrintWriter(outToServer,true);
			InputStream inFromServer = popClient.getInputStream();
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(inFromServer));
			
			fromServer.readLine();
			toServer.println("user "+popAuth.getUserName());
			fromServer.readLine();
			toServer.println("pass "+popAuth.getPassword());
			fromServer.readLine();
			
			toServer.println("stat");
			String connet[] = fromServer.readLine().split(" ");
			emailCount = Integer.parseInt(connet[1]);
			popClient.close();
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
        	return false;
		}
    	catch(Exception e){
    		return false;
    	}
    }
    public boolean getAnnex() {
    	return annex;
    }
	public boolean getEmail(User popAuth,int mailnum) throws NullPointerException {
		try {  
			Socket popClient = new Socket("pop."+popAuth.getServerAddr(),110);
			OutputStream outToServer = popClient.getOutputStream();
			PrintWriter toServer = new PrintWriter(outToServer,true);
			InputStream inFromServer = popClient.getInputStream();
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(inFromServer));
			//Login + get the email
			fromServer.readLine();
			toServer.println("user "+popAuth.getUserName());
			fromServer.readLine();
			toServer.println("pass "+popAuth.getPassword());
			fromServer.readLine();
			toServer.println("retr " + mailnum);
			fromServer.readLine();
			
            // get subject ,from , main-boundary  
			String reply = fromServer.readLine();
            doFBS(fromServer,reply);
            reply = fromServer.readLine(); 
            doFBS(fromServer,reply);
            reply = fromServer.readLine();
            doFBS(fromServer,reply);
            // 此处已经可以得到 from发件人,subject主题,multipart-type和主boundary
            //于是应该去寻找 --boundary
            while (true) { 
        		if(reply.indexOf("--"+Mainboundary)!=-1) break;     
            	reply = fromServer.readLine();
        	}
            // 根据multipart go to ？
            if(multipart==0) {  
            	// go to multipart/alternative  
            	reply = " ";
            	
            	doMialBody(fromServer,reply,Mainboundary);         	
            }else if(multipart==1||multipart==2)
            {               
            	// go to multipart/mixed
           	 doMailPart(fromServer,Mainboundary);  
            }          
            /// end of all
            if(isEndLine) {
            	popClient.close();
    			return true;
            }
            
            while (true) { 
                if (reply.toLowerCase().equals(".")||reply.indexOf("--"+Mainboundary+"--")!=-1)
                    break;
                reply = fromServer.readLine();
            }
			popClient.close();
			return true;
		}
		catch (UnknownHostException e) 
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
        	return false;
		}catch(Exception e)
		{
    		return false;
    	}
		
	}
}
