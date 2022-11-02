package model.stockpriceprovider;

import java.time.LocalDate;

/**
 * An interface used to determine the price of any stock.
 */
public interface StockDataProvider {

  /**
   * Returns the price of the given stock on the given date.
   *
   * @param stock stock whose price needs to be determined.
   * @param date  the date at which the price needs to be evaluated.
   * @return the price of stock.
   */
  double price(String stock, LocalDate date) throws IllegalArgumentException;

  boolean isValid(String stock);

}
