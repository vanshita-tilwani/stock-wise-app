package controller.commandexecutors;

import java.time.LocalDate;

import model.stocktradings.PortfolioTradeOperation;
import view.View;

/**
 * Implementation of executor responsible for selling the stocks
 * in a portfolio.
 */
public class SellStock extends AbstractExecutor {

  @Override
  public void execute(View view, PortfolioTradeOperation model) {
    try {
      // read the portfolio whose stocks you wish to sell.
      String portfolio = this.readTradeName(view);
      // read the stock that you wish to sell
      String stock = this.readStockSymbol(view);
      // read the number of shares of a stock that you wish to sell.
      Double shares = this.readShares(view);
      // read the date of sale.
      LocalDate date = this.readDateOfSale(view);
      // reads the commission fee for the trade.
      Double commission = this.readCommissionFee(view);
      // sell the stock from the given portfolio
      model.sellStock(portfolio, stock, shares, date, commission);
      view.display("The sale was completed successfully!\n");
    } catch (UnsupportedOperationException e) {
      view.display(e.getMessage());
    }
  }
}
