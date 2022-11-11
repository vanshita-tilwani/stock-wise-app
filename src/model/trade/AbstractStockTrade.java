package model.trade;

import java.time.LocalDate;

import model.stock.Stock;
import model.stock.StockImpl;
import model.stockpriceprovider.MockStockDataProvider;
import model.stockpriceprovider.StockDataProvider;

public abstract class AbstractStockTrade implements Trade<Stock> {

  protected final Stock stock;
  protected double quantity;

  protected AbstractStockTrade(String stock, double quantity) {
    this.stock = new StockImpl(stock);
    this.quantity = quantity;
  }

  @Override
  public Stock get() {
    return this.stock;
  }

  @Override
  public double value(LocalDate date) {
    return this.stock.price(date) * this.quantity;
  }

  @Override
  public double quantity() {
    return this.quantity;
  }

  @Override
  public void buy(double quantity) {
    this.quantity = this.quantity + quantity;
  }

  @Override
  public void sell(double quantity) {
    if(this.quantity < quantity) {
      throw new IllegalArgumentException("Not enough shares to sell");
    }
    this.quantity = this.quantity - quantity;
  }

}
