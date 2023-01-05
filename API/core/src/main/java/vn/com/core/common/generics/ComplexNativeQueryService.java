package vn.com.core.common.generics;


import vn.com.core.common.dto.OrderBy;
import vn.com.core.common.dto.QueryRequest;

import java.util.List;

public interface ComplexNativeQueryService<T> {

    Object query(QueryRequest<T> queryRequest);

    T create(T example);

    T update(T example);

    int delete(String id);

    List<T> getAll(List<OrderBy> orders);

    List<T> findByExample(T example);

    T findById(String id);
}
