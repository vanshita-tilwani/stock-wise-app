package controller.commandexecutors;

import model.portfolio.Portfolio;
import model.stocktradings.TradeOperation;
import view.View;

/**
 * Implementation of executor responsible for getting the portfolio composition.
 */
public class PortfolioCompositionCommand extends AbstractExecutor {
  @Override
  public void execute(View view, TradeOperation<Portfolio> model) {
    try {
      // read the name of the portfolio.
      String portfolio = this.readTradeName(view);
      // returns the composition and displays it.
      view.display(model.get(portfolio).composition());
    } catch (IllegalArgumentException e) {
      view.display(e.getMessage());
    }
  }
}
