package vn.com.core.core.dao.impl;//package com.fis.fw.core.dao.impl;////import org.springframework.stereotype.Repository;//import com.fis.fw.common.generics.impl.GenericDaoImpl;//import com.fis.fw.core.dao.LogActionDao;//import com.fis.fw.core.entity.LogAction;////import java.sql.Connection;//import java.sql.PreparedStatement;//import java.util.List;////public class LogActionDaoImpl extends GenericDaoImpl<LogAction, Integer> implements LogActionDao {//    @Override//    public List<LogAction> save(List<LogAction> lst) throws Exception {//        try (Connection conn = dataSource.getConnection()) {//            conn.setAutoCommit(false);//            PreparedStatement psmt = conn.prepareStatement(//                    "INSERT INTO ADMIN_EVN.LOG_ACTION(LOG_ACTION_ID, " +//                            " SESSION_ID, PRIVILEGE_ID, CREATE_TIME, MENU_ID, DESCRIPTION, " +//                            " USER_ID, APP_CODE, REQUEST_PAIR) " +//                            " VALUES (ADMIN_EVN.LOG_ACTION_SEQ.nextval, ?, ?, ?, ?, ?, ?, ?, ?)");//            int count = 0;//            for (LogAction item : lst) {//                psmt.setObject(1, item.getSessionId());//                psmt.setObject(2, item.getPrivilegeId());//                psmt.setObject(3, new java.sql.Date(item.getCreateTime().getTime()));//                psmt.setObject(4, item.getMenuId());//                psmt.setObject(5, item.getDescription());//                psmt.setObject(6, item.getUserId());//                psmt.setObject(7, item.getAppCode());//                psmt.setObject(8, item.getRequestPair());//                psmt.execute();//                count++;//                if (count % 100 == 0) {//                    conn.commit();//                }//            }//            conn.commit();//        } catch (Exception ex) {//            throw ex;//        }//        return lst;//    }//}