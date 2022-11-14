package controller.commandexecutors;

import java.time.LocalDate;

import model.stocktradings.PortfolioTradeOperation;
import view.View;

/**
 * Implementation of executor responsible for buying stocks for a portfolio
 * in the application.
 */
public class BuyStock extends AbstractExecutor {

  @Override
  public void execute(View view, PortfolioTradeOperation model) {
    try {
      // reads the name of the portfolio.
      String portfolio = this.readTradeName(view);
      // reads the stock symbol.
      String stock = this.readStockSymbol(view);
      // reads the number of the shares.
      Double shares = this.readShares(view);
      // reads the date of purchase of the trade.
      LocalDate date = this.readDateOfPurchase(view);
      // reads the commission fee for the trade.
      Double commission = this.readCommissionFee(view);
      // buys the trade and adds it in portfolio.
      model.buyStock(portfolio, stock, shares, date, commission);
      view.display("The purchase was completed successfully!\n");
    } catch (UnsupportedOperationException e) {
      view.display(e.getMessage());
    }
  }
}
