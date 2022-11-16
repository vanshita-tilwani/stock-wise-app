package controller.commandexecutors;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import model.portfolio.Portfolio;
import model.stocktradings.TradeOperation;
import view.View;

/**
 * Implementation of the Executor responsible for Selling the stocks from
 * the portfolio. This operation is not supported for Simulated Portfolio type
 * since they do not support trading options.
 * For Transactional Portfolio, the trade operations can be performed given the
 * portfolio contains enough shares of the stock to be sold on the date of sale
 * specified by the user.
 */
public class SellStockCommand extends AbstractExecutor {

  @Override
  public void execute(View view, TradeOperation<Portfolio> model) {
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
      model.get(portfolio).sell(stock, shares, date, commission);
      view.display("The sale was completed successfully!\n");
    } catch (UnsupportedOperationException e) {
      view.display(e.getMessage());
    }
    catch (IllegalArgumentException e) {
      view.display(e.getMessage());
    }
    catch (DateTimeParseException ex) {
      view.display("The date provided was not in the expected format.\n");
    }
  }
}
