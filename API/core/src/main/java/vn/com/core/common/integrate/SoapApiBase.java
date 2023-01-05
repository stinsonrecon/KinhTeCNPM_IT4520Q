package vn.com.core.common.integrate;//package com.fis.fw.common.integrate;
//
//import com.fis.fw.common.config.ErrorCode;
//import com.fis.fw.common.exceptions.AppException;
//import com.fis.fw.common.logging.LogManager;
//import com.fis.fw.common.utils.StringUtil;
//import com.fis.fw.core.entity.ApiPartner;
//import com.fis.fw.core.entity.LogSend;
//import com.fis.fw.core.service.ApiPartnerService;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.nio.charset.Charset;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//import java.util.Map;
//
//public abstract class SoapApiBase {
//    Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    protected ApiPartnerService apiPartnerService;
//
//    @Autowired
//    protected LogManager logManager;
//
//    protected BaseEntityRequestParam requestParam;
//    private ApiPartner apiPartner;
//
//    /**
//     * Cấu hình api partner code khai báo trong database
//     *
//     * @return api partner code
//     */
//    protected abstract String getApiPartnerCode();
//
//    /**
//     * Cấu hình chuỗi xml request cần thiết cho SOAP api
//     *
//     * @param requestParam Tham số cần thiết
//     * @return
//     */
//    protected abstract String buildXmlString(BaseEntityRequestParam requestParam);
//
//    /**
//     * Mẫu tin nhắn trả về cho Khách hàng
//     *
//     * @return Message template
//     */
//    public String getMessageTemple() {
//        return "";
//    }
//
//    /**
//     * Danh sách các parameter cần lấy từ chuỗi xml
//     *
//     * @return Danh sách parameter
//     */
//    public String[] getListParameter() {
//        return new String[]{};
//    }
//
//    /**
//     * @return true --> cau hinh basic authenticate
//     */
//    public boolean authentication() {
//        return false;
//    }
//
//
//    public void loadApiPartnerByCode(String code) {
//        apiPartner = apiPartnerService.getByCode(code);
//        if (apiPartner == null) {
//            throw new AppException(ErrorCode.APP.API_NOT_CONFIG, "Api not yet filter");
//        }
//    }
//
//    /**
//     * Gọi SOAP api Authentication
//     *
//     * @param dataQueryApiObject tham số đầu vào
//     * @return
//     */
//    public String callSoapApi(BaseEntityRequestParam dataQueryApiObject) throws Exception {
//        setRequestParam(dataQueryApiObject);
//        if (dataQueryApiObject == null) dataQueryApiObject = new BaseEntityRequestParam();
//        loadApiPartnerByCode(getApiPartnerCode());
//        dataQueryApiObject.setUsername(apiPartner.getUserName());
//        dataQueryApiObject.setPassword(apiPartner.getPassword());
//        return sendMessageBase(apiPartner.getUrl(), buildXmlString(dataQueryApiObject), apiPartner, authentication());
//    }
//
//    public BaseEntityRequestParam getRequestParam() {
//        return requestParam;
//    }
//
//    public void setRequestParam(BaseEntityRequestParam requestParam) {
//        this.requestParam = requestParam;
//    }
//
//    protected String sendMessageBase(String strUrl, String xmlString, ApiPartner apiPartner, boolean authentication) throws Exception {
//        LogSend adLogSend = new LogSend();
//        long start = System.currentTimeMillis();
//        try {
//            URL url = new URL(strUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            connection.setDoOutput(true);
//
//            connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
//            if (authentication) {
//                String auth = apiPartner.getUserName() + ":" + apiPartner.getPassword();
//                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
//                String authHeaderValue = "Basic " + new String(encodedAuth);
//                connection.setRequestProperty(HttpHeaders.AUTHORIZATION, authHeaderValue);
//            }
//            Map header = connection.getRequestProperties();
//            connection.connect();
//            //create log
//            try {
//                adLogSend.setUrl(apiPartner.getUrl());
//                adLogSend.setApiPartnerId(apiPartner.getApiPartnerId());
//                adLogSend.setMethod(connection.getRequestMethod());
//                adLogSend.setRequestHeader(header != null ? header.toString() : "");
//                adLogSend.setRequestBody(xmlString);
//            } catch (Exception e) {
//                logger.error(e.toString(), e);
//            }
//
//            //tell the web server what we are sending
//            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), Charset.forName("UTF-8"));
//            writer.write(xmlString);
//            writer.flush();
//            writer.close();
//
//            BufferedReader input = new BufferedReader(
//                    new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
//            StringBuilder buf = new StringBuilder();
//            String str;
//            while (null != (str = input.readLine())) {
//                buf.append(str).append(System.lineSeparator());
//            }
//
//            try {
//                adLogSend.setResponseStatus(String.valueOf(connection.getResponseCode()));
//                adLogSend.setResponseBody(StringUtil.objectToJson(buf.toString()));
//            } catch (Exception e) {
//                logger.error(e.toString(), e);
//            }
//
//            return buf.toString();
//        } catch (Exception e) {
//            adLogSend.setResponseBody("Exception:" + e.toString() + "|" + (adLogSend.getResponseBody() != null ? adLogSend.getRequestBody() : ""));
//            throw e;
//        } finally {
//            adLogSend.setProcessTime(String.valueOf(new Date().getTime() - start));
//            logSend(adLogSend);
//        }
//    }
//
//    private void logSend(LogSend adLogSend) {
//        try {
//            logger.info("=========== BEGIN LOG SEND ==============");
//            logger.info("Time: " + adLogSend.getProcessTime());
//            logger.info("Url: " + adLogSend.getUrl());
//            logger.info("Method: " + adLogSend.getMethod());
//            logger.info("Api Partner: " + adLogSend.getApiPartnerId());
//            logger.info("Request Header: \n" + adLogSend.getRequestHeader());
//            logger.info("Request Body: \n" + adLogSend.getRequestBody());
//            logger.info("Response Status: " + adLogSend.getResponseStatus());
//            logger.info("Response Body: \n" + adLogSend.getResponseBody());
//            adLogSend.setCreateTime(new Date());
//            logManager.submit(adLogSend);
//            logger.info("=========== END LOG SEND ==============");
//        } catch (Exception e) {
//            logger.error(e.toString(), e);
//        }
//    }
//
//}
