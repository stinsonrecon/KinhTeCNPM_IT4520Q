package vn.com.core.common.utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class SAXHandlerObject extends DefaultHandler {
    private Class clazz = Object.class;
    private final Map<String, Boolean> lstPropertiesCheck = new HashMap<>();
    private Object result = null;

    private void addConfig(Class clazz) {
        try {
            this.clazz = clazz;
            for (Field field : this.clazz.getDeclaredFields()) {
                lstPropertiesCheck.put(field.getName(), false);
            }
            result = this.clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SAXHandlerObject(Class clazz) {
        super();
        this.addConfig(clazz);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        for (Field field : this.clazz.getDeclaredFields()) {
            if (qName.equalsIgnoreCase(field.getName())) {
                this.lstPropertiesCheck.put(field.getName(), true);
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        for (Map.Entry<String, Boolean> entry : this.lstPropertiesCheck.entrySet()) {
            if (result != null && entry.getValue()) {
                try {
                    Field field = clazz.getDeclaredField(entry.getKey());
                    field.setAccessible(true);
                    field.set(result, new String(ch, start, length));

                    entry.setValue(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Object getResult() {
        return result;
    }
}
