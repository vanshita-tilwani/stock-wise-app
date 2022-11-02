package model.stockpriceprovider;

public class StockProviderFactory {
  public static StockPriceProvider getStockProvider(StockProviderType type) {
    if (type == null)
      return null;
    switch (type) {
      case API:
        return new APIStockPriceProvider();
      case SCRIPT:
        return new MockStockPriceProvider();
      default:
        throw new IllegalArgumentException("Unknown type " + type);
    }
  }
}
