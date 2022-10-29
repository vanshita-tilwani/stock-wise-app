package model.stock;

import java.time.LocalDate;

import model.stockpriceprovider.StockPriceProvider;

/**
 * Represents a Regular Stock.
 */
public class StockImpl implements Stock {

  private final String name;
  private final String company;
  private final StockPriceProvider stockPriceProvider;
  public StockImpl(String name, String company, StockPriceProvider stockPriceProvider) {
    this.name = name;
    this.company = company;
    this.stockPriceProvider = stockPriceProvider;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public String company() {
    return this.company;
  }

  @Override
  public double price(LocalDate date) {
    return stockPriceProvider.price(this, date);
  }
}
