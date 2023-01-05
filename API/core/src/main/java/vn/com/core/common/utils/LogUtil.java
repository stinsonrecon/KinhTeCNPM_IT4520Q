package vn.com.core.common.utils;

import org.slf4j.Logger;
import org.springframework.security.core.context.SecurityContextHolder;

public class LogUtil {

    public static final int LOG_BEGIN = 0;
    public static final int LOG_END = 1;

    /**
     * Show log
     * @param type (0: begin log, 1: end log)
     * @param method
     * @param startTime
     */
    public static void showLog(Logger logger, int type, String method, long startTime) {
        String className = logger.getName().substring(logger.getName().lastIndexOf(".") + 1);
        logger.info("User: "
                + SecurityContextHolder.getContext().getAuthentication().getName()
                + (type == 0 ? " BEGIN " : " END ")
                + className + "." + method
                + (type == 1 ? " in " + (System.currentTimeMillis() - startTime) + " ms" : ""));
    }

}
