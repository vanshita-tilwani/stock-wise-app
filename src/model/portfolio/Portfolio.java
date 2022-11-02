package model.portfolio;

import java.time.LocalDate;
import java.util.List;

import model.stock.Stock;
import model.trade.Trade;

/**
 * Represents a collection of Stock trades.
 */
public interface Portfolio {

  /**
   * Returns the name of the Portfolio.
   * @return name
   */
  String name();

  /**
   * Returns the value of a Portfolio on a given date.
   * @param date the date on which the value needs to be determined.
   * @return the value.
   */
  double value(LocalDate date);
}
