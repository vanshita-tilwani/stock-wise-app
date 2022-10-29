package model.cache;

public interface ICacheProvider<K, V> {

  V get(K key);

  void put(K key, V value);

  boolean contains(K key);
}
