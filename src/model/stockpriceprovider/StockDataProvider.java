package model.stockpriceprovider;

import java.time.LocalDate;

import model.stock.Stock;

/**
 * An interface works as a data provider for any stock.
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

  /**
   * Checks if the stock symbol is valid.
   *
   * @param stock stock
   * @return true/false
   */
  boolean isValid(String stock);

  boolean isAvailable(String stock, LocalDate date);

}
