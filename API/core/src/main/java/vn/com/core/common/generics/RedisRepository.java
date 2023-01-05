package vn.com.core.common.generics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository("RedisRepo")
public class RedisRepository {
    Logger logger = LoggerFactory.getLogger(getClass());
    public static String _PERMISSION_MENU = "PERMISSION_MENU";
    private HashOperations hashOperations;
    private ListOperations listOperations;

    private RedisTemplate redisTemplate;

    public void savePrivilege(Long userId, Object privilege) {
        putData(userId, privilege);
    }

    public RedisRepository(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = this.redisTemplate.opsForHash();
        this.listOperations = this.redisTemplate.opsForList();
    }

    public void putData(Long userId, Object privilege) {
        try {
            hashOperations.put(_PERMISSION_MENU, userId, privilege);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public Object findPermission(Long userId) {
        try {
            Object map = get(_PERMISSION_MENU, userId);
            return map;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public Object get(String hashKey, String key) {
        try {
            return hashOperations.get(hashKey, key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public Object get(String hashKey, Integer key) {
        try {
            return hashOperations.get(hashKey, key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public Object get(String hashKey, Long key) {
        try {
            return hashOperations.get(hashKey, key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }


    public void put(String hashKey, String key, Serializable object) {
        try {
            hashOperations.put(hashKey, key, object);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void put(String hashKey, Integer key, Serializable object) {
        try {
            hashOperations.put(hashKey, key, object);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void put(String hashKey, Long key, Serializable object) {
        try {
            hashOperations.put(hashKey, key, object);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void delete(String hashKey, String key) {
        try {
            hashOperations.delete(hashKey, key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void delete(String hashKey, Integer key) {
        try {
            hashOperations.delete(hashKey, key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void delete(String hashKey, Long key) {
        try {
            hashOperations.delete(hashKey, key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }


    public List getList(String key) {
        try {
            long size = listOperations.size(key);
            return listOperations.range(key, 0, size - 1);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public void putList(String key, List list) {
        try {
            listOperations.rightPush(key, list);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public ListOperations getListOperations() {
        return listOperations;
    }
}
