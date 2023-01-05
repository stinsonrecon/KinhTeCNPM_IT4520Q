package vn.com.core.common.filter;//package com.fis.fw.common.filter;
//
//import com.fis.fw.common.logging.LogManager;
//import com.fis.fw.common.utils.StringUtil;
//import com.fis.fw.common.security.TokenJwtHelper;
//import com.fis.fw.common.utils.ValidationUtil;
////import com.fis.fw.core.entity.LogApi;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.util.StreamUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.util.ContentCachingRequestWrapper;
//import org.springframework.web.util.ContentCachingResponseWrapper;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.UnsupportedEncodingException;
//import java.nio.charset.Charset;
//import java.util.*;
//import java.util.zip.GZIPInputStream;
//
///**
// * com.fis.fw.common.filter.LoggingFilter.java
// * TungHuynh
// * Created by sondt18@fpt.com.vn
// * Date 11/05/2019 9:47 AM
// * extends class này nếu cần ghi log API
// */
////@Component
//public class LoggingFilter extends OncePerRequestFilter {
//    Logger logger = LoggerFactory.getLogger(getClass());
//    private final List<MediaType> VISIBLE_TYPES = Arrays.asList(
//            MediaType.valueOf("text/*"),
//            MediaType.APPLICATION_FORM_URLENCODED,
//            MediaType.APPLICATION_JSON,
//            MediaType.APPLICATION_XML,
//            MediaType.valueOf("application/*+json"),
//            MediaType.valueOf("application/*+xml"),
//            MediaType.MULTIPART_FORM_DATA
//    );
//    @Autowired
//    LogManager logManager;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (isAsyncDispatch(request)) {
//            filterChain.doFilter(request, response);
//        } else {
//            doFilterWrapped(wrapRequest(request), wrapResponse(response), filterChain);
//        }
//    }
//
////    protected void doFilterWrapped(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, FilterChain filterChain) throws ServletException, IOException {
////        long start = new Date().getTime();
////        try {
////            filterChain.doFilter(request, response);
////        } finally {
////            afterRequest(request, response, start);
////            response.copyBodyToResponse();
////        }
////    }
//
////    protected void afterRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long start) {
////        LogApi logApi = new LogApi();
////        logApi.setProcessTime(String.valueOf(new Date().getTime() - start));
////        String queryString = request.getQueryString();
////        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
////            return;
////        }
////        logApi.setMethod(request.getMethod());
////        logApi.setUri(queryString == null ? request.getRequestURI() : (request.getRequestURI() + "?" + queryString));
////        StringBuilder reqHeader = new StringBuilder();
////        Collections.list(request.getHeaderNames()).forEach(headerName ->
////                Collections.list(request.getHeaders(headerName))
////                        .forEach(headerValue -> {
////                                    if (!"cookie".equalsIgnoreCase(headerName))
////                                        reqHeader.append(String.format("%s: %s|", headerName, headerValue));
////                                }
////                        ));
////        logApi.setRequestHeader(reqHeader.toString());
////        Enumeration<String> reqPairs = request.getHeaders(TokenJwtHelper.REQUEST_PAIR);
////        logApi.setRequestPair(reqPairs.hasMoreElements() ? reqPairs.nextElement() : "");
////        String reqBody = "";
////        byte[] reqContent = request.getContentAsByteArray();
////        if (reqContent.length > 0) {
////            reqBody = logContent(reqContent, request.getContentType(), request.getCharacterEncoding(), request.getRemoteAddr());
////            if (logApi.getUri().contains("/login")) {
////                reqBody = reqBody.replaceAll("\"password\"(\\s*:\\s*)\"([^\"]*)\"", "\"password\"$1\"******\"");
////            }
////            reqBody = reqBody.replaceAll("\"data:(\\w+\\/\\w+);base(\\d{2}),([\\w=\\/+\\\\]+)\"", "\"data:$1;base$2,[not show data file]\"");
////            //reqBody = reqBody.replaceAll("\"data:(\\w+\\/\\w+);base(\\d{2}),([\\w=\\/+]+)\"", "\"data:$1;base$2,[not show data file]\"");
////            if (reqBody.length() > 2000) {
////                reqBody = reqBody.substring(0, 1999) + " [...too long (2000)]";
////            }
////        }
////        logApi.setRequestBody(reqBody);
////        logApi.setResponseStatus(String.valueOf(response.getStatus()));
////        String respBody = "";
////        // phucvm3: check compressed response gzip
////        if (isCompressedResponse(response)) {
////            respBody = getCompressedResponseData(response);
////        } else {
////            byte[] respContent = response.getContentAsByteArray();
////            if (respContent.length > 0) {
////                respBody = logContent(respContent, response.getContentType(), response.getCharacterEncoding(), request.getRemoteAddr());
////            }
////        }
////
////        if (!ValidationUtil.isNullOrEmpty(respBody)) {
////            respBody = respBody.replaceAll("\"data:(\\w+\\/\\w+);base(\\d{2}),([\\w=\\/+\\\\]+)\"", "\"data:$1;base$2,[not show data file]\"");
////            if (respBody.length() > 2000) {
////                respBody = respBody.substring(0, 1999) + " [...too long (2000)]";
////            }
////        }
////
//////        byte[] respContent = response.getContentAsByteArray();
//////        if (respContent.length > 0) {
//////            respBody = logContent(respContent, response.getContentType(), response.getCharacterEncoding(), request.getRemoteAddr());
//////            respBody = respBody.replaceAll("\"data:(\\w+\\/\\w+);base(\\d{2}),([\\w=\\/+\\\\]+)\"", "\"data:$1;base$2,[not show data file]\"");
//////            if (respBody.length() > 2000) {
//////                respBody = respBody.substring(0, 1999) + " [...too long (2000)]";
//////            }
//////        }
////        if (logApi.getUri().contains("/login")) {
////            logApi.setSessionId(Integer.valueOf(StringUtil.nvl(response.getHeader("session_id"), "0")));
////            logApi.setUserId(Integer.valueOf(StringUtil.nvl(response.getHeader("user_id"), "0")));
////        } else {
////            String token = TokenJwtHelper.getInstance().getJwt(request);
////            Map<String, Object> objectMap = TokenJwtHelper.getInstance().getAllClaims(token);
////            logApi.setUserId(Integer.valueOf(StringUtil.nvl(objectMap.get("userId"), "0")));
////            logApi.setSessionId(Integer.valueOf(StringUtil.nvl(objectMap.get("sessionId"), "0")));
////        }
////        logApi.setResponseBody(respBody);
////        logApi(logApi);
////    }
//
//    private boolean isCompressedResponse(ContentCachingResponseWrapper response) {
//        if (response != null) {
//            String gzip = response.getHeader(HttpHeaders.CONTENT_ENCODING);
//            return !ValidationUtil.isNullOrEmpty(gzip) && gzip.contains("gzip");
//        }
//        return false;
//    }
//
//    private String getCompressedResponseData(ContentCachingResponseWrapper response) {
//        String contentType = response.getContentType();
//        String characterEncoding = response.getCharacterEncoding();
//
//        MediaType mediaType = MediaType.valueOf(contentType);
//        boolean visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
//        if (visible) {
//            try (final InputStream compressedInputStream = response.getContentInputStream()) {
//                try (final InputStream gzipInputStream = new GZIPInputStream(compressedInputStream)) {
//                    return StreamUtils.copyToString(gzipInputStream, Charset.forName(characterEncoding));
//                }
//            } catch (Exception ex) {
//                logger.error(ex.getMessage(), ex);
//            }
//        }
//
//        return null;
//    }
//
//    private void logApi(LogApi logApi) {
//        try {
//            logger.info("=========== BEGIN LOG API ==============");
//            logger.info("Time: " + logApi.getProcessTime());
//            logger.info("LogSession Id: " + logApi.getSessionId());
//            logger.info("User Id: " + logApi.getUserId());
//            logger.info("Request Pair: " + logApi.getRequestPair());
//            logger.info("Method: " + logApi.getMethod());
//            logger.info("URI: " + logApi.getUri());
//            logger.info("Request Header: \n" + logApi.getRequestHeader());
//            logger.info("Request Body: \n" + logApi.getRequestBody());
//            logger.info("Response Status: " + logApi.getResponseStatus());
//            logger.info("Response Body: \n" + logApi.getResponseBody());
//            logApi.setCreateTime(new Date());
//            logManager.submit(logApi);
//            logger.info("=========== END LOG API ==============");
//        } catch (Exception e) {
//            logger.error(e.toString(), e);
//        }
//    }
//
//    private String logContent(byte[] content, String contentType, String contentEncoding, String prefix) {
//        MediaType mediaType = MediaType.valueOf(contentType);
//        boolean visible = VISIBLE_TYPES.stream().anyMatch(visibleType -> visibleType.includes(mediaType));
//        if (visible) {
//            try {
//                return new String(content, contentEncoding);
//            } catch (UnsupportedEncodingException e) {
//                logger.info(String.format("logContent1: %s [%s bytes content]", prefix, content.length));
//            }
//        } else {
//            logger.info(String.format("logContent2: %s [%s bytes content]", prefix, content.length));
//        }
//        return "";
//    }
//
//    private ContentCachingRequestWrapper wrapRequest(HttpServletRequest request) {
//        if (request instanceof ContentCachingRequestWrapper) {
//            return (ContentCachingRequestWrapper) request;
//        } else {
//            return new ContentCachingRequestWrapper(request);
//        }
//    }
//
//    private ContentCachingResponseWrapper wrapResponse(HttpServletResponse response) {
//        if (response instanceof ContentCachingResponseWrapper) {
//            return (ContentCachingResponseWrapper) response;
//        } else {
//            return new ContentCachingResponseWrapper(response);
//        }
//    }
//}
