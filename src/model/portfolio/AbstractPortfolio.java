package model.portfolio;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import model.stock.Stock;
import model.stockpriceprovider.StockDataProvider;
import model.trade.Trade;

/**
 * Abstract Class to abstract out the Portfolio details pertaining to all
 * the different types of Portfolio.
 */
public abstract class AbstractPortfolio implements Portfolio {

  //name of the portfolio
  protected final String name;

  protected AbstractPortfolio(String name) {
    this.name = name;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public String toString() {
    // Prepares the formatted string for Portfolio.
    return "Portfolio Name : " + this.name + "\n";
  }

}
