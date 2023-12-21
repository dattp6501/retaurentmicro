package com.dattp.productservice.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {
    @Autowired
    private RedisTemplate<Object,Object> template;

    public boolean addDataToList(String key, Object object){
        return template.opsForList().rightPush(key, object)!=-1;
    }

    public List<Object> getData(String key, long start, long end){
        return template.opsForList().range(key, start, end);
    }

    public boolean remove(String key, long start, Object object){
        return template.opsForList().remove(key, start, object)>0;
    }

    public List<Object> getKeysByKeyPattern(String pattern){
        return new ArrayList<>(template.keys(pattern));
    }

    // hash
    public void addElementToListUseHash(String key, Object id, Object element){
        template.opsForHash().put(key, id.toString(), element);
    }
    public Object getElementFromListUseHash(String key, Object id){
        return template.opsForHash().get(key, id.toString());
    }
    public boolean deleteElementFromListUseHash(String key, Object id){
        return template.opsForHash().delete(key, id.toString())>0;
    }
    public List<Object> getAllElementFromListUseHash(String key){
        return template.opsForHash().multiGet(key, new ArrayList<>(template.opsForHash().keys(key)));
    }

    public boolean delete(String key){
        return template.delete(key);
    }
}