package model.configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of Config interface to maintain all the configuration settings
 * in memory for faster lookups.
 */
public class InMemoryConfiguration implements Configuration<String, String> {

  // singleton instance of the class
  private static final Configuration<String, String> instance = new InMemoryConfiguration();

  // map containing all the key,value pairs for all config settings.
  private final Map<String, String> configMap;

  /**
   * Initializes an instance of in-memory config.
   */
  private InMemoryConfiguration() {
    this.configMap = new HashMap<>();
    // TODO : remove this and let client handle
    this.configMap.put("data-provider", "WEBAPI");
  }

  /**
   * Returns the singleton instance of the in-memory configuration.
   * @return in memory config object.
   */
  public static Configuration<String, String> getInstance() {
    return instance;
  }

  @Override
  public String get(String key) {
    return this.configMap.get(key);
  }

  @Override
  public void put(String key, String value) {
    this.configMap.put(key, value);
  }

  @Override
  public boolean contains(String key) {
    return this.configMap.containsKey(key);
  }
}
