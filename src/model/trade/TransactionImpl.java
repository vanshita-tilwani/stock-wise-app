package model.trade;

import java.time.LocalDate;
import java.util.Date;

import model.stock.Stock;
import model.stock.StockImpl;
import model.stockpriceprovider.RandomStockPriceProvider;

public class TransactionImpl implements Transaction {

  private final Stock stock;
  private final double quantity;
  private final LocalDate purchaseDate;

  public TransactionImpl(String stock, double quantity) {
    this.stock = new StockImpl(stock, new RandomStockPriceProvider());
    this.quantity = quantity;
    this.purchaseDate = LocalDate.now();
  }

  @Override
  public Stock stock() {
    return this.stock;
  }

  @Override
  public LocalDate purchaseDate() {
    return this.purchaseDate;
  }

  @Override
  public double count() {
    return this.quantity;
  }

  @Override
  public String toString() {
    return stock.toString() + " has " + this.quantity + " holding purchased on " + this.purchaseDate;
  }
}
