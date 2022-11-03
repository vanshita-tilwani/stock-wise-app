package model.trade;

import java.time.LocalDate;

/**
 * Represents a trade(i.e a transaction).
 */
public interface Trade<T> {

  /**
   * Returns the trade(i.e. stock or other types) which was traded(purchased/sold) by a user.
   *
   * @return Stock.
   */
  T get();

  /**
   * Returns the value of a trade performed by the user at any given date.
   *
   * @param date The date at which
   * @return
   */
  double value(LocalDate date);

}
