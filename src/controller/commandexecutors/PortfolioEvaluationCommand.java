package controller.commandexecutors;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import model.portfolio.Portfolio;
import model.stocktradings.TradeOperation;
import view.View;

/**
 * Implementation of Executor responsible for fetching the Total Value of the portfolio
 * at the date specified by the User.
 * For Simulated Portfolio, the value only depends on the stock prices for the specified
 * date however for transactional portfolios, the transaction date along with the stock
 * price combine to evaluate the total value of the portfolio at any given date.
 */
public class PortfolioEvaluationCommand extends AbstractExecutor {
  @Override
  public void execute(View view, TradeOperation<Portfolio> model) {
    try {
      // reads the name of the portfolio.
      String portfolio = this.readTradeName(view);
      // reads the date of evaluation for the portfolio.
      LocalDate date = this.readDate(view);
      // gets the total value of portfolio and displays it.
      view.display("The value of portfolio is $" + model.get(portfolio).value(date) + "\n");
    } catch (IllegalArgumentException e) {
      view.display(e.getMessage());
    } catch (DateTimeParseException ex) {
      view.display("The date provided was not in the expected format.\n");
    }
  }
}
