package vn.com.core.common.generics.impl;

import vn.com.core.common.dto.OrderBy;
import vn.com.core.common.dto.Paging;
import vn.com.core.common.generics.ComplexQueryRepository;
import vn.com.core.common.generics.ComplexQueryService;

import java.io.Serializable;
import java.util.List;

@SuppressWarnings({"unchecked"})
public abstract class ComplexQueryServiceImpl<E, P extends Serializable> extends GenericServiceImpl<E, P> implements ComplexQueryService<E, P> {

    @Override
    public List<E> findByExample(E e) {
        return ((ComplexQueryRepository<E>) getRepository()).findByExample(e);
    }

    @Override
    public Object queryByExamplePageAndSort(E e, Paging pageInfo, List<OrderBy> orderBys) {
        return ((ComplexQueryRepository<E>) getRepository()).query(e, pageInfo, orderBys);
    }
}
