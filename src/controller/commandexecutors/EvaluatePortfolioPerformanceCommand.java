package controller.commandexecutors;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import model.portfolio.Portfolio;
import model.stocktradings.TradeOperation;
import view.View;

/**
 * Implementation of Command Executor responsible for evaluating Portfolio Performance
 * for a Portfolio over a range of interval specified by the User.
 * Portfolio Performance takes in account the portfolio composition at each time interval
 * along with the trade value during the period for Transactional Portfolio.
 * For Simulated Portfolio, the portfolio composition remains essentially same during the
 * interval and majorly factors in the price fluctuations of combinations of stocks during
 * the interval.
 */
public class EvaluatePortfolioPerformanceCommand extends AbstractExecutor {

  @Override
  public void execute(View view, TradeOperation<Portfolio> model) {
    try {
      // read the name of the portfolio.
      String portfolio = this.readTradeName(view);
      // read the date from which you wish to visualize performance.
      LocalDate from = this.readFromDate(view);
      // read the date till which you wish to visualize performance.
      LocalDate to = this.readToDate(view);
      // returns the composition and displays it.
      view.display("Performance of portfolio " + portfolio + " from " + from + " to " + to + "\n");
      view.draw(model.get(portfolio).values(from, to));
    } catch (IllegalArgumentException e) {
      view.display(e.getMessage());
    }
    catch (DateTimeParseException ex) {
      view.display("The date provided was not in the expected format.\n");
    }
  }
}
