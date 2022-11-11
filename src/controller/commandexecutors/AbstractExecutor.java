package controller.commandexecutors;

import java.time.LocalDate;

import model.stocktradings.TradeOperation;
import view.View;

public abstract class AbstractExecutor implements Executor {

  protected String readStockSymbol(View view) {
    view.display("Enter the stock symbol\n");
    String stock = view.input();
    return stock;
  }

  protected Double readShares(View view) {
    view.display("Enter the number of shares\n");
    String input = view.input();
    Double shares = Double.parseDouble(input);
    // fractional shares are not allowed
    // If shares are fractional then user is asked to input the number of shares again
    // until he/she enters a whole number.
    while (shares % 1.0 != 0) {
      view.display("Fractional number of shares are not allowed\n");
      view.display("Please enter the number of shares again\n");
      input = view.input();
      shares = Double.parseDouble(input);
    }
    return shares;
  }

  protected int readNumberOfStocks(View view) {
    view.display("Enter the number of stocks you wish to purchase\n");
    int stocks = Integer.parseInt(view.input());
    return stocks;
  }

  protected LocalDate readDateOfPurchase(View view) {
    view.display("Enter the date of purchase (in YYYY-MM-DD format)\n");
    LocalDate date = LocalDate.parse(view.input());
    return date;
  }

  protected LocalDate readDateOfSale(View view) {
    view.display("Enter the date of sale (in YYYY-MM-DD format)\n");
    LocalDate date = LocalDate.parse(view.input());
    return date;
  }

  protected String readTradeName(View view) {
    view.display("Enter the name of the portfolio\n");
    String portfolio = view.input();
    return portfolio;
  }
}
