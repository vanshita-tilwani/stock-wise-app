package model.trade;

import java.util.Date;

import model.stock.Stock;

/**
 * Represents a stock trade made by any User.
 */
public interface ITrade {

  /**
   * Returns the stock which was traded(purchased/sold) by a user.
   * @return Stock.
   */
  Stock stock();

  /**
   * Returns the date of the purchase of the Stock.
   * @return purchase date of the stock
   */
  Date purchaseDate();

  /**
   * Returns the number/amount of the stock traded by a user.
   * @return the number of stock
   */
  double count();
}
