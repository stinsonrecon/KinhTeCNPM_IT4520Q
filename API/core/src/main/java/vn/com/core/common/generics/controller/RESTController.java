package vn.com.core.common.generics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import vn.com.core.common.config.CoreConstants;
import vn.com.core.common.dto.*;
import vn.com.core.common.generics.GenericService;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

public class RESTController<E, P extends Serializable> {

    @Autowired
    private GenericService<E, P> genericService;

    public GenericService<E, P> getService() {
        return genericService;
    }

    @SuppressWarnings("unchecked")
    public <G> G getService(Class<G> customType) {
        return (G) genericService;
    }

    @PreAuthorize("@appAuthorizer.authorize(authentication, 'VIEW', this)")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public APIResponse findById(@PathVariable P id) {
        return new APISuccReponse().withMessage(CoreConstants.MESSAGE.SUCCESS).withBody(genericService.findById(id));
    }

    @PreAuthorize("@appAuthorizer.authorize(authentication, 'VIEW', this)")
    @RequestMapping(value = "/get-all", method = RequestMethod.POST, consumes = { "application/json" })
    @ResponseBody
    public APIResponse findAll(@RequestBody List<OrderBy> orderBys) throws Exception {
        return new APISuccReponse().withMessage(CoreConstants.MESSAGE.SUCCESS).withBody(genericService.queryAllAndSort(orderBys));
    }

    @PreAuthorize("@appAuthorizer.authorize(authentication, 'VIEW', this)")
    @RequestMapping(value = "/page", method = RequestMethod.POST)
    @ResponseBody
    public APIResponse page(@RequestParam int page, @RequestParam int size, @RequestBody List<OrderBy> orderBys) {
        Page<E> pPage = genericService.findPage(page, size, orderBys);
        PagnationRespBody<E> responseBody = new PagnationRespBody<E>().withContent(pPage.getContent())
                .withTotalPage(pPage.getTotalPages()).withNumRec(pPage.getNumberOfElements());
        return new APISuccReponse().withMessage(CoreConstants.MESSAGE.SUCCESS).withBody(responseBody);
    }

    @PreAuthorize("@appAuthorizer.authorize(authentication, 'VIEW', this)")
    @RequestMapping(value = "/find-by-example", method = RequestMethod.POST)
    @ResponseBody
    public List<E> findByExample(@RequestBody E e) {
        return genericService.findByExample(e);
    }

    @PreAuthorize("@appAuthorizer.authorize(authentication, 'CREATE', this)")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public APIResponse create(@RequestBody @Valid E e) {
        E createdObject = genericService.save(e);
        return new APISuccReponse().withMessage(CoreConstants.MESSAGE.CREAT_SUCCESS).withBody(createdObject);
    }

    @PreAuthorize("@appAuthorizer.authorize(authentication, 'UPDATE', this)")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public APIResponse update(@RequestBody @Valid E e) {
        E updatedObj = genericService.save(e);
        return new APISuccReponse().withMessage(CoreConstants.MESSAGE.UPDATE_SUCCESS).withBody(updatedObj);
    }

    @PreAuthorize("@appAuthorizer.authorize(authentication, 'DELETE', this)")
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public APIResponse delete(@PathVariable P id) {
        genericService.delete(id);
        return new APISuccReponse().withMessage(CoreConstants.MESSAGE.DELETE_SUCCESS).emptyBody();
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    @PreAuthorize("@appAuthorizer.authorize(authentication, 'VIEW', this)")
    @ResponseBody
    public APIResponse query(@Valid @NonNull @RequestBody QueryRequest<E> queryRequest) {
        Object resultObj = genericService.query(queryRequest);
        if (!(resultObj instanceof Page<?>)) {
            return new APISuccReponse().withMessage(CoreConstants.MESSAGE.SUCCESS).withBody(resultObj);
        }
        Page<E> pPage = (Page<E>) (resultObj);
        PagnationRespBody<E> responseBody = new PagnationRespBody<E>().withContent(pPage.getContent())
                .withTotalPage(pPage.getTotalPages()).withNumRec(pPage.getNumberOfElements());
        return new APISuccReponse().withMessage(CoreConstants.MESSAGE.SUCCESS)
                .withBody(responseBody);
    }
}
