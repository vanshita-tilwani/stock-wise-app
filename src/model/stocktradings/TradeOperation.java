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
