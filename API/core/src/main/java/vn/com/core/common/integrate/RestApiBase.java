package vn.com.core.common.integrate;//package com.fis.fw.common.integrate;
//
//import com.fis.fw.common.config.ErrorCode;
//import com.fis.fw.common.exceptions.AppException;
//import com.fis.fw.common.logging.LogManager;
//import com.fis.fw.common.utils.StringUtil;
//import com.fis.fw.core.entity.ApiPartner;
//import com.fis.fw.core.entity.LogSend;
//import com.fis.fw.common.dto.MessagesResponse;
//import com.fis.fw.core.service.ApiPartnerService;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.web.client.RestTemplate;
//
//import java.net.URI;
//import java.util.Date;
//
///**
// * com.fis.fw.common.integrate.RestApiBase
// * TungHuynh
// * Created by sondt18@fpt.com.vn
// * Date 20/06/2019 5:02 PM
// */
//public abstract class RestApiBase {
//    Logger logger = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    protected ApiPartnerService apiPartnerService;
//
//    @Autowired
//    protected LogManager logManager;
//
//    @Value("${checksum.default}")
//    protected String checksumDefault;
//
//    private ApiPartner apiPartner;
//
//    protected abstract String getApiPartnerCode();
//
//    public void loadApiPartnerByCode(String code) {
//        apiPartner = apiPartnerService.getByCode(code);
//        if (apiPartner == null) {
//            throw new AppException(ErrorCode.APP.API_NOT_CONFIG, "Api not yet filter");
//        }
//    }
//
//    public MessagesResponse postJson(String method, String params, String jwt, boolean onlyLogFile) throws Exception{
//        return exchangeJson(HttpMethod.POST, method, params, jwt, onlyLogFile);
//    }
//
//    public MessagesResponse post(String method, Object params, String jwt)  throws Exception{
//        return exchange(HttpMethod.POST, method, params, jwt);
//    }
//
//    public MessagesResponse get(String method, String jwt)  throws Exception{
//        return exchange(HttpMethod.GET, method, null, jwt);
//    }
//    private MessagesResponse exchange(HttpMethod method, String uriApi, Object params, String jwt)  throws Exception{
//        String jsonParams = StringUtil.objectToJson(params);
//        return exchangeJson(method, uriApi, jsonParams, jwt, false);
//    }
//
//    private MessagesResponse exchangeJson(HttpMethod method, String uriApi, String params, String jwt, boolean onlyLogFile) throws Exception{
//        LogSend adLogSend = new LogSend();
//        long start = new Date().getTime();
//        try {
//            loadApiPartnerByCode(getApiPartnerCode());
//            RestTemplate restTemplate = new RestTemplate();
//            String jsonParams = StringUtil.objectToJson(params);
//            URI uri = new URI(this.apiPartner.getUrl() + uriApi);
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            if (!StringUtil.stringIsNullOrEmty(jwt)) {
//                headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + jwt);
//            }
////            headers.set("checksum", keyChecksum);
//            HttpEntity<String> entity = new HttpEntity(jsonParams, headers);
//            //create log
//            try {
//                adLogSend.setApiPartnerId(this.apiPartner.getApiPartnerId());
//                adLogSend.setMethod(uriApi);
//                adLogSend.setRequestHeader(headers != null ? headers.toString() : "");
//                adLogSend.setRequestBody(jsonParams);
//            } catch (Exception e) {
//                logger.error(e.toString(), e);
//            }
//
//            ResponseEntity<MessagesResponse> response = restTemplate.exchange(uri, method, entity, MessagesResponse.class);
//            try {
//                adLogSend.setResponseStatus(String.valueOf(response.getStatusCodeValue()));
//                adLogSend.setResponseBody(StringUtil.objectToJson(response.getBody()));
//                //check ban tin gui khong thanh cong -> khoi tao retry=1
//                //Tien trinh quet AD_LOG_SEND, re-send cac ban tin RETRY>=1, tang RETRY trong khi <=MAX_RETRY
//                if (!MessagesResponse.RESPONSE_STATUS.SUCCESS.equalsIgnoreCase(response.getBody().getStatus())
//                    || response.getStatusCodeValue()!=200){
//                    adLogSend.setRetry(1);
//                }
//            } catch (Exception e) {
//                logger.error(e.toString(), e);
//            }
//            return response.getBody();
//        } catch (Exception e) {
//            adLogSend.setRetry(1);
//            adLogSend.setResponseBody("Exception:" + e.toString() + "|" + adLogSend.getResponseBody() != null ? adLogSend.getRequestBody() : "");
//            throw e;
//        } finally {
//            adLogSend.setProcessTime(String.valueOf(new Date().getTime() - start));
//            logSend(adLogSend, onlyLogFile);
//        }
//    }
//
//    private void logSend(LogSend adLogSend, boolean onlyLogFile) {
//        try {
//            adLogSend.setUrl(this.apiPartner.getUrl());
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
