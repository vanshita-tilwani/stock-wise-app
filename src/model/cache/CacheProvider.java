package model.cache;

/**
 * Works a cache for quick access of data i.e. to reduce fetch time for the records.
 * @param <K> the type of Key
 * @param <V> the type of value
 */
public interface CacheProvider<K, V> {

  /**
   * Gets the value for a key from the cache.
   * @param key the key for the cache.
   * @return the value stored in cache against the key.
   */
  V get(K key);

  /**
   * Adds the key value pair to the cache.
   * @param key the key of the data.
   * @param value the value of the data.
   */
  void put(K key, V value);

  /**
   * Returns if the key is available in the cache.
   * @param key the key of the cache.
   * @return true/false.
   */
  boolean contains(K key);

  /**
   * Returns the size of the cache.
   * @return size
   */
  int size();
}
