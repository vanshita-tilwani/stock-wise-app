package model.stockpriceprovider;

import java.time.LocalDate;
import model.stock.Stock;

/**
 * An interface used to determine the price of any stock.
 */
public interface StockPriceProvider {

  /**
   * Returns the price of the given stock on the given date.
   * @param stock stock whose price needs to be determined.
   * @param date the date at which the price needs to be evaluated.
   * @return the price of stock.
   */
  double price(Stock stock, LocalDate date);
}
