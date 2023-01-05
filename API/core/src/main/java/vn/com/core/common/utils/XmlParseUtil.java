package vn.com.core.common.utils;

import org.apache.commons.lang3.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

public class XmlParseUtil {

//    static String xmlResponse = "<?xml version='1.0' encoding='UTF-8'?><S:Envelope xmlns:S=\"http://schemas.xmlsoap.org/soap/envelope/\"><S:Body><ns2:receiverServiceReqResponse xmlns:ns2=\"http://object.app.telsoft/\"><result>KT_PRO_SUCC: 13955,12138,13477,13485,12157,13499,13478,12154,12156,13486,13500,12648,12820,12821,13503,13489,12824,13502,13488,12825,13501,13487,13418,13398,13760,14055,14056,14057,14060,14061,14062,14321,15053,15055,15141,15142,15084,15050,15087,15088,15089,15090,15091,15092,16811,16813,15093,15094,15114,15225,15215,15226,15228,13385,13225,11726,11727,7266,16724,16721,906,1844,4345,2843,4352,9129,9135,9134,4375,9171,4363,9139,9136,9141,9156,11654,11674,11675,11676,12764,12765,12774,13401,13403,13404,13406,13407,13405,13408,13409,13410,13381,13382,13388,13389,13390,13391,13384,13387,13377,13386,5306,14719,14254,11633,900,904,905,2863,3665,13197,13198,13217,7750,681,12716,8192,8193,8210,8191,11983,11997,16698,16710,10437,8209,16713,16695</result></ns2:receiverServiceReqResponse></S:Body></S:Envelope>";

    private SAXParser saxParser;
    private static final Logger logger = LoggerFactory.getLogger(XmlParseUtil.class);
    private static XmlParseUtil instance;

    private XmlParseUtil() {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            saxParser = factory.newSAXParser();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static SAXParser getInstance() {
        if (instance == null) {
            instance = new XmlParseUtil();
        }
        return instance.saxParser;
    }

    public static String xmlParserString(String strXml, String messageTemplate, String... params) {
        try {
            SAXHandler handler = new SAXHandler(Arrays.asList(params), messageTemplate);
            SAXParser parser = XmlParseUtil.getInstance();
            parser.parse(new InputSource(new StringReader(strXml)), handler);
            logger.info("result: " + handler.getResult());
            return handler.getResult();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return "";
    }

    public static Object xmlParserObject(String strXml, Class clazz) {
        try {
            SAXHandlerObject handler = new SAXHandlerObject(clazz);
            SAXParser parser = XmlParseUtil.getInstance();
            parser.parse(new InputSource(new StringReader(strXml)), handler);
            logger.info("result: " + handler.getResult().toString());
            return handler.getResult();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static List xmlParserList(String strXml, Class clazz, String objName) {
        try {
            SAXHandlerList handler = new SAXHandlerList(clazz, objName);
            SAXParser parser = XmlParseUtil.getInstance();
            parser.parse(new InputSource(new StringReader(strXml)), handler);
            logger.info("result: " + handler.getList().size());
            return handler.getList();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


//    public static void main(String[] args) {
//        try {
//            SAXHandler handler = new SAXHandler(Arrays.asList(new String[]{"memberName", "rankName"}), "Tên thuê bao: memberName\r\n Tên hạng:  rankName");
////            SAXHandlerObject handler = new SAXHandlerObject(Packa.class);
//            SAXParser parser = XmlParseUtil.getInstance();
//            parser.parse(new InputSource(new StringReader(xmlResponse)), handler);
//            System.out.println("result: " + handler.getResult());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * @param strXml
     * @return Kết quả api trả về trong thẻ <return>...</return>
     */
    public static String getReturnValue(String strXml) {
        try {
            String result = strXml.substring(strXml.indexOf("<return>"), strXml.indexOf("</return>")).replace("<return>", "");
            return StringEscapeUtils.unescapeXml(result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return strXml;
    }

}
