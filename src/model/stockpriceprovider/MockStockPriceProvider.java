package model.stockpriceprovider;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import model.cache.CacheProvider;
import model.cache.InMemoryCacheProvider;
import model.stock.Stock;

/**
 * Generates a Price randomly for a stock at any given date.
 */
public class MockStockPriceProvider implements StockPriceProvider {

  // cache to store the data for the stock
  private CacheProvider<String, Map<LocalDate, Double>> stockData;

  /**
   * creates an instance of random stock provider which initializes the cache of the
   * stock data.
   */
  public MockStockPriceProvider() {
    stockData = new InMemoryCacheProvider<>();
  }
  @Override
  public double price(String stock, LocalDate date) {
    double price = new Random().nextDouble();
    if(stockData.contains(stock)) {
      if(stockData.get(stock).containsKey(date)) {
        price = stockData.get(stock).get(date);
      }
      else {
        stockData.get(stock).put(date, price);
      }
    }
    else {
      Map<LocalDate, Double> obj = new HashMap<>();
      obj.put(date, price);
      stockData.put(stock, obj);
    }
    return price;
  }

  @Override
  public boolean isValid(String stock) {
    return true;
  }
}
