package model.stocktradings;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Interface to perform trade operations such as buying trade, getting the
 * trade evaluation on a specific date.
 * @param <T> the type of trade i.e(Stock, Portfolio)
 */
public interface TradeOperation<T> {

  /**
   * Used to Buy a trade.
   * @param trade The trade object.
   */
  void buy(T trade) throws IllegalArgumentException;

  /**
   * Returns the value of trade at a date.
   * @param date The date at which trade needs to be evaluated.
   * @param trade The trade which needs to be evaluated
   * @return The evaluation.
   */
  double value(LocalDate date, String trade) throws IllegalArgumentException;

  /**
   * Returns the trade in string format.
   * @param trade trade object
   * @return formatted string.
   * @throws IllegalArgumentException if the portfolio does not exist.
   */
  T get(String trade) throws IllegalArgumentException;

  Set<String> getAllTrades();

  /**
   * Saves the trade in a data-source.
   * @param trade the trade object.
   */
  boolean save(String trade) throws IllegalArgumentException;

  /**
   * Loads all the trades in the data-source to in memory.
   */
  boolean load();
}
