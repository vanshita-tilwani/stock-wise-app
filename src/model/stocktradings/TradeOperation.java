package model.stocktradings;

import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

/**
 * Interface to perform trade operations such as buying trade, getting the
 * trade evaluation on a specific date.
 */
public interface TradeOperation<T> {

  /**
   * Used to create a trade.
   *
   * @param trade The trade object.
   */
  void create(T trade) throws IllegalArgumentException;

  /**
   * Returns the trade object with the specified identifier.
   *
   * @param trade trade object
   * @return formatted string.
   * @throws IllegalArgumentException if the trade does not exist.
   */
  T get(String trade) throws IllegalArgumentException;

  /**
   * Returns the name of all the trades performed by a user.
   *
   * @return All trade names
   */
  Set<String> all();

  /**
   * Returns the value of trade at a specified date for a specified trade.
   *
   * @param date  The date at which trade needs to be evaluated.
   * @param trade The trade which needs to be evaluated
   * @return The evaluation.
   */
  double value(String trade, LocalDate date) throws IllegalArgumentException;


  /**
   * Saves the specified trade in a data-source.
   *
   * @param trade the trade object.
   */
  boolean save(String trade) throws IllegalArgumentException;

  /**
   * Loads all the trades available in the data-source to the application.
   */
  boolean load();

  /**
   * Method responsible for fetching data for analyzing portfolio performance for a specified
   * portfolio over a period of time.
   *
   * @param trade the portfolio for which the performance needs to be analyzed.
   * @param from      the start date of the period.
   * @param to        the end date of the period.
   * @return the portfolio evaluation at multiple dates during the period.
   * @throws IllegalArgumentException if the portfolio does not exist.
   */
  Map<LocalDate, Double> analyze(String trade, LocalDate from, LocalDate to) throws
          IllegalArgumentException;

}
