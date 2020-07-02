package email;

public class Mail {
    private String Addressee;
    private String subject;
    private String body;
    boolean annex;
    private String filePath;
    public void setMailContent(String addr, String subj,String body_) {
    	Addressee = addr;
    	subject = subj;
    	body = body_;
    }
    public void setFilePath(boolean an,String path) {
    	filePath = path;
    	annex = an;
    }
    public String getAddressee() {
    	return Addressee;
    }
    public String getSubject() {
    	return subject;
    }
    public String getBody() {
    	return body;
    }
    public boolean getAnnex() {
    	return annex;
    }
    public String getFilePath() {
    	return filePath;
    }
    public Mail(boolean an) {
    	annex = an;
    }
}
