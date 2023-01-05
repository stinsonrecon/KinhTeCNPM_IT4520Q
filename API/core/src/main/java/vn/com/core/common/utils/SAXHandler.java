package vn.com.core.common.utils;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SAXHandler extends DefaultHandler {
    private final Map<String, Boolean> lstPropertiesCheck = new HashMap<>();
    private List<String> lstProperties = new ArrayList<>();
    private String result = "";

    private void addConfig(List<String> lstProperties, String strFormat) {
        if (lstProperties != null) {
            this.lstProperties = lstProperties;
            for (String property : this.lstProperties) {
                lstPropertiesCheck.put(property, false);
            }
        }
        this.result = strFormat;
    }

    public SAXHandler(List<String> lstProperties, String strFormat) {
        super();
        this.addConfig(lstProperties, strFormat);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        this.lstProperties.forEach(property -> {
            if (qName.equalsIgnoreCase(property)) {
                this.lstPropertiesCheck.put(property, true);
            }
        });
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        for (Map.Entry<String, Boolean> entry : this.lstPropertiesCheck.entrySet()) {
            if (entry.getValue()) {
                this.result = this.result.replace(entry.getKey(), new String(ch, start, length));
                entry.setValue(false);
            }
        }
    }

    public String getResult() {
        return result;
    }
}
