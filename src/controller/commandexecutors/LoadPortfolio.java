package controller.commandexecutors;

import model.stocktradings.PortfolioTradeOperation;
import view.View;

/**
 * Implementation of executor responsible for loading all the portfolios in the
 * data source to the application.
 */
public class LoadPortfolio extends AbstractExecutor {

  @Override
  public void execute(View view, PortfolioTradeOperation model) {
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
