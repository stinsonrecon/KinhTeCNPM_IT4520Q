package vn.com.core.common.generics;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import vn.com.core.common.dto.OrderBy;
import vn.com.core.common.dto.QueryRequest;

import javax.validation.Valid;
import java.util.List;

public interface ComplexNativeQueryController<T> {

    ResponseEntity<Object> getAll(@RequestBody List<OrderBy> orders);

    ResponseEntity<Object> findByExample(@RequestBody T example);

    ResponseEntity<Object> query(@RequestBody QueryRequest<T> queryRequest);

    ResponseEntity<Object> create(@Valid @RequestBody T example);

    ResponseEntity<Object> update(@Valid @RequestBody T example);

    ResponseEntity<Object> delete(@PathVariable String id);

    ResponseEntity<Object> findById(@PathVariable String id);
}
