package vn.com.core.common.mail;

import vn.com.core.common.thread.ThreadManager;

import java.util.ArrayList;

public class MailManager extends ThreadManager {
    @Override
    public String getName() {
        return "MailManager";
    }

    @Override
    public void doProcess(ArrayList items) {
        MailThread thread = new MailThread();
        thread.setItems(items);
        executorService.submit(thread);
    }
}
