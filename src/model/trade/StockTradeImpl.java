package model.trade;

import java.time.LocalDate;

import model.stock.Stock;
import model.stock.StockImpl;
import model.stockpriceprovider.MockStockDataProvider;

/**
 * Represents a Trade of stocks i.e. has properties with stock and
 * the number of stocks contributing in the trade.
 */
public class StockTradeImpl implements Trade<Stock> {

  private final Stock stock;
  private final double quantity;

  /**
   * Creates a Stock Trade with default stock provider as API provider.
   *
   * @param stock    the stock object
   * @param quantity the number of stocks.
   */
  public StockTradeImpl(String stock, double quantity) {
    this.stock = new StockImpl(stock, new MockStockDataProvider());
    this.quantity = quantity;
  }


  @Override
  public Stock get() {
    return this.stock;
  }

  @Override
  public double value(LocalDate date) {
    return stock.price(date) * quantity;
  }

  @Override
  public String toString() {
    return "Stock Symbol : " + this.stock.toString() + ",Quantity : " + this.quantity + "\n";
  }
}
