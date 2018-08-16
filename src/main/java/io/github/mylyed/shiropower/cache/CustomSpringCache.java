package io.github.mylyed.shiropower.cache;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

@Slf4j
public class CustomSpringCache implements Cache {

    private Map<Object, Object> store;


    public Map<Object, Object> getStore() {
        return store;
    }

    String cacheName;

    public CustomSpringCache(String cacheName) {
        this.store = new HashMap<>();
        this.cacheName = cacheName;
    }

    @Override
    public String getName() {
        return cacheName;
    }

    @Override
    public Object getNativeCache() {
        return store;
    }

    @Override
    public ValueWrapper get(Object key) {
        return toValueWrapper(store.get(key));
    }

    @Override
    public <T> T get(Object key, @Nullable Class<T> type) {
        return (T) store.get(key);
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        return (T) this.store.computeIfAbsent(key, (Object r) -> {
            try {
                return valueLoader.call();
            } catch (Exception e) {
                e.printStackTrace();
                throw new ValueRetrievalException(key, valueLoader, e);
            }
        });
    }

    @Override
    public void put(Object key, @Nullable Object value) {
        log.debug("key类型=" + key.getClass());
        store.put(key, value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, @Nullable Object value) {
        Object existing = store.putIfAbsent(key, value);
        return toValueWrapper(existing);
    }

    @Override
    public void evict(Object key) {
        store.remove(key);
    }

    @Override
    public void clear() {
        store.clear();
    }

    @Nullable
    protected Cache.ValueWrapper toValueWrapper(@Nullable Object storeValue) {
        return (storeValue != null ? new SimpleValueWrapper(storeValue) : null);
    }

}