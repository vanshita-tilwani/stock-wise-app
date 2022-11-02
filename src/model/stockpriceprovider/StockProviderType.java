package model.stockpriceprovider;

import java.util.HashMap;
import java.util.Map;

public enum StockProviderType {
  API(1),
  SCRIPT(2);

  private final int value;

  StockProviderType(final int newValue) {
    value = newValue;
  }

  public int getValue() {
    return value;
  }

  // Mapping difficulty to difficulty id
  private static final Map<Integer, StockProviderType> _map = new HashMap<Integer, StockProviderType>();

  static {
    for (StockProviderType difficulty : StockProviderType.values())
      _map.put(difficulty.value, difficulty);
  }

  /**
   * Get difficulty from value
   *
   * @param value Value
   * @return Difficulty
   */
  public static StockProviderType from(int value) {
    return _map.get(value);
  }
}
