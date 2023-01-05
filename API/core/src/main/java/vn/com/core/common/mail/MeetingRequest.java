package vn.com.core.common.mail;

import java.util.Date;

public class MeetingRequest {
    private String title;
    private String location;
    private Date start;
    private Date end;
    private String emailOrganize;
    private String recurrenceRule;
//    private String attendeeMail;

    public MeetingRequest(){}

    public MeetingRequest(String title, String location, Date start, Date end, String emailOrganize) {
        this.title = title;
        this.location = location;
        this.start = start;
        this.end = end;
        this.emailOrganize = emailOrganize;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }


    public String getEmailOrganize() {
        return emailOrganize;
    }

    public void setEmailOrganize(String emailOrganize) {
        this.emailOrganize = emailOrganize;
    }

//    public String getAttendeeMail() {
//        return attendeeMail;
//    }
//
//    public void setAttendeeMail(String attendeeMail) {
//        this.attendeeMail = attendeeMail;
//    }

    public String getRecurrenceRule() {
        return recurrenceRule;
    }

    public void setRecurrenceRule(String recurrenceRule) {
        this.recurrenceRule = recurrenceRule;
    }
}
