package model.stock;

import java.time.LocalDate;
import java.util.Date;

import model.stockpriceprovider.IStockPriceProvider;
import model.stockpriceprovider.RandomStockPriceProvider;

/**
 * Represents a Regular Stock.
 */
public class Stock implements IStock {

  private final String name;
  private final String company;
  private final IStockPriceProvider stockPriceProvider;
  public Stock(String name, String company, IStockPriceProvider stockPriceProvider) {
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
