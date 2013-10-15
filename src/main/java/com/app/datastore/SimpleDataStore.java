package com.app.datastore;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class SimpleDataStore {
    
    private Map<Object, Object> storeMap;
    
    private SimpleDataStore() {
        storeMap = new HashMap<Object, Object>();
    }
        
    public void put(Object key, Object value) {
        storeMap.put(key, value);
    }
    
    public Object get(Object key) {
        return storeMap.get(key);
    }
    
    public boolean hasKey(Object key) {
       return storeMap.containsKey(key);
    }
    
    public Set<Object> keys() {
        return storeMap.keySet();
    }
}
