package model.stock;

import java.time.LocalDate;

import model.stockpriceprovider.StockDataProvider;
import model.stockpriceprovider.StockDataProviderFactory;

/**
 * Represents a Regular Stock.
 */
public class StockImpl implements Stock {

  // name represents the stock symbol
  private final String name;

  // price provider to determine stock price on any date.
  private final StockDataProvider stockPriceProvider;

  /**
   * A new stock with the stock symbol as name and the provider used to determine
   * the price of the stock.
   *
   * @param name stock symbol.
   */
  public StockImpl(String name) {
    this.stockPriceProvider = StockDataProviderFactory.getDataProvider();
    String tempName = name.toUpperCase();
    if (!this.stockPriceProvider.isValid(tempName)) {
      throw new IllegalArgumentException(name + " is an Invalid Stock\n");
    }
    this.name = tempName;
  }

  @Override
  public String name() {
    return this.name;
  }


  @Override
  public double price(LocalDate date) {
    return stockPriceProvider.price(this.name, date);
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (!(obj instanceof StockImpl)) {
      return false;
    }
    Stock stock = (StockImpl) obj;
    return this.name.equals(stock.name());
  }

  @Override
  public int hashCode() {
    return this.name.hashCode();
  }
}
