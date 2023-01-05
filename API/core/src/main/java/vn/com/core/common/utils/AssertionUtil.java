package vn.com.core.common.utils;

import vn.com.core.common.exceptions.AppAssertionException;

import java.util.List;

public class AssertionUtil {
    public static void assertNotNull(Object obj, String messages) throws AppAssertionException {
        if (obj != null) return;
        throw new AppAssertionException(messages);
    }

    public static void assertNotEmpty(String value, String messages) throws AppAssertionException {
        if (value != null && value.length() > 0) return;
        throw new AppAssertionException(messages);
    }

    public static boolean isNullOrEmpty(List<?> list) {
        return list == null || list.isEmpty();
    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

}
