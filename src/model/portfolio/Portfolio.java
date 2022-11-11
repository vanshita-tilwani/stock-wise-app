package model.portfolio;

import java.time.LocalDate;

import model.trade.Trade;

/**
 * Represents a collection of Stock trades.
 */
public interface Portfolio {

  /**
   * Returns the name of the Portfolio.
   *
   * @return name
   */
  String name();

  /**
   * Returns the value of a Portfolio on a given date.
   *
   * @param date the date on which the value needs to be determined.
   * @return the value.
   */
  double value(LocalDate date);

  /**
   * To add a share of a stock to the Portfolio after it has been created.
   *
   * @param stock the stock that the user wishes to buy.
   * @param shares the amount of shares of the stock.
   * @param date the date of purchase.
   * @throws IllegalArgumentException if the operation is invalid.
   */
  void buy(String stock, Double shares, LocalDate date) throws IllegalArgumentException;

  /**
   * To sell a shares of a stock after it has been created.
   *
   * @param stock the stock that the user wishes to sell.
   * @param shares the amount of shares of the stock.
   * @param date the date of purchase.
   * @throws IllegalArgumentException if the operation is invalid.
   */
  void sell(String stock, Double shares, LocalDate date) throws IllegalArgumentException;

  /**
   * Returns the total money invested in the Portfolio at any time.
   *
   * @param date the date at which you need to evaluate total invested amount.
   * @return the total invested amount.
   */
  double cost(LocalDate date);

}
