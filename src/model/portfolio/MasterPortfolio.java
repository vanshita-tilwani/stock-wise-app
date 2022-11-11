package model.portfolio;

import java.time.LocalDate;
import java.util.Set;
import model.stock.Stock;
import model.stockpriceprovider.StockDataProvider;
import model.trade.Trade;

/**
 * Implementation of Portfolio which contains an array of company stocks along with
 * the number of stocks. This type of portfolio is Inflexible and therefore does not allow
 * any addition of stocks after creation.
 */
public class MasterPortfolio extends AbstractPortfolio {

  // collection of shares
  protected Set<Trade<Stock>> shares;
  /**
   * Creates an Inflexible Portfolio with the portfolio name and collection of
   * shares.
   *
   * @param name   portfolio name
   * @param shares collection of shares.
   */
  public MasterPortfolio(String name, Set<Trade<Stock>> shares) {
    super(name);
    this.shares = shares;
  }

  @Override
  public double value(LocalDate date) {
    // Adds the value of each trade to calculate the total portfolio value
    double value = 0;
    for (Trade trade : shares) {
      double total = trade.value(date);
      value = value + total;
    }
    return value;
  }

  @Override
  public void buy(String stock, Double shares, LocalDate date) throws IllegalArgumentException {
    throw new IllegalArgumentException("Purchasing a new Stock is not allowed in this portfolio\n");
  }

  @Override
  public void sell(String stock, Double shares, LocalDate date) throws IllegalArgumentException {
    throw new IllegalArgumentException("Selling an existing Stock is not allowed in this portfolio\n");
  }

  @Override
  public double cost(LocalDate date) {
    // TODO : implement
    return 0;
  }

  @Override
  public String toString() {
    // Prepares the formatted string for Portfolio.
    StringBuilder sb = new StringBuilder(super.toString());
    for (Trade share : shares) {
      sb.append(share.toString());
    }
    return sb.toString();
  }


}
