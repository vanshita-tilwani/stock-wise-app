package model.trade;

import java.time.LocalDate;

import model.stockpriceprovider.StockDataProvider;

/**
 * Represents a Trade of stocks with stock as properties along with
 * the number of stocks contributing in the trade.
 */
public class CumulativeStockTrade extends AbstractStockTrade {

  /**
   * Creates a Stock Trade with default stock provider as API provider.
   *
   * @param stock    the stock object
   * @param quantity the number of stocks.
   */
  public CumulativeStockTrade(String stock, double quantity) {
    super(stock, quantity);
  }

  @Override
  public LocalDate purchased() throws IllegalArgumentException {
    throw new IllegalArgumentException("Does not support purchases, only for analysis");
  }


  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (!(obj instanceof CumulativeStockTrade)) {
      return false;
    }
    Trade trade = (CumulativeStockTrade) obj;
    return this.get().equals(trade.get());
  }

  @Override
  public String toString() {
    return "Stock Symbol : " + this.stock.toString() + ",Quantity : " + this.quantity + "\n";
  }
}
