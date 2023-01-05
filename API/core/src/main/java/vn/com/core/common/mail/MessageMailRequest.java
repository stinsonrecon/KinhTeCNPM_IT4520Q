package vn.com.core.common.mail;

import java.util.List;

public class MessageMailRequest {
    private String subject;
    private String content;
    private String[] receives;
    private List<Attachment> attachments;
    private MeetingRequest meetingRequest;
    private String eventCode;

    public String getEventCode() {
        return eventCode;
    }

    public void setEventCode(String eventCode) {
        this.eventCode = eventCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getReceives() {
        return receives;
    }

    public void setReceives(String[] receives) {
        this.receives = receives;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    public MeetingRequest getMeetingRequest() {
        return meetingRequest;
    }

    public void setMeetingRequest(MeetingRequest meetingRequest) {
        this.meetingRequest = meetingRequest;
    }
}
