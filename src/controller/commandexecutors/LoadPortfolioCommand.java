package controller.commandexecutors;

import model.portfolio.Portfolio;
import model.stocktradings.TradeOperation;
import view.View;

/**
 * Implementation of executor responsible for loading all the portfolios in the
 * data source to the application.
 */
public class LoadPortfolioCommand extends AbstractExecutor {

  @Override
  public void execute(View view, TradeOperation<Portfolio> model) {
    // loads the portfolios from data source to application.
    boolean result = model.load();
    if (result) {
      view.display("The load of portfolio is successfully completed from portfolio.txt "
             + "from the res folder in your current working directory!\n");
    } else {
      view.display("There were issues with portfolio load. Please make sure the file is in"
             + "expected format.\n");
    }
  }
}
