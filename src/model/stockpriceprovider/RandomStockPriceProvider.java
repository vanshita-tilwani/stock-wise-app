package model.stockpriceprovider;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import model.cache.CacheProvider;
import model.cache.InMemoryCacheProvider;
import model.stock.Stock;

public class RandomStockPriceProvider implements StockPriceProvider {

  private CacheProvider<Stock, Map<LocalDate, Double>> stockData;

  public RandomStockPriceProvider() {
    stockData = new InMemoryCacheProvider<>();
  }
  @Override
  public double price(Stock stock, LocalDate date) {
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
}
