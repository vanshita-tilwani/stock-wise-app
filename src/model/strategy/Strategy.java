package model.strategy;

import java.time.LocalDate;
import java.util.Map;


/**
 * An interface to Implement the strategies.
 */
public interface Strategy {

  /**
   * To return the investment.
   * @return
   */
  Double principle();

  /**
   * To return proportion of the stocks.
   * @return proportion of the stocks
   */
  Map<String, Double> stockProportion();

  /**
   * To return the start date.
   * @return returns the start date
   */
  LocalDate startDate();

  /**
   * To return the end date.
   * @return returns the end date.
   */
  LocalDate endDate();

  /**
   * To return the frequency.
   * @return returns the frequency.
   */
  int frequency();

  /**
   * To return the commission.
   * @return returns the commission.
   */
  double commission();
}
