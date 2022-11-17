package controller.commandexecutors;

import java.time.LocalDate;

import view.View;

/**
 * Abstract class responsible for abstracting common code for re-use. Have methods
 * to Read/Display common information from and to the View in the MVC
 * Design Among all the Command Executors.
 */
abstract class AbstractExecutor implements Executor {

  /**
   * Method to read stock symbol from the user.
   *
   * @param view View in MVC design responsible for reading/displaying data.
   * @return The stock symbol.
   */
  protected String readStockSymbol(View view) {
    view.display("Enter the stock symbol\n");
    String stock = view.input();
    return stock;
  }

  /**
   * Method responsible for reading the shares of each stock. Fractional stocks are not allowed.
   *
   * @param view View in MVC design responsible for reading/displaying data.
   * @return the number of shares of a stock.
   */
  protected Double readShares(View view) {
    view.display("Enter the number of shares\n");
    boolean parsed = false;
    Double shares = 0.0;
    while (!parsed) {
      try {
        String input = view.input();
        shares = Double.parseDouble(input);
        parsed = true;
      } catch (NumberFormatException e) {
        view.display("Invalid number of shares\n");
        view.display("Please enter the number of shares again.\n");
      }
    }
    // fractional shares are not allowed
    // If shares are fractional then user is asked to input the number of shares again
    // until he/she enters a whole number.
    while (shares % 1.0 != 0) {
      view.display("Fractional number of shares are not allowed\n");
      view.display("Please enter the number of shares again\n");
      String input = view.input();
      shares = Double.parseDouble(input);
    }
    return shares;
  }

  /**
   * Method responsible to read the number of trades that a portfolio is made up of.
   *
   * @param view View in MVC design responsible for reading/displaying data.
   * @return the number of trades.
   */
  protected int readNumberOfStocks(View view) {
    view.display("Enter the number of stocks you wish to purchase\n");
    int stocks = Integer.parseInt(view.input());
    return stocks;
  }

  /**
   * Reads the date of purchase for a trade.
   *
   * @param view View in MVC design responsible for reading/displaying data.
   * @return the date of purchase of the trade.
   */
  protected LocalDate readDateOfPurchase(View view) {
    view.display("Enter the date of purchase (in YYYY-MM-DD format)\n");
    LocalDate date = LocalDate.parse(view.input());
    return date;
  }

  /**
   * Reads the date of sole for a trade.
   *
   * @param view View in MVC design responsible for reading/displaying data.
   * @return the date of sale of the trade.
   */
  protected LocalDate readDateOfSale(View view) {
    view.display("Enter the date of sale (in YYYY-MM-DD format)\n");
    LocalDate date = LocalDate.parse(view.input());
    return date;
  }

  /**
   * Reads the name of the portfolio.
   *
   * @param view View in MVC design responsible for reading/displaying data.
   * @return the name of the portfolio.
   */
  protected String readTradeName(View view) {
    view.display("Enter the name of the portfolio\n");
    String portfolio = view.input().trim();
    while (portfolio == null || portfolio.isBlank() || portfolio.isEmpty()) {
      view.display("You have entered an invalid name. Please enter the name again\n");
      portfolio = view.input().trim();
    }
    return portfolio;
  }

  /**
   * Reads the date for which the portfolio needs to be evaluated.
   *
   * @param view View in MVC design responsible for reading/displaying data.
   * @return the date at which the portfolio needs to be evaluated.
   */
  protected LocalDate readDate(View view) {
    view.display("Enter the date at which you wish to get the retrieve the information"
            + "(in YYYY-MM-DD format)\n");
    LocalDate date = LocalDate.parse(view.input());
    return date;
  }

  /**
   * Reads the start date for portfolio performance evaluation.
   *
   * @param view View in MVC design responsible for reading/displaying data.
   * @return the date from which the portfolio needs to be analyzed.
   */
  protected LocalDate readFromDate(View view) {
    view.display("Enter the start date for performance evaluation(in YYYY-MM-DD format)\n");
    LocalDate date = LocalDate.parse(view.input());
    return date;
  }

  /**
   * Reads the end date for portfolio performance evaluation.
   *
   * @param view View in MVC design responsible for reading/displaying data.
   * @return the date til which the portfolio needs to be analyzed.
   */
  protected LocalDate readToDate(View view) {
    view.display("Enter the end date for performance evaluation(in YYYY-MM-DD format)\n");
    LocalDate date = LocalDate.parse(view.input());
    return date;
  }

  /**
   * Reads the commission fee for a given trade.
   *
   * @param view View in MVC design responsible for reading/displaying data.
   * @return the commission fee
   */
  protected double readCommissionFee(View view) {
    view.display("Enter the commission fee for this transaction\n");
    double commission = Double.parseDouble(view.input());
    return commission;
  }
}
