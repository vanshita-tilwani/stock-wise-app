package model.stock;

import java.time.LocalDate;

import model.stockpriceprovider.StockPriceProvider;

/**
 * Represents a Regular Stock.
 */
public class StockImpl implements Stock {

  private final String name;
  private final StockPriceProvider stockPriceProvider;
  public StockImpl(String name, StockPriceProvider stockPriceProvider) {
    this.name = name;
    this.stockPriceProvider = stockPriceProvider;
  }

  @Override
  public String name() {
    return this.name;
  }


  @Override
  public double price(LocalDate date) {
    return stockPriceProvider.price(this, date);
  }

  @Override
  public String toString() {
    return this.name;
  }
}
