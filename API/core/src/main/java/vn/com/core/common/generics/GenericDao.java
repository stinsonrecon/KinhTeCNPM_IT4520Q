package vn.com.core.common.generics;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface GenericDao<T, ID extends Serializable> {
    List<T> getByCondition(Map map);

}
