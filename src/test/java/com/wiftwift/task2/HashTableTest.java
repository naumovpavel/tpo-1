package com.wiftwift.task2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class HashTableTest {

    private HashTable<String, Integer> hashTable;
    
    @BeforeEach
    void setUp() {
        hashTable = new HashTable<>(10);
        hashTable.clearTrace();
    }
    
    @Test
    void testPutNewKey() {
        hashTable.put("test", 1);
        
        List<HashTable.TracePoint> trace = hashTable.getTrace();
        List<HashTable.TracePoint> expected = List.of(
            HashTable.TracePoint.PUT_START,
            HashTable.TracePoint.PUT_NEW_CHAIN,
            HashTable.TracePoint.PUT_KEY_NOT_EXISTS
        );
        
        assertEquals(expected, trace);
        assertEquals(1, hashTable.get("test"));
    }
    
    @Test
    void testPutExistingKey() {
        hashTable.put("test", 1);
        hashTable.clearTrace();
        
        hashTable.put("test", 2);
        
        List<HashTable.TracePoint> trace = hashTable.getTrace();
        List<HashTable.TracePoint> expected = List.of(
            HashTable.TracePoint.PUT_START,
            HashTable.TracePoint.PUT_CHAIN_EXISTS,
            HashTable.TracePoint.PUT_KEY_EXISTS
        );
        
        assertEquals(expected, trace);
        assertEquals(2, hashTable.get("test"));
    }
    
    @Test
    void testPutKeyCollision() {
        class CollisionKey {
            private final String value;
            private final int hashCode;
            
            public CollisionKey(String value, int hashCode) {
                this.value = value;
                this.hashCode = hashCode;
            }
            
            @Override
            public int hashCode() {
                return hashCode;
            }
            
            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                CollisionKey that = (CollisionKey) obj;
                return Objects.equals(value, that.value);
            }
            
            @Override
            public String toString() {
                return value;
            }
        }
        
        HashTable<CollisionKey, Integer> customHashTable = new HashTable<>(10);
        
        CollisionKey key1 = new CollisionKey("key1", 123);
        CollisionKey key2 = new CollisionKey("key2", 123); // Тот же хеш, но другой ключ
        
        customHashTable.put(key1, 1);
        customHashTable.clearTrace();
        
        customHashTable.put(key2, 2);
        
        List<HashTable.TracePoint> trace = customHashTable.getTrace();
        List<HashTable.TracePoint> expected = List.of(
            HashTable.TracePoint.PUT_START,
            HashTable.TracePoint.PUT_CHAIN_EXISTS,
            HashTable.TracePoint.PUT_KEY_NOT_EXISTS
        );
        
        assertEquals(expected, trace);
        assertEquals(1, customHashTable.get(key1));
        assertEquals(2, customHashTable.get(key2));
    }
    
    @Test
    void testGetExistingKey() {
        hashTable.put("test", 1);
        hashTable.clearTrace();
        
        hashTable.get("test");
        
        List<HashTable.TracePoint> trace = hashTable.getTrace();
        List<HashTable.TracePoint> expected = List.of(
            HashTable.TracePoint.GET_START,
            HashTable.TracePoint.GET_KEY_FOUND
        );
        
        assertEquals(expected, trace);
    }
    
    @Test
    void testGetNonExistingKey() {
        hashTable.get("nonexistent");
        
        List<HashTable.TracePoint> trace = hashTable.getTrace();
        List<HashTable.TracePoint> expected = List.of(
            HashTable.TracePoint.GET_START,
            HashTable.TracePoint.GET_NO_CHAIN
        );
        
        assertEquals(expected, trace);
    }
    
    @Test
    void testGetNonExistingKeyInExistingChain() {
        class CollisionKey {
            private final String value;
            private final int hashCode;
            
            public CollisionKey(String value, int hashCode) {
                this.value = value;
                this.hashCode = hashCode;
            }
            
            @Override
            public int hashCode() {
                return hashCode;
            }
            
            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                CollisionKey that = (CollisionKey) obj;
                return Objects.equals(value, that.value);
            }
        }
        
        HashTable<CollisionKey, Integer> customHashTable = new HashTable<>(10);
        
        CollisionKey key1 = new CollisionKey("key1", 123);
        customHashTable.put(key1, 1);
        customHashTable.clearTrace();
        
        CollisionKey key2 = new CollisionKey("key2", 123);
        customHashTable.get(key2);
        
        List<HashTable.TracePoint> trace = customHashTable.getTrace();
        List<HashTable.TracePoint> expected = List.of(
            HashTable.TracePoint.GET_START,
            HashTable.TracePoint.GET_KEY_NOT_FOUND
        );
        
        assertEquals(expected, trace);
    }
    
    @Test
    void testRemoveExistingKey() {
        hashTable.put("test", 1);
        hashTable.clearTrace();
        
        boolean result = hashTable.remove("test");
        
        List<HashTable.TracePoint> trace = hashTable.getTrace();
        List<HashTable.TracePoint> expected = List.of(
            HashTable.TracePoint.REMOVE_START,
            HashTable.TracePoint.REMOVE_KEY_FOUND
        );
        
        assertEquals(expected, trace);
        assertTrue(result);
        assertNull(hashTable.get("test"));
    }
    
    @Test
    void testRemoveNonExistingKey() {
        boolean result = hashTable.remove("nonexistent");
        
        List<HashTable.TracePoint> trace = hashTable.getTrace();
        List<HashTable.TracePoint> expected = List.of(
            HashTable.TracePoint.REMOVE_START,
            HashTable.TracePoint.REMOVE_NO_CHAIN
        );
        
        assertEquals(expected, trace);
        assertFalse(result);
    }
    
    @Test
    void testRemoveNonExistingKeyInExistingChain() {
        class CollisionKey {
            private final String value;
            private final int hashCode;
            
            public CollisionKey(String value, int hashCode) {
                this.value = value;
                this.hashCode = hashCode;
            }
            
            @Override
            public int hashCode() {
                return hashCode;
            }
            
            @Override
            public boolean equals(Object obj) {
                if (this == obj) return true;
                if (obj == null || getClass() != obj.getClass()) return false;
                CollisionKey that = (CollisionKey) obj;
                return Objects.equals(value, that.value);
            }
        }
        
        HashTable<CollisionKey, Integer> customHashTable = new HashTable<>(10);
        
        CollisionKey key1 = new CollisionKey("key1", 123);
        customHashTable.put(key1, 1);
        customHashTable.clearTrace();
        
        CollisionKey key2 = new CollisionKey("key2", 123);
        boolean result = customHashTable.remove(key2);
        
        List<HashTable.TracePoint> trace = customHashTable.getTrace();
        List<HashTable.TracePoint> expected = List.of(
            HashTable.TracePoint.REMOVE_START,
            HashTable.TracePoint.REMOVE_KEY_NOT_FOUND
        );
        
        assertEquals(expected, trace);
        assertFalse(result);
        assertEquals(1, customHashTable.get(key1));
    }
}
