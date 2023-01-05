package vn.com.core.common.utils;

import org.springframework.data.domain.Sort;
import vn.com.core.common.dto.OrderBy;
import vn.com.core.common.dto.TreeData;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataUtil {

    public static List<? extends TreeData> parseToTree(List<? extends TreeData> lstData) {
        if (ValidationUtil.isNullOrEmpty(lstData)) {
            return new ArrayList<TreeData>();
        }

        List<TreeData> lstTreeData = new ArrayList<TreeData>();
        Map<Long, TreeData> mapTreeData = new HashMap<Long, TreeData>();

        for (TreeData node : lstData) {
            Long nodeId = node.getNodeId();
            mapTreeData.put(nodeId, node);
            if (node.isRoot()) {
                lstTreeData.add(node);
            }
        }
        for (TreeData node : lstData) {
            TreeData parentNode = mapTreeData.get(node.getParentNodeId());
            if (parentNode != null) {
                parentNode.addChild(node);
            }
        }

        return lstTreeData;
    }

    public static List<Order> buildOrderList(CriteriaBuilder cb, Root root, List<OrderBy> orders) {
        List<Order> lstOrder = new ArrayList<Order>();
        if (ValidationUtil.isNullOrEmpty(orders)) {
            return lstOrder;
        }
        for (OrderBy item : orders) {
            if (Sort.Direction.fromString(item.getDirection()).isAscending()) {
                lstOrder.add(cb.asc(root.get(item.getProperty())));
            } else if (Sort.Direction.fromString(item.getDirection()).isDescending()) {
                lstOrder.add(cb.desc(root.get(item.getProperty())));
            }
        }
        return lstOrder;
    }

    public static String buildOrderNative(List<OrderBy> orders) {
        if (ValidationUtil.isNullOrEmpty(orders)) {
            return "";
        }

        return getOrderByString(orders, "ORDER BY ");
    }

    public static String buildOrderNativeHierarchy(List<OrderBy> orders) {
        if (ValidationUtil.isNullOrEmpty(orders)) {
            return "";
        }

        return getOrderByString(orders, "ORDER SIBLINGS BY ");
    }

    private static String getOrderByString(List<OrderBy> orders, String orderByQuery) {
        StringBuilder sb = new StringBuilder(orderByQuery);

        for (int i = 0; i < orders.size(); i++) {
            OrderBy order = orders.get(i);
            sb.append(order.getProperty()).append(" ").append(order.getDirection());
            if (i < orders.size() - 1) {
                sb.append(", ");
            } else {
                sb.append(" ");
            }
        }

        return sb.toString();
    }

    public static void setNativeNamedParamsValue(Object example, String sql, Query query) {
        List<String> lstParams = getNamedParamsFromQueryString(sql);
        if (ValidationUtil.isNullOrEmpty(lstParams) || ValidationUtil.isNull(example)) {
            return;
        }

        for (String param : lstParams) {
            Object value = ReflectionUtil.callGetter(example, param);
            query.setParameter(param, value);
        }
    }

    public static List<String> getNamedParamsFromQueryString(String sql) {
        List<String> lstParams = new ArrayList<String>();
        if (ValidationUtil.isNullOrEmpty(sql)) {
            return lstParams;
        }

        String namedParamPrefix = ":";
        String space = " ";

        for (int index = sql.indexOf(namedParamPrefix); index >= 0; index = sql.indexOf(namedParamPrefix, index + 1)) {
            int endIndex = sql.indexOf(space, index + 1);
            endIndex = endIndex >= 0 ? endIndex : sql.length();

            String param = sql.substring(index + 1, endIndex);
            if (param.endsWith(",")) {
                param = param.substring(0, param.indexOf(","));
            } else if (param.endsWith(")")) {
                param = param.substring(0, param.indexOf(")"));
            }
            lstParams.add(param);
        }

        return lstParams;
    }

    public static String getNativeCountQueryString(String queryDataString) {
        if (ValidationUtil.isNullOrEmpty(queryDataString)) {
            return "";
        }

        String orderBy = "ORDER BY";
        String orderSiblingsBy = "ORDER SIBLINGS BY";

        String sqlCount;
        if (queryDataString.contains(orderBy)) {
            sqlCount = queryDataString.substring(0, queryDataString.indexOf(orderBy));
        } else if (queryDataString.contains(orderSiblingsBy)) {
            sqlCount = queryDataString.substring(0, queryDataString.indexOf(orderSiblingsBy));
        } else {
            sqlCount = queryDataString;
        }

        return "SELECT COUNT(*) FROM (" + sqlCount + ")";
    }
}
