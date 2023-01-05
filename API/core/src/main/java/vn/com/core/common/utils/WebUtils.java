package vn.com.core.common.utils;

import javax.servlet.http.HttpServletRequest;

public class WebUtils {

    public static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            System.out.println("X-FORWARDED-FOR: " + remoteAddr);
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
                System.out.println("getRemoteAddr: " + remoteAddr);
                if (remoteAddr.contains("0:0:0:0:0:")) {
                    remoteAddr = "127.0.0.1";
                }
            }
        }
        return remoteAddr;
    }
}
