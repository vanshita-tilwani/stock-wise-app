package model.stocktradings;

import java.time.LocalDate;
import java.util.Set;

/**
 * Interface to perform trade operations such as buying trade, getting the
 * trade evaluation on a specific date.
 *
 * @param <T> the type of trade i.e(Stock, Portfolio)
 */
public interface TradeOperation<T> {

  /**
   * Used to create a trade.
   *
   * @param trade The trade object.
   */
  void create(T trade) throws IllegalArgumentException;

  /**
   * Returns the value of trade at a specified date for a specified trade.
   *
   * @param date  The date at which trade needs to be evaluated.
   * @param trade The trade which needs to be evaluated
   * @return The evaluation.
   */
  double value(LocalDate date, String trade) throws IllegalArgumentException;

  /**
   * Returns the trade composition after all the transactions for a specified trade.
   *
   * @param trade trade object
   * @return formatted string.
   * @throws IllegalArgumentException if the trade does not exist.
   */
  T get(String trade) throws IllegalArgumentException;

  /**
   * Returns the trade composition at a specified date for a specified trade.
   *
   * @param trade the trade who composition needs to be determined.
   * @param date the date at which the trade composition needs to be determined.
   * @return returns the composition of the specified trade at a specified date.
   * @throws IllegalArgumentException if the trade does not exist.
   */
  T get(String trade, LocalDate date) throws IllegalArgumentException;

  /**
   * Returns the name of all the trades performed by a user.
   *
   * @return All trade names
   */
  Set<String> getAllTrades();

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

}
