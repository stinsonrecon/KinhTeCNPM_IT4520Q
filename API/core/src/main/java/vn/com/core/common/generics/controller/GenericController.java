package vn.com.core.common.generics.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.core.common.config.CoreConstants;
import vn.com.core.common.dto.MessagesResponse;
import vn.com.core.common.dto.OrderBy;
import vn.com.core.common.dto.PagnationRespBody;
import vn.com.core.common.dto.QueryRequest;
import vn.com.core.common.generics.GenericService;
import vn.com.core.common.utils.LogUtil;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;


public class GenericController<E, P extends Serializable> {
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GenericService<E, P> genericService;

    public GenericService<E, P> getService() {
        return genericService;
    }

    @SuppressWarnings("unchecked")
    public <G> G getService(Class<G> customType) {
        return (G) genericService;
    }

    @PreAuthorize("@appAuthorizer.authorize(authentication, '" + CoreConstants.PRIVILEGE.VIEW + "', this)")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> findById(@PathVariable P id) {
        long startTime = System.currentTimeMillis();
        LogUtil.showLog(logger, LogUtil.LOG_BEGIN, "findById", startTime);
        MessagesResponse msg = new MessagesResponse();

        try {
            msg.setData(genericService.findById(id));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        } finally {
            LogUtil.showLog(logger, LogUtil.LOG_END, "findById", startTime);
        }

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("@appAuthorizer.authorize(authentication, '" + CoreConstants.PRIVILEGE.VIEW + "', this)")
    @RequestMapping(value = "/getAll", method = RequestMethod.POST, consumes = { "application/json" })
    @ResponseBody
    public ResponseEntity<Object> getAll(@RequestBody List<OrderBy> orderBys) throws Exception {
        long startTime = System.currentTimeMillis();
        LogUtil.showLog(logger, LogUtil.LOG_BEGIN, "getAll", startTime);

        MessagesResponse msg = new MessagesResponse();

        try {
            msg.setData(genericService.queryAllAndSort(orderBys));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        } finally {
            LogUtil.showLog(logger, LogUtil.LOG_END, "getAll", startTime);
        }

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("@appAuthorizer.authorize(authentication, '" + CoreConstants.PRIVILEGE.VIEW + "', this)")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> page(@RequestParam int page, @RequestParam int size, @RequestBody List<OrderBy> orderBys) {
        long startTime = System.currentTimeMillis();
        LogUtil.showLog(logger, LogUtil.LOG_BEGIN, "page", startTime);
        MessagesResponse msg = new MessagesResponse();
        try {
            Page<E> pPage = genericService.findPage(page, size, orderBys);
            PagnationRespBody<E> responseBody = new PagnationRespBody<E>().withContent(pPage.getContent())
                    .withTotalPage(pPage.getTotalPages()).withNumRec(pPage.getNumberOfElements())
                    .withTotalRecord(pPage.getTotalElements());
            msg.setData(responseBody);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        } finally {
            LogUtil.showLog(logger, LogUtil.LOG_END, "page", startTime);
        }

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("@appAuthorizer.authorize(authentication, '" + CoreConstants.PRIVILEGE.VIEW + "', this)")
    @RequestMapping(value = "/findByExample", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> findByExample(@RequestBody E e) {
        long startTime = System.currentTimeMillis();
        LogUtil.showLog(logger, LogUtil.LOG_BEGIN, "findByExample", startTime);

        MessagesResponse msg = new MessagesResponse();

        try {
            msg.setData(genericService.findByExample(e));
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        } finally {
            LogUtil.showLog(logger, LogUtil.LOG_END, "findByExample", startTime);
        }

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("@appAuthorizer.authorize(authentication, '" + CoreConstants.PRIVILEGE.INSERT + "', this)")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Object> create(@RequestBody @Valid E e) {
        long startTime = System.currentTimeMillis();
        LogUtil.showLog(logger, LogUtil.LOG_BEGIN, "create", startTime);

        MessagesResponse msg = new MessagesResponse();

        try {
            E createdObject = genericService.save(e);
            msg.setData(createdObject);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        } finally {
            LogUtil.showLog(logger, LogUtil.LOG_END, "create", startTime);
        }

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("@appAuthorizer.authorize(authentication, '" + CoreConstants.PRIVILEGE.UPDATE + "', this)")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Object> update(@RequestBody @Valid E e) {
        long startTime = System.currentTimeMillis();
        LogUtil.showLog(logger, LogUtil.LOG_BEGIN, "update", startTime);

        MessagesResponse msg = new MessagesResponse();

        try {
            E updatedObj = genericService.save(e);
            msg.setData(updatedObj);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        } finally {
            LogUtil.showLog(logger, LogUtil.LOG_END, "update", startTime);
        }

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PreAuthorize("@appAuthorizer.authorize(authentication, '" + CoreConstants.PRIVILEGE.DELETE + "', this)")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<Object> delete(@PathVariable P id) {
        long startTime = System.currentTimeMillis();
        LogUtil.showLog(logger, LogUtil.LOG_BEGIN, "delete", startTime);

        MessagesResponse msg = new MessagesResponse();

        try {
            genericService.delete(id);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        } finally {
            LogUtil.showLog(logger, LogUtil.LOG_END, "delete", startTime);
        }

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @PreAuthorize("@appAuthorizer.authorize(authentication, '" + CoreConstants.PRIVILEGE.VIEW + "', this)")
    @ResponseBody
    public ResponseEntity<Object> query(@Valid @NonNull @RequestBody QueryRequest<E> queryRequest) {
        long startTime = System.currentTimeMillis();
        LogUtil.showLog(logger, LogUtil.LOG_BEGIN, "query", startTime);

        MessagesResponse msg = new MessagesResponse();

        try {
            Object resultObj = genericService.query(queryRequest);
            if (!(resultObj instanceof Page<?>)) {
                msg.setData(resultObj);
                return new ResponseEntity<>(msg, HttpStatus.OK);
            }
            Page<E> pPage = (Page<E>) (resultObj);
            PagnationRespBody<E> responseBody = new PagnationRespBody<E>().withContent(pPage.getContent())
                    .withTotalPage(pPage.getTotalPages()).withNumRec(pPage.getNumberOfElements())
                    .withTotalRecord(pPage.getTotalElements());
            msg.setData(responseBody);
        } catch (Exception ex) {
            logger.error(ex.getMessage(), ex);
            throw ex;
        } finally {
            LogUtil.showLog(logger, LogUtil.LOG_END, "query", startTime);
        }

        return new ResponseEntity<>(msg, HttpStatus.OK);
    }
}
