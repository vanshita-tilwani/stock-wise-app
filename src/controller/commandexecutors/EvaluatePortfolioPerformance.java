package controller.commandexecutors;

import java.time.LocalDate;

import model.stocktradings.PortfolioTradeOperation;
import view.View;

/**
 * Implementation of executor responsible for evaluating the performance of the portfolio
 * over a period of time.
 */
public class EvaluatePortfolioPerformance extends AbstractExecutor {

  @Override
  public void execute(View view, PortfolioTradeOperation model) {
    try {
      // read the name of the portfolio.
      String portfolio = this.readTradeName(view);
      // read the date from which you wish to visualize performance.
      LocalDate from = this.readFromDate(view);
      // read the date till which you wish to visualize performance.
      LocalDate to = this.readToDate(view);
      // returns the composition and displays it.
      view.display("Performance of portfolio " + portfolio + " from " + from + " to " + to + "\n");
      view.draw(model.analyzePortfolio(portfolio, from, to));
    } catch (UnsupportedOperationException e) {
      view.display(e.getMessage());
    }
  }
}
