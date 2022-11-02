package model.cache;

import java.util.HashMap;
import java.util.Map;

/**
 * Implements the In Memory cache.
 *
 * @param <K> the type of key
 * @param <V> the type of value
 */
public class InMemoryCacheProvider<K, V> implements CacheProvider<K, V> {

  // uses the map as key, value pair for the cache.
  private Map<K, V> map;

  /**
   * Creates a new instance of in memory cache with empty cache.
   */
  public InMemoryCacheProvider() {
    map = new HashMap<>();
  }

  @Override
  public V get(Object key) {
    if (!map.containsKey(key))
      return null;
    return map.get(key);
  }

  @Override
  public void put(K key, V value) {
    if (map.containsKey(key))
      return;
    map.put(key, value);
  }

  @Override
  public boolean contains(K key) {
    return map.containsKey(key);
  }

  @Override
  public int size() {
    return map.size();
  }
}
