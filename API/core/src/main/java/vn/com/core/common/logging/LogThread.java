package vn.com.core.common.logging;//package com.fis.fw.common.logging;
//
//import com.fis.fw.core.entity.LogSend;
////import com.fis.fw.core.service.LogActionService;
////import com.fis.fw.core.service.LogApiService;
////import com.fis.fw.core.service.LogSendService;
//import com.fis.fw.core.service.LogActionService;
//import com.fis.fw.core.service.LogApiService;
//import com.fis.fw.core.service.LogSendService;
//import org.slf4j.Logger;
//import com.fis.fw.common.thread.Task;
//import com.fis.fw.core.entity.LogAction;
//import com.fis.fw.core.entity.LogApi;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * com.fis.fw.common.logging.LogThread
// * TungHuynh
// * Created by sondt18@fpt.com.vn
// * Date 11/05/2019 9:47 AM
// */
//@Component
//public class LogThread extends Task {
//    private final Logger logger = LoggerFactory.getLogger(LogThread.class);
//
//    @Autowired
//    LogApiService logApiService;
//
//    @Autowired
//    LogActionService logActionService;
//
//    @Autowired
//    LogSendService logSendService;
//
//    @Override
//    public Integer call() throws Exception {
//        List lstLog = getItems();
//        try {
//            if (lstLog != null && !lstLog.isEmpty()) {
//                List<LogApi> lstApi = new ArrayList();
//                List<LogAction> lstAction = new ArrayList();
//                List<LogSend> lstSend = new ArrayList();
//                for (Object item : lstLog) {
//                    if (item instanceof LogApi) {
//                        lstApi.add((LogApi) item);
//                    } else if (item instanceof LogAction) {
//                        lstAction.add((LogAction) item);
//                    } else if (item instanceof LogSend) {
//                        lstSend.add((LogSend) item);
//                    }
//                }
//                if (!lstApi.isEmpty()) {
//                    logApiService.save(lstApi);
//                }
//                if (!lstAction.isEmpty()) {
//                    logActionService.save(lstAction);
//                }
//                if (!lstSend.isEmpty()) {
//                    logSendService.save(lstSend);
//                }
//            }
//        } catch (Exception e) {
//            logger.error(e.toString(), e);
//            return 0;
//        }
//        return 1;
//    }
//}
