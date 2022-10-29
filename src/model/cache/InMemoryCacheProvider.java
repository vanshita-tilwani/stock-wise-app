package model.cache;
import java.util.HashMap;
import java.util.Map;

public class InMemoryCacheProvider<K,V> implements CacheProvider<K,V> {
  private Map<K, V> map;

  public InMemoryCacheProvider() {
    map = new HashMap<>();
  }
  @Override
  public V get(Object key) {
    if(!map.containsKey(key))
      return null;
    return map.get(key);
  }

  @Override
  public void put(K key, V value) {
    if(map.containsKey(key))
      return;
    map.put(key, value);
  }

  @Override
  public boolean contains(K key) {
    return map.containsKey(key);
  }
}
