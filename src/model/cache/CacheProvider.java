package model.cache;

public interface CacheProvider<K, V> {

  V get(K key);

  void put(K key, V value);

  boolean contains(K key);
}
