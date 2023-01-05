package vn.com.core.common.generics.impl;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import vn.com.core.common.dto.OrderBy;
import vn.com.core.common.dto.Paging;
import vn.com.core.common.dto.QueryRequest;
import vn.com.core.common.utils.DataUtil;
import vn.com.core.common.utils.ValidationUtil;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unchecked"})
public abstract class ComplexNativeQueryRepository<T> {

    private boolean isPaging(Paging pageInfo) {
        return !ValidationUtil.isNull(pageInfo);
    }

    public T findById(Object id) {
        String sql = getSearchQueryString();
        if (ValidationUtil.isNullOrEmpty(sql)) {
            return null;
        }

        StringBuilder sb = new StringBuilder(sql);
        sb.append(addFindByIdCondition());

        Query query = getNativeQuery(sb.toString());
        DataUtil.setNativeNamedParamsValue(id, sb.toString(), query);

        List<T> lstMappedResult = getLstMappedResult(query);
        return lstMappedResult.get(0);
    }

    protected String addFindByIdCondition() {
        throw new UnsupportedOperationException("The operation is not supported");
    }

    private List<T> getLstMappedResult(Query query) {
        List<T> lstMappedResult;

        if (useTuple()) {
            List<Tuple> lstResult = query.getResultList();
            lstMappedResult = new ArrayList<T>(buildListResult(lstResult));
        } else {
            lstMappedResult = query.getResultList();
        }
        return lstMappedResult;
    }

    public List<T> findByExample(T example) {
        Map<String, Object> params = new HashMap<String, Object>();
        String sql = buildFullQuery(getSearchQueryString(), example, null, params);

        Query query = getNativeQuery(sql);

        if (!params.isEmpty()) {
            for (String param : params.keySet()) {
                query.setParameter(param, params.get(param));
            }
        }

        return getLstMappedResult(query);
    }

    public List<T> getAll(List<OrderBy> orders) {
        String sql = buildFullQuery(getSearchQueryString(), null, orders, null);
        Query query = getNativeQuery(sql);

        return getLstMappedResult(query);
    }

    public Object query(QueryRequest<T> queryRequest) {
        T example = queryRequest.getSample();
        Paging pageInfo = queryRequest.getPageInfo();
        List<OrderBy> orders = queryRequest.getOrders();

        Map<String, Object> params = new HashMap<String, Object>();

        EntityManager em = getEntityManager();
        String sql = buildFullQuery(getSearchQueryString(), example, orders, params);
        Query query = getNativeQuery(sql);

        Query queryCount = null;
        if (isPaging(pageInfo)) {
            String sqlCount = sql.contains("ORDER BY") ? sql.substring(0, sql.indexOf("ORDER BY")) : sql;
            sqlCount = "SELECT COUNT(*) FROM (" + sqlCount + ")";
            queryCount = em.createNativeQuery(sqlCount);
        }

        if (!params.isEmpty()) {
            for (String param : params.keySet()) {
                query.setParameter(param, params.get(param));
                if (!ValidationUtil.isNull(queryCount)) {
                    queryCount.setParameter(param, params.get(param));
                }
            }
        }

        if (isPaging(pageInfo)) {
            query.setFirstResult(pageInfo.getIndex() * pageInfo.getSize());
            query.setMaxResults(pageInfo.getSize());
        }

        List<T> lstMappedResult = getLstMappedResult(query);

        if (isPaging(pageInfo)) {
            long count = ((BigDecimal) queryCount.getSingleResult()).longValue();
            return new PageImpl<T>(lstMappedResult, PageRequest.of(pageInfo.getIndex(), pageInfo.getSize()), count);
        }

        return lstMappedResult;
    }

    private Query getNativeQuery(String sql) {
        EntityManager em = getEntityManager();
        return useTuple() ? em.createNativeQuery(sql, Tuple.class)
                : em.createNativeQuery(sql, getNamedResultSetMapping());
    }

    private String buildFullQuery(String sql, T example, List<OrderBy> orders, Map<String, Object> params) {
        if (ValidationUtil.isNullOrEmpty(sql)) {
            return "";
        }
        StringBuilder sb = new StringBuilder(sql);

        // build where condition
        sb.append(addWhereCondition(example, params));

        // build order by
        sb.append(DataUtil.buildOrderNative(orders));

        return sb.toString();
    }

    protected String getNamedResultSetMapping() {
        return null;
    }

    private boolean useTuple() {
        return ValidationUtil.isNullOrEmpty(getNamedResultSetMapping());
    }

    protected boolean isValidInputAddWhereCondition(T example, Map<String, Object> params) {
        if (ValidationUtil.isNull(example)) {
            return false;
        }
        if (ValidationUtil.isNull(params)) {
            params = new HashMap<String, Object>();
        }
        return true;
    }

    protected abstract List<T> buildListResult(List<Tuple> lstResult);

    protected abstract String addWhereCondition(T example, Map<String, Object> params);

    protected abstract String getSearchQueryString();

    protected abstract EntityManager getEntityManager();
}
