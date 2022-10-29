package model.stockpriceprovider;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import model.cache.ICacheProvider;
import model.cache.ICacheProviderImpl;
import model.stock.IStock;

public class RandomStockPriceProvider implements IStockPriceProvider{

  private ICacheProvider<IStock, Map<LocalDate, Double>> stockData;

  public RandomStockPriceProvider() {
    stockData = new ICacheProviderImpl<>();
  }
  @Override
  public double price(IStock stock, LocalDate date) {
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
