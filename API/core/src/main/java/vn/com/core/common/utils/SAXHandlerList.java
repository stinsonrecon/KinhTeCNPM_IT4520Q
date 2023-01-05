package vn.com.core.common.utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SAXHandlerList extends DefaultHandler {
    private Class clazz = Object.class;
    private final Map<String, Boolean> lstPropertiesCheck = new HashMap<>();
    private List list = new ArrayList();
    private String objName = "";
    private Object item = null;
    private StringBuilder data = null;

    private void addConfig(Class clazz, String objName) {
        try {
            this.clazz = clazz;
            this.objName = objName;
            for (Field field : this.clazz.getDeclaredFields()) {
                lstPropertiesCheck.put(field.getName(), false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SAXHandlerList(Class clazz, String objName) {
        super();
        this.addConfig(clazz, objName);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        data = new StringBuilder();
        try {
            if (qName.equals(this.objName)) {
                item = this.clazz.newInstance();
            } else
                for (Field field : this.clazz.getDeclaredFields()) {
                    if (qName.equalsIgnoreCase(field.getName())) {
                        this.lstPropertiesCheck.put(field.getName(), true);
                    }
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals(objName)) {
            list.add(item);
        } else
            for (Map.Entry<String, Boolean> entry : this.lstPropertiesCheck.entrySet()) {
                if (item != null && entry.getValue()) {
                    try {
                        Field field = clazz.getDeclaredField(entry.getKey());
                        field.setAccessible(true);
                        field.set(item, data.toString());
                        entry.setValue(false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(new String(ch, start, length));

    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }
}
