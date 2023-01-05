package vn.com.hust.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import vn.com.hust.common.CommonConstant;
import vn.com.hust.common.Constants;
import vn.com.hust.dto.CommonValuesInput;
import vn.com.hust.dto.MessagesResponse;
import vn.com.hust.dto.ReportRequestObject;
import vn.com.hust.report.DetectDateFormat;
import vn.com.hust.service.ReportService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

@CrossOrigin(origins = "*")
@RestController
public class ReportEngineContoller {
    @Autowired
    private Environment ev;

    @Autowired
    ReportService reportService;

    private static final Logger LOG = LoggerFactory.getLogger(ReportEngineContoller.class);

    @RequestMapping(value = { Constants.ACTION_EXPORT_FILE }, method = RequestMethod.POST)
    public ResponseEntity<?> exportLogin(@RequestBody CommonValuesInput input) {
        String nomeMetodo = ".exportLogin() ";
        LOG.info(LOG.getName() + nomeMetodo + " user: "
                + SecurityContextHolder.getContext().getAuthentication().getName() + CommonConstant.BEGIN_LOG);

        ReportRequestObject response = new ReportRequestObject();
        HttpHeaders headers = new HttpHeaders();
        try {
            response = exportFile(input.getStaffName());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            MessagesResponse mess = new MessagesResponse();
            mess.setMessages(e.getMessage());
            mess.setStatus(String.valueOf(CommonConstant.STATUS_DEFAULT));
            LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
            return new ResponseEntity<MessagesResponse>(mess, HttpStatus.OK);
        }
        LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
        return new ResponseEntity<ReportRequestObject>(response, headers, HttpStatus.OK);
    }

    public ReportRequestObject exportFile(String reportInput) throws Exception {
        String nomeMetodo = ".exportFile() ";
        LOG.info(LOG.getName() + nomeMetodo + " user: "
                + SecurityContextHolder.getContext().getAuthentication().getName() + CommonConstant.BEGIN_LOG);

        ReportRequestObject response = new ReportRequestObject();
        try {
            LOG.info("ReportInput: " + reportInput);
            DetectDateFormat ddf = new DetectDateFormat();
            HashMap<String, Object> parameters = new ObjectMapper().readValue(reportInput, HashMap.class);
            HashMap<String, Object> parametersNew = SerializationUtils.clone(parameters);
            for (String key : parameters.keySet()) {
                try {
                    if (ddf.isValidDate((String) parameters.get(key))) {
                        // Convert Date to dd-MMM-yyyy
                        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                        parameters.put(key, df.format(ddf.parse((String) parameters.get(key))));
                    }
                } catch (Exception ex) {
                    continue;
                }
            }
            // parameters.put("IS_IGNORE_PAGINATION", "true");
            response.setMimeType(reportService.getMediaType((String) parameters.get("fileType")));

            String templateFile = ev.getProperty("upload.path.template") + (String) parameters.get("fileTempName")
                    + CommonConstant.TEMPLATE_TYPE;
            String destinationPath = ev.getProperty("DestinationPath");

            // path
            parameters.put("realPath", ev.getProperty("upload.path.template"));

            LOG.info("destinationPath: " + destinationPath);

            ReportRequestObject requestObject = new ReportRequestObject(templateFile,
                    (String) parameters.get("fileType"), (String) parameters.get("fileTempName"), destinationPath,
                    CommonConstant.EMPTY);
            LOG.info("templateFile: " + templateFile);

            response = reportService.generatorReport(requestObject, parameters);
            response.setMimeType(reportService.getMediaType((String) parameters.get("fileType")));
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
            LOG.error(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
            throw new Exception(e.getMessage());
        }
        LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
        return response;
    }
}
