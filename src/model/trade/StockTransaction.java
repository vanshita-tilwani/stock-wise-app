package model.trade;

import java.time.LocalDate;

import model.stockpriceprovider.StockDataProvider;

public class StockTransaction extends AbstractStockTrade {

  private final LocalDate purchased;

  public StockTransaction(String stock, double quantity, LocalDate purchased) {
    super(stock, quantity);
    this.purchased = purchased;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    } else if (!(obj instanceof StockTransaction)) {
      return false;
    }
    Trade trade = (StockTransaction) obj;
    return this.get().equals(trade.get()) && this.purchased.equals(trade.purchased());
  }

  @Override
  public String toString() {
    return "Stock Symbol : " + this.stock.toString() + ",Quantity : " + this.quantity +
            ",Date of Purchase : " + this.purchased +"\n";
  }

  @Override
  public LocalDate purchased() throws IllegalArgumentException {
    return this.purchased;
  }
}
