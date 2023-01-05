package vn.com.hust.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import vn.com.hust.business.ReportEngineBusiness;
import vn.com.hust.model.MessagesResponse;
import vn.com.hust.utils.CommonConstant;
import vn.com.hust.utils.Constants;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ReportEngineController {
    @Autowired
    ReportEngineBusiness reportEngineBusiness;
    private static final Logger LOG = LoggerFactory.getLogger(ReportEngineController.class);

    @RequestMapping(value = { Constants.REQUEST_MAPPING.ACTION_COMMON_REPORT }, method = RequestMethod.POST)
    public ResponseEntity<?> commonExportFile(HttpServletRequest request,
                                              @RequestBody String reportInput) {
        String nomeMetodo = ".commonExportFile() ";
        LOG.info(LOG.getName() + nomeMetodo + " user: "
                + SecurityContextHolder.getContext().getAuthentication().getName() + CommonConstant.BEGIN_LOG);

        ResponseEntity<?> response = null;
        try {
            response = reportEngineBusiness.exportFile(request, reportInput,
                    Constants.REQUEST_MAPPING.ACTION_COMMON_REPORT);
        } catch (Exception e) {
            LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
            MessagesResponse mess = new MessagesResponse();
            mess.setMessages(e.getMessage());
            if (e.getMessage() == null) {
                mess.setMessages("java.lang.NullPointerException");
            }
            mess.setStatus(String.valueOf(CommonConstant.STATUS_DEFAULT));
            return new ResponseEntity<MessagesResponse>(mess, HttpStatus.OK);
        }
        LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
        return response;
    }
}
