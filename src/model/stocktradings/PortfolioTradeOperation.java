package model.stocktradings;

import java.time.LocalDate;
import java.util.Map;

import model.portfolio.Portfolio;

/**
 * Interface responsible for trading on Portfolio. Extends TradeOperation Portfolio
 * which support common operations among all the trades.
 */
public interface PortfolioTradeOperation extends TradeOperation<Portfolio> {

  /**
   * Method responsible for Buying a specific stock and adding it in the specified portfolio.
   *
   * @param portfolio  the name of the portfolio to add the purchase in.
   * @param stock      the stock that is to be purchased.
   * @param shares     the number of shares of the stocks that needs to be purchased.
   * @param date       the date of purchase.
   * @param commission the commission fee against the transaction.
   * @throws IllegalArgumentException      if the portfolio does not exist.
   * @throws UnsupportedOperationException if the portfolio does not support adding more stocks
   *                                       to an existing portfolio.
   */
  void buyStock(String portfolio, String stock, Double shares, LocalDate date, Double commission)
          throws IllegalArgumentException, UnsupportedOperationException;

  /**
   * Method responsible for Selling a specific stock from the specified portfolio.
   *
   * @param portfolio  the name of the portfolio to sell the stock from.
   * @param stock      the stock that is to be sold.
   * @param shares     the number of shares of the stocks that needs to be sold.
   * @param date       the date of sale.
   * @param commission the commission fee against the transaction.
   * @throws IllegalArgumentException      if the portfolio does not exist.
   * @throws UnsupportedOperationException if the portfolio does not support sale of stocks
   *                                       from an existing portfolio.
   */
  void sellStock(String portfolio, String stock, Double shares, LocalDate date, Double commission)
          throws IllegalArgumentException, UnsupportedOperationException;

  /**
   * Method responsible for fetching data for analyzing portfolio performance for a specified
   * portfolio over a period of time.
   *
   * @param portfolio the portfolio for which the performance needs to be analyzed.
   * @param from      the start date of the period.
   * @param to        the end date of the period.
   * @return the portfolio evaluation at multiple dates during the period.
   * @throws IllegalArgumentException if the portfolio does not exist.
   */
  Map<LocalDate, Double> analyzePortfolio(String portfolio, LocalDate from, LocalDate to) throws
          IllegalArgumentException;
}
