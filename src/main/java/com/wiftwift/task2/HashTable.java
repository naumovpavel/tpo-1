package com.wiftwift.task2;

import java.util.LinkedList;
import java.util.List;

public class HashTable<K, V> {
    
    private static class Entry<K, V> {
        private final K key;
        private V value;
        
        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    
    public enum TracePoint {
        INIT,               
        PUT_START,          
        PUT_NEW_CHAIN,      
        PUT_CHAIN_EXISTS,   
        PUT_KEY_EXISTS,     
        PUT_KEY_NOT_EXISTS, 
        GET_START,          
        GET_NO_CHAIN,       
        GET_KEY_FOUND,      
        GET_KEY_NOT_FOUND,  
        REMOVE_START,       
        REMOVE_NO_CHAIN,    
        REMOVE_KEY_FOUND,   
        REMOVE_KEY_NOT_FOUND
    }
    
    private List<TracePoint> trace = new LinkedList<>();
    private final int capacity;
    private final List<LinkedList<Entry<K, V>>> buckets;
    
    public HashTable(int capacity) {
        this.capacity = capacity;
        buckets = new LinkedList<>();
        for (int i = 0; i < capacity; i++) {
            buckets.add(null);
        }
        trace.add(TracePoint.INIT);
    }
    
    private int getHash(K key) {
        return key == null ? 0 : Math.abs(key.hashCode() % capacity);
    }
    
    public void put(K key, V value) {
        trace.add(TracePoint.PUT_START);
        int hash = getHash(key);
        
        if (buckets.get(hash) == null) {
            trace.add(TracePoint.PUT_NEW_CHAIN);
            buckets.set(hash, new LinkedList<>());
        } else {
            trace.add(TracePoint.PUT_CHAIN_EXISTS);
        }
        
        LinkedList<Entry<K, V>> chain = buckets.get(hash);
        
        for (Entry<K, V> entry : chain) {
            if (entry.key.equals(key)) {
                trace.add(TracePoint.PUT_KEY_EXISTS);
                entry.value = value;
                return;
            }
        }
        
        trace.add(TracePoint.PUT_KEY_NOT_EXISTS);
        chain.add(new Entry<>(key, value));
    }
    
    public V get(K key) {
        trace.add(TracePoint.GET_START);
        int hash = getHash(key);
        
        LinkedList<Entry<K, V>> chain = buckets.get(hash);
        if (chain == null) {
            trace.add(TracePoint.GET_NO_CHAIN);
            return null;
        }
        
        for (Entry<K, V> entry : chain) {
            if (entry.key.equals(key)) {
                trace.add(TracePoint.GET_KEY_FOUND);
                return entry.value;
            }
        }
        
        trace.add(TracePoint.GET_KEY_NOT_FOUND);
        return null;
    }
    
    public boolean remove(K key) {
        trace.add(TracePoint.REMOVE_START);
        int hash = getHash(key);
        
        LinkedList<Entry<K, V>> chain = buckets.get(hash);
        if (chain == null) {
            trace.add(TracePoint.REMOVE_NO_CHAIN);
            return false;
        }
        
        for (Entry<K, V> entry : chain) {
            if (entry.key.equals(key)) {
                trace.add(TracePoint.REMOVE_KEY_FOUND);
                chain.remove(entry);
                return true;
            }
        }
        
        trace.add(TracePoint.REMOVE_KEY_NOT_FOUND);
        return false;
    }
    
    public List<TracePoint> getTrace() {
        return new LinkedList<>(trace);
    }
    
    public void clearTrace() {
        trace.clear();
    }
}
