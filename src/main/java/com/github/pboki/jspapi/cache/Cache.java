package com.github.pboki.jspapi.cache;

import com.github.pboki.jspapi.requester.Requester;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author PBoki
 */
public abstract class Cache<T> {

    private final ConcurrentHashMap<Integer, T> data = new ConcurrentHashMap<>();

    public Optional<T> get(int id) {
        return Optional.ofNullable(this.data.get(id));
    }

    public ConcurrentHashMap<Integer, T> getData() {
        return data;
    }

    public void set(int id, T obj) {
        this.data.put(id, obj);
    }

    public abstract void refresh(Requester requester);

}