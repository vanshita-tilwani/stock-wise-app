package model.portfolio;

import java.time.LocalDate;
import java.util.Map;

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
   * @param commission the commission fee involved in the trade.
   * @throws IllegalArgumentException if the operation is invalid.
   */
  void add(String stock, Double shares, LocalDate date, Double commission)
          throws UnsupportedOperationException;

  /**
   * To sell a shares of a stock after it has been created.
   *
   * @param stock the stock that the user wishes to sell.
   * @param shares the amount of shares of the stock.
   * @param date the date of purchase.
   * @param commission the commission fee involved in the trade.
   * @throws IllegalArgumentException if the operation is invalid.
   */
  void sell(String stock, Double shares, LocalDate date, Double commission)
          throws UnsupportedOperationException;

  /**
   * Returns the total money invested in the Portfolio at any time.
   *
   * @param date the date at which you need to evaluate total invested amount.
   * @return the total invested amount.
   */
  double costBasis(LocalDate date) throws UnsupportedOperationException;

  /**
   * Returns the aggregated composition of a Portfolio (i.e. describing each stock with the number
   * of shares that a portfolio contains) at a date later than all the trades in the portfolio.
   *
   * @return composition of portfolio.
   */
  String composition();

  /**
   * Returns the aggregated composition of a Portfolio (i.e. describing each stock with the number
   * of shares that a portfolio contains) at a specified date.
   *
   * @param date the date at which you want to evaluate portfolio composition.
   * @return the composition of a portfolio.
   */
  String composition(LocalDate date) throws UnsupportedOperationException;

  /**
   * Returns all value of portfolio within the time period specified in the argument.
   *
   * @param from the start date of the time period.
   * @param to the last date of the time period.
   * @return the value of portfolio at multiple dates.
   */
  Map<LocalDate, Double> values(LocalDate from, LocalDate to) throws IllegalArgumentException;

}
