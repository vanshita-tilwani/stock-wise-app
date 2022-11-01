package model.stock;

import java.time.LocalDate;

import model.stockpriceprovider.StockPriceProvider;

/**
 * Represents a Regular Stock.
 */
public class StockImpl implements Stock {

  // name represents the stock symbol
  private final String name;

  // price provider to determine stock price on any date.
  private final StockPriceProvider stockPriceProvider;

  /**
   * A new stock with the stock symbol as name and the provider used to determine
   * the price of the stock.
   * @param name stock symbol.
   * @param stockPriceProvider price provider.
   */
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
