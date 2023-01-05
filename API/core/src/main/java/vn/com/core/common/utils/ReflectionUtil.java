package vn.com.core.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public class ReflectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(ReflectionUtil.class);

    public static void callSetter(Object obj, String fieldName, Object value) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
            pd.getWriteMethod().invoke(obj, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static Object callGetter(Object obj, String fieldName) {
        try {
            if (BeanUtils.isSimpleValueType(obj.getClass())) {
                return obj;
            }
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
            return pd.getReadMethod().invoke(obj);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return null;
    }

    public static boolean isGetter(Method method) {
        // identify get methods
        if ((method.getName().startsWith("get") || method.getName().startsWith("is")) && method.getParameterCount() == 0
                && !method.getReturnType().equals(void.class)) {
            return true;
        }
        return false;
    }

    public static boolean isSetter(Method method) {
        // identify set methods
        if (method.getName().startsWith("set") && method.getParameterCount() == 1
                && method.getReturnType().equals(void.class)) {
            return true;
        }
        return false;
    }
}
