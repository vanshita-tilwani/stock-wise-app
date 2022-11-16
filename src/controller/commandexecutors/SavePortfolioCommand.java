package controller.commandexecutors;

import model.portfolio.Portfolio;
import model.stocktradings.TradeOperation;
import view.View;

/**
 * Implementation of Executor responsible for saving the portfolio specified
 * by the user to the data source. The data source can be passed to the model.
 * For this specific feature, the executor uses file as the data source for
 * the portfolio to be saved.
 */
public class SavePortfolioCommand extends AbstractExecutor {

  @Override
  public void execute(View view, TradeOperation<Portfolio> model) {
    // read name of the portfolio which you wish to save.
    String portfolio = this.readTradeName(view);
    // save the portfolio
    Boolean result = model.save(portfolio);
    if (result) {
      view.display("The portfolio saved to file successfully to portfolio.txt in res folder in"
              + " your current working directory!\n");
    } else {
      view.display("The save could not be completed. Please make sure the portfolio name"
              + " is entered correctly and the data source(file) exist.\n");
    }
  }
}
