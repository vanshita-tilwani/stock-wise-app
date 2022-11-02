package model.trade;

import java.time.LocalDate;

import model.stock.Stock;
import model.stock.StockImpl;
import model.stockpriceprovider.APIStockPriceProvider;
import model.stockpriceprovider.StockPriceProvider;
import model.stockpriceprovider.StockProviderFactory;
import model.stockpriceprovider.StockProviderType;

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
    this.stock = new StockImpl(stock, new APIStockPriceProvider());
    this.quantity = quantity;
  }

  /**
   * Creates a stock trade with the provided Provider type.
   *
   * @param stock         the stock object.
   * @param quantity      the number of stocks
   * @param stockProvider the type of price provider that needs to be used to evaluate price.
   */
  public StockTradeImpl(String stock, double quantity, StockProviderType stockProvider) {
    StockPriceProvider provider = StockProviderFactory.getStockProvider(stockProvider);
    this.stock = new StockImpl(stock, provider);
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
