package model.portfolio;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.stock.Stock;
import model.trade.Trade;
import model.utility.CalendarUtility;

/**
 * Abstract Class to abstract out the Portfolio details pertaining to all
 * the different types of Portfolio.
 */
abstract class AbstractPortfolio implements Portfolio {

  //name of the portfolio
  protected final String name;

  /**
   * Creates an instance of a portfolio and sets the name of the type.
   * Abstract class so this will only be called by concrete implementation.
   *
   * @param name name of the portfolio
   */
  protected AbstractPortfolio(String name) {
    this.name = name;
  }

  @Override
  public String name() {
    return this.name;
  }

  @Override
  public Map<LocalDate, Double> analyze(LocalDate from, LocalDate to) {
    Map<LocalDate, Double> values = new HashMap<>();
    List<LocalDate> dates = CalendarUtility.getWorkingDays(from, to);
    for (LocalDate date : dates) {
      values.put(date, this.value(date));
    }
    return values;
  }

  @Override
  public String toString() {
    // Prepares the formatted string for Portfolio.
    return "Portfolio Name : " + this.name + "\n";
  }

  /**
   * Evaluates the value of the set of trades at a date.
   *
   * @param shares the set of aggregated shares comprising portfolio.
   * @param date   the date of evaluation.
   * @return the value of evaluation.
   */
  protected double evaluateValue(Set<Trade<Stock>> shares, LocalDate date) {
    // Adds the value of each trade to calculate the total portfolio value
    double value = 0;
    for (Trade trade : shares) {
      double total = trade.value(date);
      value = value + total;
    }
    return value;
  }

  /**
   * Returns the aggregated composition of shares in a portfolio.
   *
   * @param shares the set of aggregated shares comprising portfolio.
   * @return the portfolio composition.
   */
  protected String getComposition(Set<Trade<Stock>> shares) {
    StringBuilder sb = new StringBuilder().append("TYPE : MASTER\n");
    sb.append(super.toString());
    sb.append("STOCKS : \n");
    for (Trade share : shares) {
      sb.append(share.toString());
    }
    sb.append("------ END ------\n");
    return sb.toString();
  }

}
