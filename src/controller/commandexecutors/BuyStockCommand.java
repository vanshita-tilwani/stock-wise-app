package controller.commandexecutors;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import model.portfolio.Portfolio;
import model.stocktradings.TradeOperation;
import view.View;

/**
 * Implementation of Executor responsible for executing command to purchase a stock and
 * adding it to the Portfolio specified by the User. The Simulated Portfolio does not allow
 * adding Stocks to the Portfolio once created and therefore will accordingly
 * display the message to the User. The message will be displayed to user if the trade to be
 * performed is invalid due to illegal trade date/stock value.
 */
public class BuyStockCommand extends AbstractExecutor {

  @Override
  public void execute(View view, TradeOperation<Portfolio> model) {
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
      model.get(portfolio).buy(stock, shares, date, commission);
      view.display("The purchase was completed successfully!\n");
    } catch (UnsupportedOperationException e) {
      view.display(e.getMessage());
    } catch (IllegalArgumentException e) {
      view.display(e.getMessage());
    }
    catch (DateTimeParseException ex) {
      view.display("The date provided was not in the expected format.\n");
    }
  }
}
