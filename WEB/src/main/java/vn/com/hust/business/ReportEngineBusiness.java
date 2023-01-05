package vn.com.hust.business;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import vn.com.hust.model.CommonValuesInput;
import vn.com.hust.report.ReportRequestObject;
import vn.com.hust.utils.CommonConstant;
import vn.com.hust.ws.WebService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ReportEngineBusiness {
    @Autowired
    WebService webService;
    @Autowired
    private Environment ev;
    @Autowired
    private ResourceLoader resourceLoader;
    private static final Logger LOG = LoggerFactory.getLogger(ReportEngineBusiness.class);

    public ResponseEntity<?> exportFile(HttpServletRequest request, String reportInput, String urlCallAPi) throws Exception
    {
        String nomeMetodo = ".exportFile()";
        LOG.info(LOG.getName() + nomeMetodo + " user: " + SecurityContextHolder.getContext().getAuthentication().getName() + CommonConstant.BEGIN_LOG);

        ReportRequestObject responseFile = new ReportRequestObject();
        HttpHeaders headers = new HttpHeaders();
        File file = null;
        FileSystemResource fileSystemResource = null;
        try
        {
            // call api
            /* responseFile = exportFileCommon(reportInput); */

            CommonValuesInput inputReport = new CommonValuesInput();
            inputReport.setStaffName(reportInput);

            Gson gson = new Gson();
            String dataString = gson.toJson(inputReport);
            String url = ev.getProperty("login.url") + urlCallAPi;
            String tmp = webService.apiServiceMethodPOST(request, url, "", dataString);
            if (!"".equals(tmp))
            {
                ObjectMapper jsonMapper = new ObjectMapper();
                responseFile = jsonMapper.readValue(tmp.toString(), new TypeReference<ReportRequestObject>() {
                });
            }

            file = resourceLoader.getResource("file:" + responseFile.getDownloadLink()).getFile();
            fileSystemResource = new FileSystemResource(file);
            headers.setContentType(MediaType.parseMediaType(responseFile.getMimeType()));
            headers.set("MyDownloadFile", responseFile.getFileName());
            headers.set("FileType", responseFile.getFileType());

            List<String> listOfAccessControl = new ArrayList<String>();
            listOfAccessControl.add("MyDownloadFile");
            listOfAccessControl.add("FileType");

            headers.setAccessControlExposeHeaders(listOfAccessControl);
        } catch (Exception e)
        {
            LOG.error(e.getMessage(), e);
            LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
            throw e;
        }

        LOG.info(LOG.getName() + nomeMetodo + CommonConstant.END_LOG);
        return new ResponseEntity<>(fileSystemResource, headers, HttpStatus.OK);
    }

}
