package controller.commandexecutors;

import java.time.LocalDate;
import model.stocktradings.PortfolioTradeOperation;
import view.View;

/**
 * Implementation of executor responsible for evaluating the cost basis (total money
 * invested) of a portfolio.
 */
public class EvaluateCostBasis extends AbstractExecutor {

  @Override
  public void execute(View view, PortfolioTradeOperation model) {
    try {
      // read the name of the portfolio.
      String portfolio = this.readTradeName(view);
      // read the date at which you wish to determine the cost basis.
      LocalDate date = this.readDate(view);
      // returns the composition and displays it.
      view.display("The cost basis for the portfolio is " + model.get(portfolio).costBasis(date));
    } catch (UnsupportedOperationException e) {
      view.display(e.getMessage());
    }
  }
}
