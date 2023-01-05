package vn.com.core.common.generics;

import org.springframework.data.domain.Page;
import vn.com.core.common.dto.OrderBy;
import vn.com.core.common.dto.Paging;
import vn.com.core.common.dto.QueryRequest;

import java.util.List;
import java.util.Map;

public interface GenericService<T, ID> {

    T findById(ID id);

    List<T> findAll(List<ID> ids);

    T save(T obj);

    List<T> save(List<T> lst) throws Exception;

    void delete(ID key);

    void delete(List<T> lst);

    void deleteAll();

    boolean existsById(ID key);

    List<T> queryAllAndSort(List<OrderBy> orderBys);

    List<T> findByExample(T e);

    List<T> findLimit(int numberSkip, int limit);

    Page<T> findPage(int numberSkip, int limit, List<OrderBy> orderBys);

    Object query(QueryRequest<T> request);

    Object queryByExamplePageAndSort(T e, Paging pageInfo, List<OrderBy> orderBys);

    List<T> findAll();

    List<T> getByCondition(Map map);
}
