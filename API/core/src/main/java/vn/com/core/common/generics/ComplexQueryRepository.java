package vn.com.core.common.generics;

import vn.com.core.common.dto.OrderBy;
import vn.com.core.common.dto.Paging;

import java.util.List;

public interface ComplexQueryRepository<T> {

    List<T> findByExample(T example);

    Object query(T example, Paging pageInfo, List<OrderBy> lstOrder);
}
