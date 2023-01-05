package vn.com.core.common.mail;

import org.java_websocket.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vn.com.core.common.thread.Task;
import vn.com.core.common.utils.DateUtil;
import vn.com.core.common.utils.FileUtil;
import vn.com.core.common.utils.StringUtil;

import java.io.File;
import java.util.Date;
import java.util.List;

public class MailThread extends Task {
    private final Logger logger = LoggerFactory.getLogger(MailThread.class);

    @Override
    public Integer call() throws Exception {
        List lst = getItems();
        try {
            if (lst != null && !lst.isEmpty()) {
                for (Object item : lst) {
                    if (item instanceof MessageMail) {
                        MessageMail msg = (MessageMail) item;
                        logger.info("========== START SEND MAIL =========");
                        logger.info("Host: " + msg.getHost());
                        logger.info("User: " + msg.getUser());
                        logger.info("Subject: " + msg.getSubject());
                        logger.info("Sender: " + msg.getSender());
                        logger.info("Object: " + StringUtil.objectToJson(msg));
                        SMTPMail smtpMailSender = new SMTPMail(msg.getHost(), msg.getUser(), new String(Base64.decode(msg.getPass())));
                        smtpMailSender.setEmailList(msg.getReceives());
                        smtpMailSender.setMeetingRequest(msg.getMeetingRequest());

                        if (msg.getAttachments() != null && !msg.getAttachments().isEmpty()) {
                            String folder = msg.getFolderAttachments() + "/" + DateUtil.dateToString(new Date(), "yyyy/MM/dd");
                            File f = new File(folder);
                            if (!f.exists()){
                                f.mkdirs();
                            }
                            for (Attachment attachment : msg.getAttachments()) {
                                String path = folder + "/" + DateUtil.dateToString(new Date(), "HHmmssS_") + attachment.getFileName();
                                FileUtil.decodeBase64StringToFile(attachment.getBase64(), path);
                                smtpMailSender.addAttachedFile(path);
                            }
                            logger.info("Processed Attachment");
                        }

                        smtpMailSender.postMail(msg.getSubject(), msg.getContent(), msg.getSender());
                        logger.info("========== END SEND MAIL =========");
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.toString(), e);
            return 0;
        }
        return 1;
    }
}
