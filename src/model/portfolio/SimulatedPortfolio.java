package model.portfolio;

import java.time.LocalDate;
import java.util.Set;

import model.stock.Stock;
import model.trade.PortfolioType;
import model.trade.Trade;

/**
 * Implementation of Portfolio which contains an array of company stocks along with
 * the number of stocks. This type of portfolio is Inflexible and therefore does not allow
 * any addition of stocks after creation.
 */
public class SimulatedPortfolio extends AbstractPortfolio {

  // collection of shares
  protected Set<Trade<Stock>> shares;

  /**
   * Creates an Inflexible Portfolio with the portfolio name and collection of
   * shares.
   *
   * @param name   portfolio name
   * @param shares collection of shares.
   */
  public SimulatedPortfolio(String name, Set<Trade<Stock>> shares) {
    super(name);
    this.shares = shares;
  }

  @Override
  public double value(LocalDate date) {
    if (date.isAfter(LocalDate.now())) {
      throw new IllegalArgumentException("The entered date is in future.\n");
    }
    return this.evaluateValue(this.shares, date);
  }

  @Override
  public void add(String stock, Double shares, LocalDate date, Double commission)
          throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Purchasing a new Stock is not allowed "
            + "in this portfolio\n");
  }

  @Override
  public void sell(String stock, Double shares, LocalDate date, Double commission)
          throws UnsupportedOperationException {
    throw new UnsupportedOperationException("Selling an existing Stock is not allowed "
            + "in Simulated Portfolio\n");
  }

  @Override
  public double costBasis(LocalDate date) throws UnsupportedOperationException {
    throw new UnsupportedOperationException("The cost basis cannot be evaluated for Simulated"
            + "Portfolio.\n");
  }

  @Override
  public String composition() {
    return this.getComposition(PortfolioType.SIMULATED, this.shares);
  }

  @Override
  public String composition(LocalDate date) throws UnsupportedOperationException {
    return this.getComposition(PortfolioType.SIMULATED, this.shares);
  }


  @Override
  public String toString() {

    return this.getComposition(PortfolioType.SIMULATED, this.shares);
  }


}
