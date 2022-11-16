package controller.commandexecutors;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import model.portfolio.Portfolio;
import model.stocktradings.TradeOperation;
import view.View;

/**
 * Implementation of Command Executor responsible for evaluating Cost basis for a Portfolio.
 * Cost basis is the total money invested in the portfolio for all the purchases along with
 * the commission fee for each stock transaction.
 */
public class EvaluateCostBasisCommand extends AbstractExecutor {

  @Override
  public void execute(View view, TradeOperation<Portfolio> model) {
    try {
      // read the name of the portfolio.
      String portfolio = this.readTradeName(view);
      // read the date at which you wish to determine the cost basis.
      LocalDate date = this.readDate(view);
      // returns the composition and displays it.
      view.display("The cost basis for the portfolio is $" + model.get(portfolio).costBasis(date)
              + "\n");
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
