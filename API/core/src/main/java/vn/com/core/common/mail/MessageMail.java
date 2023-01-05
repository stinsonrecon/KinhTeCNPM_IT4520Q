package vn.com.core.common.mail;

public class MessageMail extends MessageMailRequest{
    private String host;
    private String user;
    private String pass;
    private String sender;
    private String folderAttachments;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getFolderAttachments() {
        return folderAttachments;
    }

    public void setFolderAttachments(String folderAttachments) {
        this.folderAttachments = folderAttachments;
    }
}
