package vn.com.core.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil extends AssertionUtil {

    public static boolean isNullOrEmpty(String st) {
        return st == null || st.equals("");
    }

    public static boolean isURL(String url) {
        try {
//            (new java.net.URL(url)).openStream().close();
            new java.net.URL(url);
            return true;
        } catch (Exception ex) {
        }
        return false;
    }

    public static boolean isPhoneNumber(String phone) {
        if (StringUtil.stringIsNullOrEmty(phone)) return false;

        String regexStr = "^[0-9]*$";
        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    public static boolean isJsonValid(String jsonInString) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
