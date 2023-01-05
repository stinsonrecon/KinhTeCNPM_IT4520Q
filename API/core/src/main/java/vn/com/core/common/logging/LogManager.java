package vn.com.core.common.logging;//package com.fis.fw.common.logging;
//
//import com.fis.fw.common.thread.ThreadManager;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.PreDestroy;
//import java.util.ArrayList;
//
///**
// * com.fis.fw.common.logging.LogManager
// * TungHuynh
// * Created by sondt18@fpt.com.vn
// * Date 11/05/2019 9:47 AM
// */
//@Component
//public class LogManager extends ThreadManager {
//
//    @Autowired
//    LogThread logThread;
//
//    @Override
//    public String getName() {
//        return "LogManager";
//    }
//
//    @Override
//    public void doProcess(ArrayList items) {
//        logThread.setItems(items);
//        executorService.submit(logThread);
//    }
//
//    @PostConstruct
//    @Override
//    public synchronized void listen() {
//        super.listen();
//    }
//
//    @PreDestroy
//    @Override
//    public void stop() {
//        super.stop();
//    }
//}
