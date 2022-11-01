package model.portfolio;

import java.time.LocalDate;
import java.util.List;
import model.stock.Stock;
import model.trade.Trade;

/**
 * Implementation of Portfolio which contains an array of company stocks along with
 * the number of stocks.
 */
public class PortfolioImpl implements Portfolio {
  // name of the portfolio.
  private final String name;

  // collection of shares.
  private final List<Trade<Stock>> shares;

  /**
   * Creates a Portfolio with the portfolio name and collection of
   * shares.
   * @param name portfolio name
   * @param shares collection of shares.
   */
  public PortfolioImpl(String name, List<Trade<Stock>> shares) {
    this.name = name;
    this.shares = shares;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public double value(LocalDate date) {
    double value = 0;
    for(Trade trade : shares) {
      double total = trade.value(date);
      value = value + total;
    }
    return  value;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Portfolio Name : ").append(this.name).append("\n");
    for(Trade share : shares) {
      sb.append(share.toString());
    }
    return sb.toString();
  }
}
