package vn.com.core.common.mail;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.activation.MailcapCommandMap;
import javax.activation.MimetypesFileTypeMap;
import javax.mail.*;
import javax.mail.internet.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;

public class SMTPMail {

    private String mstrHostName;
    private String mstrUserName;
    private String mstrPassword;
    private static String[] mastrEmailList = null;
    private boolean mblnAuthentic = false;
    private boolean mdblDebug = false;
    private Authenticator auth = new SMTPAuthenticator();
    private Session mSession = null;
    private InternetAddress[] mAddressTo = null;
    private String mstrMailType = "text/html; charset=UTF-8";
    private String mstrSubjectType = "UTF-8";
    private Vector mvtAttachedFiles = new Vector();
    private MeetingRequest meetingRequest = null;

    public SMTPMail(String HostName) {
        this.mstrHostName = HostName;
        this.mblnAuthentic = false;
    }

    public SMTPMail(String HostName, String Auth_User, String Auth_Pwd) {
        this.mstrHostName = HostName;
        this.mstrUserName = Auth_User;
        this.mstrPassword = Auth_Pwd;
        this.mblnAuthentic = true;
    }

    public void setMailType(String Mail_Type) {
        this.mstrMailType = Mail_Type;
    }

    public void setEmailList(String[] strEmailList)
            throws Exception {
        int i = 0;
        try {
            mastrEmailList = strEmailList;
            this.mAddressTo = new InternetAddress[mastrEmailList.length];
            for (i = 0; i < mastrEmailList.length; i++) {
                this.mAddressTo[i] = new InternetAddress(mastrEmailList[i]);
            }
        } catch (AddressException e) {
            throw new AddressException("Recipient " + mastrEmailList[i] + " is not valid");
        }
    }

    public void setSubjectType(String mstrSubjectType) {
        this.mstrSubjectType = mstrSubjectType;
    }

    public void setMeetingRequest(MeetingRequest obj) {
        this.meetingRequest = obj;
    }

    public void addAttachedFile(String strFileName)
            throws Exception {
        MimeBodyPart mbp = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(strFileName);
        mbp.setDataHandler(new DataHandler(fds));
        mbp.setDisposition("attachment");
        mbp.setFileName(fds.getName());
        this.mvtAttachedFiles.addElement(mbp);
    }

    public void postMail(String subject, String content, String from)
            throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.host", this.mstrHostName);
        if (this.mblnAuthentic) {
            props.put("mail.smtp.auth", "true");
            this.mSession = Session.getDefaultInstance(props, this.auth);
        } else {
            props.put("mail.smtp.auth", "false");
            this.mSession = Session.getDefaultInstance(props);
        }
        this.mSession.setDebug(this.mdblDebug);
        MimeMessage message = new MimeMessage(this.mSession);
        InternetAddress addressFrom = new InternetAddress(from);
        message.setFrom(addressFrom);
        message.setRecipients(Message.RecipientType.TO, this.mAddressTo);
        message.setSubject(subject, this.mstrSubjectType);
        Multipart multipart = new MimeMultipart();

        //Content body
        MimeBodyPart contentBody = new MimeBodyPart();
        contentBody.setContent(content, this.mstrMailType);
        multipart.addBodyPart(contentBody);

        //Attachments
        if ((this.mvtAttachedFiles != null) && (this.mvtAttachedFiles.size() > 0)) {
            for (int i = 0; i < this.mvtAttachedFiles.size(); i++) {
                multipart.addBodyPart((MimeBodyPart) this.mvtAttachedFiles.elementAt(i));
            }
        }

        //Meeting request
        if (this.meetingRequest != null) {
            //register the text/calendar mime type
            MimetypesFileTypeMap mimetypes = (MimetypesFileTypeMap) MimetypesFileTypeMap.getDefaultFileTypeMap();
            mimetypes.addMimeTypes("text/calendar ics ICS");
            //register the handling of text/calendar mime type
            MailcapCommandMap mailcap = (MailcapCommandMap) MailcapCommandMap.getDefaultCommandMap();
            mailcap.addMailcap("text/calendar;; x-java-content-handler=com.sun.mail.handlers.text_plain");

            message.addHeaderLine("method=REQUEST");
            message.addHeaderLine("charset=UTF-8");
            message.addHeaderLine("component=VEVENT");

            BodyPart calendarPart = buildCalendarPart(this.meetingRequest);
            multipart.addBodyPart(calendarPart);
        }

        message.setContent(multipart);
        message.setSentDate(new Date());
        message.saveChanges();
        Transport transport = this.mSession.getTransport("smtp");
        transport.connect(this.mstrHostName, this.mstrUserName, this.mstrPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    private BodyPart buildCalendarPart(MeetingRequest obj) throws Exception {
        SimpleDateFormat iCalendarDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmm'00'");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        BodyPart calendarPart = new MimeBodyPart();
        String calendarContent = CALENDAR_MEETING_REQUEST_TEMPLATE
                .replaceAll("@P_DTSTART", iCalendarDateFormat.format(obj.getStart()))
                .replaceAll("@P_DTEND", iCalendarDateFormat.format(obj.getEnd()))
                .replaceAll("@P_SUMMARY", obj.getTitle())
                .replaceAll("@P_ORGANIZER", obj.getEmailOrganize())
                .replaceAll("@P_RECURRENCE", obj.getRecurrenceRule())
//                .replaceAll("@P_ATTENDEE", obj.getAttendeeMail())
                .replaceAll("@P_UID", dateFormat.format(new Date())
                        +dateFormat.format(obj.getStart())
                        +dateFormat.format(obj.getEnd())
                        +obj.toString())
                .replaceAll("@P_LOCATION", obj.getLocation());
        calendarPart.addHeader("Content-Class", "urn:content-classes:calendarmessage");
        calendarPart.setContent(calendarContent, "text/calendar;method=REQUEST;charset=utf-8");
        return calendarPart;
    }

    public static void main(String[] args)
            throws Exception {
        try {
            SMTPMail smtpMailSender = new SMTPMail("mail.fpt.com.vn", "sondt18@fpt.com.vn", "******");
            smtpMailSender.setEmailList(new String[]{"tunghuynh.tn@gmail.com", "tunghuynh.ictn@gmail.com", "sondt18@fpt.com.vn"});

            //Start - Mail Meeting Request
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, 1);
            Date start = cal.getTime();
            cal.add(Calendar.HOUR_OF_DAY, 3);
            Date end = cal.getTime();
            MeetingRequest meetingRequest = new MeetingRequest("Họp tiến độ Meeting Room", "Paris, Keang Nam, Hà Nội", start, end, "sondt18@fpt.com.vn");
            meetingRequest.setRecurrenceRule("FREQ=WEEKLY;COUNT=5;INTERVAL=1;BYDAY=MO,TH;WKST=SU");
//            smtpMailSender.setMeetingRequest(meetingRequest);
            //End - Mail Meeting Request

            //Start - Attachment
            smtpMailSender.addAttachedFile("D:/settings.zip");
            //End - Attachment
            smtpMailSender.postMail(
                    "SONDT18 FIS FTU",
                    "<font size = 15 color=blue>Xin chao tat ca moi nguoi</font><br>",
                    "sondt18@fpt.com.vn");
            System.out.println("Sucessfully Sent mail to All Users");
            System.out.println("20170718 SONDT18 - Bo sung UTF-8 cho Subject");
            System.out.println("20190617 SONDT18 - Bo sung Send Meeting Request");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class SMTPAuthenticator
            extends Authenticator {

        private SMTPAuthenticator() {
        }

        public PasswordAuthentication getPasswordAuthentication() {
            String username = SMTPMail.this.mstrUserName;
            String password = SMTPMail.this.mstrPassword;
            return new PasswordAuthentication(username, password);
        }
    }

    private static String CALENDAR_MEETING_REQUEST_TEMPLATE
            = "BEGIN:VCALENDAR\n"
            + "METHOD:REQUEST\n"
            + "PRODID:Meeting Room - FTU\n"
            + "VERSION:2.0\n"
            + "BEGIN:VTIMEZONE\n"
            + "TZID:SE Asia Standard Time\n"
            + "BEGIN:STANDARD\n"
            + "DTSTART:@P_DTSTART\n"
            + "TZOFFSETFROM:+0700\n"
            + "TZOFFSETTO:+0700\n"
            + "END:STANDARD\n"
            + "BEGIN:DAYLIGHT\n"
            + "DTSTART:@P_DTSTART\n"
            + "TZOFFSETFROM:+0700\n"
            + "TZOFFSETTO:+0700\n"
            + "END:DAYLIGHT\n"
            + "END:VTIMEZONE\n"
            + "BEGIN:VEVENT\n"
            + "DTSTAMP:@P_DTSTART\n"
            + "DTSTART:@P_DTSTART\n"
            + "DTEND:@P_DTEND\n"
            + "RRULE:@P_RECURRENCE\n"
            + "SUMMARY:@P_SUMMARY\n"
            + "UID:@P_UID\n"
            + "ORGANIZER:MAILTO:@P_ORGANIZER\n"
//            + "ATTENDEE;ROLE=REQ-PARTICIPANT;PARTSTAT=NEEDS-ACTION;RSVP=TRUE;CN=@P_ATTENDEE:MAILTO:@P_ATTENDEE\n"
            + "LOCATION:@P_LOCATION\n"
            + "SEQUENCE:0\n"
            + "PRIORITY:5\n"
            + "CLASS:PUBLIC\n"
            + "STATUS:CONFIRMED\n"
            + "TRANSP:OPAQUE\n"
            + "BEGIN:VALARM\n"
            + "ACTION:DISPLAY\n"
            + "DESCRIPTION:REMINDER\n"
            + "END:VALARM\n"
            + "END:VEVENT\n"
            + "END:VCALENDAR";
}
