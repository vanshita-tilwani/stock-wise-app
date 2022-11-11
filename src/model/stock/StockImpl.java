package model.stock;

import java.time.LocalDate;

import model.stockpriceprovider.MockStockDataProvider;
import model.stockpriceprovider.StockDataProvider;
import model.stockpriceprovider.WebAPIStockDataProvider;


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
   * @param name               stock symbol.
   */
  public StockImpl(String name) {
    this.stockPriceProvider = WebAPIStockDataProvider.getInstance();
    if (!this.stockPriceProvider.isValid(name)) {
      throw new IllegalArgumentException(name + " is an Invalid Stock\n");
    }
    this.name = name;
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
    return this.name.toUpperCase();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (!(obj instanceof StockImpl)) {
      return false;
    }
    Stock stock = (StockImpl) obj;
    return this.toString().equals(stock.toString());
  }

  @Override
  public int hashCode() {
    return this.toString().hashCode();
  }
}
